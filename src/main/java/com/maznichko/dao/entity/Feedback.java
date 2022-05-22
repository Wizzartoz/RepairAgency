package com.maznichko.dao.entity;

import java.sql.Timestamp;
import java.util.Objects;

/**
 * This class is entity for feedback
 */
public class Feedback implements Entity{
    private long feedbackID;
    private String feedbackText;
    private Integer rating;
    private Timestamp date;
    private long requestID;
    private String masterLogin;

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
        return Objects.equals(feedback.getRequestID(),this.requestID);
    }
    @Override
    public int hashCode() {
        return (int) this.feedbackID;
    }

    @Override
    public String toString() {
        return String.valueOf(getRequestID());
    }
}
