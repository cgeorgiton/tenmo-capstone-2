package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Transfer {

    private int transferId;
    private BigDecimal amount;
    private int accountFromId;
    private int accountToId;
    private String description;
    private boolean isDeleted;
    private String status;
    private String transferType;

    public Transfer() {
    }

    public Transfer(int transferId, BigDecimal amount, int accountFromId, int accountToId, String description, boolean isDeleted, String status, String transferType) {
        this.transferId = transferId;
        this.amount = amount;
        this.accountFromId = accountFromId;
        this.accountToId = accountToId;
        this.description = description;
        this.isDeleted = isDeleted;
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

    public int getAccountFromId() {
        return accountFromId;
    }

    public void setAccountFromId(int accountFromId) {
        this.accountFromId = accountFromId;
    }

    public int getAccountToId() {
        return accountToId;
    }

    public void setAccountToId(int accountToId) {
        this.accountToId = accountToId;
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
                ", accountFromId=" + accountFromId +
                ", accountToId=" + accountToId +
                ", description='" + description + '\'' +
                ", isDeleted=" + isDeleted +
                ", status='" + status + '\'' +
                ", transferType='" + transferType + '\'' +
                '}';
    }
}
