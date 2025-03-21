package Launchers;



import Accounts.*;
import Bank.*;
import Main.*;



public class CreditAccountLauncher extends AccountLauncher
{
    public static void credAccountInit() throws IllegalAccountType
    {
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

    private static void creditPaymentProcess(CreditAccount creditAccount) throws IllegalAccountType
    {
        int tryies=0;
        while(true)
        {
            tryies+=1;
            if(tryies==2){break;}
            Field<String, String> targetaccountnumberField = new Field<String,String>("targetAccount",
                    String.class, " ", new Field.StringFieldValidator());
            targetaccountnumberField.setFieldValue("Enter target account number: ");
            String accountNumber = targetaccountnumberField.getFieldValue();

            // Field for entering the amount to pay
            Field<Double, Double> amountField = new Field<Double,Double>("amount",
                    Double.class, 0.0, new Field.DoubleFieldValidator());
            amountField.setFieldValue("Enter target account number: "); // Mistake in prompt text, should be "Enter amount to pay:"
            double amountToPay = amountField.getFieldValue();

            // Find the target account and attempt the payment
            Account targetAccount = BankLauncher.findAccount(accountNumber);
            CreditAccount loggedCreditAccount=getLoggedAccount();
            double processingFee = loggedCreditAccount.getBANK().getProcessingFee();
            double payAmountWithFee = amountToPay + processingFee;
            boolean paySuccess = loggedCreditAccount.pay(targetAccount, payAmountWithFee);

            if (paySuccess){
                System.out.println("Transfer of ₱" + amountToPay + " processed successfully.");
            }
            else{
                System.out.println("Insufficient Balance. Please Retry.");
            }
        }

    }
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

    protected static CreditAccount getLoggedAccount()
    {
        Bank loggedBank = BankLauncher.getLoggedBank();
        if (loggedBank != null)
        {
            for (Account account : loggedBank.getBANKACCOUNTS())
            {
                if (account instanceof CreditAccount)
                {
                    return (CreditAccount) account;
                }
            }
        }
        return null;
    }

}
