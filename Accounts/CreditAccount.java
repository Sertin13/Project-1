package Accounts;

import Bank.*;
import java.util.*;
import Launchers.*;

public class CreditAccount extends Account implements Payment,Recompense
{
    private double loan;
    private static final double CreditLimit = 100000.0;

    public CreditAccount(Bank bank, String accountNum, String ownerFName, String ownerLName, String ownerEmail, String pin)
    {
        super(bank, accountNum, ownerFName, ownerLName, ownerEmail, pin);

    }
    public String getLoanStatement()
    {
        return "Loan: P"+ loan;
    }
    public boolean canCredit(double amountAdjustment)
    {
        return loan >= amountAdjustment;
    }
    public void adjustLoanAmount(double amountAdjustment)
    {
        loan += amountAdjustment;
    }

    public String toString()
    {
        return "Credit Account\nAccount Number: " +getAccountNumber()+"\nOwner: "+ getOwnerFullName() + "\n" + getLoanStatement()+ "\n";
    }
    //override
    @Override
    public boolean pay(Account target_account, double amount_to_pay) throws IllegalAccountType
    {
        if (!(target_account instanceof CreditAccount || target_account instanceof SavingsAccount))
        {
            throw new IllegalAccountType("Credit accounts cannot pay to other credit accounts.");
        }
        else
        {
         if (loan >= amount_to_pay)
         {
             adjustLoanAmount(-amount_to_pay); //check the payment amount from the loan
             System.out.println("\\u001B[32mPayment successful.\\u001B[0m");
             return true;
         }
         else
         {
             System.out.println("\u001B[31mLoan account insufficient. Please Retry.\u001B[0m");
         }  return false;
        }
    }

    // Recompenses a specified amount from the loan, reducing the total loan amount
    @Override
    public boolean recompense(double amount){
        if (amount <= 0 || amount > loan){
            return false; // Recompense amount is invalid
        }
        adjustLoanAmount(-amount); // Deducts the recompense amount from the loan
        return true;
    }
}
