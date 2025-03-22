package Launchers;

import Accounts.*;
import Bank.*;
import Launchers.*;
import Main.*;

import java.util.Objects;

public class CreditAccountLauncher extends AccountLauncher
{
    //Check if correct implementation & Simplyfied the code structure thus easy to read
    public static void credAccountInit() throws IllegalAccountType {
        cred:
        while(true)
        {
            Account current = AccountLauncher.getLoggedAccount();
            Class<CreditAccount> creditAccountClass = CreditAccount.class;

            if (creditAccountClass.isInstance(current)) {
                CreditAccount creditAccount = (CreditAccount) current;

                Main.showMenuHeader("Credit Account Menu");
                Main.showMenu(41);
                Main.setOption();

                //"Show Credits", "Pay", "Recompense", "Show Transactions", "Logout"
                switch (Main.getOption()) {
                    case 1 -> Main.print(creditAccount.getLoanStatement());
                    case 2 -> creditPaymentProcess(creditAccount);
                    case 3 -> creditRecompenseProcess(creditAccount);
                    case 4 -> Main.print(creditAccount.getTransactionsInfo());
                    case 5 -> {
                        Main.print("Logging out...");
                        break cred;
                    }
                    default -> Main.print("Invalid option! Please select again.");
                }
            } else {
                Main.print("Invalid account.");
            }
        }

    }

    private static void creditPaymentProcess(CreditAccount creditAccount) throws IllegalAccountType {
        int attempts = 0;

        while (attempts < 2) {
            attempts++;

            // Input field for payment amount
            Field<Double, Double> amountField = new Field<Double, Double>("amount", Double.class, 0.0, new Field.DoubleFieldValidator());
            amountField.setFieldValue("Enter Amount: ");
            double amountToPay = amountField.getFieldValue();

            CreditAccount loggedCreditAccount = getLoggedAccount();

            if (loggedCreditAccount != null) {
                double processingFee = loggedCreditAccount.getBANK().getProcessingFee();
                double totalAmount = amountToPay + processingFee;
                boolean paymentSuccess = loggedCreditAccount.pay(creditAccount, totalAmount);

                if (paymentSuccess) {
                    System.out.printf("Transfer of ₱%.2f processed successfully.%n", amountToPay);
                    return; // Exit method after successful payment
                } else {
                    System.out.println("Insufficient balance. Please try again.");
                }
            } else {
                System.out.println("No logged-in credit account found!");
                return; // Exit if no logged account is found
            }
        }

        System.out.println("Maximum attempts reached. Payment process terminated.");
    }


    //Check if correct implementation
    private static void creditRecompenseProcess(CreditAccount creditAccount)
    {
        Field<Double, Double> amountField = new Field<Double,Double>("amount",
                Double.class, 0.0, new Field.DoubleFieldValidator());
        amountField.setFieldValue("Enter amount: ");
        double amountToPay = amountField.getFieldValue();

        // Attempt the recompense and provide feedback
        boolean recompenseSuccess = creditAccount.recompense(amountToPay);
        if (recompenseSuccess){
            System.out.println("Credit recompense of ₱" + amountToPay + " processed successfully.");
        } else {
            System.out.println("Credit recompense unsuccessful. Please Retry.");
        }
    }

    //Check if correct implementation
    protected static CreditAccount getLoggedAccount() {
        Bank loggedBank = BankLauncher.getLoggedBank();

        if (loggedBank == null) {
            System.out.println("No bank is currently logged in.");
            return null;
        }

        return loggedBank.getBANKACCOUNTS().stream()
                .filter(account -> account instanceof CreditAccount)
                .map(account -> (CreditAccount) account)
                .findFirst()
                .orElse(null);
    }
}