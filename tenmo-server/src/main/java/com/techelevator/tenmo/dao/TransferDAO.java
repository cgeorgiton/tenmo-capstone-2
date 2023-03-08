package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.util.List;

public interface TransferDAO {
    public List<Transfer> list(int accountId);
    public Transfer addTransfer(Transfer transfer);
    public boolean update(Transfer updatedTransfer);
    public boolean delete(int transferId);
}
