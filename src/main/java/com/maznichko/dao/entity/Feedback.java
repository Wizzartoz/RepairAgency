package com.maznichko.dao.entity;

import java.sql.Timestamp;
import java.util.Objects;

public class Feedback implements Entity{
    long feedbackID;
    String feedbackText;
    Integer rating;
    Timestamp date;
    long requestID;
    String masterLogin;

    public long getFeedbackID() {
        return feedbackID;
    }

    public void setFeedbackID(long feedbackID) {
        this.feedbackID = feedbackID;
    }

    public String getFeedbackText() {
        return feedbackText;
    }

    public void setFeedbackText(String feedbackText) {
        this.feedbackText = feedbackText;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public long getRequestID() {
        return requestID;
    }

    public void setRequestID(long requestID) {
        this.requestID = requestID;
    }

    public String getMasterLogin() {
        return masterLogin;
    }

    public void setMasterLogin(String masterLogin) {
        this.masterLogin = masterLogin;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Feedback)) return false;
        Feedback feedback = (Feedback) obj;
        return Objects.equals(feedback.getRequestID(),this.feedbackID);
    }
//Гарантированная колизия , нужно это как то исправить
    @Override
    public int hashCode() {
        return (int) this.feedbackID;
    }

    @Override
    public String toString() {
        return String.valueOf(getRequestID());
    }
}
