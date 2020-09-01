package banking;

import java.util.Scanner;

public class Main {
    
    static Scanner scanner = new Scanner(System.in);
    static String[] account;

    private static void signUp() {
        AuthenticationRegistration.signUp();
    }

    private static void logIn() {
        AuthenticationRegistration.logIn();
    }

    private static void exit() {
        System.out.println("Bye!");
        System.exit(0);
    }

    // Main menu for logging into the bank account
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
                exit();
        }
    }
    
    public static void main(String[] args) {
        if (args[0].equals("-fileName")) {
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

