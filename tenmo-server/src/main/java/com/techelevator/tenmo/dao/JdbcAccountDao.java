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
            String sql = "INSERT INTO public.transfer(transfer_type_id, transfer_status_id, account_from, account_to, amount, description, deleted) " +
                    "VALUES ((SELECT transfer_type.transfer_type_id FROM transfer_type WHERE transfer_type_desc = ?), " +
                    "(SELECT transfer_status.transfer_status_id FROM transfer_status WHERE transfer_status_desc = ?), " +
                    "(SELECT account.account_id FROM account WHERE user_id = ?), " +
                    "(SELECT account.account_id FROM account WHERE user_id = ?), ?, ?, ?) RETURNING transfer_id;";

            int newTransferId = jdbcTemplate.update(sql, transfer.getTransferType(), transfer.getStatus(), transfer.getUserFromId(), transfer.getUserToId(), transfer.getAmount(), transfer.getDescription(), transfer.isDeleted());
            transfer.setTransferId(newTransferId);


        return null; // TODO complete transaction
    }

    @Override
    public List<Transfer> listAll(int userId) {
        // TODO complete listAll() method
        return null;
    }

    @Override
    public List<Transfer> listFiltered(String status) {
        return null; // TODO do we need filtered list
    }

    @Override
    public boolean delete(int transferId) {
        return false;
    }

    private Account mapRowToAccount(SqlRowSet rowSet) {
        Account account = new Account();

        account.setUserId(rowSet.getInt("user_id"));
        account.setAccountId(rowSet.getInt("account_id"));
        account.setBalance(rowSet.getBigDecimal("balance"));

        return account;
    }
}
