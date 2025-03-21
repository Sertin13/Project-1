package Launchers;

import Accounts.Account;
import Accounts.CreditAccount;
import Accounts.SavingsAccount;
import Main.Main;
import Bank.Bank;
import Bank.BankLauncher;
import Exceptions.IllegalAccountType;

import java.util.Scanner;

public class AccountLauncher {
    private static Account loggedAccount;
    private static Bank assocBank;
    private static final Scanner input = new Scanner(System.in);

    // Check if user is logged in
    private static boolean isLoggedIn() {
        return loggedAccount != null;
    }

    public static void accountLogin() throws IllegalAccountType {
        while (true) {
            Bank loginBank = selectBank();
            assocBank = loginBank;
           
            if (loginBank != null) {
                BankLauncher.setBankSession(loginBank);
                if (BankLauncher.getLoggedBank() != null) {
                    Main.showMenuHeader("Select Account Type");
                    Main.showMenu(33);
                    Main.setOption();

                    int option = Main.getOption();
                    if (option == 1) {
                        loginCreditAccount();
                    } else if (option == 2) {
                        loginSavingsAccount();
                    } else {
                        Main.print("Invalid Input!");
                        break;
                    }
                }
            } else {
                Main.print("Bank Not Found");
                break;
            }
        }
    }

    private static void loginCreditAccount() {
        int tries = 0;
        Main.showMenuHeader("Credit Account Login");

        while (tries < 3) {
            tries++;
            String accnum = Main.prompt("Enter Account Number: ", true);
            Account found = assocBank.getBankAccount(BankLauncher.getLoggedBank(), accnum);

            if (found instanceof CreditAccount && validatePin(found)) {
                loggedAccount = found;
                setLogSession();
                CreditAccountLauncher.credAccountInit((CreditAccount) found);
                destroyLogSession();
                return;
            }
            System.out.println("Invalid Account!");
        }
        System.out.println("Too many unsuccessful attempts! Account locked.");
    }

    private static void loginSavingsAccount() {
        int tries = 0;
        Main.showMenuHeader("Savings Account Login");

        while (tries < 3) {
            tries++;
            String accnum = Main.prompt("Enter Account Number: ", true);
            Account found = assocBank.getBankAccount(BankLauncher.getLoggedBank(), accnum);

            if (found instanceof SavingsAccount && validatePin(found)) {
                loggedAccount = found;
                setLogSession();
                SavingAccountLauncher.savingsAccountInit((SavingsAccount) found);
                destroyLogSession();
                return;
            }
            System.out.println("Invalid Account!");
        }
        System.out.println("Too many unsuccessful attempts! Account locked.");
    }

    private static boolean validatePin(Account found) {
        int tries = 0;
        while (tries < 3) {
            String pin = Main.prompt("Enter PIN: ", true).trim();
            if (found.getPin().trim().equals(pin)) {
                return true;
            }
            System.out.println("Invalid PIN! Try again. Attempts left: " + (2 - tries));
            tries++;
        }
        return false;
    }

    private static Bank selectBank() {
        Main.showMenuHeader("Bank Selection");
        BankLauncher.showBanksMenu();
        System.out.print("Enter Bank ID: ");
       
        while (!input.hasNextInt()) {
            System.out.println("Invalid input! Please enter a valid number.");
            input.next();
        }
        int bankId = input.nextInt();
        input.nextLine(); // Consume the newline

        for (Bank b : BankLauncher.getBankList()) {
            if (b.getID() == bankId) {
                return b;
            }
        }
        return null;
    }

    private static void setLogSession() {
        System.out.println("Session started for account: " + loggedAccount.getAccountNumber());
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
