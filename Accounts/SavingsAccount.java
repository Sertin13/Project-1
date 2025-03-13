package Accounts;

import Bank.*;
import Launchers.*;
import Accounts.*;


public class SavingsAccount extends Account implements Withdrawal, Deposit, FundTransfer{
    private double balance;

    public SavingsAccount(Bank bank, String accNum, String ownerFName, String ownerLName, String ownerEmail, String pin, double balance)
    {
        super(bank, accNum, ownerFName,ownerLName, ownerEmail, pin);
        this.balance = balance;
    }

    public String getAccountBalance() {
        return "Balance: " + balance;
    }

    private boolean hasEnoughBalance(double amount)
    {
        return this.balance >= amount   ;
    }

    private void insufficientBalance()
    {
        System.out.println("Insufficient Balance!");
    }

    private void adjustAccountBalance(double amount)
    {
        this.balance += amount;
    }


    public String toString() {
        return null;
    }
}
