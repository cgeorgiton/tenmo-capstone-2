package com.techelevator.tenmo;

import com.techelevator.tenmo.model.*;
import com.techelevator.tenmo.services.AccountService;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.ConsoleService;

import java.math.BigDecimal;

public class App {

    private static final String API_BASE_URL = "http://localhost:8080/";

    private final ConsoleService consoleService = new ConsoleService();
    private final AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);
    private final AccountService accountService = new AccountService(API_BASE_URL);
    private final int REQUEST = 1;
    private final int SEND = 2;
    private final int PENDING = 1;
    private final int APPROVED = 2;
    private final int REJECTED = 3;

    private AuthenticatedUser currentUser;

    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    private void run() {
        consoleService.printGreeting();
        loginMenu();
        if (currentUser != null) {
            mainMenu();
        }
    }

    private void loginMenu() {
        int menuSelection = -1;
        while (menuSelection != 0 && currentUser == null) {
            consoleService.printLoginMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                handleRegister();
            } else if (menuSelection == 2) {
                handleLogin();
            } else if (menuSelection != 0) {
                System.out.println("Invalid Selection");
                consoleService.pause();
            }
        }
    }

    private void handleRegister() {
        System.out.println("Please register a new user account");
        UserCredentials credentials = consoleService.promptForCredentials();
        if (authenticationService.register(credentials)) {
            System.out.println("Registration successful. You can now login.");
        } else {
            consoleService.printErrorMessage();
        }
    }

    private void handleLogin() {
        UserCredentials credentials = consoleService.promptForCredentials();
        currentUser = authenticationService.login(credentials);
        accountService.setCurrentUser(currentUser);
        if (currentUser == null) {
            consoleService.printErrorMessage();
        }
    }

    private void mainMenu() {
        int menuSelection = -1;
        while (menuSelection != 0) {
            consoleService.printMainMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                viewCurrentBalance();
            } else if (menuSelection == 2) {
                viewTransferHistory();
            } else if (menuSelection == 3) {
                viewPendingRequests();
            } else if (menuSelection == 4) {
                sendBucks();
            } else if (menuSelection == 5) {
                requestBucks();
            } else if (menuSelection == 0) {
                continue;
            } else {
                System.out.println("\nInvalid Selection");
            }
            consoleService.pause();
        }
    }

    private void viewCurrentBalance() {
        BigDecimal balance = accountService.getCurrentUserAccount().getBalance();
        System.out.println(String.format("\nYour current balance is $%.2f", balance));
    }

    private void viewTransferHistory() {

        // TODO complete viewTransferHistory()

    }

    private void viewPendingRequests() {
        // TODO complete viewPendingRequests()

    }

    private void sendBucks() {
        Transfer send = new Transfer();
        send.setTransferTypeId(SEND);
        send.setTransferStatusId(APPROVED);
        send.setUserFromId(currentUser.getUser().getId());

        User selectedUser = selectUser();
        send.setUserToId(selectedUser.getId());

        send.setAmount(consoleService.promptForBigDecimal("\nHow much money do you want to transfer?: "));
        send.setDescription(getDescription());

        consoleService.printTransactionSummary(send, selectedUser.getUsername());

        while (true) {
            String userInput = consoleService.promptForString("Do you want to complete this transaction? (Y/N): ");
            if (userInput.equalsIgnoreCase("Y")) {
                Transfer updatedTransfer = new Transfer();
                updatedTransfer = accountService.makeTransaction(send);
                consoleService.printTransfers(updatedTransfer);

            } else if (userInput.equals("N")) {
                break;
            } else {
                System.out.println("\nPlease only enter Y for yes or N for no\n");
            }
        }
        // TODO complete sendBucks()
        // TODO add exit to main menu
    }

    private void requestBucks() {
        Transfer request = new Transfer();
        request.setTransferTypeId(REQUEST);
        request.setTransferStatusId(PENDING);
        request.setUserToId(currentUser.getUser().getId());

        User selectedUser = selectUser();
        request.setUserFromId(selectedUser.getId());

        request.setAmount(consoleService.promptForBigDecimal("\nHow much money do you want to request?: "));
        request.setDescription(getDescription());

        consoleService.printTransactionSummary(request, selectedUser.getUsername());

        while (true) {
            String userInput = consoleService.promptForString("Do you want to complete this transaction? (Y/N): ");
            if (userInput.equalsIgnoreCase("Y")) {

            } else if (userInput.equals("N")) {
                break;
            } else {
                System.out.println("\nPlease only enter Y for yes or N for no\n");
            }
        }

        // TODO complete requestBucks()
    }

    private User[] getUsers() {
        return accountService.getAllUsers();
    }

    private User selectUser() {
        User[] users = getUsers();
        consoleService.printUsers(users, currentUser.getUser().getUsername());

        int userInput = -1;
        boolean validInput = false;

        while (!validInput) {
            userInput = consoleService.promptForInt("\nPlease select a user: ");
            if (userInput <= 0 || userInput > users.length || currentUser.getUser().getUsername().equals(users[userInput - 1].getUsername())) {
                System.out.println("\nInvalid Selection");
            } else {
                validInput = true;
            }
        }

        return users[userInput - 1];
    }

    private String getDescription() {
        String description = "";
        boolean validInput = false;

        while (!validInput) {
            description = consoleService.promptForString("\nPlease enter a description for this transaction (It cannot be blank): ");
            validInput = !description.equals("\n") && !description.isEmpty();
        }
        return description;
    }
}
