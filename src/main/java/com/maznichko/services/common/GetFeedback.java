package com.maznichko.services.common;

import com.maznichko.dao.DBException;
import com.maznichko.dao.FeedbackDAO;
import com.maznichko.dao.entity.Feedback;
import com.maznichko.services.Path;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

public class GetFeedback {
    private final FeedbackDAO feedbackDAO;
    private static final Logger log = Logger.getLogger(GetBank.class);
    public GetFeedback(FeedbackDAO feedbackDAO){
        this.feedbackDAO = feedbackDAO;
    }
    public String getFeedback(HttpServletRequest request, String masterLogin){
        List<Feedback> feedbacks;
        try {
            feedbacks = feedbackDAO.findAll();
        } catch (DBException e) {
            log.error("<----------- find all feedbacks is failed",e);
            return Path.ERROR;
        }
        List<Feedback> table = feedbacks
                .stream()
                .filter(feedback -> feedback.getMasterLogin().equals(masterLogin))
                .collect(Collectors.toList());
        request.setAttribute("table", table);
        return Path.RATING_JSP;
    }
}
