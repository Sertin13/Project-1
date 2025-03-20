package Launchers;

import Accounts.*;
import Bank.*;
import java.util.*;
import Main.*;

public class SavingAccountLauncher extends AccountLauncher
{

    private static final Scanner input = new Scanner(System.in);

    //MENU in SavingAccountLauncher
    public static void savingsAccountInit(SavingsAccount found) throws IllegalAccountType {
        if (found != null) {
            while (true) {
                Main.showMenuHeader("Savings Account Menu");
                Main.showMenu(51);
                Main.setOption();

                // Show balance
                if (Main.getOption() == 1) {
                    Main.print(Objects.requireNonNull(getLoggedAccount()).showBalance());
                }
                // Deposit
                else if (Main.getOption() == 2) {
                    System.out.print("Enter Deposit amount: ");
                    int amount = input.nextInt();
                    input.nextLine();
                    depositProcess(amount);
                }
                // Withdraw
                else if (Main.getOption() == 3) {
                    System.out.print("Enter Withdraw amount: ");
                    int amount = input.nextInt();
                    input.nextLine();
                    withdrawProcess(-amount);
                }
                // Fund Transfer
                else if (Main.getOption() == 4) {
                    transfer:
                    while (true) {
                        Main.showMenuHeader("Fund Transfer");
                        Main.print("[1] Account to Account Fund Transfer\n[2] Bank to Bank Fund Transfer\n[3] Return");
                        String choice = Main.prompt("Enter Choice: ", true);

                        // Account to Account Transfer
                        if (choice.equals("1")) {
                            Main.showMenuHeader("Find Target Account");
                            String accNum = Main.prompt("Enter Account Number: ", true);
                            Account search = BankLauncher.findAccount(accNum);

                            SavingsAccount target = (SavingsAccount) search;
                            if (target != null) {
                                if (target.getAccountNumber().equals(found.getAccountNumber())) {
                                    Main.print("Cannot transfer funds to yourself!");
                                    break transfer;
                                }

                                Main.print("Found Account: " + target.getAccountNumber());
                                double amount = Double.parseDouble(Main.prompt("Enter amount: ", true));
                                boolean foundCheck = found.transfer(target, amount);

                                if (foundCheck) {
                                    Main.print("Successfully sent ₱" + amount + " to " + target.getAccountNumber());
                                }
                                break transfer;
                            }
                            Main.print("Account " + accNum + " not found!");
                            break transfer;
                        }
                        // Bank to Bank Transfer
                        else if (choice.equals("2")) {
                            bankLogin:
                            while (true) {
                                Bank targetBank = null;
                                while (targetBank == null) {
                                    BankLauncher.showBanksMenu();
                                    int bankID = Integer.parseInt(Main.prompt("Enter Target Bank ID(0 - Exit): ", true));
                                    Bank currentBank = BankLauncher.getLoggedBank();

                                    if (currentBank.getID() == bankID) {
                                        Main.print("Target Bank must not be the current Bank!\nSelect another Bank!");
                                        break;
                                    }
                                    if (bankID == 0) {
                                        break;
                                    }
                                    for (Bank b : BankLauncher.getBankList()) {
                                        if (b.getID() == bankID) {
                                            targetBank = b;
                                        }
                                    }
                                }

                                if (targetBank != null) {
                                    SavingsAccount targetAccount = null;
                                    int attempts = 0;

                                    finding:
                                    while (true) {
                                        attempts++;
                                        if (attempts == 4) {
                                            Main.print("Too many unsuccessful attempts");
                                            break;
                                        }

                                        String targetAccNum = Main.prompt("Enter Target Account Number: ", true);
                                        for (Account account : targetBank.getBANKACCOUNTS()) {
                                            if (account.getAccountNumber().equals(targetAccNum)) {
                                                if (account instanceof SavingsAccount) {
                                                    targetAccount = (SavingsAccount) account;
                                                    break finding;
                                                }
                                            }
                                        }
                                        Main.print("Account not found!");
                                    }

                                    if (targetAccount != null) {
                                        Main.print("Found Account: " + targetAccount.getAccountNumber());
                                        double amount = Double.parseDouble(Main.prompt("Enter amount: ", true));
                                        boolean foundCheck = found.withdrawal(amount);
                                        boolean check = targetAccount.cashDeposit(amount);

                                        if (!foundCheck) {
                                            Main.print("Insufficient Balance");
                                            break transfer;
                                        } else if (check) {
                                            Main.print("Successfully sent ₱" + amount + " to " + targetAccount.getAccountNumber());
                                            found.addNewTransaction(found.getAccountNumber(), Transaction.Transactions.FundTransfer, "Sent ₱" + amount + " to " + targetAccount.getAccountNumber());
                                            targetAccount.addNewTransaction(targetAccount.getAccountNumber(), Transaction.Transactions.FundTransfer, "Received ₱" + amount + " from " + found.getAccountNumber());
                                            break transfer;
                                        }
                                        Main.print("Fund Transfer Unsuccessful!");
                                    }
                                    Main.print("Account not Found!");
                                }
                                break;
                            }
                        }
                        // Exit Fund Transfer
                        else if (choice.equals("3")) {
                            break;
                        } else {
                            Main.print("Invalid input!");
                        }
                    }
                }
                // Show Transactions
                else if (Main.getOption() == 5) {
                    Main.print(found.getTransactionInfo());
                }
                // Logout
                else if (Main.getOption() == 6) {
                    break;
                } else {
                    Main.print("Invalid input");
                }
            }
        }
    }

    private static  void depositProcess(double amount)
    {
        System.out.println("Processing deposit of: ₱" + amount);
        boolean check= Objects.requireNonNull(getLoggedAccount()).cashDeposit(amount);
        if(check){
            Main.print("Deposit Successful!");
            SavingsAccount account = getLoggedAccount();
            account.addNewTransaction(account.getAccountNumber(),Transaction.Transactions.Deposit,"Deposit amount of ₱"+amount+" to Account: "+account.getAccountNumber());
        }


    }


    public static void withdrawProcess(double amount)
    {
        System.out.println("Processing withdrawal of: ₱" + amount);
        SavingsAccount account = getLoggedAccount();
        if (account != null)
        {
            boolean check= account.withdrawal(-amount);
            if(check)
            {
                Main.print("Withdrawal Successful!");
                account.addNewTransaction(account.getAccountNumber(), Transaction.Transactions.Withdraw, "Withdraw amount of ₱"+amount+" from Account: "+account.getAccountNumber());
            }
            else
            {
                Main.print("Withdrawal unsuccessful!\nInsufficient Balance");
            }
        }
    }

    private static void fundTransferProcess(String recipientAcc, double amount)
    {
        System.out.println("Transferring ₱" + amount + "To Account: " + recipientAcc);
    }

    protected static SavingsAccount getLoggedAccount()
    {
        Account check = AccountLauncher.getLoggedAccount();
        if (check instanceof SavingsAccount)
        {
            return (SavingsAccount) check;

        }
            System.out.println("DEBUG: No SavingsAccount found for logged user.");
            return null;
    }
}

