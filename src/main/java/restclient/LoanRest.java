package restclient;

import entities.Loan;
import service.LoanService;
import service.QueryService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("Loan")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Authz
public class LoanRest {

    @Inject
    LoanService loanService;

    @Inject
    QueryService queryService;

    @Path("new")
    @POST
    public Response createLoan(Loan loan){
        loanService.createLoan(loan);
        return Response.ok(loan).build();
    }

    @Path("{id}")
    @GET
    public Loan getLoan(@PathParam("id") Long id){
        return queryService.getLoanById(id);
    }

    @Path("all")
    @GET
    public List<Loan> getLoans(){
        return queryService.getAllLoans();
    }

    @Path("approve")
    @POST
    public Response approveLoan(@QueryParam("id") Long id){
        Loan loan = loanService.getLoanById(id);
        loan.setApprovalStatus(true);
        loanService.updateLoan(loan);

        return Response.ok(loan).build();


    }

    @Path("deny")
    @POST
    public Response denyLoan(@QueryParam("id") Long id){
        Loan loan = loanService.getLoanById(id);
        loan.setApprovalStatus(false);
        loanService.updateLoan(loan);

        return Response.ok(loan).build();


    }
}
