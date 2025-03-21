package Launchers;

import Accounts.*;
import Bank.*;
import java.util.*;
import Main.*;

public class AccountLauncher {
    private static Account loggedAccount;
    private static Bank assocBank;
    private static final Scanner input = new Scanner(System.in);

    private static boolean isLoggedIn() {
        return loggedAccount != null;
    }

    public static void accountLogin() throws IllegalAccountType {
        while (true) {
            assocBank = selectBank();
            if (assocBank == null) {
                Main.print("Bank Not Found");
                return;
            }
            
            BankLauncher.setBankSession(assocBank);
            if (BankLauncher.getLoggedBank() == null) {
                Main.print("Failed to log into the bank.");
                return;
            }
            
            Main.showMenuHeader("Select Account Type");
            Main.showMenu(33);
            Main.setOption();
            
            if (Main.getOption() == 1) {
                handleAccountLogin(CreditAccount.class);
            } else if (Main.getOption() == 2) {
                handleAccountLogin(SavingsAccount.class);
            } else {
                Main.print("Invalid Input!");
                return;
            }
        }
    }

    private static <T extends Account> void handleAccountLogin(Class<T> accountType) {
        int attempts = 0;
        
        while (attempts < 3) {
            String accNum = Main.prompt("Enter Account Number: ", true);
            Account found = assocBank.getBankAccount(BankLauncher.getLoggedBank(), accNum);
            
            if (accountType.isInstance(found)) {
                if (validatePin(found)) {
                    loggedAccount = found;
                    setLogSession();
                    
                    if (found instanceof CreditAccount) {
                        CreditAccountLauncher.credAccountInit();
                    } else if (found instanceof SavingsAccount) {
                        SavingAccountLauncher.savingsAccountInit((SavingsAccount) found);
                    }
                    
                    destroyLogSession();
                    return;
                }
                
                Main.print("Too many unsuccessful attempts! Account locked.");
                return;
            }
            
            attempts++;
            Main.print("Invalid Account! Attempts left: " + (3 - attempts));
        }
        Main.print("Too many unsuccessful attempts!");
    }
    
    private static boolean validatePin(Account account) {
        int attempts = 0;
        while (attempts < 3) {
            String pin = Main.prompt("Enter PIN: ", true).trim();
            if (String.valueOf(account.getPin()).trim().equals(pin)) {
                Main.print("Login Successful!");
                return true;
            }
            attempts++;
            Main.print("Invalid PIN! Attempts left: " + (3 - attempts));
        }
        return false;
    }

    private static Bank selectBank() {
        Main.showMenuHeader("Bank Selection");
        BankLauncher.showBanksMenu();
        
        System.out.print("Enter Bank ID: ");
        try {
            int bankId = input.nextInt();
            input.nextLine(); // Consume newline
            for (Bank b : BankLauncher.getBankList()) {
                if (b.getID() == bankId) {
                    return b;
                }
            }
        } catch (Exception e) {
            input.nextLine(); // Clear invalid input
            Main.print("Invalid input! Please enter a valid Bank ID.");
        }
        return null;
    }

    private static void setLogSession() {
        if (loggedAccount != null) {
            System.out.println("Session started for account: " + loggedAccount.getAccountNumber());
        }
    }

    private static void destroyLogSession() {
        if (loggedAccount != null) {
            System.out.println("Logging out: " + loggedAccount.getAccountNumber());
            loggedAccount = null;
        } else {
            System.out.println("No active session to log out.");
        }
    }

    public static Account checkCredentials(String accountNum, String pin) {
        Account found = BankLauncher.findAccount(accountNum);
        return (found != null && found.getPin().equals(pin)) ? found : null;
    }

    protected static Account getLoggedAccount() {
        return loggedAccount;
    }
}
