package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

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
    public int addTransfer(Transfer transfer) {
        String sql = "INSERT INTO public.transfer(transfer_type_id, transfer_status_id, account_from, account_to, user_from_id, user_to_id, amount, description) " +
                     "VALUES (?, ?, " +
                     "(SELECT account.account_id FROM account WHERE user_id = ?), " +
                     "(SELECT account.account_id FROM account WHERE user_id = ?), ?, ?, ?, ?) RETURNING transfer_id;";

        Integer newTransferId = jdbcTemplate.queryForObject(sql, Integer.class,
                transfer.getTransferTypeId(), transfer.getTransferStatusId(),
                transfer.getUserFromId(), transfer.getUserToId(), transfer.getUserFromId(), transfer.getUserToId(), transfer.getAmount().doubleValue(), transfer.getDescription());

        return newTransferId;
    }

    @Override
    public void withdrawAndDeposit(int userFromId, int userToId, BigDecimal amount) {
        String sql = "UPDATE account " +
                "SET balance = balance - ? " +
                "WHERE user_id = ?; " +
                "UPDATE account " +
                "SET balance = balance + ? " +
                "WHERE user_id = ?;";


        jdbcTemplate.update(sql, amount, userFromId, amount, userToId);
    }

    @Override
    public List<Transfer> listAllTransfers(int userId) {
        String sql = "SELECT transfer.transfer_id, transfer.transfer_type_id, transfer.transfer_status_id, " +
                "transfer.account_from, transfer.account_to, transfer.user_from_id, transfer.user_to_id, " +
                "transfer.amount, transfer.description, to_tenmo_user.username AS to_username, " +
                "from_tenmo_user.username AS from_username " +
                "FROM public.transfer " +
                "INNER JOIN account AS from_account ON transfer.user_from_id = from_account.user_id " +
                "INNER JOIN account AS to_account ON transfer.user_to_id = to_account.user_id " +
                "INNER JOIN tenmo_user AS from_tenmo_user ON from_account.user_id = from_tenmo_user.user_id " +
                "INNER JOIN tenmo_user AS to_tenmo_user ON to_account.user_id = to_tenmo_user.user_id " +
                "WHERE transfer.user_from_id = ? " +
                "OR transfer.user_to_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql,userId, userId);

        List<Transfer> transfers = new ArrayList<>();

        while (results.next()) {
            transfers.add(mapRowToTransfer(results));
        }
        return transfers;
    }

    @Override
    public List<Transfer> listFiltered(int userId) {
        String sql = "SELECT transfer.transfer_id, transfer.transfer_type_id, transfer.transfer_status_id, " +
                "                transfer.account_from, transfer.account_to, transfer.user_from_id, transfer.user_to_id, " +
                "                transfer.amount, transfer.description, to_tenmo_user.username AS to_username, " +
                "                from_tenmo_user.username AS from_username " +
                "                FROM public.transfer " +
                "                INNER JOIN account AS from_account ON transfer.user_from_id = from_account.user_id " +
                "                INNER JOIN account AS to_account ON transfer.user_to_id = to_account.user_id " +
                "                INNER JOIN tenmo_user AS from_tenmo_user ON from_account.user_id = from_tenmo_user.user_id " +
                "                INNER JOIN tenmo_user AS to_tenmo_user ON to_account.user_id = to_tenmo_user.user_id " +
                "                WHERE (transfer.user_from_id = ?) " +
                "                AND transfer.transfer_status_id = 1;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);

        List<Transfer> pendingRequests = new ArrayList<>();

        while (results.next()) {
            pendingRequests.add(mapRowToTransfer(results));
        }
        return pendingRequests;
    }

    @Override
    public Transfer getTransferById(int transferId, int userId) {
        String sql = "SELECT transfer.transfer_id, transfer.transfer_type_id, transfer.transfer_status_id, " +
                "transfer.account_from, transfer.account_to, transfer.user_from_id, transfer.user_to_id, " +
                "transfer.amount, transfer.description, to_tenmo_user.username AS to_username, " +
                "from_tenmo_user.username AS from_username " +
                "FROM public.transfer " +
                "INNER JOIN account AS from_account ON transfer.user_from_id = from_account.user_id " +
                "INNER JOIN account AS to_account ON transfer.user_to_id = to_account.user_id " +
                "INNER JOIN tenmo_user AS from_tenmo_user ON from_account.user_id = from_tenmo_user.user_id " +
                "INNER JOIN tenmo_user AS to_tenmo_user ON to_account.user_id = to_tenmo_user.user_id " +
                "WHERE transfer.transfer_id = ?" +
                "AND (transfer.user_from_id = ? OR transfer.user_to_id = ?);";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferId, userId, userId);

        Transfer transfer = new Transfer();

        if (results.next()) {
            transfer = mapRowToTransfer(results);
        }
        return transfer;
    }

    @Override
    public Transfer updateRequest(Transfer transfer, int userId) {
        String sql = "UPDATE transfer " +
                "SET transfer_status_id = ? " +
                "WHERE transfer_id = ? " +
                "AND user_from_id = ?";

        jdbcTemplate.update(sql, transfer.getTransferStatusId(), transfer.getTransferId(), userId);

        Transfer returnedTransfer = getTransferById(transfer.getTransferId(), userId);

        if (returnedTransfer.getTransferStatusId() != transfer.getTransferStatusId()) {
            return null;
        } else {
            return returnedTransfer;
        }
    }

    private Transfer mapRowToTransfer(SqlRowSet sqlRowSet) {
        Transfer transfer = new Transfer();

        transfer.setTransferId(sqlRowSet.getInt("transfer_id"));
        transfer.setAmount(sqlRowSet.getBigDecimal("amount"));
        transfer.setUserFromId(sqlRowSet.getInt("user_from_id"));
        transfer.setUserToId(sqlRowSet.getInt("user_to_id"));
        transfer.setDescription(sqlRowSet.getString("description"));
        transfer.setTransferStatusId(sqlRowSet.getInt("transfer_status_id"));
        transfer.setTransferTypeId(sqlRowSet.getInt("transfer_type_id"));
        transfer.setToUsername(sqlRowSet.getString("to_username"));
        transfer.setFromUsername(sqlRowSet.getString("from_username"));

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
