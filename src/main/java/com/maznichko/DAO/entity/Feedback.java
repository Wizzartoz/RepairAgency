package com.maznichko.DAO.entity;

import java.sql.Timestamp;
import java.util.Objects;

public class Feedback {
    Integer feedbackID;
    String feedbackText;
    Integer rating;
    Timestamp date;
    Integer requestID;
    String masterLogin;

    public Integer getFeedbackID() {
        return feedbackID;
    }

    public void setFeedbackID(Integer feedbackID) {
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

    public Integer getRequestID() {
        return requestID;
    }

    public void setRequestID(Integer requestID) {
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
        return Objects.equals(feedback.getRequestID(),this.requestID);
    }

    @Override
    public int hashCode() {
        return this.requestID.hashCode();
    }

    @Override
    public String toString() {
        return String.valueOf(getRequestID());
    }
}
