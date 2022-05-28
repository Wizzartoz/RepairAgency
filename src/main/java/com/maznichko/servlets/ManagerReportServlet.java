package com.maznichko.servlets;

import com.maznichko.dao.impl.FeedbackDAOimpl;
import com.maznichko.dao.impl.RequestDAOimpl;
import com.maznichko.dao.impl.UserDAOimpl;
import com.maznichko.services.Path;
import com.maznichko.services.customer.Paid;
import com.maznichko.services.manager.MasterReport;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ManagerReportServlet", value = "/ManagerReportServlet")
public class ManagerReportServlet extends HttpServlet {
    MasterReport masterReport;
    private static final Logger log = Logger.getLogger(ManagerReportServlet.class);
    @Override
    public void init() throws ServletException {
        masterReport = new MasterReport(new RequestDAOimpl(),new FeedbackDAOimpl());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        masterReport.execute(request,response);
        request.getRequestDispatcher(Path.REPORT_JSP)
                .forward(request,response);


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
