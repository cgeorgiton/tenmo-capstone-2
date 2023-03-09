package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.*;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class UserController {
    private UserDao userDao;
    private AccountDao accountDao;
    private JdbcTemplate jdbcTemplate = new JdbcTemplate();

    public UserController(UserDao userDao, AccountDao accountDao) {
        this.userDao = userDao;
        this.accountDao = accountDao;
    }

    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public List<User> findAllUsers() {
        return userDao.findAll();
    }

    @RequestMapping(path = "/users/account", method = RequestMethod.GET)
    public Account getUserBalance(Principal principal) {
        int userId = userDao.findIdByUsername(principal.getName());
        return accountDao.getCurrentUserAccount(userId);

    }
}


