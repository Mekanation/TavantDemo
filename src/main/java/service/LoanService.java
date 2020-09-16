package service;

import entities.Loan;
import entities.User;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import java.util.List;
import java.util.Map;

@Stateless
public class LoanService {

    @Inject
    private EntityManager entityManager;

    @Inject
    private QueryService queryService;

    @Inject
    private SecurityUtil securityUtil;

    @Context
    private SecurityContext securityContext;

    public User saveUser(User user){
        Long count = (Long) queryService.countUserByEmail(user.getEmail()).get(0);

        if(user.getId() == null && count == 0){
            Map<String, String> credMap = securityUtil.hashPassword(user.getPassword());
            user.setPassword(credMap.get(SecurityUtil.HASHED_PASSWORD_KEY));
            user.setSalt(credMap.get(SecurityUtil.SALT_KEY));

            entityManager.persist(user);
            credMap.clear();
        }
        return user;
    }

    public Loan createLoan(Loan loan){
        User userByEmail = queryService.findUserByEmail(securityContext.getUserPrincipal().getName());

        if(userByEmail != null) {
            loan.setLoanRequester(userByEmail);
            entityManager.persist(loan);
        }
        return loan;
    }

    public Loan updateLoan(Loan loan){
        entityManager.merge(loan);
        return loan;
    }

    public Loan getLoanById(Long id) { return queryService.getLoanById(id);}
    public List<Loan> getLoans(){return queryService.getAllLoans();}






}
