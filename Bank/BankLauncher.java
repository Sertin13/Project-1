package Bank;

import java.sql.Savepoint;
import Main.*;
import Accounts.*;

import java.util.*;

public class BankLauncher {

    private static final List<Bank> BANKS = new ArrayList<>();
    private static Bank loggedBank = null;
    private static final Scanner input = new Scanner(System.in);

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

                    case 2 -> { // New Accounts
                        Main.showMenuHeader("Select Account Type");
                        Main.showMenu(33);
                        Main.setOption();

                        switch (Main.getOption()) {
                            case 1 -> { // New Credit Account
                                CreditAccount newCredit = loggedBank.createNewCreditAccount();
                                if (newCredit != null) {
                                    loggedBank.addNewAccount(newCredit);
                                }
                            }
                            case 2 -> { // New Savings Account
                                SavingsAccount newSavings = loggedBank.createNewSavingsAccount();
                                if (newSavings != null) {
                                    loggedBank.addNewAccount(newSavings);
                                }
                            }
                            default -> Main.print("Invalid Option");
                        }
                    }

                    case 3 -> { // Log Out
                        logout();
                        break Init;
                    }

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

    public static void createNewBank() {
        Main.showMenuHeader("Create New Bank");

        while (true) {
            try {
                int bankID = Integer.parseInt(Main.prompt("Enter Bank ID: ", true));
                if (BANKS.stream().anyMatch(b -> b.getID() == bankID)) {
                    Main.print("Bank ID already exists! Try again.");
                    continue;
                }

                String bankName = Main.prompt("Enter Bank Name: ", true).trim();
                if (bankName.isEmpty()) {
                    Main.print("Bank name cannot be empty! Try again.");
                    continue;
                }

                String bankPIN = Main.prompt("Enter 4-Digit PIN: ", true);
                if (!bankPIN.matches("\\d{4}")) {
                    Main.print("Invalid PIN! Must be exactly 4 digits.");
                    continue;
                }

                addBank(new Bank(bankID, bankName, bankPIN));
                Main.print("Bank successfully created!");
                return;
            } catch (NumberFormatException e) {
                Main.print("Invalid input! Bank ID must be a number.");
            }
        }
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
