package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class JdbcTransferDAO implements TransferDAO{
    private final JdbcTemplate jdbcTemplate;

    public JdbcTransferDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

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
