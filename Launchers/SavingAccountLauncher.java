package Launchers;

import Accounts.*;
import java.util.*;
import Main.*;

public class SavingAccountLauncher extends AccountLauncher {

    private static final Scanner input = new Scanner(System.in);


    public static void login(SavingsAccount acc)
    {
        loggedAccount=acc;
        print("Login to "+loggedAccount.getAccountNumber()+" success...\n");
    }
    public static void logout()
    {
        loggedAccount=null;
        print("Logout success...\n");
    }

    //MENU in SavingAccountLauncher
    public static void savingsAccountInit()
    {
        //Test
        // Implementation logic here
        Account current=AccountLauncher.getLoggedAccount();
        if(current.getClass().isInstance(SavingsAccount.class)) {
            //"Show Balance", "Deposit", "Withdraw", "Fund Transfer",
            //            "Show Transactions", "Logout"
            while (true) {
                Main.showMenuHeader("Savings Account Menu");
                Main.showMenu(51);
                Main.setOption();
                //Show balance
                if (Main.getOption() == 1) {
                }
                //Deposit
                else if (Main.getOption() == 2) {
                    System.out.print("Enter Deposit amount: ");
                    int amount = input.nextInt();
                    input.nextLine();
                    depositProcess(amount);
                }
                //Withdraw
                else if (Main.getOption() == 3)
                {
                }
                //Fund Transfer
                else if (Main.getOption() == 4)
                {
                }
                //Show Transactions
                else if (Main.getOption() == 5)
                {
                    current.getTransactionsInfo();
                }
                //logout
                else if (Main.getOption() == 6) {
                    break;
                } else {
                    Main.print("Invalid input");
                }
            }
        }


    }

    private static void depositProcess(double amount)
    {
        //Test
        // Implementation logic here
        System.out.println("Processing deposit of: ₱" + amount);
    }

    private static void withdrawProcess(double amount)
    {
        //Test
        // Implementation logic here
        System.out.println("Processing withdrawal of: ₱" + amount);
    }

    private static void fundTransferProcess(String targetAccNum, double amount)
    {
        //Test
        // Implementation logic here
        System.out.println("Transferring ₱" + amount + "To Account: " + recipientacc);

    protected static Account getLoggedAccount()
    {
        Account check =AccountLauncher.getLoggedAccount();
        if(check!=null)
        {
            if(check.getClass().isInstance(SavingsAccount.class))
            {
                return null;
            }
        }
        return null;
    }
}
