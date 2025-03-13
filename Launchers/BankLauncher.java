package Launchers;

import Main.*;
import Accounts.*;
import Bank.*;

import java.util.*;

public class BankLauncher {

    private static ArrayList<Bank> BANKS = new ArrayList<>();
    private static Bank loggedBank = null;
    private static final Scanner input = new Scanner(System.in);


    public static Boolean isLogged()
    {
        return loggedBank == null;
    }
    public static void bankInit()
    {

    }
    private static void showAccount()
    {

    }
    private static void newAccount()
    {

    }
    public static void bankLogin()
    {

    }
    private static void setLogSession(Bank b )
    {

    }
    private static void logout()
    {

    }
    public static void createNewBank()
    {

    }
    public static void showBanksMenu()
    {

    }
    private static void addBank(Bank b)
    {

    }
    public static Bank getLoggedBank()
    {
        return loggedBank;
    }
    public static Bank getBank(Comparator<Bank> comparator, Bank bank)
    {
        return null;
    }

    public static Account findAccount(String accountNum)
    {
        return null;
    }

    public static int bankSize()
    {
        return BANKS.size();
    }
}
