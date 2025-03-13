package Launchers;

import Accounts.*;
import Bank.*;

import java.util.Scanner;
public class AccountLauncher {

    private static loggedAccount;
    private static assocBank;
    private static final Scanner input = new Scanner(System.in);

    private static Boolean isLoggedIn()
    {
        return loggedAccount != null;
    }
    public static void accountLogin()
    {

    }

    private static void selectBank(Bank bank)
    {
        assocBank = bank;
    }

    private static void setLogSession()
    {

    }
    private static void destroyLogSession()
    {

    }
    public static Account checkCredentials()
    {
        return null;
    }

    protected static Account getLoggedAccount()
    {
        return loggedAccount;
    }

}