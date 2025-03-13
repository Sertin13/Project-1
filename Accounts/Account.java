package Accounts;

import java.util.*;
import Bank.*;

public class Account {
    private BANK bank;
    private String accountNumber;
    private String ownerFName, ownerLName, ownerEmail;
    private String pin;
    private ArrayList<Transaction> transactions;

    public Account(Bank bank, String accountNumber, String ownerFName, String ownerLName, String ownerEmail, String pin)
    {
        this.bank = bank;
        this.accountNumber = accountNumber;
        this.ownerFName = ownerFName;
        this.ownerLName = ownerLName;
        this.ownerEmail = ownerEmail;
        this.pin = pin;
        this.transactions = new ArrayList<>();
    }

    public String getOwnerFullName()
    {
        return ownerFName + " " + ownerLName;
    }

    public void addNewTransaction(String accountNumber, Transaction.Transactions type, String description)
    {
        transactions.add(new Transaction(accountNum, type, description));
    }

    public String getTransactionInfo()
    {
        StringBuilder info = new StringBuilder();
        info.append("=====Transactions=====");
        for (Transaction transaction : transactions) {
            info.append(transaction.toString()).append("\n");
        }
        return info.toString();
    }

    @Override
    public String toString() {
        return "Account Number: " + accountNumber + "\nOwner: " + getOwnerFullName()+"\n";
    }

}
