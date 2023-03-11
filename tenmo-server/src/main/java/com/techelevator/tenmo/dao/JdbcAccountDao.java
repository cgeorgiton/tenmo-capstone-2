package com.techelevator.tenmo.dao;


import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Component
public class JdbcAccountDao implements AccountDao {
    private final JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Account getCurrentUserAccount(int userId) {
        String sql = "SELECT account.user_id, account.account_id, account.balance " +
                "FROM account " +
                "WHERE user_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);

        if (results.next()) {
            return mapRowToAccount(results);
        } else {
            return null;
        }
    }

    @Override
    public Transfer completeTransaction(Transfer transfer) {

        //create new transfer in database
        String sql = "INSERT INTO public.transfer(transfer_type_id, transfer_status_id, account_from, account_to, amount, description) " +
                "VALUES ( " +
                "(SELECT transfer_type.transfer_type_id FROM transfer_type WHERE transfer_type_desc = ?), " +
                "(SELECT transfer_status.transfer_status_id FROM transfer_status WHERE transfer_status_desc = ?), " +

                "(SELECT account.account_id FROM account WHERE user_id = ?), " +
                "(SELECT account.account_id FROM account WHERE user_id = ?), ?, ?) RETURNING transfer_id;";

        // TODO adjust SQL statement
        Integer newTransferId = jdbcTemplate.queryForObject(sql, Integer.class,
                transfer.getTransferType(), transfer.getStatus(),
                transfer.getUserFromId(), transfer.getUserToId(), transfer.getAmount().doubleValue(), transfer.getDescription());
        transfer.setTransferId(newTransferId);


        return transfer; // TODO complete transaction
    }

    @Override
    public List<Transfer> listAll(int userId) {
        String sql = "SELECT transfer.transfer_id, transfer_type.transfer_type_desc, transfer_status.transfer_status_desc, transfer.account_from, transfer.account_to, transfer.amount, transfer.description, transfer.deleted " +
                "FROM transfer " +
                "JOIN account ON account.account_id = transfer.account_from " +
                "OR account.account_id = transfer.account_to " +
                "JOIN transfer_status ON transfer_status.transfer_status_id = transfer.transfer_status_id " +
                "JOIN transfer_type ON transfer_type.transfer_type_id = transfer.transfer_type_id " +
                "WHERE account.user_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);

        List<Transfer> transfers = new ArrayList<>();

        while (results.next()) {
            transfers.add(mapRowToTransfer(results));
        }
        // TODO test listAll
        return transfers;
    }

    @Override
    public List<Transfer> listFiltered(String status) {
        return null; // TODO do we need filtered list
    }

    @Override
    public boolean delete(int transferId) {
        return false;
    }

    private Transfer mapRowToTransfer(SqlRowSet sqlRowSet) {
        Transfer transfer = new Transfer();

        transfer.setTransferId(sqlRowSet.getInt("transfer_id"));
        transfer.setAmount(sqlRowSet.getBigDecimal("amount"));
        transfer.setDescription(sqlRowSet.getString("description"));
        transfer.setStatus(sqlRowSet.getString("transfer_status_desc"));
        transfer.setTransferType(sqlRowSet.getString("transfer_type_desc"));
// TODO work on mapRowToTransfer
        return transfer;
    }
    private Account mapRowToAccount(SqlRowSet rowSet) {
        Account account = new Account();

        account.setUserId(rowSet.getInt("user_id"));
        account.setAccountId(rowSet.getInt("account_id"));
        account.setBalance(rowSet.getBigDecimal("balance"));

        return account;
    }
}
