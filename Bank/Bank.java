package Bank;

import Accounts.*;
import Main.Field;

import java.util.*;

public class Bank {
    private int ID;
    private String name, password;
    private double DEPOSITLIMIT, WITHDRAWLIMIT, CREDITLIMIT;
    private double processingFee;
    private ArrayList <Account> BANKACCOUNTS;

    public Bank(int ID, String name, String password )
    {
        this.ID = ID;
        this.name = name;
        this.password = password;
        this.BANKACCOUNTS = new ArrayList<>();
    }

    public Bank(int ID, String name, String password, double DEPOSITLIMIT, double WITHDRAWLIMIT, double CREDITLIMIT, double processingFee)
    {
        this(ID, name, password);
        this.DEPOSITLIMIT=DEPOSITLIMIT;
        this.WITHDRAWLIMIT=WITHDRAWLIMIT;
        this.CREDITLIMIT=CREDITLIMIT;
        this.processingFee=processingFee;
    }
    public <T> void showAccounts(Class<T> accountType) {
        for (Account account : this.BANKACCOUNTS)
        {
            if (accountType.isInstance(account))
            {
                System.out.println(account);
            }
        }
    }
    public Account getBankAccount(String accountNum) {
        for (Account account : BANKACCOUNTS) {
            if (account.getAccountNumber().equals(accountNum)) {
                return account;
            }
        }
        return null;
    }
    public ArrayList<Field<String, ?>> createNewAccount() {
        return new ArrayList<>();
    }

    public CreditAccount createNewCreditAccount(Bank bank, String accountNumber, String ownerFName, String ownerLName, String ownerEmail, String pin) {
        return new CreditAccount( bank,  accountNumber,  ownerFName,  ownerLName,  ownerEmail,  pin);
    }

    public SavingsAccount createNewSavingsAccount(Bank bank, String accNum, String ownerFName, String ownerLName, String ownerEmail, String pin, double balance) {
        return new SavingsAccount(bank, accNum, ownerFName, ownerLName,ownerEmail, pin,balance);
    }

    public void addNewAccount(Account account) {
        BANKACCOUNTS.add(account);
    }

    public boolean accountExists(Bank bank, String accountNum) {
        for(Account accs: bank.BANKACCOUNTS)
        {
            if(accs.getAccountNumber().equals(accountNum))
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Bank ID: " + ID + ", Name: " + name;
    }


}






