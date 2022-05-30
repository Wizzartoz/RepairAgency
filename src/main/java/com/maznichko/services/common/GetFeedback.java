package com.maznichko.services.common;

import com.maznichko.dao.DBException;
import com.maznichko.dao.FeedbackDAO;
import com.maznichko.dao.entity.Feedback;

import java.util.List;
import java.util.stream.Collectors;

public class GetFeedback {
    FeedbackDAO feedbackDAO;
    public GetFeedback(FeedbackDAO feedbackDAO){
        this.feedbackDAO = feedbackDAO;
    }
    public List<Feedback> getFeedback(String masterLogin){
        List<Feedback> feedbacks;
        try {
            feedbacks = feedbackDAO.findAll();
        } catch (DBException e) {
            throw new RuntimeException(e);
        }
        return feedbacks
                .stream()
                .filter(feedback -> feedback.getMasterLogin().equals(masterLogin))
                .collect(Collectors.toList());
    }
}
