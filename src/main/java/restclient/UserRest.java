package restclient;

import entities.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import service.LoanService;
import service.SecurityUtil;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;

@Path("user")
public class UserRest {

    @Inject
    private SecurityUtil securityUtil;

    @Context
    private UriInfo uriInfo;

    @Inject
    private LoanService loanService;

    @Path("login")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response login(@NotNull @FormParam("email") String email, @NotNull @FormParam("password") String password){

        boolean authenticated = securityUtil.authenticateUser(email, password);
        if(!authenticated){
            throw new NotAuthorizedException("Email or password not valid");
        }

        String token = generateToken(email);

        return Response.ok().header(HttpHeaders.AUTHORIZATION, SecurityUtil.BEARER + " " + token).build();
    }

    private String generateToken(String email){
        Key securityKey = securityUtil.getSecurityKey();
        return Jwts.builder().setSubject(email).setIssuedAt(new Date()).setIssuer(uriInfo.getBaseUri().toString())
                .setAudience(uriInfo.getAbsolutePath().toString())
                .setExpiration(securityUtil.toDate(LocalDateTime.now().plusMinutes(15)))
                .signWith(SignatureAlgorithm.HS512, securityKey).compact();
    }

    @POST
    @Path("createuser")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(@NotNull User user){
        loanService.saveUser(user);
        return Response.ok(user).build();

    }








}
