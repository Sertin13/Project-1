package Launchers;

import Accounts.*;
import java.util.*;


public class SavingAccountLauncher extends AccountLauncher {
    private static SavingsAccount loggedAccount = null;
    private static final Scanner input = new Scanner(System.in);
    private static void print(String Value)
    {
        System.out.print(Value);
    }

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
    public static void savingsAccountInit()
    {

        // Implementation logic here
    }

    private static void depositProcess(double amount)
    {

        // Implementation logic here
    }

    private static void withdrawProcess(double amount)
    {

        // Implementation logic here
    }

    private static void fundTransferProcess(String targetAccNum, double amount)
    {

        // Implementation logic here
    }

    protected static Account getLoggedAccount()
    {
        return AccountLauncher.getLoggedAccount();
    }
}
