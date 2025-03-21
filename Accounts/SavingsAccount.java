package Accounts;

import Bank.*;
import Main.Main;



public class SavingsAccount extends Account implements  Withdrawal, Deposit, FundTransfer
{
    private double balance;

    public String showBalance()
    {
        return "Remaining Balance: ₱"+ balance;
    }

    public SavingsAccount(Bank bank, String accountNum, String ownerFName, String ownerLName, String ownerEmail, String pin, double initialDeposit)
    {
        super(bank, accountNum, ownerFName, ownerLName, ownerEmail, pin);
        this.balance = initialDeposit ;
    }

    public String getAccountBalanceState()
    {
        return String.format("Account Balance: ₱%.2f",this.balance);
    }

    private boolean hasEnoughBalance(double amount)
    {
        return this.balance >= amount;
    }

    private void insufficientBalance()
    {
        Main.print("Account has insufficient Balance.");
    }

    private void adjustAccountBalance(double amount)
    {
        if(this.balance+amount<0)
        {
            this.balance=0;
        }
        this.balance+=amount;
    }

    public String toString() {
        return String.format("Account Number: %s\n%s\nOwner: %s",
                getAccountNumber(), getAccountBalanceState(), getOwnerFullName());
    }




    //Additional Methods
    @Override
    public boolean transfer(Account account, double amount)throws IllegalAccountType
    {
        if (!(account instanceof Account)) {
            throw new IllegalAccountType("Cannot transfer funds to non Savings Account!");
        }

        SavingsAccount account1 = (SavingsAccount) account;

        if (hasEnoughBalance(amount)) {
            account1.adjustAccountBalance(amount);
            adjustAccountBalance(-amount);
            addNewTransaction(this.getAccountNumber(), Transaction.Transactions.FundTransfer, "Transferred Amount: " + amount + " to Recipient: " + account.getAccountNumber());
            account.addNewTransaction(account.getAccountNumber(), Transaction.Transactions.FundTransfer, "Received ₱" + amount + " from " + account.getAccountNumber());
            return true;
        }
        insufficientBalance();
        return false;
    }


    @Override
    public boolean transfer(Bank bank, Account account, double amount)
    {
        return false;
    }

    @Override
    public boolean cashDeposit(double amount) {
        if (amount > BankLauncher.getLoggedBank().getDepositLimit()) {
            System.out.println("Deposit amount exceeds the bank's limit.");
            return false;
        }
        this.adjustAccountBalance(amount);
        return true;
    }

    @Override
    public boolean withdrawal(double amount) {
        if(balance>=amount)
        {
            balance -= amount;
            return true;
        }
        return false;
    }

    public double getAccountBalance(){
        return balance;
    }

    public Bank getBank(){
        return BankLauncher.getLoggedBank();
    }
}



