package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import com.techelevator.util.BasicLogger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

public class AccountService {
    private final String baseUrl;
    private final RestTemplate restTemplate = new RestTemplate();

    public AccountService(String url) {
        this.baseUrl = url;
    }

    public User[] getAllUsers() {
        User[] users = null;
        try {
            users = restTemplate.getForObject(baseUrl + "users", User[].class);
        } catch (ResourceAccessException | RestClientResponseException e) {
            return null;
        }
        return users;
    }

    // TODO look into authenticating users
}
