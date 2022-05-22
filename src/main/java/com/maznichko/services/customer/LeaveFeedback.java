package com.maznichko.services.customer;

import com.maznichko.dao.DBException;
import com.maznichko.dao.FeedbackDAO;
import com.maznichko.dao.RequestDAO;
import com.maznichko.dao.entity.Feedback;
import com.maznichko.dao.entity.Request;
import com.maznichko.services.Path;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class LeaveFeedback implements CustomerCommand {
    private final FeedbackDAO feedbackDAO;
    private final RequestDAO requestDAO;

    public LeaveFeedback(FeedbackDAO feedbackDAO, RequestDAO requestDAO) {
        this.feedbackDAO = feedbackDAO;
        this.requestDAO = requestDAO;
    }

    /**
     * Put feedback into DB
     * @param req - request who we are getting
     * @return - path of servlet
     */
    @Override
    public String execute(HttpServletRequest req) {
        //checking rating
        String feedbackText = req.getParameter("feedback");
        int rating;
        try {
            rating = Integer.parseInt(req.getParameter("rating"));
        } catch (NumberFormatException e) {
            req.setAttribute("result", "data in evaluation field is incorrect");
            return Path.CUSTOMER_SERVLET;
        }
        if (!(rating >= 1 && rating <= 5)) {
            req.setAttribute("result", "rating have incorrect value");
            return Path.CUSTOMER_SERVLET;
        }
        //checking id
        long id;
        try {
            id = Long.parseLong(req.getParameter("feedbackID"));
        } catch (NumberFormatException e) {
            req.setAttribute("result", e.getMessage());
            return Path.ERROR;
        }
        //get and checking request
        Request request;
        try {
            request = requestDAO.getData(id);
        } catch (DBException e) {
            req.setAttribute("result", e.getMessage());
            return Path.ERROR;
        }
        if (!request.getComplicationStatus().equals("done")) {
            req.setAttribute("result", "the master has not made an order yet");
            return Path.CUSTOMER_SERVLET;
        }
        //set up feedbacks
        Feedback feedback = new Feedback();
        feedback.setFeedbackText(feedbackText);
        feedback.setRating(rating);
        feedback.setRequestID(id);
        feedback.setMasterLogin(request.getMasterLogin());
        if (feedbackText.isEmpty()) {
            req.setAttribute("result", "review is empty");
            return Path.CUSTOMER_SERVLET;
        }
        //check feedback on duplicate
        List<Feedback> feedbackList;
        try {
            feedbackList = feedbackDAO.findAll();
        } catch (DBException e) {
            req.setAttribute("result", e.getMessage());
            return Path.ERROR;
        }
        if (feedbackList.contains(feedback)) {
            req.setAttribute("result", "feedback already left");
            return Path.CUSTOMER_SERVLET;
        }
        //insert feedback
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
