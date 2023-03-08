package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Request extends Transfer {

    public Request() {
    }

    public Request(int transferId, BigDecimal amount, int accountFromId, int accountToId, String description, boolean isDeleted) {
        super(transferId, amount, accountFromId, accountToId, description, isDeleted);

        setTransferType("Request");
        setStatus("Pending");

    }

    public void setStatusApproval(String status) {
        if (status.equalsIgnoreCase("Approved")) {
            setStatus("Approved");
        }
        else setStatus("Rejected");
    }

}
