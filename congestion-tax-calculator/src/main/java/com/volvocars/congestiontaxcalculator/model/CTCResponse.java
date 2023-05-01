package com.volvocars.congestiontaxcalculator.model;

public class CTCResponse {

    private int taxAmount;

    private String detailMessage;

    public CTCResponse() { }

    public CTCResponse(int taxAmount, String message)
    {
        super();
        this.taxAmount = taxAmount;
        this.detailMessage = message;
    }

    public int getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(int taxAmount) {
        this.taxAmount = taxAmount;
    }

    public String getDetailMessage() {
        return detailMessage;
    }

    public void setDetailMessage(String detailMessage) {
        this.detailMessage = detailMessage;
    }
}
