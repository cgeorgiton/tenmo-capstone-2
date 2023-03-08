package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.math.BigDecimal;
import java.security.Principal;

public class JdbcAccountDAO implements AccountDAO {

    JdbcTemplate jdbcTemplate = new JdbcTemplate();

    public JdbcAccountDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public BigDecimal getBalance(int accountId) {
        BigDecimal balance;

        String sql = "SELECT account.balance " +
                "FROM account " +
                "WHERE account_id = ?;";
         return jdbcTemplate.queryForObject(sql, BigDecimal.class, accountId);
    }

    @Override
    public BigDecimal withdraw(int accountId, BigDecimal amount) {
        return null;
    }

    @Override
    public BigDecimal deposit(int accountId, BigDecimal amount) {
        return null;
    }


    private Account mapRowToAccount(SqlRowSet rowSet) {
        Account account = new Account();
        account.setAccountId(rowSet.getInt("account_id"));
        account.setUserId(rowSet.getInt("user_id"));
        account.setBalance(rowSet.getBigDecimal("balance"));
        return account;
    }
}
