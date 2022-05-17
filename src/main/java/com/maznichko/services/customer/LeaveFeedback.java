package com.maznichko.services.customer;

import com.maznichko.dao.DBException;
import com.maznichko.dao.FeedbackDAO;
import com.maznichko.dao.entity.Feedback;
import com.maznichko.services.Path;

import javax.servlet.http.HttpServletRequest;

public class LeaveFeedback implements CustomerCommand {
    private final FeedbackDAO feedbackDAO;
    public LeaveFeedback(FeedbackDAO feedbackDAO){
        this.feedbackDAO = feedbackDAO;
    }
    @Override
    public String execute(HttpServletRequest req) {
        String feedbackText = req.getParameter("feedback");
        String status = req.getParameter("comp");
        int rating;
        try {
            rating = Integer.parseInt(req.getParameter("rating"));
        } catch (NumberFormatException e) {
            req.setAttribute("result", "data in evaluation field is incorrect");
            return Path.CUSTOMER_SERVLET;
        }
        long id = Long.parseLong(req.getParameter("feedbackID"));
        Feedback feedback = new Feedback();
        feedback.setFeedbackText(feedbackText);
        feedback.setRating(rating);
        feedback.setRequestID(id);
        if (feedbackText.isEmpty()) {
            req.setAttribute("result", "review is empty");
            return Path.CUSTOMER_SERVLET;
        }
        if (!status.equals("done")) {
            req.setAttribute("result", "the master has not made an order yet");
            return Path.CUSTOMER_SERVLET;
        }
        Feedback feedback1;
        try {
            feedback1 = feedbackDAO.getData(id);
        } catch (DBException e) {
            req.setAttribute("result", e.getMessage());
            return Path.ERROR;
        }
        if (feedback1 != null) {
            req.setAttribute("result", "you have already submitted a request");
            return Path.CUSTOMER_SERVLET;
        }
        try {
            feedbackDAO.insert(feedback);
        } catch (DBException e) {
            req.setAttribute("result", "Can't leave a comment");
            return Path.ERROR;
        }
        req.setAttribute("result", "comment successfully posted");
        return Path.CUSTOMER_SERVLET;


    }
}
