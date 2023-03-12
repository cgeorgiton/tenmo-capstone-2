package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Transfer {
    private int transferId;
    private BigDecimal amount;
    private int userToId;
    private int userFromId;
    private String description;
    private int transferStatusId;
    private int transferTypeId;
    private String toUsername;
    private String fromUsername;

    public Transfer() {
    }

    public Transfer (int transferStatusId, int transferTypeId) {
        this.transferStatusId = transferStatusId;
        this.transferTypeId = transferTypeId;

    }

    public Transfer(int transferId, BigDecimal amount, int userToId, int userFromId, String description, int transferStatusId, int transferTypeId, String toUsername, String fromUsername) {
        this.transferId = transferId;
        this.amount = amount;
        this.userToId = userToId;
        this.userFromId = userFromId;
        this.description = description;
        this.transferStatusId = transferStatusId;
        this.transferTypeId = transferTypeId;
        this.toUsername = toUsername;
        this.fromUsername = fromUsername;
    }

    public String getToUsername() {
        return toUsername;
    }

    public void setToUsername(String toUsername) {
        this.toUsername = toUsername;
    }

    public String getFromUsername() {
        return fromUsername;
    }

    public void setFromUsername(String fromUsername) {
        this.fromUsername = fromUsername;
    }

    public int getTransferId() {
        return transferId;
    }

    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public int getUserFromId() {
        return userFromId;
    }

    public void setUserFromId(int userFromId) {
        this.userFromId = userFromId;
    }

    public int getUserToId() {
        return userToId;
    }

    public void setUserToId(int userToId) {
        this.userToId = userToId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTransferStatusId() {
        return transferStatusId;
    }

    public void setTransferStatusId(int transferStatusId) {
        this.transferStatusId = transferStatusId;
    }

    public int getTransferTypeId() {
        return transferTypeId;
    }

    public void setTransferTypeId(int transferTypeId) {
        this.transferTypeId = transferTypeId;
    }

}