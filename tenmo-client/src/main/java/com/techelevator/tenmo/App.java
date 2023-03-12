package com.techelevator.tenmo;

import com.techelevator.tenmo.model.*;
import com.techelevator.tenmo.services.AccountService;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.ConsoleService;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;

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

        while (true) {
            int userInput = consoleService.promptForInt("\n1. Search by transfer ID\n" +
                                                        "2. View all transfers\n\n" +
                                                        "Enter your selection: ");
            if (userInput == 1) {
                userInput = consoleService.promptForInt("\nPlease enter the transfer ID: ");

                Transfer transfer = new Transfer(userInput, BigDecimal.ZERO, 0, 0, "", 2, 2, "", "");
                Transfer returnedTransfer = accountService.getTransferById(transfer);

                if (returnedTransfer.getUserToId() == 0) {
                    System.out.println("\nThat transfer ID was invalid or you don't have access to this transfer history");
                    break;
                }

                Transfer[] transferArray = new Transfer[]{returnedTransfer};
                consoleService.printTransfers(transferArray);
                break;
            } else if (userInput == 2) {
                Transfer[] transfers = accountService.getAllTransfers();
                consoleService.printTransfers(transfers);
                break;
            } else {
                System.out.println("\nInvalid Selection");
            }
        }
    }

    private void viewPendingRequests() {
        Transfer[] pendingRequests = accountService.getAllPendingRequests();

        while (true) {
            consoleService.printTransfers(pendingRequests);

            int userInput = consoleService.promptForInt("\n1: Approve\n" +
                                                    "2: Reject\n" +
                                                    "0: Don't approve or reject\n" +
                                                    "Please choose an option: ");
            if (userInput == 1 || userInput == 2) {
                int transferIdInput = consoleService.promptForInt("\nPlease enter the request ID: ");
                int transferStatus = userInput == 1 ? APPROVED : REJECTED;

                Transfer selectedTransfer = new Transfer();

                for (Transfer transfer : pendingRequests) {
                    if (transfer.getTransferId() == transferIdInput) {
                        selectedTransfer = transfer;
                    }
                }
                if (selectedTransfer.getTransferId() == 0) {
                    System.out.println("\nThat transfer ID was invalid or you don't have access to this transfer history");
                } else {

                    selectedTransfer.setTransferStatusId(transferStatus);

                    Transfer updatedRequest = accountService.updateRequest(selectedTransfer);

                    Transfer[] requestArray = new Transfer[]{updatedRequest};

                    consoleService.printTransfers(requestArray);

                    break;
                }
            } else if (userInput == 0) {
                break;
            } else if (userInput != 1 || userInput != 2 || userInput != 0) {
                System.out.println("\nInvalid Selection");
            }
        }
    }

    private void sendBucks() {
        Transfer send = new Transfer(APPROVED, SEND);

        send.setUserFromId(currentUser.getUser().getId());

        User selectedUser = selectUser();
        send.setUserToId(selectedUser.getId());

        BigDecimal moneyInput = BigDecimal.ZERO;

        while (true) {
            moneyInput = consoleService.promptForBigDecimal("\nHow much money do you want to transfer?: ");
            if (moneyInput.compareTo(accountService.getCurrentUserAccount().getBalance()) <= 0) {
                break;
            }
            System.out.println("\nInvalid amount");
        }

        send.setAmount(moneyInput);
        send.setDescription(getDescription());

        consoleService.printTransactionSummary(send, selectedUser.getUsername());

        while (true) {
            String userInput = consoleService.promptForString("Do you want to complete this transaction? (Y/N): ");
            if (userInput.equalsIgnoreCase("Y")) {
                Transfer[] updatedTransfer = new Transfer[]{accountService.makeTransfer(send)};
                consoleService.printTransfers(updatedTransfer);
                break;
            } else if (userInput.equalsIgnoreCase("N")) {
                break;
            } else {
                System.out.println("\nPlease only enter Y for yes or N for no\n");
            }
        }
    }

    private void requestBucks() {
        Transfer request = new Transfer(PENDING, REQUEST);

        request.setUserToId(currentUser.getUser().getId());

        User selectedUser = selectUser();
        request.setUserFromId(selectedUser.getId());

        request.setAmount(consoleService.promptForBigDecimal("\nHow much money do you want to request?: "));
        request.setDescription(getDescription());

        consoleService.printTransactionSummary(request, selectedUser.getUsername());

        while (true) {
            String userInput = consoleService.promptForString("Do you want to complete this transaction? (Y/N): ");
            if (userInput.equalsIgnoreCase("Y")) {
                Transfer[] updatedRequest = new Transfer[]{accountService.makeRequest(request)};
                consoleService.printTransfers(updatedRequest);
                break;
            } else if (userInput.equals("N")) {
                break;
            } else {
                System.out.println("\nPlease only enter Y for yes or N for no\n");
            }
        }
    }

    private User selectUser() {
        User[] users = accountService.getAllUsers();

        consoleService.printUsers(users, currentUser);

        int userInput;
        User selectedUser = new User();

        while (true) {
            userInput = consoleService.promptForInt("\nPlease select a user by their user-id: ");
            if (userInput == currentUser.getUser().getId()) {
                System.out.println("\nInvalid Selection");
            } else {
                for (User user : users) {
                    if (user.getId() == userInput) {
                        selectedUser = user;
                    }
                }
                if (selectedUser.getUsername() != null) {
                    break;
                }
            }
        }
        return selectedUser;
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