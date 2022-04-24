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
        String feedbackText = req.getParameter("feedback");
        int rating = Integer.parseInt(req.getParameter("rating"));
        FeedbackDAO feedbackDAO = new FeedbackDAO();
        int id = Integer.parseInt((String) httpSession.getAttribute("feedbackID"));
        Feedback feedback = new Feedback();
        feedback.setFeedbackText(feedbackText);
        feedback.setRating(rating);
        feedback.setRequestID(id);
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
