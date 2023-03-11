package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;

import java.util.List;

public interface AccountDao {
    public Account getCurrentUserAccount(int userId);

    public Transfer completeTransaction(Transfer transfer);

    public List<Transfer> listAll(int userId);

    public List<Transfer> listFiltered(String status);


    // TODO add delete method
}
