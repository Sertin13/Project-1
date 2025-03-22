package Launchers;

import Accounts.*;
import Main.*;
import Bank.*;

import java.util.Scanner;

public class AccountLauncher {
    private static Account loggedAccount;
    private static Bank assocBank;
    private static final Scanner input = new Scanner(System.in);

    //Methods
    private static boolean isLoggedIn()
    {
        return loggedAccount != null;
    }

    public static void accountLogin() throws IllegalAccountType {
        while (true) {
            Bank loginBank = selectBank();

            if (loginBank == null) {
                Main.print("Bank Not Found");
                break;
            }

            BankLauncher.setBankSession(loginBank);
            assocBank = loginBank;

            if (BankLauncher.getLoggedBank() == null) {
                continue;
            }

            Main.showMenuHeader("Select Account Type");
            Main.showMenu(33);
            Main.setOption();
            int accountType = Main.getOption();

            if (accountType != 1 && accountType != 2) {
                Main.print("Invalid Input!");
                break;
            }

            String accountTypeName = (accountType == 1) ? "Credit" : "Savings";
            Main.showMenuHeader(accountTypeName + " Account Login");

            int attempts = 0;

            while (attempts < 3) {
                attempts++;
                String accNum = Main.prompt("Enter Account Number: ", true).trim();
                Account found = assocBank.getBankAccount(BankLauncher.getLoggedBank(), accNum);

                if ((accountType == 1 && found instanceof CreditAccount) ||
                        (accountType == 2 && found instanceof SavingsAccount)) {

                    int pinAttempts = 0;

                    while (pinAttempts < 3) {
                        String pin = Main.prompt("Enter PIN: ", true).trim();

                        if (found.getPin().trim().equals(pin)) {
                            loggedAccount = found;
                            setLogSession();
                            System.out.println("Login Successful!");
                            System.out.println("Session started for account: " + found.getAccountNumber());

                            if (accountType == 1) {
                                CreditAccountLauncher.credAccountInit();
                            } else {
                                SavingsAccountLauncher.savingsAccountInit((SavingsAccount) found);
                            }

                            destroyLogSession();
                            return;
                        } else {
                            pinAttempts++;
                            System.out.println("Invalid PIN! Attempts left: " + (3 - pinAttempts));
                        }
                    }

                    System.out.println("Too many unsuccessful PIN attempts! Account locked.");
                    break;
                } else {
                    System.out.println("Invalid Account! Attempts left: " + (3 - attempts));
                }
            }

            System.out.println("Too many unsuccessful login attempts! Please try again later.");
            break;
        }
    }

    //Done
    private static Bank selectBank()
    {
//        Main.showMenuHeader("Bank Selection");
        BankLauncher.showBanksMenu();
        System.out.print("\nEnter Bank Name: ");
        String bankName=input.nextLine();
        for(Bank b :BankLauncher.getBankList())
        {
            if(b.getName().equals(bankName))
            {
                return b;
            }
        }
        return null;
    }

    //Done
    private static void setLogSession()
    {
         System.out.println("Session started for account: " + loggedAccount.getAccountNumber());
    }

    //Done
    private static void destroyLogSession()
    {
        if (loggedAccount != null) {
            System.out.println("Logging out: " + loggedAccount.getAccountNumber());
            loggedAccount = null;
        }
        else {
            System.out.println("No active session to log out.");
        }
    }

    //Use in Account Login
    public static Account checkCredentials(String accountNum, String pin)
    {
        Account found=BankLauncher.findAccount(accountNum);
        if(found!=null)
        {
            if(found.getPin().equals(pin))
            {
                return found;
            }
        }
        return null;
    }

    protected static Account getLoggedAccount()
    {
        return loggedAccount;
    }
}