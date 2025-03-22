package Bank;

//import java.sql.Savepoint;
import Main.*;
import Accounts.*;
import java.util.*;

public class BankLauncher {

    private static final List<Bank> BANKS = new ArrayList<>();
    private static Bank loggedBank = null;
    private static final Scanner input = new Scanner(System.in);
    private static int IDCount=0;

    // Getters
    public static Bank getLoggedBank()
    {
        return loggedBank;
    }
    public static boolean isLogged()
    {
        return loggedBank != null;
    }
    public static List<Bank> getBankList()
    {
        return BANKS;
    }

    // Setters
    public static void setBankSession(Bank bank)
    {
        if (bank != null) loggedBank = bank;
    }

    public static void incrementID()
    {
        IDCount+=1;
    }
    public int getIDCount() {
        return IDCount;
    }

    // Bank Initialization
    public static void bankInit()
    {
        Init:
        while (true) {
            if (loggedBank != null) {
                Main.showMenuHeader("Bank Menu");
                Main.showMenu(31);
                Main.setOption();

                switch (Main.getOption()) {
                    case 1 -> showAccounts(); // Show Accounts
                    case 2 -> createNewAccount(); // New Accounts
                    case 3 -> { logout(); break Init; } // Log Out
                    default -> Main.print("Invalid Option. Please try again.");
                }
            } else {
                Main.print("No bank selected!");
                break;
            }
        }

    }

    private static void showAccounts() {
        while (true) {
            Main.showMenuHeader("Show Account Options");
            Main.showMenu(32);
            String choice = Main.prompt("Enter Choice: ", true);

            switch (choice) {
                case "1" -> loggedBank.showAccounts(CreditAccount.class);
                case "2" -> loggedBank.showAccounts(SavingsAccount.class);
                case "3" -> loggedBank.showAccounts(Account.class);
                case "4" -> { return; }
                default -> Main.print("Invalid input! Try again.");
            }
        }
    }

    private static void createNewAccount() {
        Main.showMenuHeader("Select Account Type");
        Main.showMenu(33);
        Main.setOption();

        switch (Main.getOption()) {
            case 1 -> Optional.ofNullable(loggedBank.createNewCreditAccount()).ifPresent(loggedBank::addNewAccount);
            case 2 -> Optional.ofNullable(loggedBank.createNewSavingsAccount()).ifPresent(loggedBank::addNewAccount);
            default -> Main.print("Invalid Option");
        }
    }

    public static void bankLogin() {
        while (true) {
            Main.showMenuHeader("Bank");
            Main.showMenu(3);
            Main.setOption();

            if (Main.getOption() == 1) {
                showBanksMenu();
                if (BANKS.isEmpty()) {
                    Main.print("No banks available!");
                    return;
                }

                System.out.print("Enter Bank ID: ");
                int bankID = input.nextInt();
                input.nextLine(); // Clear buffer

                Bank selectedBank = BANKS.stream().filter(b -> b.getID() == bankID).findFirst().orElse(null);
                if (selectedBank == null) {
                    Main.print("Bank not found!");
                    continue;
                }

                // 3 attempts for PIN entry
                for (int attempts = 0; attempts < 3; attempts++) {
                    System.out.print("Enter 4-Digit PIN: ");
                    String bankPin = input.nextLine();

                    if (bankPin.matches("\\d{4}") && selectedBank.getPasscode().equals(bankPin)) {
                        setBankSession(selectedBank);
                        bankInit();
                        return;
                    }
                    Main.print(attempts == 2 ? "Too many unsuccessful attempts" : "Invalid PIN, try again.");
                }
            } else if (Main.getOption() == 2) {
                return; // Exit login process
            }
        }
    }

    private static void logout() {
        loggedBank = null;
        Main.print("Logged out successfully!\n");
    }

    public static void createNewBank()
    {
        Main.showMenuHeader("Create New Bank");
        if (input == null){
            throw new IllegalStateException("Scanner is not initialized.");
        }
        System.out.print("Enter Bank Name: ");
        String name = input.hasNextLine() ? input.nextLine().trim() : "";

        System.out.print("Enter Bank Passcode (Enter - Set to default): ");
        String passcode = input.hasNextLine() ? input.nextLine().trim() : "1234";

        System.out.print("Enter Deposit Limit(Enter - Set to default): ");
        double depositLimit = input.hasNextDouble() ? input.nextDouble() : 50000.0;
        if (input.hasNextLine()) input.nextLine(); // Consume newline

        System.out.print("Enter Withdraw Limit(Enter - Set to default): ");
        double withdrawLimit = input.hasNextDouble() ? input.nextDouble() : 50000.0;
        if (input.hasNextLine()) input.nextLine();

        System.out.print("Enter Credit Limit (Enter - Set to default): ");
        double creditLimit = input.hasNextDouble() ? input.nextDouble() : 100000.0;
        if (input.hasNextLine()) input.nextLine();

        System.out.print("Enter Processing Fee (Enter - Set to default): ");
        double processingFee = input.hasNextDouble() ? input.nextDouble() : 10.0;
        if (input.hasNextLine()) input.nextLine();

        addBank(new Bank(IDCount, name, passcode, depositLimit, withdrawLimit, creditLimit, processingFee));

    }


    public static void showBanksMenu() {
        if (BANKS.isEmpty()) {
            Main.print("No Banks!");
        } else {
            Main.showMenuHeader("Available Banks");
            BANKS.forEach(bank -> System.out.println("Bank ID: " + bank.getID() + " - " + bank.getName()));
        }
    }
    //return to private
    public static void addBank(Bank bank) {
        if (bank != null) BANKS.add(bank);
    }

    public static Bank getBank(Comparator<Bank> bankComparator, Bank bank) {
        return BANKS.stream().filter(b -> bankComparator.compare(b, bank) == 0).findFirst().orElse(null);
    }

    public static Account findAccount(String accountNumber) {
        for(Bank bank:BANKS)
        {
            Account account=loggedBank.getBankAccount(bank,accountNumber);
            if(account!=null && account.getAccountNumber().equals(accountNumber))
            {
                return account;
            }

        }
        return null;
    }

    public static int bankSize() {
        return BANKS.size();
    }


}
