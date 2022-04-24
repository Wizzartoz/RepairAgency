package com.maznichko.services;

import com.maznichko.DAO.DBException;
import com.maznichko.DAO.FeedbackDAO;
import com.maznichko.DAO.entity.Feedback;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LeaveFeedback implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession httpSession = req.getSession();
        FeedbackDAO feedbackDAO = new FeedbackDAO();
        String feedbackText = req.getParameter("feedback");
        String status = (String) httpSession.getAttribute("comp");
        int rating;
        try {
            rating = Integer.parseInt(req.getParameter("rating"));
        } catch (NumberFormatException e) {
            req.setAttribute("result", "data in evaluation field is incorrect");
            return "/jsp/Customer/feedback.jsp";
        }

        int id = Integer.parseInt((String) httpSession.getAttribute("feedbackID"));
        Feedback feedback = new Feedback();
        feedback.setFeedbackText(feedbackText);
        feedback.setRating(rating);
        feedback.setRequestID(id);
        if (feedbackText.isEmpty()) {
            req.setAttribute("result", "review is empty");
            return "/jsp/Customer/feedback.jsp";
        }
        if (!status.equals("done")) {
            req.setAttribute("result", "the master has not made an order yet");
            return "/jsp/Customer/feedback.jsp";
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
            return "/jsp/Customer/feedback.jsp";
        }
        try {
            feedbackDAO.insertFeedback(feedback);
        } catch (DBException e) {
            req.setAttribute("result", "Can't leave a comment");
            return "/jsp/Error.jsp";
        }
        req.setAttribute("result", "comment successfully posted");
        return "/jsp/Customer/feedback.jsp";


    }
}
