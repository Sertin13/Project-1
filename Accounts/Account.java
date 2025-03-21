package Accounts;

import java.util.*;
import Main.*;
import Bank.*;

public class Account
{
    private Bank Bank;
    private String AccountNumber;
    private String OwnerFName, OwnerLName, OwnerEmail;
    private String Pin;
    private ArrayList<Transaction> Transactions;

    public Account(Bank bank, String accountNum, String ownerFName, String ownerLName, String ownerEmail, String pin)
    {
        this.Bank = bank;
        this.AccountNumber = accountNum;
        this.OwnerFName = ownerFName;
        this.OwnerLName = ownerLName;
        this.OwnerEmail = ownerEmail;
        this.Pin = pin;
        this.Transactions=new ArrayList<>();

    }
    //methods add

    public String getPin()
    {
        return this.Pin;
    }

    public String getAccountNumber()
    {
        return this.AccountNumber;
    }

    public String getOwnerFullName()
    {
        return OwnerFName + " " + OwnerLName;
    }

    public Bank getBANK(){
        return Bank;
    }

    public void addNewTransaction(String accountNum, Transaction.Transactions type, String description)
    {
        Transaction newTransaction= new Transaction(accountNum,type,description);
        this.Transactions.add(newTransaction);
    }

    public String getTransactionsInfo()
    {
        Main.showMenuHeader("Transactions");

        StringBuilder result= new StringBuilder();
        if(Transactions.isEmpty())
        {
            result.append("No Transactions");
            return result.toString();
        }
        for(Transaction transaction:this.Transactions)
        {
            String result1=String.format("Account Number: "+transaction.accountNumber+"\tTransaction Type: "+transaction.transactionType+"\tDescription: "+transaction.description+"\n");
            result.append(result1);
        }
        return result.toString();
    }


    public String toString() {
        return String.format("Account Owner: %s\n",this.getOwnerFullName());
    }

    public String getOwnerEmail() {
        return OwnerEmail;
    }
}
