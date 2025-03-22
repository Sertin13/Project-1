package Bank;

import Accounts.*;
import Main.*;
import java.util.*;

public class Bank {
    private int ID;
    private String name;
    private String passcode;
    private double DEPOSITLIMIT, WITHDRAWLIMIT, CREDITLIMIT;
    private double processingFee=10.0;
    private ArrayList<Account> BANKACCOUNTS=new ArrayList<>();
    private static final Scanner input = new Scanner(System.in);
    private int AccountID=1000;

    //Constructors
    public Bank(int ID, String Name, String Passcode)
    {
        this.ID=ID;
        this.name=Name;
        this.passcode=Passcode;
        this.DEPOSITLIMIT=50000.0;
        this.WITHDRAWLIMIT=50000.0;
        this.CREDITLIMIT=100000.0;
    }
    public Bank(int ID,String Name, String Passcode,double DL, double WL, double CL, double Fee)
    {
        this.ID=ID;
        this.name=Name;
        this.passcode=Passcode;
        this.DEPOSITLIMIT=DL;
        this.WITHDRAWLIMIT=WL;
        this.CREDITLIMIT=CL;
        this.processingFee=Fee;
        BankLauncher.incrementID();
    }

    //Bank Methods
    public ArrayList<Account> getBANKACCOUNTS()
    {
        return BANKACCOUNTS;
    }
    public int getID()
    {
        return ID;
    }
    public String getPasscode()
    {
        return passcode;
    }
    public String getName() {
        return name;
    }

    public <T> void showAccounts(Class<T> AccountType)
    {
            boolean found = false;
            for (Account account : BANKACCOUNTS) {
                if (AccountType.isInstance(account)) {
                    System.out.println(account);
                    found = true;
                }
            }
            if (!found) {
                System.out.println("There are currently no accounts stored.");
            }

    }

    public Account getBankAccount(Bank bank, String accountNumber) {
        if (bank == null || accountNumber == null || accountNumber.isEmpty()) {
            System.out.println("Invalid bank or account number.");
            return null;
        }

        for (Account account : bank.getBANKACCOUNTS()) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }

        System.out.println("Account not found in the specified bank.");
        return null;
    }


    public ArrayList<Field<String, ?>> createNewAccount() {
        ArrayList<Field<String, ?>> accountFields = new ArrayList<>();

        Main.print("Auto Generated ID Number: " + AccountID);

        // List of field prompts and labels for better scalability
        String[][] fieldDetails = {
                {"PIN", "Enter PIN: "},
                {"First Name", "Enter first name: "},
                {"Last Name", "Enter last name: "},
                {"Email", "Enter email: "}
        };

        // Dynamically create fields based on the details above
        for (String[] detail : fieldDetails) {
            Field<String, String> field = new Field<String, String>(detail[0], String.class, "", new Field.StringFieldValidator());
            field.setFieldValue(detail[1]);
            accountFields.add(field);
        }

        return accountFields;
    }


    public SavingsAccount createNewSavingsAccount() {
        List<Field<String, ?>> accountFields = createNewAccount();

        String pin = accountFields.get(0).getFieldValue();
        String firstName = accountFields.get(1).getFieldValue();
        String lastName = accountFields.get(2).getFieldValue();
        String email = accountFields.get(3).getFieldValue();

        // Efficient input and validation for the initial deposit
        System.out.print("Enter Initial Deposit (Press Enter to skip): ");
        double initialDeposit = 0.0;

        try {
            String depositInput = input.nextLine().trim();
            initialDeposit = depositInput.isEmpty() ? 0.0 : Math.max(Double.parseDouble(depositInput), 0.0);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Setting initial deposit to 0.");
        }

        AccountID++;
        Main.print("Account " + AccountID + " created successfully!");

        return new SavingsAccount(this, String.valueOf(AccountID), pin, firstName, lastName, email, initialDeposit);
    }


    public CreditAccount createNewCreditAccount() {
        List<Field<String, ?>> accountFields = createNewAccount();

        String pin = accountFields.get(0).getFieldValue();
        String firstName = accountFields.get(1).getFieldValue();
        String lastName = accountFields.get(2).getFieldValue();
        String email = accountFields.get(3).getFieldValue();

        AccountID++;
        Main.print("Account " + AccountID + " created successfully!");

        return new CreditAccount(this, String.valueOf(AccountID), pin, firstName, lastName, email);
    }


    public void addNewAccount(Account account) {
        if (account == null || BankLauncher.getLoggedBank() == null) {
            Main.print("Invalid account or no bank is logged in.");
            return;
        }

        if (accountExists(BankLauncher.getLoggedBank(), account.getAccountNumber())) {
            Main.print("Account already exists.");
        } else {
            BANKACCOUNTS.add(account);
            Main.print("Account successfully added.");
        }
    }

    public static boolean accountExists(Bank bank, String accountNumber) {
        if (bank == null || accountNumber == null || accountNumber.isEmpty()) {
            return false;
        }

        return bank.BANKACCOUNTS.stream()
                .anyMatch(account -> account.getAccountNumber().equals(accountNumber));
    }


    public String toString()
    {
        return "\nBank Name: "+name+"\n"+getCreditLimit()+"\nRegistered Accounts: "+BANKACCOUNTS.size();
    }

    public double getDepositLimit() {
        return DEPOSITLIMIT;
    }

    public double getCreditLimit() {
        return CREDITLIMIT;
    }

    public double getProcessingFee() {
        return processingFee;
    }


    //Sub classes
    public class Bankcomparator implements Comparator<Bank> {
//        @Override
        public int compare(Bank b1, Bank b2) {
            return b1.getName().compareTo(b2.getName());
        }
    }
    public class BankCredComparator implements Comparator<Bank> {
        @Override
        public int compare(Bank b1, Bank b2) {
            return b1.getPasscode().compareTo(b2.getPasscode());
        }
    }
    public static class BankIdComparator implements Comparator<Bank> {
        @Override
        public int compare(Bank b1, Bank b2) {
            return Integer.compare(b1.getID(), b2.getID());
        }
    }
}



