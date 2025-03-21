package Bank;

import Accounts.*;
import Main.*;
import java.util.*;

public class Bank {
    private int ID;
    private String name;
    private String passcode;
    private double DEPOSITLIMIT, WITHDRAWLIMIT, CREDITLIMIT;
    private double processingFee = 10.0;
    private ArrayList<Account> BANKACCOUNTS = new ArrayList<>();

    public Bank(int ID, String name, String passcode) {
        this.ID = ID;
        this.name = name;
        this.passcode = passcode;
        this.DEPOSITLIMIT = 12000.0;
        this.WITHDRAWLIMIT = 12000.0;
        this.CREDITLIMIT = 24000.0;

    }

    public Bank(int ID, String name, String passcode, double DL, double WL, double CL, double PF) {
        this.ID = ID;
        this.name = name;
        this.passcode = passcode;
        this.DEPOSITLIMIT = DL;
        this.WITHDRAWLIMIT = WL;
        this.CREDITLIMIT = CL;
        this.processingFee = PF;
    }

    //Bank Methods
    public ArrayList<Account> getBANKACCOUNTS() {
        return BANKACCOUNTS;
    }
    public int getID() {
        return ID;
    }
    public String getPasscode() {
        return passcode;
    }
    public String getName() {
        return name;
    }




    public <T> void showAccounts(Class<T> accountType) {
        boolean found = false;
        for (Account account : BANKACCOUNTS) {
            if (accountType.isInstance(account)) {
                System.out.println(account);
                found = true;
            }
        }
        if (!found) {
            System.out.println("There are currently no accounts stored.");
        }
    }

    public Account getBankAccount(Bank bank, String AccountNumber) {
        for (Bank b : BankLauncher.getBankList()) {
            if (b.equals(bank)) {
                for (Account account : b.getBANKACCOUNTS()) {
                    if (account.getAccountNumber().equals(AccountNumber)) {
                        return account;
                    }
                }
            }
        }
        return null;
    }

    public ArrayList<Field<String, ?>> createNewAccount() {
        ArrayList<Field<String, ?>> accountFields = new ArrayList<>();

        Field<String, ?> accountNumber = new Field<String, String>("ID Number", String.class, "", new Field.StringFieldValidator());
        accountNumber.setFieldValue("Enter ID number: ");
        accountFields.add(accountNumber);

        Field<String, String> ownerFName = new Field<String, String>("First Name", String.class, "", new Field.StringFieldValidator());
        ownerFName.setFieldValue("Enter First name: ");
        accountFields.add(ownerFName);

        Field<String, String> ownerLName = new Field<String, String>("Last Name", String.class, "", new Field.StringFieldValidator());
        ownerLName.setFieldValue("Enter Last name: ");
        accountFields.add(ownerLName);

        Field<String, String> ownerEmail = new Field<String, String>("Email", String.class, "", new Field.StringFieldValidator());
        ownerEmail.setFieldValue("Enter Email: ");
        accountFields.add(ownerEmail);

        Field<String, String> Pin = new Field<String, String>("PIN", String.class, "", new Field.StringFieldValidator());
        Pin.setFieldValue("Enter PIN: ");
        accountFields.add(Pin);

        Field<String, String> initialDepositField = new Field<String, String>("Initial Deposit", String.class, "", new Field.StringFieldValidator());
        initialDepositField.setFieldValue("Enter initial deposit: ");
        accountFields.add(initialDepositField);

        return accountFields;
    }

    public CreditAccount createNewCreditAccount() {
        List<Field<String, ?>> accountFields = createNewAccount();


        String accountNumber = accountFields.get(0).getFieldValue();
        String ownerFName = accountFields.get(1).getFieldValue();
        String ownerLName = accountFields.get(2).getFieldValue();
        String ownerEmail = accountFields.get(3).getFieldValue();
        String pin = accountFields.get(4).getFieldValue();


        CreditAccount newAcc = new CreditAccount(this, accountNumber, ownerFName, ownerLName, ownerEmail, pin);
        BANKACCOUNTS.add(newAcc);
        return newAcc;
    }

    public SavingsAccount createNewSavingsAccount() {
        List<Field<String, ?>> accountFields = createNewAccount();

        String accountNUM = accountFields.get(0).getFieldValue();
        String ownerFName = accountFields.get(1).getFieldValue();
        String ownerLName = accountFields.get(2).getFieldValue();
        String ownerEmail = accountFields.get(3).getFieldValue();
        String pin = accountFields.get(4).getFieldValue();
        double initialDeposit = Double.parseDouble(accountFields.get(5).getFieldValue());

        SavingsAccount newAcc = new SavingsAccount(this, accountNUM, ownerFName, ownerLName, ownerEmail, pin, initialDeposit);
        BANKACCOUNTS.add(newAcc);
        return newAcc;
    }

    public void addNewAccount(Account account) {
        if (!accountExists(BankLauncher.getLoggedBank(), account.getAccountNumber())) {
            BANKACCOUNTS.add(account);
            Main.print("Account added successfully!");
        } else {
            Main.print("Account already exists!");
        }
    }

    // Check if an account exists within a given bank
    public static boolean accountExists(Bank bank, String accountNumber) {
        if (bank != null)
        {
            for (Account account : bank.BANKACCOUNTS)
            { // Use bank instance, not Bank.BANKACCOUNTS
                if (account.getAccountNumber().equals(accountNumber))
                { // Fixed variable name
                    return true;
                }
            }
        }
        return false;
    }


    // Convert Bank object to a readable string format
    @Override
    public String toString() {
        return "Bank Name: " + name + "\nRegistered Accounts: " + BANKACCOUNTS.size();
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


    //subclass
    public class BankComparator implements Comparator<Bank> {
        @Override
        public int compare(Bank b1, Bank b2)
        {
            return b1.getName().compareTo(b2.getName());
        }
    }
    public class BankCredComparator implements Comparator<Bank>
    {
        @Override
        public int compare(Bank b1, Bank b2)
        {
                return b1.getPasscode().compareTo(b2.getPasscode());
        }
    }
    public static class BankIdComparator implements Comparator<Bank> {
        @Override
        public int compare(Bank b1, Bank b2)
        {
            return Integer.compare(b1.getID(), b2.getID());
        }
    }
}








