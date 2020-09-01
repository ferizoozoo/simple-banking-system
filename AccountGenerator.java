package banking;

import java.util.Random;


public class AccountGenerator {

    static Random random = new Random();

    public static String generateAccountNumber() {
        String accountNumber = "400000";
        // Accountnumber without the checksum
        for (int i = 0; i < 9; i++) {
            accountNumber += String.valueOf(random.nextInt(10));
        }
        // Add the checksum to the accountnumber
        accountNumber += LuhnAlgorithm.checksum(accountNumber);
        return accountNumber;
    }

    public static String generatePin() {
        String pin = "";
        for (int i = 0; i < 4; i++) {
            pin += String.valueOf(random.nextInt(10));
        }
        return pin;
    }
}

