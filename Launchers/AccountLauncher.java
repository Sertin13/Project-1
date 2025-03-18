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
        Main:
        while(true)
        {
            Bank loginBank=selectBank();
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
                            Account found = BankLauncher.getLoggedBank().getBankAccount(BankLauncher.getLoggedBank(), accnum);


                            if (found instanceof CreditAccount)

                            {   int tries2 = 0;

                                while (tries2 < 3) {
                                    String pin = Main.prompt("Enter Pin: ", true).trim();

                                    if (String.valueOf(found.getPin()).trim().equals(pin)) {
                                        setLogSession(found);
                                        System.out.println("Login Successful!");
                                        System.out.println("Session started for account: " + found.getAccountNumber());
                                        CreditAccountLauncher.credAccountInit((CreditAccount) found);
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
    private static void setLogSession(Account account)
    {
        if(account!=null)
        {
            loggedAccount = account;
            System.out.println("Session started for account: " + loggedAccount.getAccountNumber());
        }
        else
        {
            Main.print("Invalid Account!");
        }
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