package com.maznichko.servlets;

import com.maznichko.dao.RequestDAO;
import com.maznichko.dao.impl.FeedbackDAOimpl;
import com.maznichko.dao.impl.RequestDAOimpl;
import com.maznichko.services.Path;
import com.maznichko.services.manager.MasterReport;
import com.maznichko.services.manager.UserReport;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ManagerReportServlet", value = "/ManagerReportServlet")
public class ManagerReportServlet extends HttpServlet {
    private MasterReport masterReport;
    private UserReport userReport;
    private static final Logger log = Logger.getLogger(ManagerReportServlet.class);
    @Override
    public void init() throws ServletException {
        RequestDAO requestDAO = new RequestDAOimpl();
        masterReport = new MasterReport(requestDAO,new FeedbackDAOimpl());
        userReport = new UserReport(requestDAO);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("repo") == null){
            masterReport.execute(request,response);
        }else {
            userReport.getReport(request);
        }
        request.getRequestDispatcher(Path.REPORT_JSP)
                .forward(request,response);


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
