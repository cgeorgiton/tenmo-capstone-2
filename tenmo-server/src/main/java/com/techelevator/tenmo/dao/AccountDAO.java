package com.techelevator.tenmo.dao;

import java.math.BigDecimal;
import java.security.Principal;

public interface AccountDAO {
    public BigDecimal getBalance(int accountId);

    public BigDecimal withdraw(int accountId, BigDecimal amount);

    public BigDecimal deposit(int accountId, BigDecimal amount);

    // TODO add delete method
}
