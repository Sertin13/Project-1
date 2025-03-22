package Launchers;

import Accounts.*;
import Bank.*;
import java.util.*;
import Main.*;

public class SavingsAccountLauncher extends AccountLauncher {

    private static final Scanner input = new Scanner(System.in);

    public static void savingsAccountInit(SavingsAccount found) throws IllegalAccountType {
        if (found != null)
        {
            while (true) {
                Main.showMenuHeader("Savings Account Menu");
                Main.showMenu(51);
                Main.setOption();
                //Show balance
                if (Main.getOption() == 1)
                {
                    Main.print(Objects.requireNonNull(getLoggedAccount()).showBalance());
                }
                //Deposit
                else if (Main.getOption() == 2) {
                    System.out.print("Enter Deposit amount: ");
                    int amount = input.nextInt();
                    input.nextLine();
                    depositProcess(amount);
                }
                //Withdraw
                else if (Main.getOption() == 3)
                {
                    System.out.print("Enter Withdraw amount: ");
                    int amount = input.nextInt();
                    input.nextLine();
                    withdrawProcess(-amount);
                }
                //Fund Transfer
                else if (Main.getOption() == 4)
                {
                    //select fund transfer type
                    transfer:
                    while(true)
                    {
                        Main.showMenuHeader("Fund Transfer");
                        Main.print("[1] Internal Fund Transfer\n[2] External Transfer\n[3] Return");
                        String choice= Main.prompt("Enter Choice: ",true);
                        //Account only
                        if(choice.equals("1"))
                        {
                            Main.showMenuHeader("Find Target Account");
                            String accNum= Main.prompt("Enter Account Number: ",true);
                            Account search= BankLauncher.findAccount(accNum);

                            SavingsAccount target= (SavingsAccount)search;
                            if(target != null)
                            {
                                if(target.getAccountNumber().equals(found.getAccountNumber()))
                                {
                                    Main.print("Cannot transfer funds to yourself!");
                                    break transfer;
                                }
                                if(target instanceof SavingsAccount)
                                {
                                    Main.print("Found Account: "+target.getAccountNumber());
                                    double amount=Double.parseDouble(Main.prompt("Enter amount: ",true));
                                    boolean foundCheck=found.transfer(target,amount);

                                    if(foundCheck) {
                                        Main.print(String.format("Successfully sent P%.2f to %s", amount, target.getAccountNumber()));

                                    }
                                    break transfer;
                                }
                                Main.print("Target account "+accNum+" is not a Savings Account!");
                                break transfer;
                            }
                            Main.print("Account "+accNum+" not found!");
                            break transfer;
                        }

                        //Different Bank
                        else if(choice.equals("2"))
                        {
                            Bank targetBank=null;
                            BankLauncher.showBanksMenu();
                            System.out.print("Enter Target Bank ID (0 - Exit): ");
                            String bankName=input.nextLine();
                            Bank currentBank= BankLauncher.getLoggedBank();
                            if(currentBank.getName().equals(bankName))
                            {
                                Main.print("Target Bank must not be the current Bank!\nSelect another Bank!");
                                break;
                            }
                            if(bankName.equals("0")){break ;}
                            for(Bank b:BankLauncher.getBankList())
                            {
                                if(b.getName().equals(bankName))
                                {
                                    targetBank=b;
                                    break;
                                }
                            }

                            if(targetBank!=null)
                            {
                                SavingsAccount targetAccount=null;
                                int tru=0;
                                finding:
                                while (true)
                                {
                                    tru+=1;
                                    if(tru==4){
                                        Main.print("Too many unsuccessful attempts");
                                        break ;
                                    }
                                    String targetAccNum= Main.prompt("Enter Target Account Number: ",true);
                                    for(Account account:targetBank.getBANKACCOUNTS())
                                    {
                                        if(account.getAccountNumber().equals(targetAccNum))
                                        {
                                            if(account instanceof SavingsAccount) {
                                                targetAccount =(SavingsAccount) account;
                                                break finding;
                                            }
                                        }
                                    }
                                    Main.print("Account not found!");
                                }
                                if(targetAccount!=null)
                                {
                                    Main.print("Found Account: "+targetAccount.getAccountNumber());
                                    double amount=Double.parseDouble(Main.prompt("Enter amount: ",true));
                                    boolean foundCheck=found.withdrawal(amount);
                                    boolean check=targetAccount.cashDeposit(amount);
                                    if(!foundCheck)
                                    {
                                        Main.print("Insufficient Balance");
                                        break transfer;
                                    }
                                    else if(check)
                                    {
                                        Main.print("Successfully sent ₱"+amount+" to "+targetAccount.getAccountNumber());
                                        found.addNewTransaction(found.getAccountNumber(), Transaction.Transactions.FundTransfer,"Sent ₱"+amount+" to "+targetAccount.getAccountNumber());
                                        targetAccount.addNewTransaction(targetAccount.getAccountNumber(), Transaction.Transactions.FundTransfer, "Received ₱"+amount+" from "+found.getAccountNumber());
                                        break transfer;
                                    }
                                    Main.print("Fund Transfer Unsuccessful!");
                                }
                                Main.print("Account not Found!");
                            }
                            Main.print("Bank Not Found");
                            break;

                        }
                        else if(choice.equals("3"))
                        {
                            break;
                        }
                        else
                        {
                            Main.print("Invalid input!");
                        }
                    }
                }
                //Show Transactions
                else if (Main.getOption() == 5)
                {
                    Main.print(found.getTransactionsInfo());
                }
                //logout
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
        if (account != null) {
            boolean check= account.withdrawal(-amount);
            if(check)
            {
                Main.print("Withdrawal Successful!");
                account.addNewTransaction(account.getAccountNumber(), Transaction.Transactions.Withdraw, "Withdraw amount of ₱"+amount+" from Account: "+account.getAccountNumber());
            }
            else {
                Main.print("Withdrawal unsuccessful!\nInsufficient Balance");
            }
        }

    }

    private static void fundTransferProcess(String recipientacc, double amount)
    {
        System.out.println("Transferring ₱" + amount + "To Account: " + recipientacc);
    }

    protected static SavingsAccount getLoggedAccount() {
        Account check = AccountLauncher.getLoggedAccount();
        if (check instanceof SavingsAccount) {
            return (SavingsAccount) check;
        }
        System.out.println("DEBUG: No SavingsAccount found for logged user.");
        return null;
    }
}
