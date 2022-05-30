package com.maznichko.services.manager;

import com.maznichko.dao.DBException;
import com.maznichko.dao.FeedbackDAO;
import com.maznichko.dao.RequestDAO;
import com.maznichko.dao.entity.Feedback;
import com.maznichko.dao.entity.Request;
import com.maznichko.dao.entity.User;
import com.maznichko.services.common.GetMasters;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class MasterReport implements Command {
    private final RequestDAO requestDAO;
    private final FeedbackDAO feedbackDAO;

    private static final Logger log = Logger.getLogger(MasterReport.class);

    public MasterReport(RequestDAO requestDAO, FeedbackDAO feedbackDAO) {
        this.requestDAO = requestDAO;
        this.feedbackDAO = feedbackDAO;
    }

    /**
     * This method generates a masters report
     *
     * @param req  - request who we are getting
     * @param resp - response of servlet
     * @return - path to servlet
     */
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        //Get all masters
        List<User> masters = GetMasters.findMasters(req);
        List<ReportEntity> report = new ArrayList<>();
        //setup list of reportEntity
        for (User user : masters) {
            ReportEntity reportEntity = new ReportEntity();
            List<Request> requests;
            String masterLogin = user.getLogin();
            try {
                requests = requestDAO.getRequestByLogin(masterLogin);
            } catch (DBException e) {
                log.error(e.getMessage() + "<-------- get request by login is failed");
                throw new RuntimeException(e);
            }
            reportEntity.setAmountOfOrders(requests.size());
            reportEntity.setMasterLogin(masterLogin);
            String rating = String.valueOf(getRating(masterLogin)).substring(0,3);
            reportEntity.setRate(rating);
            report.add(reportEntity);
            reportEntity.setEarnings(requests
                    .stream()
                    .map(Request::getPrice)
                    .reduce(Float::sum).orElse(0F));
            reportEntity.setDoneOrders(doneRequest(requests));
            reportEntity.setTakeOrders(takeRequest(requests));
        }
        req.setAttribute("totalSum", allSum(report));
        req.setAttribute("report", report.stream().sorted().collect(Collectors.toList()));

        return "/jsp/Manager/report.jsp";
    }

    private int takeRequest(List<Request> requests) {
        return (int) requests
                .stream()
                .filter(request -> request.getComplicationStatus().equals("in progress"))
                .count();
    }

    private int doneRequest(List<Request> requests) {
        return (int) requests
                .stream()
                .filter(request -> request.getComplicationStatus().equals("done"))
                .count();
    }

    private double allSum(List<ReportEntity> reportEntity) {
        return reportEntity
                .stream()
                .map(ReportEntity::getEarnings)
                .mapToDouble(x -> x)
                .sum();
    }

    private double getRating(String masterLogin) {
        List<Feedback> feedbacks;
        try {
            feedbacks = feedbackDAO.findAll();
        } catch (DBException e) {
            log.error(e.getMessage() + "<------- find all feedback is failed");
            throw new RuntimeException(e);
        }
        return feedbacks
                .stream()
                .filter(feedback -> feedback.getMasterLogin().equals(masterLogin))
                .map(Feedback::getRating)
                .mapToDouble(x -> x)
                .average()
                .orElse(0);
    }

}
