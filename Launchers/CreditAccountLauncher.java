package Launchers;

import Accounts.*;
import Bank.*;
import Main.*;

public class CreditAccountLauncher extends AccountLauncher {
    public static void credAccountInit() {
        Account current = AccountLauncher.getLoggedAccount();
        
        if (current instanceof CreditAccount creditAccount) {
            while (true) {
                Main.showMenuHeader("Credit Account Menu");
                Main.showMenu(41);
                Main.setOption();
                
                switch (Main.getOption()) {
                    case 1 -> showCredits(creditAccount);
                    case 2 -> creditPaymentProcess(creditAccount);
                    case 3 -> creditRecompense(creditAccount);
                    case 4 -> showTransactions(creditAccount);
                    case 5 -> {
                        Main.print("Logging out...");
                        return;
                    }
                    default -> Main.print("Invalid option! Please try again.");
                }
            }
        } else {
            Main.print("Invalid account type. Please log in with a Credit Account.");
        }
    }
    
    private static void showCredits(CreditAccount account) {
        Main.print("Current Credit Balance: " + account.getLoanBalance());
    }
    
    private static void creditPaymentProcess(CreditAccount account) {
        double amount = Double.parseDouble(Main.prompt("Enter payment amount: ", true));
        if (account.canCreditTransaction(amount)) {
            account.adjustLoanAmount(-amount);
            Main.print("Payment successful. New loan balance: " + account.getLoanBalance());
        } else {
            Main.print("Insufficient funds or invalid amount.");
        }
    }
    
    private static void creditRecompense(CreditAccount account) {
        double recompenseAmount = Double.parseDouble(Main.prompt("Enter recompense amount: ", true));
        if (account.canCreditTransaction(recompenseAmount)) {
            account.adjustLoanAmount(recompenseAmount);
            Main.print("Recompense successful. New loan balance: " + account.getLoanBalance());
        } else {
            Main.print("Invalid recompense amount.");
        }
    }
    
    private static void showTransactions(CreditAccount account) {
        Main.print("Transaction History:");
        Main.print(account.getTransactions());
    }
    
    protected static CreditAccount getLoggedAccount() {
        Account account = AccountLauncher.getLoggedAccount();
        return account instanceof CreditAccount ? (CreditAccount) account : null;
    }
}
