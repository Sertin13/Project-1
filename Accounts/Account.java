package Accounts;

import java.util.*;
import Bank.*;
import Main.*;

public abstract class Account {
    private Bank BANK;
    private String AccountNumber;
    private String OwnerFName,OwnerLName, OwnerEmail;
    private String Pin;
    private ArrayList<Transaction> TRANSACTIONS;

    //Methods
    public Account(Bank bank, String accountNumber,String pin,String FirstName,String LastName, String Email)
    {
        this.BANK=bank;
        this.AccountNumber=accountNumber;
        this.OwnerFName=FirstName;
        this.OwnerLName=LastName;
        this.OwnerEmail=Email;
        this.Pin=pin;
        this.TRANSACTIONS=new ArrayList<>();
    }
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
        return OwnerLName+", "+ OwnerFName;
    }

    public Bank getBANK(){
        return BANK;
    }

    public void addNewTransaction(String accountNumber, Transaction.Transactions type, String description)
    {
        Transaction newTransaction= new Transaction(accountNumber,type,description);
        this.TRANSACTIONS.add(newTransaction);
    }

    public String getTransactionsInfo() {
        Main.showMenuHeader("Transactions");

        if (TRANSACTIONS.isEmpty()) {
            return "No Transactions Available.";
        }

        StringBuilder result = new StringBuilder();
        TRANSACTIONS.forEach(transaction -> result.append(
                String.format("Account Number: %s\tTransaction Type: %s\tDescription: %s%n",
                        transaction.accountNumber,
                        transaction.transactionType,
                        transaction.description)
        ));

        return result.toString();
    }


    public String toString()
    {
        return String.format("Account Owner: %s\n",this.getOwnerFullName());
    }

    public String getOwnerEmail() {
        return OwnerEmail;
    }
}
