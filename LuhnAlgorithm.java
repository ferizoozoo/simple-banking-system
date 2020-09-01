package banking;

public class LuhnAlgorithm {
    public static String checksum(String accountNumberWithoutCheckSum) {
        int sum = 0;
        for (int i = 0; i < accountNumberWithoutCheckSum.length(); i++) {
            int digit = Integer.parseInt(String.valueOf(accountNumberWithoutCheckSum.charAt(i)));
            if ((i + 1) % 2 == 1 && digit * 2 > 9) {
                sum += digit * 2 - 9;
            } else if ((i + 1) % 2 == 1 && digit * 2 < 9) {
                sum += digit * 2;
            } else {
                sum += digit;
            }
        }
        return sum % 10 != 0 ? String.valueOf(10 - sum % 10) : String.valueOf(0);
    }
}

