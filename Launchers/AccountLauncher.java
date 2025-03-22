package Launchers;

import Accounts.*;
import Main.*;
import Bank.*;

import java.util.Scanner;

public class AccountLauncher {
    private static Account loggedAccount;
    private static Bank assocBank;
    private static final Scanner input = new Scanner(System.in);

    //methods
    private static boolean isLoggedIn()
    {
        return loggedAccount != null;
    }

    public static void accountLogin() throws IllegalAccountType {
        Main:
        while(true)
        {
            Bank loginBank=selectBank();
            assocBank=loginBank;
            if(loginBank!=null)
            {
                BankLauncher.setBankSession(loginBank);
                if(BankLauncher.getLoggedBank()!=null)
                {
                    Main.showMenuHeader("Select Account Type");
                    Main.showMenu(33);
                    Main.setOption();
                    //Credit account
                    if(Main.getOption()==1)
                    {
                        int tries=0;
                        Main.showMenuHeader("Credit Account Login");
                        while(true)
                        {
                            tries+=1;
                            String accnum=Main.prompt("Enter Account Number: ",true);
                            Account found = assocBank.getBankAccount(BankLauncher.getLoggedBank(), accnum);


                            if (found instanceof CreditAccount)

                            {   int tries2 = 0;

                                while (tries2 < 3) {
                                    String pin = Main.prompt("Enter Pin: ", true).trim();

                                    if (found != null && String.valueOf(found.getPin()).trim().equals(pin))
                                    {
                                        loggedAccount=found;
                                        setLogSession();
                                        System.out.println("Login Successful!");
                                        System.out.println("Session started for account: " + found.getAccountNumber());

                                        CreditAccountLauncher.credAccountInit();
                                        destroyLogSession();
                                        return;
                                    }

                                    System.out.println("Invalid PIN! Try again.");
                                    tries2++;
                                }

                                System.out.println("Too many unsuccessful attempts! Account locked.");

                            }
                            if(tries==3)
                            {
                                Main.print("Too many unsuccessful attempts!");
                                break Main;
                            }
                            else {
                                Main.print("Invalid Account!");
                            }
                        }
                    }
                    //Savings Account
                    else if(Main.getOption()==2)
                    {
                        int tries=0;
                        Main.showMenuHeader("Savings Account Login");
                        while(true)
                        {
                            tries+=1;
                            String accnum=Main.prompt("Enter Account Number: ",true);
                            Account found = assocBank.getBankAccount(BankLauncher.getLoggedBank(), accnum);


                            if (found instanceof SavingsAccount) {
                                int tries2 = 0;

                                while (tries2 < 3) {
                                    String pin = Main.prompt("Enter PIN: ", true).trim();

                                    if (found.getPin().trim().equalsIgnoreCase(pin.trim())) {
                                        loggedAccount=found;
                                        setLogSession();
                                        System.out.println("\n Login Successful!");
                                        System.out.println("Session started for account: " + found.getAccountNumber());


                                        SavingAccountLauncher.savingsAccountInit((SavingsAccount) found);

                                        return;
                                    }

                                    tries2++;
                                    System.out.println("\n Invalid PIN! Try again. Attempts left: " + (3 - tries2));
                                }

                                System.out.println("\n Too many unsuccessful attempts! Account locked.");
                            }

                            if(tries==3)
                            {
                                Main.print("Too many unsuccessful attempts!");
                                break Main;
                            }
                            else {
                                Main.print("Invalid Account!");
                            }
                        }
                    }
                    else {
                        Main.print("Invalid Input!");
                        break;
                    }

                }
            }
            else
            {
                Main.print("Bank Not Found");
                break;
            }
        }
    }

    //Done
    private static Bank selectBank()
    {
        Main.showMenuHeader("Bank Selection");
        BankLauncher.showBanksMenu();
        System.out.print("Enter Bank ID: ");
        int bankId=input.nextInt();
        input.nextLine();
        for(Bank b :BankLauncher.getBankList())
        {
            if(b.getID()==bankId)
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