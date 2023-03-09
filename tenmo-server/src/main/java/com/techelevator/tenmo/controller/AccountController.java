package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class AccountController {

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
