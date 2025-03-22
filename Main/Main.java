package Main;

import Accounts.*;
import Bank.*;
import Launchers.*;
import java.util.*;

import static Launchers.AccountLauncher.accountLogin;

public class Main
{

    private static final Scanner input = new Scanner(System.in);
    /**
     * Option field used when selection options during menu prompts. Do not create a different
     * option variable in menus. Just use this instead. <br>
     * As to how to utilize Field objects properly, refer to the following:
     *
     * @see #prompt(String, boolean)
     * @see #setOption() How Field objects are used.
     */
    public static Field<Integer, Integer> option = new Field<Integer, Integer>("Option",
            Integer.class, -1, new Field.IntegerFieldValidator());

    public static void main(String[] args) throws IllegalAccountType {
        Bank BDO = new Bank(1234, "BDO", "0928");
        BankLauncher.addBank(BDO);
        CreditAccount cred=new CreditAccount(BDO,"1234","Johnny","Bravo","bravo@gmail.com","0987");
        BDO.addNewAccount(cred);
        SavingsAccount savingsAccount=new SavingsAccount(BDO, "1112","Johnny","Sins","sinns@gmail.com","1111",10000);
        BDO.addNewAccount(savingsAccount);
        BDO.addNewAccount(new SavingsAccount(BDO, "1113","Adolf","Hitler", "hitler@gmail.com","1234",0));

        Bank Landbank = new Bank(1112, "Land Bank", "0987");
        BankLauncher.addBank(Landbank);
        CreditAccount credL = new CreditAccount(Landbank, "2222", "Lanbanker", "Bravo", "bravo@gmail.com", "0987");
        Landbank.addNewAccount(credL);
        SavingsAccount savingsAccountL = new SavingsAccount(Landbank, "22223", "Murcialgo", "Sinns", "sinns@gmail.com", "12324", 10000);
        Landbank.addNewAccount(savingsAccountL);
        Landbank.addNewAccount(new SavingsAccount(Landbank, "22224", "Bin laden", "Hitler", "hitler@gmail.com", "1234", 0));
        while (true)
        {
            showMenuHeader("Main Menu");
            showMenu(1);
            setOption();

            try {
                int choice = getValidInteger("Enter your choice: ");

                // Using switch-case for clearer control flow
                switch (choice) {
                    case 1 -> accountLogin();
                    case 2 -> BankLauncher.bankLogin();
                    case 3 -> BankLauncher.createNewBank();
                    case 4 -> {
                        System.out.println("Exiting. Thank you for banking!");
                        return;
                    }
                    default -> System.out.println("Invalid option!");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                input.next();
            }
        }
    }
// Make sure to get valid input
    public static int getValidInteger(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return input.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                input.next();
            }
        }
    }

    /**
     * Show menu based on index given. <br>
     * Refer to Menu enum for more info about menu indexes. <br>
     * Use this method if you want a single menu option every line.
     *
     * @param menuIdx Main.Menu index to be shown
     */
    public static void showMenu(int menuIdx)
    {
        showMenu(menuIdx, 1);
    }

    /**
     * Show menu based on index given. <br>
     * Refer to Menu enum for more info about menu indexes.
     *
     * @param menuIdx Main.Menu index to be shown
     * @param inlineTexts Number of menu options in a single line. Set to 1 if you only want a
     *        single menu option every line.
     * @see Menu
     */
    public static void showMenu(int menuIdx, int inlineTexts)
    {
        String[] menu = Menu.getMenuOptions(menuIdx);
        if (menu == null)
        {
            System.out.println("Invalid menu index given!");
        }
        else
        {
            String space = inlineTexts == 0 ? "" : "%-20s";
            String fmt = "[%d] " + space;
            int count = 0;
            for (String s : menu)
            {
                count++;
                System.out.printf(fmt, count, s);
                if (count % inlineTexts == 0)
                {
                    System.out.println();
                }
            }
        }
    }

    /**
     * Prompt some input to the user. Only receives on non-space containing String. This string can
     * then be parsed into targeted data type using DataTypeWrapper.parse() method.
     *
     * @param phrase Prompt to the user.
     * @param inlineInput A flag to ask if the input is just one entire String or receive an entire
     *        line input. <br>
     *        Set to <b>true</b> if receiving only one String input without spaces. <br>
     *        Set to <b>false</b> if receiving an entire line of String input.
     * @return Value of the user's input.
     * @see Field#setFieldValue(String, boolean) How prompt is utilized in Field.
     */
    public static String prompt(String phrase, boolean inlineInput)
    {
        System.out.print(phrase);
        if (inlineInput)
        {
            String val = input.next();
            input.nextLine();
            return val;
        }
        return input.nextLine();
    }

    /**
     * Prompts user to set an option based on menu outputted.
     *
     * @throws NumberFormatException May happen if the user attempts to input something other than
     *         numbers.
     */
    public static void setOption() throws NumberFormatException
    {
        option.setFieldValue("Select an option: ");
    }

    /**
     * @return Recently inputted option by the user.
     */
    public static int getOption()
    {
        return Main.option.getFieldValue();
    }

    /**
     * Used for printing the header whenever a new menu is accessed.
     *
     * @param menuTitle Title of the menu to be outputted.
     */
    public static void showMenuHeader(String menuTitle)
    {
        System.out.printf("<---- %s ----->\n", menuTitle);
    }


    public static void print(String s)
    {
        System.out.println(s);
    }
}
