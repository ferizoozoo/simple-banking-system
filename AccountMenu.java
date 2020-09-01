package banking;

public class AccountMenu {
    private static void showBalance() {
        System.out.println(Main.account[2]);
    }

    private static void addIncome() {
        System.out.println("Enter income:");
        String income = Main.scanner.nextLine();
        Db.addIncomeUpdateRecord(Main.account[0], income);
        System.out.println("Income was added!\n");
    }

    private static void transfer() {
        String destNumber = Main.scanner.nextLine();
        if (LuhnAlgorithm.checksum(destNumber.substring(0, destNumber.length() - 1)) != destNumber.substring(destNumber.length() - 1)) {
            System.out.println("Probably you made mistake in the card number. Please try again!");
        } else if (Db.checkAccount(destNumber)) {
            System.out.println("Such a card does not exist.");
        } else {
            System.out.println("Enter how much money you want to transfer:");
            String amount = Main.scanner.nextLine();
            if (Integer.parseInt(Db.checkAmount(Main.account[0])) < Integer.parseInt(amount)) {
                System.out.println("Not enough money!");
            } else {
                Db.doTransfer(Main.account[0], destNumber, amount);
            }
        }
    }

    private static void closeAccount() {
        Db.deleteRecord(Main.account[0], Main.account[1]);
        Main.account = null;
    }

    private static void logOut() {
        AuthenticationRegistration.logOut();
    }

    private static void exit() {
        System.out.println("Bye!");
        System.exit(0);
    }

    // Account menu with options to do with the bank account
    public static void menu() {
        System.out.println("1. Balance\n" +
                "2. Add income\n" +
                "3. Do transfer\n" +
                "4. Close account\n" +
                "5. Log out\n" +
                "0. Exit");
        int choice = Main.scanner.nextInt();
        switch (choice) {
            case 1:
                showBalance();
                break;
            case 2:
                addIncome();
                break;
            case 3:
                transfer();
                break;
            case 4:
                closeAccount();
                break;
            case 5:
                logOut();
                break;
            case 0:
                exit();
            }
    }
}

