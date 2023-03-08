package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.*;
import com.techelevator.tenmo.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AccountController {
    private AccountDAO accountDAO;
    private UserDao userDao;
    private TransferDAO transferDAO;

    JdbcTemplate jdbcTemplate = new JdbcTemplate();

    public AccountController() {
        this.accountDAO = new JdbcAccountDAO(jdbcTemplate);
        this.userDao = new JdbcUserDao(jdbcTemplate);
        this.transferDAO = new JdbcTransferDAO(jdbcTemplate);
    }

    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public List<User> findAllUsers() {
        return userDao.findAll();
    }
}

