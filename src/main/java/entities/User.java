package entities;


import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.*;

@Entity
@Table(name = "Users")
@NamedQuery(name = User.FIND_ALL_USERS, query = "select u from User u order by u.lastname")
@NamedQuery(name = User.FIND_USER_BY_EMAIl, query = "select u from User u where u.email = :email")
@NamedQuery(name = User.FIND_USER_BY_PASSWORD, query = "select u from User u where u.password = :password")
public class User extends AbstractEntity{

    public static final String FIND_ALL_USERS = "User.findAllUsers";
    public static final String FIND_USER_BY_EMAIl = "User.findByEmail";
    public static final String FIND_USER_BY_PASSWORD = "User.findByPassword";

    @NotNull(message = "First name must be set")
    @Pattern(regexp = "(?i)^[A-Z]+$", message = "First Name must be alphabetical only")
    private String firstname;

    @NotNull(message = "Last name must be set")
    @Pattern(regexp = "(?i)^[A-Z]+$", message = "Last Name must be alphabetical only")
    private String lastname;

    @NotNull(message = "Email must be set")
    @Email(message = "Email must be in the form user@domain.com")
    private String email;

    @NotNull(message = "Password cannot be empty")
    private String password;

    private String salt;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
