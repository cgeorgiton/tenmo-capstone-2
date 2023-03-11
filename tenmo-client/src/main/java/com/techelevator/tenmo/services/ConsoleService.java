package com.techelevator.tenmo.services;


import com.techelevator.tenmo.model.AuthenticatedUser;
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
        String username = promptForString("\nUsername: ");
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
                amount = new BigDecimal(scanner.nextLine());
                if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                    throw new NumberFormatException();
                }
                validInput = true;
            } catch (NumberFormatException e) {
                System.out.print("\nPlease enter a decimal number greater than $0.00: ");
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
        for (User user : users) {
            System.out.println(String.format("\t%d: %s", i, user.getUsername()));
            i++;
        }
    }

    public void printTransactionSummary(Transfer transfer, String username) {
        String requestOrTransfer = transfer.getTransferTypeId() == 2 ? "sending" : "requesting";
        String toOrFrom = requestOrTransfer.equals("requesting") ? "from" : "to";
        System.out.println(String.format("\n<<< You are %s $%.2f %s %s >>>\n",
                                        requestOrTransfer, transfer.getAmount(), toOrFrom, username));
    }


    public void printTransferInfo(Transfer transfer, User selectedUser, AuthenticatedUser currentUser) {
        System.out.println(String.format("\nTransfer Summary: \n" +
                                         "\tTransfer ID: %d \n" +
                                         "\tSent To: %s \n" +
                                         "\tSent From: %s \n", transfer.getTransferId(), selectedUser.getUsername(), currentUser.getUser().getUsername()));
        }



    public void printErrorMessage() {
        System.out.println("An error occurred. Check the log for details.");
    }
}
