package com.maznichko.servlets;

import com.maznichko.dao.impl.RequestDAOimpl;
import com.maznichko.dao.impl.UserDAOimpl;
import com.maznichko.services.Path;
import com.maznichko.services.common.GetBank;
import com.maznichko.services.common.Replenishment;
import com.maznichko.services.customer.CustomerCommand;
import com.maznichko.services.customer.CustomerContainer;
import com.maznichko.services.filtration.Filterable;
import com.maznichko.services.filtration.GenerateTableRequests;
import com.maznichko.services.filtration.Pagination;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;


@WebServlet(name = "GeneralCustomerServlet", value = "/GeneralCustomerServlet")
public class GeneralCustomerServlet extends HttpServlet {
    private Filterable getTable;
    private Replenishment replenishment;

    @Override
    public void init() {
        getTable = new GenerateTableRequests(new RequestDAOimpl());
        getTable.linkWith(new Pagination());
        replenishment = new Replenishment(new UserDAOimpl());
    }

    private static final Logger log = Logger.getLogger(GeneralCustomerServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getTable.action(new ArrayList<>(), request);
        GetBank.getBank(request);
        String result = request.getParameter("result");
        request.setAttribute("result", result);
        Integer bank = (Integer) request.getSession().getAttribute("bank");
        request.setAttribute("bank", bank);
        RequestDispatcher dispatcher = request.getRequestDispatcher(Path.CUSTOMER_JSP);
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = Path.CUSTOMER_JSP;
        if (request.getParameter("money") != null) {
            boolean isReplenishment = replenishment.replenishment(request);
            if (isReplenishment) {
                path = Path.CUSTOMER_SERVLET;
            } else {
                log.error("<------------- replenishment is failed");
                path = Path.ERROR;
            }
        }
        CustomerCommand command = CustomerContainer.get(request.getParameter("command"));
        if (command != null) {
            path = command.execute(request);
            log.info("<------------ command: " + command + " is completed");
        }
        response.sendRedirect(path + "?result=" + request.getAttribute("result"));
    }
}
