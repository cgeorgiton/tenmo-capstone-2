package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;
import java.util.List;

public interface AccountDao {
    public Account getCurrentUserAccount(int userId);

    public Transfer addTransfer(Transfer transfer);

    public void withdrawAndDeposit(int userFromId, int userToId, BigDecimal amount);

    public List<Transfer> listAllTransfers(int userId);

    public List<Transfer> listFiltered(String status);

    public Transfer getTransferById(int transferId, int userId);

}
