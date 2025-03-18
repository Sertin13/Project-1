package Launchers;

import Accounts.*;
import Bank.*;
import java.util.*;
import Main.*;

public class SavingAccountLauncher extends AccountLauncher {

    private static final Scanner input = new Scanner(System.in);


//    public static void login(SavingsAccount acc)
//    {
//        loggedAccount = acc;
//        Main.print("Login to "+ loggedAccount.getAccountNumber()+" success...\n");
//    }
//    public static void logout()
//    {
//        loggedAccount=null;
//        Main.print("Logout success...\n");
//    }

    //MENU in SavingAccountLauncher
    public static void savingsAccountInit()
    {
        //Test
        // Ensure there's a logged-in account
        Account current = AccountLauncher.getLoggedAccount();

        if (current instanceof SavingsAccount) {
            //"Show Balance", "Deposit", "Withdraw", "Fund Transfer",
            //            "Show Transactions", "Logout"
            while (true) {
                Main.showMenuHeader("Savings Account Menu");
                Main.showMenu(51);
                Main.setOption();

                if (Main.getOption() == 1) {
                    System.out.println(((SavingsAccount) current).getAccountBalance());
                }
                else if (Main.getOption() == 2) {
                    System.out.print("Enter Deposit Amount: ");
                    while (!input.hasNextDouble()) {
                        System.out.println("Invalid amount! Please enter a valid number.");
                        input.next();
                    }
                    double amount = input.nextDouble();
                    input.nextLine();

                    if (amount > 0) {
                        depositProcess(amount);
                    } else {
                        System.out.println("Invalid deposit amount! Must be greater than zero.");
                    }
                }
                else if (Main.getOption() == 3) {
                    System.out.print("Enter Withdrawal Amount: ");
                    while (!input.hasNextDouble()) {
                        System.out.println("Invalid amount! Please enter a valid number.");
                        input.next();
                    }
                    double amount = input.nextDouble();
                    input.nextLine();

                    if (amount > 0) {
                        withdrawProcess(amount);
                    } else {
                        System.out.println("Invalid withdrawal amount!");
                    }
                }
                else if (Main.getOption() == 4) {
                    System.out.print("Enter Target Account Number: ");
                    String targetAccNum = input.next();

                    System.out.print("Enter Amount to Transfer: ");
                    while (!input.hasNextDouble()) {
                        System.out.println("Invalid amount! Please enter a valid number.");
                        input.next();
                    }
                    double amount = input.nextDouble();
                    input.nextLine();

                    if (amount > 0) {
                        fundTransferProcess(targetAccNum, amount);
                    } else {
                        System.out.println("Invalid transfer amount!");
                    }
                }
                else if (Main.getOption() == 5) {
                    current.getTransactionInfo();
                }
                else if (Main.getOption() == 6) {
                    break;
                } else {
                    System.out.println("Invalid input! Please enter a valid option.");
                }
            }
        } else {
            System.out.println("Invalid account type. Access denied.");
        }
    }

    private static void depositProcess(double amount)
    {
        //Test
        // Implementation logic here
        System.out.println("Processing deposit of: ₱" + amount);

//        Account current = getLoggedAccount();
//        if (current instanceof SavingsAccount) {
//            if (amount > 0 && ((SavingsAccount) current).getHasEnoughBalance(amount)) {
//                ((SavingsAccount) current).updateAccountBalance(-amount);
//                System.out.println("Withdrawal successful. " + ((SavingsAccount) current).getAccountBalance());
//            } else {
//                System.out.println("Insufficient balance!");
//            }
//        }
    }


    private static void withdrawProcess(double amount)
    {
        //Test
        // Implementation logic here
        System.out.println("Processing withdrawal of: ₱" + amount);
//        Account current = getLoggedAccount();
//        if (current instanceof SavingsAccount) {
//            if (amount > 0 && ((SavingsAccount) current).getHasEnoughBalance(amount)) {
//                ((SavingsAccount) current).updateAccountBalance(-amount);
//                System.out.println("Withdrawal successful. " + ((SavingsAccount) current).getAccountBalance());
//            } else {
//                System.out.println("Insufficient balance!");
//            }
//        }
//    }

    }
//    //getter
//    public static double getwithdrawProcess(double amount) {
//        withdrawProcess(amount);
//    }

    private static void fundTransferProcess(String targetAccNum, double amount) {
        //Test
        // Implementation logic here
        System.out.println("Transferring ₱" + amount + "To Account: " + targetAccNum);

//        Account current = getLoggedAccount(); // Get the logged-in account
//
//        if (current == null) {
//            System.out.println("No account is logged in!");
//            return;
//        }
//
//        if (!(current instanceof SavingsAccount)) {
//            System.out.println("Only Savings Accounts can perform fund transfers!");
//            return;
//        }
//
//        Account recipient = BankLauncher.findAccount(targetAccNum); // Find the recipient account
//
//        if (recipient == null || !(recipient instanceof SavingsAccount)) {
//            System.out.println("Invalid recipient account!");
//            return;
//        }
//
//        try {
//            ((SavingsAccount) current).transfer(recipient, amount); // Perform transfer
//            System.out.println("Transfer successful! Transferred ₱" + amount + " to " + targetAccNum);
//        } catch (IllegalAccountType e) {
//            System.out.println("Transfer failed: " + e.getMessage());
//        }

    }

//    //getter
//    public static void getfundTransferProcess(String targetAccNum, double amount)
//    {
//        fundTransferProcess(targetAccNum, amount);
//    }

    protected static Account getLoggedAccount() {
        Account check = AccountLauncher.getLoggedAccount(); // Get the logged account

        if (check != null) {
            if (check instanceof SavingsAccount) { // Check if it's a SavingsAccount
                return null; // Return null if it's a SavingsAccount
            }
        }

        return check; // Return the account if it's not a SavingsAccount
    }
}
