package com.techelevator.dao;

import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class JdbcAccountDaoTests extends BaseDaoTests{

    protected static final User USER_1 = new User(1001, "user1", "user1", "USER");
    protected static final User USER_2 = new User(1002, "user2", "user2", "USER");
    private static final User USER_3 = new User(1003, "user3", "user3", "USER");
    private static final Account ACCOUNT_1 = new Account(2001, 1001, BigDecimal.valueOf(5.00).setScale(2));
    private static final Account ACCOUNT_2 = new Account(2002, 1002, BigDecimal.valueOf(10.00).setScale(2));
    private static final Account ACCOUNT_3 = new Account(2003, 1003, BigDecimal.valueOf(15.00).setScale(2));
    private static final Transfer TRANSFER_1 = new Transfer(3001, BigDecimal.valueOf(10), 1001, 1002, "test", 1, 1, "user1", "user2");
    private static final Transfer TRANSFER_2 = new Transfer(3002, BigDecimal.valueOf(5), 1002, 1003, "test", 2, 2, "user2", "user3");
    private static final Transfer TRANSFER_3 = new Transfer(3003, BigDecimal.valueOf(15), 1003, 1002, "test", 3, 1, "user3", "user2");

    private JdbcAccountDao sut;

    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcAccountDao(jdbcTemplate);
    }

    @Test
    public void getCurrentUserAccount_returns_correct_account() {
        Account account = sut.getCurrentUserAccount(1001);
        Assert.assertEquals(ACCOUNT_1, account);
    }

}
