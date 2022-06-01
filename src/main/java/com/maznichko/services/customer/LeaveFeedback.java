package com.maznichko.services.customer;

import com.maznichko.dao.DBException;
import com.maznichko.dao.FeedbackDAO;
import com.maznichko.dao.RequestDAO;
import com.maznichko.dao.entity.Feedback;
import com.maznichko.dao.entity.Request;
import com.maznichko.services.Path;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class LeaveFeedback implements CustomerCommand {
    private final FeedbackDAO feedbackDAO;
    private final RequestDAO requestDAO;
    private final Feedback feedback;
    private static final Logger log = Logger.getLogger(LeaveFeedback.class);

    public LeaveFeedback(FeedbackDAO feedbackDAO, RequestDAO requestDAO) {
        this.feedbackDAO = feedbackDAO;
        this.requestDAO = requestDAO;
        feedback = new Feedback();
    }

    /**
     * Put feedback into DB
     *
     * @param req - request who we are getting
     * @return - path of servlet
     */
    @Override
    public String execute(HttpServletRequest req) {
        //Checking rating
        String feedbackText = req.getParameter("feedback");
        int rating;
        try {
            rating = Integer.parseInt(req.getParameter("rating"));
        } catch (NumberFormatException e) {
            req.setAttribute("result", "Data in evaluation field is incorrect");
            return Path.CUSTOMER_SERVLET;
        }
        if (!(rating >= 1 && rating <= 5)) {
            req.setAttribute("result", "Rating have incorrect value");
            return Path.CUSTOMER_SERVLET;
        }
        //Checking id
        long id;
        try {
            id = Long.parseLong(req.getParameter("feedbackID"));
        } catch (NumberFormatException e) {
            log.error("<---------- leave feedback is failed", e);
            return Path.ERROR;
        }
        //Getting and checking request
        Request request = getRequest(id);
        if (request == null) {
            return Path.ERROR;
        }
        if (!request.getComplicationStatus().equals("done")) {
            req.setAttribute("result", "The master has not made an order yet");
            return Path.CUSTOMER_SERVLET;
        }
        //Set up feedback
        feedback.setFeedbackText(feedbackText);
        feedback.setRating(rating);
        feedback.setRequestID(id);
        feedback.setMasterLogin(request.getMasterLogin());
        if (feedbackText.isEmpty()) {
            req.setAttribute("result", "Review is empty");
            return Path.CUSTOMER_SERVLET;
        }
        //Checking feedback on duplicate
        List<Feedback> feedbacks = findAllFeedback();
        if (feedbacks == null) {
            return Path.ERROR;
        }
        if (feedbacks.contains(feedback)) {
            req.setAttribute("result", "Feedback already left");
            return Path.CUSTOMER_SERVLET;
        }
        //Inserting feedback
        boolean isInsert = insertFeedback();
        if (!isInsert) {
            return Path.ERROR;
        }
        req.setAttribute("result", "Comment successfully posted");
        return Path.CUSTOMER_SERVLET;
    }

    private Request getRequest(long id) {
        Request request;
        try {
            request = requestDAO.getData(id);
        } catch (DBException e) {
            log.error("<--------- leave feedback is failed", e);
            return null;
        }
        return request;
    }

    private boolean insertFeedback() {
        try {
            feedbackDAO.insert(feedback);
        } catch (DBException e) {
            log.error("<--------- leave feedback is failed", e);
            return false;
        }
        return true;
    }

    private List<Feedback> findAllFeedback() {
        List<Feedback> feedbacks;
        try {
            feedbacks = feedbackDAO.findAll();
        } catch (DBException e) {
            log.error("<---------- leave feedback is failed", e);
            return null;
        }
        return feedbacks;
    }

}
