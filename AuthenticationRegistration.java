package banking;

public class AuthenticationRegistration {

    public static void signUp() {
        String accountNumber = AccountGenerator.generateAccountNumber();
        String pin = AccountGenerator.generatePin();
        System.out.printf("Your card has been created\nYour card number:\n%s\nYour card PIN:\n%s\n\n", accountNumber, pin);
        String[] user = new String[] {accountNumber, pin, "0"};
        Db.saveRecord(user);
        Main.account = user;
    }

    public static void logIn() {
        System.out.println("Enter your card number:");
        String accountNumber = Main.scanner.nextLine();
        System.out.println("Enter your PIN:");
        String pin = Main.scanner.nextLine();
        String[] result = Db.getRecord(accountNumber, pin);
        if (result != null) {
            Main.account = result;
            System.out.println("You have successfully logged in!\n");
            while (true) {
                AccountMenu.menu();
            }
        } else {
            System.out.println("Wrong card number or PIN!\n");
        }
    }

    public static void logOut() {
        Main.account = null;
        System.out.println("You have successfully logged out!\n");
    }
}

