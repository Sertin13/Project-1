package Bank;

import java.util.*;
import Accounts.*;
import Main.*;

public class BankLauncher {
    private static ArrayList<Bank> BANKS=new ArrayList<>();
    private static Bank LoggedBank=null;
    private static final Scanner input = new Scanner(System.in);
    private static int IDCount=0;

    //Methods
    public static Bank getLoggedBank()
    {
        return LoggedBank;
    }
    public static boolean isLogged()
    {
        return LoggedBank!=null;
    }
    public static ArrayList<Bank> getBankList()
    {
        return BANKS;
    }
    public static void setBankSession(Bank bank)
    {
        if(bank!=null)
        {
            LoggedBank=bank;
        }
    }
    public static void incrementID(){ IDCount+=1;}
    public int getIDCount() {
        return IDCount;
    }

    public static void BankINIT() {
        while (true) {
            if (LoggedBank != null) {
                Main.showMenuHeader("Bank Menu");
                Main.showMenu(31);
                Main.setOption();

                switch (Main.getOption()) {
                    // Show Accounts
                    case 1 -> showAccounts();

                    // New Accounts
                    case 2 -> {
                        Main.showMenuHeader("Select Account Type");
                        Main.showMenu(33);
                        Main.setOption();

                        // New Credit Account
                        if (Main.getOption() == 1) {
                            CreditAccount newCred = LoggedBank.createNewCreditAccount();
                            if (newCred != null) {
                                LoggedBank.addNewAccount(newCred);
                                Main.print("Credit account created successfully!");
                            }
                        }
                        // New Savings Account
                        else if (Main.getOption() == 2) {
                            SavingsAccount newSave = LoggedBank.createNewSavingsAccount();
                            if (newSave != null) {
                                LoggedBank.addNewAccount(newSave);
                                Main.print("Savings account created successfully!");
                            }
                        } else {
                            Main.print("Invalid option! Please select a valid account type.");
                        }
                    }

                    // Log Out
                    case 3 -> {
                        logOut();
                        Main.print("Logged out successfully.");
                        return;
                    }

                    default -> Main.print("Invalid option! Please try again.");
                }
            } else {
                Main.print("No bank is currently logged in!");
                break;
            }
        }
    }


    private static void showAccounts()
    {
        do {
            Main.showMenuHeader("Show Account Options");
            Main.showMenu(32);
            String show = Main.prompt("Enter Choice: ", true);
            //"Credit Accounts", "Savings Accounts", "All Accounts", "Go Back"
            switch (show) {
                case "1" -> {

                    Main.showMenuHeader("Credit Accounts");
                    LoggedBank.showAccounts(CreditAccount.class);
                }
                case "2" -> {
                    Main.showMenuHeader("Savings Accounts");
                    LoggedBank.showAccounts(SavingsAccount.class);
                }
                case "3" -> {
                    Main.showMenuHeader("All Accounts");
                    LoggedBank.showAccounts(Account.class);
                }
                case "4" -> {
                    return;
                }
                default -> {
                    Main.print("Invalid input!\n");
                    continue;
                }
            }
            break;
        } while (true);
    }

    private static void newAccounts()
    {

    }

    public static void bankLogin()
    {
        showBanksMenu();
        System.out.print("Enter Bank Name: ");
        String Name = input.nextLine();
        for(Bank b: BANKS)
        {
            if(!b.getName().equals(Name))
            {
                Main.print("Bank not found!");
                return;
            }
            int tries=0;
            while(true)
            {
                tries+=1;
                if(tries==4){Main.print("Too many attempts!");break;}
                System.out.print("Enter Passcode: ");
                String Passcode = input.hasNextLine() ? input.nextLine() : "";
                if(b.getPasscode().equals(Passcode))
                {
                    setBankSession(b);
                    BankINIT();
                    break;
                }
                else
                {
                    Main.print("Invalid Pin");
                }
            }

        }
    }

    private static void setLogSession(Bank bank)
    {
        LoggedBank=bank;
        Main.print("Logged in to "+bank.getName());
    }

    private static void logOut()
    {
        LoggedBank=null;
        Main.print("Logged out successfully!\n");
    }

    public static void createNewBank() {
        Main.showMenuHeader("Create New Bank");

        System.out.print("Enter Bank Name: ");
        String name = input.hasNextLine() ? input.nextLine().trim() : "";

        System.out.print("Enter Bank Passcode (Enter - Set to default '1234'): ");
        String passcode = input.hasNextLine() ? input.nextLine().trim() : "1234";

        double depositLimit = 50000.0;
        double withdrawLimit = 50000.0;
        double creditLimit = 100000.0;
        double processingFee = 10.0;

        System.out.print("Enter Deposit Limit (Default: 50000.0): ");
        String depositInput = input.hasNextLine() ? input.nextLine().trim() : "";
        if (!depositInput.isEmpty()) {
            depositLimit = Double.parseDouble(depositInput);
        }

        System.out.print("Enter Withdraw Limit (Default: 50000.0): ");
        String withdrawInput = input.hasNextLine() ? input.nextLine().trim() : "";
        if (!withdrawInput.isEmpty()) {
            withdrawLimit = Double.parseDouble(withdrawInput);
        }

        System.out.print("Enter Credit Limit (Default: 100000.0): ");
        String creditInput = input.hasNextLine() ? input.nextLine().trim() : "";
        if (!creditInput.isEmpty()) {
            creditLimit = Double.parseDouble(creditInput);
        }

        System.out.print("Enter Processing Fee (Default: 10.0): ");
        String feeInput = input.hasNextLine() ? input.nextLine().trim() : "";
        if (!feeInput.isEmpty()) {
            processingFee = Double.parseDouble(feeInput);
        }

        for (Bank b : getBankList()) {
            if (b.getName().equalsIgnoreCase(name)) {
                Main.print("Bank with the name \"" + name + "\" already exists!");
                return;
            }
        }

        addBank(new Bank(IDCount, name, passcode, depositLimit, withdrawLimit, creditLimit, processingFee));
        Main.print("Bank \"" + name + "\" created successfully!");
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

    private static void addBank(Bank bank)
    {
        if(bank!=null)
        {
            BANKS.add(bank);
        }
    }

    public static Bank getBank(Comparator<Bank> bankComparator, Bank bank) {
        return BANKS.stream().filter(b -> bankComparator.compare(b, bank) == 0).findFirst().orElse(null);
    }

    public static Account findAccount(String accountNumber)
    {
        for(Bank bank:BANKS)
        {
            for(Account account:bank.getBANKACCOUNTS())
//            Account account=LoggedBank.getBankAccount(bank,accountNumber);
            {
                if (account.getAccountNumber().equals(accountNumber)) {
                    return account;
                }
            }

        }
        return null;
    }

    public static int bankSize()
    {
        return BANKS.size();
    }
}
