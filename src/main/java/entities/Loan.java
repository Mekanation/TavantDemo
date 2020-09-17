package entities;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@NamedQuery(name = Loan.GET_ALL_LOANS_BY_OWNER, query = "select l from Loan l where l.loanRequester.email = :email")
@NamedQuery(name = Loan.GET_LOAN_BY_ID, query = "select l from Loan l where l.id = :id and l.loanRequester.email = :email")
public class Loan extends AbstractEntity{

    public static final String GET_ALL_LOANS_BY_OWNER = "Loan.findByOwner";
    public static final String GET_LOAN_BY_ID = "Loan.findById";

    @NotNull(message = "Loan name must be set")
    @Pattern(regexp = "(?i)^[A-Z]+$", message = "Loan Name must be alphabetical only")
    private String loanName;

    @NotNull(message = "Income needs to be verified")
    private boolean isIncomeVerified;


    private boolean approvalStatus;

    @NotNull(message = "Annual income needs to be set.")
    @DecimalMin(value= "0.00", message = "Value cannot be negative")
    private BigDecimal annualIncome;

    @NotNull(message = "Assets value needs to be set.")
    @DecimalMin(value= "0.00", message = "Value cannot be negative")
    private BigDecimal assetsValue;

    @NotNull(message = "Debts value needs to be set.")
    @DecimalMin(value= "0.00", message = "Value cannot be negative")
    private BigDecimal debtsValue;

    @NotNull(message = "Credit score needs to be set.")
    @Max(value = 850, message = "The max possible credit score is 850")
    @Min(value = 300, message = "The minimum possible credit score is 300")
    private int creditScore;

    @NotNull(message = "Loan amount requested needs to be set.")
    @DecimalMin(value= "0.00", message = "Value cannot be negative")
    private BigDecimal loanAmountRequested;

    @ManyToOne
    private User loanRequester;

    private LocalDate dateCreated;

    @PrePersist
    private void init() {
        setDateCreated(LocalDate.now());
    }

    private void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }


    public String getLoanName() {
        return loanName;
    }

    public void setLoanName(String loanName) {
        this.loanName = loanName;
    }

    public boolean isIncomeVerified() {
        return isIncomeVerified;
    }

    public void setIncomeVerified(boolean incomeVerified) {
        isIncomeVerified = incomeVerified;
    }

    public BigDecimal getAnnualIncome() {
        return annualIncome;
    }

    public void setAnnualIncome(BigDecimal annualIncome) {
        this.annualIncome = annualIncome;
    }

    public BigDecimal getAssetsValue() {
        return assetsValue;
    }

    public void setAssetsValue(BigDecimal assetsValue) {
        this.assetsValue = assetsValue;
    }

    public BigDecimal getDebtsValue() {
        return debtsValue;
    }

    public void setDebtsValue(BigDecimal debtsValue) {
        this.debtsValue = debtsValue;
    }

    public int getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(int creditScore) {
        this.creditScore = creditScore;
    }

    public BigDecimal getLoanAmountRequested() {
        return loanAmountRequested;
    }

    public void setLoanAmountRequested(BigDecimal loanAmountRequested) {
        this.loanAmountRequested = loanAmountRequested;
    }

    public User getLoanRequester() {
        return loanRequester;
    }

    public void setLoanRequester(User loanRequester) {
        this.loanRequester = loanRequester;
    }

    public boolean isApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(boolean approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }
}
