package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
    public Transfer completeTransaction(@RequestBody Transfer transfer, Principal principal) {
        accountDao.withdrawAndDeposit(transfer.getUserFromId(), transfer.getUserToId(), transfer.getAmount());
        int newTransferId = accountDao.addTransfer(transfer);
        int userId = userDao.findIdByUsername(principal.getName());
        return accountDao.getTransferById(newTransferId, userId);
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

    @RequestMapping(path = "/request", method = RequestMethod.POST)
    public Transfer makeRequest(@RequestBody Transfer transfer, Principal principal) {
        int newTransferId = accountDao.addTransfer(transfer);
        int userId = userDao.findIdByUsername(principal.getName());
        return accountDao.getTransferById(newTransferId, userId);
    }

    @RequestMapping(path = "/request/pending", method = RequestMethod.GET)
    public List<Transfer> listAllPendingRequests(Principal principal) {
        return accountDao.listFiltered(userDao.findIdByUsername(principal.getName()));
    }

    @RequestMapping(path = "/request/update", method = RequestMethod.PUT)
    public Transfer updateRequest(@RequestBody Transfer transfer, Principal principal) {
        Transfer returnedRequest = null;
        int principalUserId = userDao.findIdByUsername(principal.getName());

        if (transfer.getTransferStatusId() == 3) {
            returnedRequest = accountDao.updateRequest(transfer, principalUserId);
        } else if (transfer.getTransferStatusId() == 2) {
            accountDao.withdrawAndDeposit(transfer.getUserFromId(), transfer.getUserToId(), transfer.getAmount());
            returnedRequest = accountDao.updateRequest(transfer, principalUserId);
        }
        if (returnedRequest.getTransferId() == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Request not updated");
        }
        return returnedRequest;
    }
}

