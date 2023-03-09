package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.security.Principal;

public class AccountService {
    private final String baseUrl;
    private final RestTemplate restTemplate = new RestTemplate();
    private AuthenticatedUser currentUser;

    public AccountService(String url) {
        this.baseUrl = url;
    }

    public void setCurrentUser(AuthenticatedUser currentUser) {
        this.currentUser = currentUser;
    }

    public User[] getAllUsers() {
        User[] users = null;

        try {
            ResponseEntity<User[]> response =
                    restTemplate.exchange(baseUrl + "users",
                            HttpMethod.GET, makeAuthEntity(), User[].class);
            users = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return users;

    }

    public Account getCurrentUserAccount() {
        Account currentAccount = null;
        try {
            ResponseEntity<Account> response =
                    restTemplate.exchange(baseUrl + "users/account",
                            HttpMethod.GET, makeAuthEntity(), Account.class);
            currentAccount = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return currentAccount;
    }

    public Transfer[] viewAllTransfers() {
        Transfer[] transfers = null;

        try {
            ResponseEntity<Transfer[]> response =
                    restTemplate.exchange(baseUrl + "transfers",
                            HttpMethod.GET, makeAuthEntity(), Transfer[].class);
            transfers = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }

        return transfers;

        // TODO check that transfers work
    }

    /*private HttpEntity<Reservation> makeReservationEntity(Reservation reservation) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON); // TODO create HttpEntity<Transfer> can Transfer also be used for Request??
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(reservation, headers);
    }*/

    /**
     * Returns an HttpEntity with the `Authorization: Bearer:` header
     */
    private HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(currentUser.getToken());
        return new HttpEntity<>(headers);
    }
}
