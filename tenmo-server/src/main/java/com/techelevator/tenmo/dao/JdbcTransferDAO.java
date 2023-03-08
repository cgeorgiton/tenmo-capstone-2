package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.util.List;

public class JdbcTransferDAO implements TransferDAO{
    @Override
    public List<Transfer> list(int accountId) {
        return null;
    }

    @Override
    public Transfer addTransfer(Transfer transfer) {
        return null;
    }

    @Override
    public boolean update(Transfer updatedTransfer) {
        return false;
    }

    @Override
    public boolean delete(int transferId) {
        return false;
    }
}
