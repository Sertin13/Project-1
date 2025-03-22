package Accounts;

import Bank.*;
import java.util.*;
import Launchers.*;

public class CreditAccount extends Account implements Payment, Recompense {
    private double Loan;

    public CreditAccount(Bank bank, String accountNumber, String pin, String FirstName, String LastName, String Email) {
        super(bank, accountNumber, pin, FirstName, LastName, Email);
    }

    // Get Loan Statement
    public String getLoanStatement() {
        return String.format("Loan: ₱%.2f", Loan);
    }

    // Check if loan can be adjusted
    private boolean canCredit(double amountAdjustment) {
        return Loan >= amountAdjustment;
    }

    // Adjust loan amount
    private void adjustLoanAmount(double amountAdjustment) {
        if (canCredit(amountAdjustment)) {
            Loan = Math.max(0, Loan + amountAdjustment); // Ensures loan does not go negative
        }
    }

    @Override
    public String toString() {
        return String.format("Credit Account\nAccount Number: %s\nOwner: %s\n%s\n",
                getAccountNumber(), getOwnerFullName(), getLoanStatement());
    }

    public double getLoan() {
        return Loan;
    }

    @Override
    public boolean pay(Account targetAccount, double amountToPay) throws IllegalAccountType {
        if (!(targetAccount instanceof CreditAccount)) {
            throw new IllegalAccountType("Credit accounts cannot pay to non-credit accounts.");
        }

        if (Loan >= amountToPay) {
            adjustLoanAmount(-amountToPay);
            System.out.println("Payment successful.");
            return true;
        } else {
            System.out.println("Insufficient loan balance. Please retry.");
            return false;
        }
    }

    @Override
    public boolean recompense(double amount) {
        if (amount <= 0 || amount > Loan) {
            System.out.println("Invalid recompense amount.");
            return false;
        }

        adjustLoanAmount(-amount);
        System.out.println("Recompense successful.");
        return true;
    }
}
