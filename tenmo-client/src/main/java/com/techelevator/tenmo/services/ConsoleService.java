package com.techelevator.tenmo.services;


import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.UserCredentials;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Scanner;

public class ConsoleService {

    private final Scanner scanner = new Scanner(System.in);

    public int promptForMenuSelection(String prompt) {
        int menuSelection;
        System.out.print(prompt);
        try {
            menuSelection = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            menuSelection = -1;
        }
        return menuSelection;
    }

    public void printGreeting() {
        System.out.println("*********************");
        System.out.println("* Welcome to TEnmo! *");
        System.out.println("*********************");
    }

    public void printLoginMenu() {
        System.out.println();
        System.out.println("1: Register");
        System.out.println("2: Login");
        System.out.println("0: Exit");
        System.out.println();
    }

    public void printMainMenu() {
        System.out.println();
        System.out.println("1: View your current balance");
        System.out.println("2: View your past transfers");
        System.out.println("3: View your pending requests");
        System.out.println("4: Send TE bucks");
        System.out.println("5: Request TE bucks");
        System.out.println("0: Exit");
        System.out.println();
    }

    public UserCredentials promptForCredentials() {
        String username = promptForString("Username: ");
        String password = promptForString("Password: ");
        return new UserCredentials(username, password);
    }

    public String promptForString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public int promptForInt(String prompt) {
        System.out.print(prompt);
        int amount = 0;
        boolean validInput = false;

        while (!validInput) {
            try {
                amount = Integer.parseInt(scanner.nextLine());
                validInput = true;
            } catch (NumberFormatException e) {
                System.out.print("\nPlease enter a number: ");
            }
        }
        return amount;
    }

    public BigDecimal promptForBigDecimal(String prompt) {
        System.out.print(prompt);
        BigDecimal amount = BigDecimal.ZERO;
        boolean validInput = false;

        while (!validInput) {
            try {
                amount =  new BigDecimal(scanner.nextLine());
                validInput = true;
            } catch (NumberFormatException e) {
                System.out.print("\nPlease enter a decimal number: ");
            }
        }
        return amount;
    }

    public void pause() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    public void printUsers(User[] users, String currentUsername) {
        System.out.println("\nAll available users:");
        int i = 1;
        for(User user : users) {
                System.out.println(String.format("\t%d: %s", i, user.getUsername()));
                i++;

        }
    }

    public void printTransfer(Transfer transfer, String username) {
        System.out.println(String.format("You are sending %.2f to %s\n", transfer.getAmount(), username));
        System.out.println("Do you want to complete this transaction? (Y/N)");
    }

    public void printRequest(Transfer transfer, String username) {
        System.out.println(String.format("You are requesting %.2f from %s\n", transfer.getAmount(), username));
        System.out.println("Do you want to complete this transaction? (Y/N)");
    }


    /*public void printTransfers(String status, Transfer[] transfers) {
        System.out.println(String.format("\nHere is your %s transfer history:", status));
        for(Transfer transfer : transfers) {
            System.out.println("Sender username: %s\n" +
                               "Receiver username: %s\n" +
                                "Amount sent: %f.2", transfer.getAccountFromId(), transfer.getAccountToId());
        }*/


    public void printErrorMessage() {
        System.out.println("An error occurred. Check the log for details.");
    }
}
