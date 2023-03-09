package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JdbcTransferDAO implements TransferDAO{
    private final JdbcTemplate jdbcTemplate;

    public JdbcTransferDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Transfer> list(int accountId) {
        // TODO complete list()
        return null;
    }

    @Override
    public Transfer addTransfer(Transfer transfer) {
        // TODO complete addTransfer()
        return null;
    }

    @Override
    public boolean update(Transfer updatedTransfer) {
        // TODO complete update()
        return false;
    }

    @Override
    public boolean delete(int transferId) {
        // TODO complete delete()
        return false;
    }

    // TODO complete mapRowToTransfer
}
