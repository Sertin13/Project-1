package Transactions;

import Accounts.*;
import Launchers.*;
import Main.*;

public class Payment implements Accounts.Payment{
    @Override
    public boolean pay(Account account, double amount) throws IllegalAccountType {
        return false;
    }
}
