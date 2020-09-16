package service;

import entities.Loan;
import entities.User;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import java.util.List;

@Stateless
public class QueryService {

    @Inject
    EntityManager entityManager;

    @Inject
    private SecurityUtil securityUtil;

    @Context
    private SecurityContext securityContext;

    public User findUserByEmail(String email){

        List<User> userList = entityManager.createNamedQuery(User.FIND_USER_BY_EMAIl, User.class).setParameter("email", email)
                .getResultList();

        if(!userList.isEmpty()){
            return userList.get(0);
        }
        return null;
    }

    public List countUserByEmail(String email){
        return entityManager.createNativeQuery("select count(id) from Users where exists (select id from Users where email = ?)")
                .setParameter(1, email).getResultList();
    }

    public List<Loan> getAllLoans() {
        return entityManager.createNamedQuery(Loan.GET_ALL_LOANS_BY_OWNER, Loan.class).setParameter("email",
                securityContext.getUserPrincipal().getName()).getResultList();
    }


    public Loan getLoanById(Long id) {
        List<Loan> result = entityManager.createNamedQuery(Loan.GET_LOAN_BY_ID, Loan.class)
                .setParameter("id", id)
                .setParameter("email", securityContext.getUserPrincipal()).getResultList();
        if(!result.isEmpty()){
            return result.get(0);
        }
        return null;
    }


}
