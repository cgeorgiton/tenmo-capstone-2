package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class AccountController {
<<<<<<< HEAD
=======
    private AccountDAO accountDAO;
    private UserDao userDao;
    private TransferDAO transferDAO;

    JdbcTemplate jdbcTemplate = new JdbcTemplate();

    public AccountController(AccountDAO accountDAO, UserDao userDao, TransferDAO transferDAO) {
        this.accountDAO = accountDAO;
        this.userDao = userDao;
        this.transferDAO = transferDAO;
    }

    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public List<User> findAllUsers() {
        return userDao.findAll();
    }

    @RequestMapping(path = "/users/{id}", method = RequestMethod.GET)
    public User getUserById(@PathVariable int id) { return userDao.getUserById(id);
    }



    // TODO get user by ID
    // TODO transfers by user_id
    // TODO create request
    // TODO create transfer
    // TODO get account info by ID
    // TODO get balance by ID


}
>>>>>>> d297d60741c679336174ebfd038d5dc33bd07a32

       private AccountDao accountDao;
       private UserDao userDao;

        JdbcTemplate jdbcTemplate = new JdbcTemplate();

        public AccountController(AccountDao accountDao) {
            this.accountDao = accountDao;
        }

        @RequestMapping(path = "/transfers", method = RequestMethod.GET)
        public List<Transfer> listAllTransfers(Principal principal) {

            return accountDao.listAll(userDao.findIdByUsername(principal.getName()));
        }


}
