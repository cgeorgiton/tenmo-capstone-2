package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;
import java.util.List;

public interface AccountDao {
    public Account getCurrentUserAccount(int userId);

    public int addTransfer(Transfer transfer);

    public void withdrawAndDeposit(int userFromId, int userToId, BigDecimal amount);

    public List<Transfer> listAllTransfers(int userId);

    public List<Transfer> listFiltered(int id);

    public Transfer getTransferById(int transferId, int userId);

    public Transfer updateRequest(Transfer transfer, int userId);

}
