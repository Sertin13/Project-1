//package Launchers;
//
//import java.sql.Savepoint;
//import java.util.*;
//import Accounts.*;
//import Main.*;
//
//import Bank.*;
//public class BankLauncher {
//    private static ArrayList<Bank> BANKS=new ArrayList<>();
//    private static Bank LoggedBank=null;
//    private static final Scanner input = new Scanner(System.in);
//
//    //Methods
//    public static Bank getLoggedBank()
//    {
//        return LoggedBank;
//    }
//    public static boolean isLogged()
//    {
//        return LoggedBank!=null;
//    }
//    public static ArrayList<Bank> getBankList()
//    {
//        return BANKS;
//    }
//    public static void setBankSession(Bank bank)
//    {
//        if(bank!=null)
//        {
//            LoggedBank=bank;
//        }
//    }
//
//    public static void BankINIT()
//    {
//        INIT:
//        while(true)
//        {
//            if(LoggedBank!=null)
//            {
//                Main.showMenuHeader("Bank Menu");
////        "Show Accounts", "New Accounts", "Log Out";
//                Main.showMenu(31);
//                Main.setOption();
//                switch (Main.getOption())
//                {
//                    //Show Accounts
//                    case 1->{
//                        showAccounts();
//                    }
//                    //New Accounts
//                    case 2->{
//                        //New account creation
//                        Main.showMenuHeader("Select Account Type");
//                        Main.showMenu(33);
//                        Main.setOption();
//                        //New Credit account
//                        if(Main.getOption()==1)
//                        {
//                            CreditAccount newCred=LoggedBank.createNewCreditAccount();
//                            if(newCred!=null)
//                            {
//                                LoggedBank.addNewAccount(newCred);
//                            }
//                        }
//                        //New Savings Account
//                        else if(Main.getOption()==2)
//                        {
//                            SavingsAccount newSave=LoggedBank.createNewSavingsAccount();
//                            if(newSave!=null)
//                            {
//                                LoggedBank.addNewAccount(newSave);
//                            }
//                        }
//                        else {Main.print("Invalid Option");}
//                    }
//                    case 3-> {
//                        logOut();
//                        break INIT;
//                    }
//                }
//            }
//            else
//            {
//                Main.print("No bank selected!");
//                break;
//            }
//        }
//
//    }
//
//    private static void showAccounts()
//    {
//        do {
//            Main.showMenuHeader("Show Account Options");
//            Main.showMenu(32);
//            String show = Main.prompt("Enter Choice: ", true);
//            //"Credit Accounts", "Savings Accounts", "All Accounts", "Go Back"
//            switch (show) {
//                case "1" -> {
//
//                    Main.showMenuHeader("Credit Accounts");
//                    LoggedBank.showAccounts(CreditAccount.class);
//                }
//                case "2" -> {
//                    Main.showMenuHeader("Savings Accounts");
//                    LoggedBank.showAccounts(SavingsAccount.class);
//                }
//                case "3" -> {
//                    Main.showMenuHeader("All Accounts");
//                    LoggedBank.showAccounts(Account.class);
//                }
//                case "4" -> {
//                    return;
//                }
//                default -> {
//                    Main.print("Invalid input!\n");
//                    continue;
//                }
//            }
//            break;
//        } while (true);
//    }
//
//    private static void newAccounts()
//    {
//
//    }
//
//    public static void bankLogin()
//    {
//        Login:
//        while(true)
//        {
//            Main.showMenuHeader("Bank");
//            Main.showMenu(3);
//            Main.setOption();
//            if(Main.getOption()==1)
//            {
//                BankLauncher.showBanksMenu();
//                if(!BANKS.isEmpty())
//                {
//                    System.out.print("Enter Bank ID: ");
//                    int bankID=input.nextInt();
//                    input.nextLine();
//                    for(Bank b: BANKS)
//                    {
//                        if(b.getID()==bankID)
//                        {
//                            int i= 0;
//                            while(i<3)
//                            {
//                                i++;
//                                System.out.print("Enter 4-Digit PIN: ");
//                                String bankPin=input.nextLine();
//                                if(b.getPasscode().equals(bankPin))
//                                {
//                                    setLogSession(b);
//                                    BankINIT();
//                                    break Login;
//                                }
//                                if(i==3)
//                                {
//                                    Main.print("Too many unsuccessful attempts");
//                                    break;
//                                }
//                                else
//                                {
//                                    System.out.println("Invalid pin");
//                                }
//                            }
//                        }
//
//                    }
//                    Main.print("Bank Not found");
//                }
//            }
//            else if (Main.getOption()==2) {
//                Main.print("Exiting Bank Login");
//                return;
//            }
//        }
//    }
//
//    private static void setLogSession(Bank bank)
//    {
//        LoggedBank=bank;
//        Main.print("Logged in to "+bank.getName());
//    }
//
//    private static void logOut()
//    {
//        LoggedBank=null;
//        Main.print("Logged out successfully!\n");
//    }
//
//    public static void createNewBank()
//    {
//        Main.showMenuHeader("Create New Bank");
//
//        int bankID;
//        String bankName, bankPIN;
//
//        while (true) {
//            try {
//                bankID = Integer.parseInt(Main.prompt("Enter Bank ID: ", true));
//                int finalBankID = bankID;
//                if (BANKS.stream().anyMatch(b -> b.getID() == finalBankID)) {
//                    Main.print("Bank ID already exists! Try again.");
//                    continue;
//                }
//
//                bankName = Main.prompt("Enter Bank Name: ", true).trim();
//                if (bankName.isEmpty()) {
//                    Main.print("Bank name cannot be empty! Try again.");
//                    continue;
//                }
//
//                bankPIN = Main.prompt("Enter 4-Digit PIN: ", true);
//                if (!bankPIN.matches("\\d{4}")) {
//                    Main.print("Invalid PIN! Must be exactly 4 digits.");
//                    continue;
//                }
//                break;
//            } catch (NumberFormatException e) {
//                Main.print("Invalid input! Bank ID must be a number.");
//            }
//        }
//
//        addBank(new Bank(bankID, bankName, bankPIN));
//        Main.print("Bank successfully created!");
//    }
//
//    public static void showBanksMenu()
//    {
//        if(!BANKS.isEmpty())
//        {
//            Main.showMenuHeader("Available Banks");
//            for(Bank bank: BANKS)
//            {
//                System.out.println("Bank ID: "+bank.getID()+" - "+bank.getName());
//            }
//        }
//        else {
//            Main.print("No Banks!");
//        }
//    }
//    //return to private
//    public static void addBank(Bank bank)
//    {
//        if(bank!=null)
//        {
//            BANKS.add(bank);
//        }
//    }
//
//    public static Bank getBank(Comparator<Bank> comparator, Bank bank )
//    {
//        for(Bank BANK:BANKS)
//        {
//            if(BANK.getClass().isInstance(comparator.getClass())||BANK.getClass().isInstance(bank.getClass()))
//            {
//                return BANK;
//            }
//        }
//        return null;
//    }
//
//    public static Account findaccount(String accountNumber)
//    {
//        for(Bank bank:BANKS)
//        {
//            Account account=LoggedBank.getBankAccount(bank,accountNumber);
//            if(account!=null && account.getAccountNumber().equals(accountNumber))
//            {
//                return account;
//            }
//
//        }
//        return null;
//    }
//
//    public static int bankSize()
//    {
//        return BANKS.size();
//    }
//}
