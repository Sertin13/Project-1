package Launchers;

import Main.*;
import Accounts.*;
import Bank.*;

import java.util.*;

public class BankLauncher {

    private static ArrayList<Bank> BANKS = new ArrayList<>();
    private static Bank loggedBank = null;
    private static final Scanner input = new Scanner(System.in);

    //methods
    public static Bank getloggedBank()
    {
        return loggedBank;
    }
    public static boolean isLogged()
    {
        return loggedBank!=null;
    }
    public static ArrayList<Bank> getBankList()
    {
        return BANKS;
    }


    //menu in BankLauncher
    public static void bankInit()
    {
        Init:
        while (true)
        {
            if(loggedBank != null)
            {
                Main.showMenuHeader("Bank Menu");
                //Show Accounts, New Account, Log Out
                Main.showMenu(31);
                Main.setOption();

                switch (Main.getOption())
                {
                    //Show Accounts
                    case 1 ->//Credit Accounts, Savings Accounts, All Accounts, Go Back
                    {
                        Main.showMenuHeader(loggedBank.getName() + "Accounts");
                        Main.showMenu(32);
                        Main.setOption();
                        if(Main.getOption() == 1)
                        {
                            Main.showMenuHeader("Credit Accounts");
                            loggedBank.showAccounts(CreditAccount.class);
                        }
                        else if(Main.getOption() == 2)
                        {
                            Main.showMenuHeader("Savings Accounts");
                            loggedBank.showAccounts(SavingsAccount.class);
                        }
                        else if(Main.getOption() == 3)
                        {
                            Main.showMenuHeader("All Accounts");
                            loggedBank.showAccounts(Account.class);
                        }
                        else
                        {
                            Main.print("Invalid Choice");
                            return;
                        }
                    }
                    //New Accounts
                    case 2->{}
                    case 3-> {
                        logout();
                        break Init;
                    }
                }
            }
            else
            {
                Main.print("No bank selected!");
                break;
            }
        }

    }

    private static void showAccounts()
    {
        while(true)
        {
            Main.showMenuHeader("Show Accounts");
            Main.showMenu(32);
            String show = Main.prompt("Enter Choice: ", true);
            //Credit Accounts, Savings Accounts, All Accounts, Go Back
            switch (show) {
                case "1":
                    loggedBank.showAccounts(CreditAccount.class);
                    break;
                case "2":
                    loggedBank.showAccounts(SavingsAccount.class);
                    break;
                case "3":
                    loggedBank.showAccounts(Account.class);
                    break;
                case "4":
                    return;//exit method
                default:
                    Main.print("Invalid Input!\n");
                    break;
            }
        }

    }

    public static void getshowAccounts()
    {
        showAccounts();
    }

    private static void newAccount()
    {

    }
    public static void bankLogin()
    {
        Login:
        while(true)
        {
            Main.showMenuHeader("Bank");
            Main.showMenu(3);
            Main.setOption();

            if(Main.getOption()==1)
            {
                BankLauncher.showBanksMenu();
                if(!BANKS.isEmpty())
                {
                    System.out.print("Enter Bank ID: ");
                    int bankID=input.nextInt();
                    input.nextLine();
                    for(Bank b: BANKS)
                    {
                        if(b.getID()==bankID)
                        {
                            int i= 0;
                            while(i<3)
                            {
                                i++;
                                System.out.print("Enter 4-Digit PIN: ");
                                String bankPin=input.nextLine();
                                if(b.getPasscode().equals(bankPin))
                                {
                                    setLogSession(b);
                                    bankInit();
                                    break Login;
                                }
                                if(i==3)
                                {
                                    Main.print("Too many unsuccessful attempts");
                                    break;
                                }
                                else
                                {
                                    System.out.println("Invalid pin");
                                }
                            }
                        }
                        else
                        {
                            Main.print("Bank Not found");

                        }
                    }

                }
            }
            else if (Main.getOption()==2) {
                return;
            }
        }
    }

//    return to private
    public static void setLogSession(Bank bank )
    {
        if(bank!=null)
        {
            loggedBank=bank;
        }
    }
    private static void logout()
    {
        loggedBank=null;
        Main.print("Logged out successfully!\n");
    }

    public static void createNewBank()
    {

        System.out.print("Enter Bank ID: ");
        int ID  = input.nextInt();
        input.nextLine();

        System.out.print("Enter New Bank Name: ");
        String name = input.nextLine();

        System.out.print("Enter Bank Passcode: ");
        String passcode = input.nextLine();

        System.out.print("Enter Deposit Limit: ");
        double DL = input.nextDouble();

        System.out.print("Enter Withdraw Limit: ");
        double WL = input.nextDouble();

        System.out.print("Enter Credit Limit: ");
        double CL = input.nextDouble();

        System.out.print("Enter Processing Fee: ");
        double PF  = input.nextDouble();

        Bank newBank = new Bank(ID, name, passcode, DL , WL, CL, PF);
        BANKS.add(newBank);
    }

    public static void showBanksMenu()
    {
        if(!BANKS.isEmpty())
        {
            Main.showMenuHeader("Available Banks");
            for(Bank bank: BANKS)
            {
                System.out.println("Bank ID: "+bank.getID()+" - "+bank.getName());
            }
        }
        else {
            Main.print("No Banks!");
        }
    }

    //return to private
    public static void addBank(Bank bank)
    {
        if(bank != null)
        {
            BANKS.add(bank);
        }
    }
    public static Bank getLoggedBank()
    {
        return loggedBank;
    }
    public static Bank getBank(Comparator<Bank> comparator, Bank bank)
    {
        for(Bank BANK:BANKS)
        {
            if(BANK.getClass().isInstance(comparator.getClass())||BANK.getClass().isInstance(bank.getClass()))
            {
                return BANK;
            }
        }
        return null;
    }

    public static Account findAccount(String accountNum)
    {
        for(Bank bank:BANKS)
        {
            Account account=loggedBank.getBankAccount(bank,accountNum);
            if(account!=null&&account.getAccountNumber().equals(accountNum))
            {
                return account;
            }
        }
        return null;
    }

    public static int bankSize()
    {
        return BANKS.size();
    }
}
