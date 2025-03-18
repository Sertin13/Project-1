package Accounts;

import Bank.*;

import java.util.*;
import java.sql.Savepoint;
import Launchers.*;
import Main.Main;


public class SavingsAccount extends Account
{

    private double balance;

    public SavingsAccount(Bank bank, String accountNum, String ownerFName, String ownerLName, String ownerEmail, String pin, double balance)
    {
        super(bank, accountNum, ownerFName, ownerLName, ownerEmail, pin);
        this.balance = balance;
    }

    public String getAccountBalance()
    {
        return String.format("Account Balance: ₱%.2f",this.balance);
    }

    private boolean hasEnoughBalance(double amount) {
        return this.balance >= amount;
    }

    // Getter method
    public boolean getHasEnoughBalance(double amount) {
        return hasEnoughBalance(amount);
    }


    private boolean insufficientBalance(double amount)
    {
        if(this.balance<amount)
        {
            Main.print("Account has insufficient Balance.");
        }
        return false;
    }


    private void adjustAccountBalance(double amount)
    {
        if(this.balance+amount<0)
        {
            this.balance=0;
        }
        this.balance+=amount;
    }

    //getter
    public void updateAccountBalance(double amount) {
        adjustAccountBalance(amount); // Calls the private method to adjust balance
    }


    public String toString()
    {
        return String.format("Account Number: %s\n%s\nOwner: %s",getAccountNumber(),getAccountBalance(),getOwnerFullName() + "\n");
    }

    //Additional Methods
    public void transfer(Account account, double amount)throws IllegalAccountType
    {
        if(account!=null&&hasEnoughBalance(amount)&&account.getClass().isInstance(SavingsAccount.class))
        {
            adjustAccountBalance(-amount);
            addNewTransaction(this.getAccountNumber(),Transaction.Transactions.FundTransfer,"Transferred Amount: "+amount+" to Recipient: "+account.getAccountNumber());
        }
    }

    public void transfer(Bank bank, Account account, double amount)
    {
        //
    }
}
