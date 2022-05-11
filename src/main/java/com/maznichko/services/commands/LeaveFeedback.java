package com.maznichko.services.commands;

import com.maznichko.dao.DBException;
import com.maznichko.dao.entity.Feedback;
import com.maznichko.dao.impl.FeedbackDAOimpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LeaveFeedback implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        FeedbackDAOimpl feedbackDAOimpl = new FeedbackDAOimpl();
        String feedbackText = req.getParameter("feedback");
        String status = req.getParameter("comp");
        int rating;
        try {
            rating = Integer.parseInt(req.getParameter("rating"));
        } catch (NumberFormatException e) {
            req.setAttribute("result", "data in evaluation field is incorrect");
            return "/jsp/Customer/customerMain.jsp";
        }

        long id = Long.parseLong(req.getParameter("feedbackID"));
        Feedback feedback = new Feedback();
        feedback.setFeedbackText(feedbackText);
        feedback.setRating(rating);
        feedback.setRequestID(id);
        if (feedbackText.isEmpty()) {
            req.setAttribute("result", "review is empty");
            return "/jsp/Customer/customerMain.jsp";
        }
        if (!status.equals("done")) {
            req.setAttribute("result", "the master has not made an order yet");
            return "/jsp/Customer/customerMain.jsp";
        }
        Feedback feedback1;
        try {
            feedback1 = feedbackDAOimpl.getData(id);
        } catch (DBException e) {
            req.setAttribute("result", e.getMessage());
            return "/jsp/Error.jsp";
        }
        if (feedback1 != null) {
            req.setAttribute("result", "you have already submitted a request");
            return "/jsp/Customer/customerMain.jsp";
        }
        try {
            feedbackDAOimpl.insert(feedback);
        } catch (DBException e) {
            req.setAttribute("result", "Can't leave a comment");
            return "/jsp/Error.jsp";
        }
        req.setAttribute("result", "comment successfully posted");
        return "/jsp/Customer/customerMain.jsp";


    }
}
