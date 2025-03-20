package Launchers;



import Accounts.*;
import Bank.*;

import Main.*;



public class CreditAccountLauncher extends AccountLauncher
{
    public static void credAccountInit()
    {
        Account current= AccountLauncher.getLoggedAccount();
        Class<CreditAccount> creditAccountClass = CreditAccount.class;
        if (creditAccountClass.isInstance(current))

        {
            Main.showMenuHeader("Credit Account Menu");
            Main.showMenu(41);
            Main.setOption();
            //"Show Credits", "Pay", "Recompense", "Show Transactions", "Logout"
            switch (Main.getOption())
            {
                case 1 ->
                {

                }
                case 2 ->
                {

                }
            }
        }
        else

        {
        Main.print("Invalid account");
        }
    }

    private static void creditPaymentProcess() {

    }
    private static void creditRecompense()
    {

    }

    protected static CreditAccount getLoggedAccount()
    {
        return null;
    }

}
