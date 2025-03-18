package Launchers;



import Accounts.*;
import Bank.*;

import Main.*;



public class CreditAccountLauncher extends AccountLauncher
{
    public static void credAccountInit(CreditAccount loggedAccount) throws IllegalAccountType {
        Class<CreditAccount> creditAccountClass = CreditAccount.class;
        if(loggedAccount !=null)

        {
            Main.showMenuHeader("Credit Account Menu");
            Main.showMenu(41);
            Main.setOption();
            //"Show Credits", "Pay", "Recompense", "Show Transactions", "Logout"
            switch (Main.getOption()) {
                case 1 -> {

                }
                case 2 ->
                {
                    creditPaymentProcess(loggedAccount);
                }
                case 3 ->
                {
                    loggedAccount.getTransactionInfo();
                }
            }
        }
        else

        {
        Main.print("Invalid account");
        }
}

private static void creditPaymentProcess(CreditAccount loggedAccount) throws IllegalAccountType {
    if(loggedAccount.getClass().isInstance(CreditAccount.class))
    {
        Field<String, String> targetaccountnumberField = new Field<String,String>("targetAccount",
                String.class, " ", new Field.StringFieldValidator());
        targetaccountnumberField.setFieldValue("Enter target account number: ");
        String accountNumber = targetaccountnumberField.getFieldValue();

        // Field for entering the amount to pay
        Field<Double, Double> amountField = new Field<Double, Double>("amount",
                Double.class, 0.0, new Field.DoubleFieldValidator());
        amountField.setFieldValue("Enter target account number: "); // Mistake in prompt text, should be "Enter amount to pay:"
        double amountToPay = amountField.getFieldValue();

        // Find the target account and attempt the payment
        Account targetAccount = BankLauncher.findAccount(accountNumber);
        double processingFee = loggedAccount.getBank().getProcessingFee();
        double payAmountWithFee = amountToPay + processingFee;
        boolean paySuccess = loggedAccount.pay(targetAccount, payAmountWithFee);

        if (paySuccess){
            System.out.println("\u001B[32mTransfer of ₱" + amountToPay + " processed successfully.\u001B[0m");
        }
        else{
            System.out.println("\u001B[31mInsufficient Balance. Please Retry.\u001B[0m");
        }
    }
}
    private static void creditRecompense(CreditAccount loggedAccount)
    {
//        System.out.print("Enter recompense amount: ");
//        double amount = input.nextDouble();
//        if (loggedAccount.recompense(amount)) {
//            System.out.println("Recompense successful.");
//        } else {
//            System.out.println("Recompense failed.");
//        }
    }

    protected static CreditAccount getLoggedAccount() {
        Account check = AccountLauncher.getLoggedAccount(); // Get the logged account

        if (check instanceof CreditAccount) {
            return (CreditAccount) check; // Return the CreditAccount if valid
        }

        return null; // If it's not a CreditAccount, return null
    }

}
