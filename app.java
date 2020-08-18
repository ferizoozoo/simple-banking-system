package banking;

import java.util.Scanner;
import java.util.Random;

public class Main {
    
    static Scanner scanner = new Scanner(System.in);
    static Random random = new Random();
    static String[] account;
    
    public static String generateAccountNumber() {
        String accountNumber = "400000";
        for (int i = 0; i < 10; i++) {
            accountNumber += String.valueOf(random.nextInt(10));
        }
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
        account = new String[] {accountNumber, pin, "0"};
    }
    
    public static void logIn() {
        System.out.println("Enter your card number:");
        String accountNumber = scanner.nextLine();
        System.out.println("Enter your PIN:");
        String pin = scanner.nextLine();
        if (accountNumber.equals(account[0]) & pin.equals(account[1])) {
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
        while (true) {
            mainMenu();
        }
    }
}

