package Launchers;



import Accounts.*;
import Bank.*;
import Launchers.*;
import Main.*;



public class CreditAccountLauncher extends AccountLauncher {

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
            case 2 -> {
                creditPaymentProcess(loggedAccount);
            }
            case 3 -> {
                loggedAccount.getTransactionsInfo();
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
        Account targetAccount = BankLauncher.findaccount(accountNumber);
        double processingFee = loggedAccount.getBANK().getProcessingFee();
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

    protected static CreditAccount getLoggedAccount()
    {
//        if (AccountLauncher.getLoggedAccount() instanceof CreditAccount) {
//            return (CreditAccount) AccountLauncher.getLoggedAccount();
//        }
        return null;
    }
}
