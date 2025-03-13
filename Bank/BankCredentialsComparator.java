package Bank;

import java.util.Comparator;

public class BankCredentialsComparator implements Comparator<Bank>
{
    @Override
    public int compare(Bank b1, Bank b2) {
        return b1.password.compareTo(b2.password);

    }
}
