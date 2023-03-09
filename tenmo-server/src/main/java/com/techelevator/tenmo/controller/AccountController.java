package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class AccountController {
    private AccountDao accountDao;
    private UserDao userDao;

    JdbcTemplate jdbcTemplate = new JdbcTemplate();

    public AccountController(AccountDao accountDAO, UserDao userDao) {
        this.accountDao = accountDAO;
        this.userDao = userDao;
    }




    @RequestMapping(path = "/transfers", method = RequestMethod.GET)
    public List<Transfer> listAllTransfers(Principal principal) {
        return accountDao.listAll(userDao.findIdByUsername(principal.getName()));
    // TODO make sure transfers work
    }

    // TODO transfers by user_id
    // TODO create request
    // TODO create transfer
    // TODO get account info by ID
    // TODO get balance by ID


}

