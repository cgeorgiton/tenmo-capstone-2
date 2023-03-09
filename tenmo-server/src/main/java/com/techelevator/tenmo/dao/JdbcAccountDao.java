package com.techelevator.tenmo.dao;


import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.util.List;


@Component
public class JdbcAccountDao implements AccountDao {
    private final JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public BigDecimal getBalance(int accountId) {
        return null;
    }

    @Override
    public boolean transaction(int fromAccountId, int toAccountId, BigDecimal amount) {
        return false;
    }

    @Override
    public List<Transfer> listAll(int userId) {
        return null;
    }

    @Override
    public List<Transfer> listFiltered(String status) {
        return null;
    }

    @Override
    public boolean delete(int transferId) {
        return false;
    }

    @Override
    public Transfer createTransfer(Transfer transfer) {
        return null;
    }

}
