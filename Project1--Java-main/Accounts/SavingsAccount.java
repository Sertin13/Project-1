package Accounts;

import Bank.*;

import Main.Main;

public class SavingsAccount extends Account implements Withdrawal,Deposit,FundTransfer
{
    private double Balance;

    public String showBalance()
    {
        return "Remaining Balance: ₱"+Balance;
    }

    public SavingsAccount(Bank bank, String accountNumber,String pin,String FirstName,String LastName, String Email, double initialDeposit)
    {
        super(bank, accountNumber, pin, FirstName, LastName, Email);
        this.Balance=initialDeposit;
    }

    //Methods
    public String getAccountBalanceStatement()
    {
        return String.format("Account Balance: ₱%.2f",this.Balance);
    }

    private boolean hasEnoughBalance(double amount)
    {
        return Balance >= amount;
    }


    private void insufficientBalance()
    {
            Main.print("Account has insufficient Balance.");

    }

    private void adjustAccountBalance(double amount)
    {
        if(this.Balance+amount<0)
        {
            this.Balance=0;
        }
        this.Balance+=amount;
    }

    public String toString()
    {
        return String.format("Savings Account\nAccount Number: %s\n%s\nOwner: %s\n",getAccountNumber(),getAccountBalanceStatement(),getOwnerFullName());
    }

    //Additional Methods
    @Override
    public boolean transfer(Account targetAccount, double amount) throws IllegalAccountType {
        if (!(targetAccount instanceof SavingsAccount)) {
            throw new IllegalAccountType("Transfers are only allowed to Savings Accounts.");
        }

        if (!hasEnoughBalance(amount)) {
            insufficientBalance();
            return false;
        }

        SavingsAccount recipientAccount = (SavingsAccount) targetAccount;
        double processingFee = getBank().getProcessingFee();
        double totalDeduction = amount + processingFee;

        // Adjust balances
        adjustAccountBalance(-totalDeduction);  // Deduct amount + fee from sender
        recipientAccount.adjustAccountBalance(amount);  // Add amount to recipient

        // Record transactions for both sender and recipient
        addNewTransaction(this.getAccountNumber(), Transaction.Transactions.FundTransfer,
                String.format("Transferred ₱%.2f to Account: %s | Processing Fee: ₱%.2f", amount, targetAccount.getAccountNumber(), processingFee));

        targetAccount.addNewTransaction(targetAccount.getAccountNumber(), Transaction.Transactions.FundTransfer,
                String.format("Received ₱%.2f from Account: %s", amount, this.getAccountNumber()));

        System.out.println("Transfer successful!");
        return true;
    }

    

    

    @Override
    public boolean transfer(Bank bank, Account account, double amount)
    {
        return false;
    }

    @Override
    public boolean cashDeposit(double amount) {
        Bank loggedBank = BankLauncher.getLoggedBank();

        if (amount <= 0) {
            System.out.println("Invalid deposit amount. Please enter a positive value.");
            return false;
        }

        if (amount > loggedBank.getDepositLimit()) {
            System.out.println("Deposit amount exceeds the bank's limit of ₱" + loggedBank.getDepositLimit() + ".");
            return false;
        }

        double processingFee = getBank().getProcessingFee();
        adjustAccountBalance(amount);   // Add deposit amount
        adjustAccountBalance(-processingFee);  // Deduct processing fee

        addNewTransaction(this.getAccountNumber(), Transaction.Transactions.Deposit,
                String.format("Deposited ₱%.2f | Processing Fee: ₱%.2f", amount, processingFee));

        System.out.println("Cash deposit successful! Amount deposited: ₱" + amount);
        return true;
    }


    @Override
    public boolean withdrawal(double amount) {
        if(Balance>=amount)
        {
            Balance -= amount;
            return true;
        }
        return false;
    }

    public double getAccountBalance() {
        return Balance;
    }

    public Bank getBank() {
        return BankLauncher.getLoggedBank();
    }
}
