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
        return String.format("Account Balance: %2.f",this.balance);
    }

    private boolean hasEnoughBalance(double amount)
    {
        return this.balance >= amount   ;
    }

    private boolean insufficientBalance(double amount)
    {
        if(this.balance<amount)
        {
            Main.print("Account has insufficient Balance.");
        }
    }


    private void adjustAccountBalance(double amount)
    {
        if(this.balance+amount<0)
        {
            this.balance=0;
        }
        this.balance+=amount;
    }


    public String toString()
    {
        return String.format("Account Number: %s\n%s\nOwner: %s",getAccountNumber(),getAccountBalance(),getOwnerFullName());
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
