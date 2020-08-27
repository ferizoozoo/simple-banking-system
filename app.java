package banking;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.Random;

public class Main {
    
    static Scanner scanner = new Scanner(System.in);
    static Random random = new Random();
    static String[] account;
    
    public static String luhnAlgorithm(String accountNumberWithoutCheckSum) {
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
    
    public static String generateAccountNumber() {
        String accountNumber = "400000";
        // Accountnumber without the checksum
        for (int i = 0; i < 9; i++) {
            accountNumber += String.valueOf(random.nextInt(10));
        }
        // Add the checksum to the accountnumber
        accountNumber += luhnAlgorithm(accountNumber);
        return accountNumber;
    }
    
    public static String generatePin() {
        String pin = "";
        for (int i = 0; i < 4; i++) {
            pin += String.valueOf(random.nextInt(10));
        }
        return pin;
    }
    
    public static void signUp() {
        String accountNumber = generateAccountNumber();
        String pin = generatePin();
        System.out.printf("Your card has been created\nYour card number:\n%s\nYour card PIN:\n%s\n\n", accountNumber, pin);
        String[] user = new String[] {accountNumber, pin, "0"};
        Db.saveRecord(user);
        account = user;
    }
    
    public static void logIn() {
        System.out.println("Enter your card number:");
        String accountNumber = scanner.nextLine();
        System.out.println("Enter your PIN:");
        String pin = scanner.nextLine();
        String[] result = Db.getRecord(accountNumber, pin);
        if (result != null) {
            account = result;
            System.out.println("You have successfully logged in!\n");
            accountMenu();
        } else {
            System.out.println("Wrong card number or PIN!\n");
        }
    }
    
    public static void logOut() {
        account = null;
        System.out.println("You have successfully logged out!\n");
    }
    
    public static void mainMenu() {
        System.out.println("1. Create an account\n2. Log into account\n0. Exit\n");
        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice) {
            case 1:
                signUp();
                break;
            case 2:
                logIn();
                break;
            case 0:
                System.out.println("Bye!");
                System.exit(0);
        }
    }
    
    public static void accountMenu() {
        System.out.println("1. Balance\n2. Log out\n0. Exit\n");
        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                System.out.println(account[2]);
                break;
            case 2:
                logOut();
                break;
            case 0:
                System.out.println("Bye!");
                System.exit(0);       
        }
    }
    
    public static void main(String[] args) {
        if (args.length == 2 && args[0].equals("-fileName")) {
            Db.fileName = args[1];
            Db.createTable();
            while (true) {
                mainMenu();
            }
        } else {
            System.out.println("Provide a filename for your database.");
        }
    }
}

