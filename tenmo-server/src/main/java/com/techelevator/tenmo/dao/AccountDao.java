package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;
import java.util.List;

public interface AccountDao {
    public Account getCurrentUserAccount(int userId);

    public Transfer completeTransaction(int fromAccountId, int toAccountId, BigDecimal amount);

    public List<Transfer> listAll(int userId);

    public List<Transfer> listFiltered(String status);

    public boolean delete(int transferId);

    public int createTransfer(Transfer transfer);


    // TODO add delete method
}
