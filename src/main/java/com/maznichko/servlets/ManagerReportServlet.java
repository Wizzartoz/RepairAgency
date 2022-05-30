package com.maznichko.servlets;

import com.maznichko.dao.FeedbackDAO;
import com.maznichko.dao.RequestDAO;
import com.maznichko.dao.entity.Feedback;
import com.maznichko.dao.impl.FeedbackDAOimpl;
import com.maznichko.dao.impl.RequestDAOimpl;
import com.maznichko.dao.impl.UserDAOimpl;
import com.maznichko.services.Path;
import com.maznichko.services.common.GetFeedback;
import com.maznichko.services.manager.BLockUser;
import com.maznichko.services.manager.MasterReport;
import com.maznichko.services.manager.UserReport;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ManagerReportServlet", value = "/ManagerReportServlet")
public class ManagerReportServlet extends HttpServlet {
    private MasterReport masterReport;
    private UserReport userReport;
    private GetFeedback getFeedback;
    private BLockUser bLockUser;
    private static final Logger log = Logger.getLogger(ManagerReportServlet.class);

    @Override
    public void init() throws ServletException {
        RequestDAO requestDAO = new RequestDAOimpl();
        FeedbackDAO feedbackDAO = new FeedbackDAOimpl();
        masterReport = new MasterReport(requestDAO, feedbackDAO);
        userReport = new UserReport(requestDAO);
        getFeedback = new GetFeedback(feedbackDAO);
        bLockUser = new BLockUser(new UserDAOimpl());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path;
        String masterLogin = request.getParameter("rating");
        if (masterLogin != null) {
            List<Feedback> feedbacks = getFeedback.getFeedback(masterLogin);
            request.setAttribute("table", feedbacks);
            path = "/jsp/Manager/rating.jsp";
        } else if (request.getParameter("repo") == null) {
            path = userReport.getReport(request);
        } else {
            path = masterReport.execute(request, response);
        }
        request.getRequestDispatcher(path)
                .forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = Path.REPORT_JSP;
       if (request.getParameter("block") != null) {
            if (request.getParameter("block").equals("Block")) {
                path = bLockUser.block(request.getParameter("login"));
            } else {
                path = bLockUser.unblock(request.getParameter("login"));
            }
        }
       response.sendRedirect(path);
    }
}
