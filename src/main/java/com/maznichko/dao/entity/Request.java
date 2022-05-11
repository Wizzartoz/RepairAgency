package com.maznichko.dao.entity;

import java.sql.Timestamp;
import java.util.Objects;

public class Request implements Entity {
    private long requestID;
    private String description;
    private Timestamp date;
    private String complicationStatus;
    private String paymentStatus;
    private Float price;
    private String masterLogin;

    public String getMasterLogin() {
        return masterLogin;
    }

    public void setMasterLogin(String masterLogin) {
        this.masterLogin = masterLogin;
    }

    public long getRequestID() {
        return requestID;
    }

    public void setRequestID(Integer requestID) {
        this.requestID = requestID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getComplicationStatus() {
        return complicationStatus;
    }

    public void setComplicationStatus(String complicationStatus) {
        this.complicationStatus = complicationStatus;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Request)) return false;
        Request request = (Request) obj;
        return Objects.equals(request.getRequestID(), this.requestID);
    }

    @Override
    public int hashCode() {
        return (int) this.requestID;
    }

    @Override
    public String toString() {
        return String.valueOf(requestID);
    }
}
