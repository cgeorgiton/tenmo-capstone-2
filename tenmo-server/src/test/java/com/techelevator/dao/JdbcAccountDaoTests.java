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
import java.util.List;

public class JdbcAccountDaoTests extends BaseDaoTests {

    protected static final User USER_1 = new User(1001, "user1", "user1", "USER");
    protected static final User USER_2 = new User(1002, "user2", "user2", "USER");
    private static final User USER_3 = new User(1003, "user3", "user3", "USER");
    private static final Account ACCOUNT_1 = new Account(2001, 1001, BigDecimal.valueOf(1000.00).setScale(2));
    private static final Account ACCOUNT_2 = new Account(2002, 1002, BigDecimal.valueOf(1000.00).setScale(2));
    private static final Account ACCOUNT_3 = new Account(2003, 1003, BigDecimal.valueOf(1000.00).setScale(2));
    private static final Transfer TRANSFER_1 = new Transfer(3001, BigDecimal.valueOf(10), 1001, 1002, "test", 1, 1, "user1", "user2");
    private static final Transfer TRANSFER_2 = new Transfer(3002, BigDecimal.valueOf(5), 1002, 1003, "test", 2, 2, "user2", "user3");
    private static final Transfer TRANSFER_3 = new Transfer(3003, BigDecimal.valueOf(15), 1003, 1002, "test", 3, 1, "user3", "user2");
    private Transfer testTransfer;
    private JdbcAccountDao sut;

    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcAccountDao(jdbcTemplate);
        testTransfer = new Transfer(0, BigDecimal.valueOf(10).setScale(2), 1001, 1002, "test", 1, 1, "user1", "user2");
    }

    @Test
    public void getCurrentUserAccount_returns_correct_account() {
        Account account = sut.getCurrentUserAccount(1001);
        Assert.assertEquals(ACCOUNT_1.getAccountId(), account.getAccountId());
    }

    @Test
    public void addTransfer_shouldReturnANewTransferUsingId() {
        int newTransferId = sut.addTransfer(testTransfer);
        Assert.assertTrue(newTransferId > 0);

        testTransfer.setTransferId(newTransferId);

        Transfer returnedTransfer = sut.getTransferById(newTransferId, 1002);

        Assert.assertEquals(testTransfer.getTransferId(), returnedTransfer.getTransferId());
    }

    @Test
    public void withdrawAndDeposit_shouldUpdateBalances() {
        sut.withdrawAndDeposit(1001, 1002, BigDecimal.valueOf(10));
        double user1Balance = sut.getCurrentUserAccount(1001).getBalance().doubleValue();
        double user2Balance = sut.getCurrentUserAccount(1002).getBalance().doubleValue();

        Assert.assertEquals(990.00, user1Balance, 0.00001);
        Assert.assertEquals(1010.00, user2Balance, 0.00001);

    }

    @Test
    public void listAllTransfers_shouldReturnListOfTransfersForCurrentUser() {
        List<Transfer> allTransfersUser1 = sut.listAllTransfers(1001);
        List<Transfer> allTransfersUser2 = sut.listAllTransfers(1002);

        Assert.assertEquals(1, allTransfersUser1.size());
        Assert.assertEquals(3, allTransfersUser2.size());

    }

    @Test
    public void getTransferById_shouldReturnCorrectTransfer() {
        Transfer returnedTransfer = sut.getTransferById(3003, 1002);
        Assert.assertEquals(TRANSFER_3.getTransferId(), returnedTransfer.getTransferId());
    }

}


