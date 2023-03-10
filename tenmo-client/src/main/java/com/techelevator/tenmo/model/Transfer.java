package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Transfer {

    private int transferId;
    private BigDecimal amount;
    private int userIdFrom;
    private int userIdTo;
    private String description;
    private boolean isDeleted;
    private String status;
    private String transferType;

    public Transfer() {
    }

    public Transfer(int transferId, BigDecimal amount, int userIdFrom, int userIdTo, String description, String status, String transferType) {
        this.transferId = transferId;
        this.amount = amount;
        this.userIdFrom = userIdFrom;
        this.userIdTo = userIdTo;
        this.description = description;
        this.isDeleted = false;
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

    public int getUserIdFrom() {
        return userIdFrom;
    }

    public void setUserIdFrom(int userIdFrom) {
        this.userIdFrom = userIdFrom;
    }

    public int getUserIdTo() {
        return userIdTo;
    }

    public void setUserIdTo(int userIdTo) {
        this.userIdTo = userIdTo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
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
                ", userIdFrom=" + userIdFrom +
                ", userIdTo=" + userIdTo +
                ", description='" + description + '\'' +
                ", isDeleted=" + isDeleted +
                ", status='" + status + '\'' +
                ", transferType='" + transferType + '\'' +
                '}';
    }
}
