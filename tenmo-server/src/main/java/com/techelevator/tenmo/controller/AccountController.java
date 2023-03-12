package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping("/accounts")
public class AccountController {
    private AccountDao accountDao;
    private UserDao userDao;

    JdbcTemplate jdbcTemplate = new JdbcTemplate();
    public AccountController(AccountDao accountDAO, UserDao userDao) {
        this.accountDao = accountDAO;
        this.userDao = userDao;
    }

    @RequestMapping(path = "/current", method = RequestMethod.GET)
    public Account getCurrentAccount(Principal principal) {
        int userId = userDao.findIdByUsername(principal.getName());
        return accountDao.getCurrentUserAccount(userId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/transfer", method = RequestMethod.POST)
    public Transfer completeTransaction(@RequestBody Transfer transfer) {
        accountDao.withdrawAndDeposit(transfer.getUserFromId(), transfer.getUserToId(), transfer.getAmount());
        return accountDao.addTransfer(transfer);
    }

    @RequestMapping(path = "/transfer-list", method = RequestMethod.GET)
    public List<Transfer> listAllTransfers(Principal principal) {
        return accountDao.listAllTransfers(userDao.findIdByUsername(principal.getName()));
    }

    @RequestMapping(path = "/transfer/id", method = RequestMethod.POST)
    public Transfer getTransferById(@RequestBody Transfer transfer, Principal principal) {
        int principalId = userDao.findIdByUsername(principal.getName());
        return accountDao.getTransferById(transfer.getTransferId(), principalId);
    }

    // TODO filtered transfer
    // TODO create Request
}

