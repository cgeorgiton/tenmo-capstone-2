package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Transfer {

    private int transferId;
    private BigDecimal amount;
    private int userToId;
    private int userFromId;
    private String description;
    private String status;
    private String transferType;

    public Transfer() {
    }

    public Transfer(int transferId, BigDecimal amount, int userToId, int userFromId, String description, String status, String transferType) {
        this.transferId = transferId;
        this.amount = amount;
        this.userToId = userToId;
        this.userFromId = userFromId;
        this.description = description;
        this.status = status;
        this.transferType = transferType;
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

    public int getUserToId() {
        return userToId;
    }

    public void setUserToId(int userToId) {
        this.userToId = userToId;
    }

    public int getUserFromId() {
        return userFromId;
    }

    public void setUserFromId(int userFromId) {
        this.userFromId = userFromId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTransferType() {
        return transferType;
    }

    public void setTransferType(String transferType) {
        this.transferType = transferType;
    }

    @Override
    public String toString() {
        return "Transfer{" +
                "transferId=" + transferId +
                ", amount=" + amount +
                ", userToId=" + userToId +
                ", userFromId=" + userFromId +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", transferType='" + transferType + '\'' +
                '}';
    }
}
