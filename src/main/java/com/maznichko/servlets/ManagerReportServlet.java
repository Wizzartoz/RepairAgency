package com.maznichko.servlets;

import com.maznichko.SendEmail;
import com.maznichko.dao.FeedbackDAO;
import com.maznichko.dao.RequestDAO;
import com.maznichko.dao.impl.FeedbackDAOimpl;
import com.maznichko.dao.impl.RequestDAOimpl;
import com.maznichko.dao.impl.UserDAOimpl;
import com.maznichko.services.Path;
import com.maznichko.services.common.GetFeedback;
import com.maznichko.services.manager.BLockUser;
import com.maznichko.services.manager.GetMasterRequests;
import com.maznichko.services.manager.MasterReport;
import com.maznichko.services.manager.UserReport;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ManagerReportServlet", value = "/ManagerReportServlet")
public class ManagerReportServlet extends HttpServlet {
    private MasterReport masterReport;
    private UserReport userReport;
    private GetFeedback getFeedback;
    private BLockUser bLockUser;
    private GetMasterRequests getMasterRequests;

    @Override
    public void init() {
        RequestDAO requestDAO = new RequestDAOimpl();
        FeedbackDAO feedbackDAO = new FeedbackDAOimpl();
        masterReport = new MasterReport(requestDAO, feedbackDAO);
        userReport = new UserReport(requestDAO);
        getFeedback = new GetFeedback(feedbackDAO);
        bLockUser = new BLockUser(new UserDAOimpl(), new SendEmail());
        getMasterRequests = new GetMasterRequests(requestDAO);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path;
        String masterLogin = request.getParameter("rating");
        if (masterLogin != null) {
            path = getFeedback.getFeedback(request, masterLogin);
        } else if (request.getParameter("requests") != null) {
            path = getMasterRequests.getRequests(request, request.getParameter("requests"));
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
