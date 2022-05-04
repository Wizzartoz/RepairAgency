package com.maznichko.services.commands;

import com.maznichko.DAO.DBException;
import com.maznichko.DAO.FeedbackDAO;
import com.maznichko.DAO.entity.Feedback;
import com.maznichko.services.commands.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LeaveFeedback implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        FeedbackDAO feedbackDAO = new FeedbackDAO();
        String feedbackText = req.getParameter("feedback");
        String status = req.getParameter("comp");
        int rating;
        try {
            rating = Integer.parseInt(req.getParameter("rating"));
        } catch (NumberFormatException e) {
            req.setAttribute("result", "data in evaluation field is incorrect");
            return "/jsp/Customer/customerMain.jsp";
        }

        int id = Integer.parseInt(req.getParameter("feedbackID"));
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
            feedback1 = feedbackDAO.getFeedback(id);
        } catch (DBException e) {
            req.setAttribute("result", e.getMessage());
            return "/jsp/Error.jsp";
        }
        if (feedback1 != null) {
            req.setAttribute("result", "you have already submitted a request");
            return "/jsp/Customer/customerMain.jsp";
        }
        try {
            feedbackDAO.insertFeedback(feedback);
        } catch (DBException e) {
            req.setAttribute("result", "Can't leave a comment");
            return "/jsp/Error.jsp";
        }
        req.setAttribute("result", "comment successfully posted");
        return "/jsp/Customer/customerMain.jsp";


    }
}
