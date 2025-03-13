package Accounts;

public class CreditAccount
{
    private double loan;

    public CreditAccount(Bank bank, String accountNumber, String ownerFName, String ownerLName, String ownerEmail, String pin)
    {
        super( bank,  accountNumber,  ownerFName,  ownerLName,  ownerEmail,  pin);
        this.bank = bank;
        this.accountNumber = accountNumber;
        this.ownerFName = ownerFName;
        this.ownerLName = ownerLName;
        this.ownerEmail = ownerEmail;
        this.pin = pin;
        this.transactions = new ArrayList<>();
    }
    public String getLoanStatement()
    {
        return ""+Loan;
    }
    public static boolean canCredit(double amountAdjustment)
    {
        return amountAdjustment<Loan;
    }
    public void adjustLoanAmount(double amountAdjustment)
    {
        if (canCredit(amountAdjustment)) {
            Loan -= amountAdjustment;
            System.out.println("Loan payment successful. Remaining loan: " + getLoanStatement());
        }
    }
    public String toString()
    {
        return "Account Details:\nNumber: "+this.accountNumber+"\nName: "+
                this.ownerLName+", "+this.ownerFName+"\nOutstanding Balance: ₱"+getLoanStatement();
    }
}
