package com.maznichko.servlets;

import com.maznichko.dao.impl.RequestDAOimpl;
import com.maznichko.dao.impl.UserDAOimpl;
import com.maznichko.services.Path;
import com.maznichko.services.common.GetBank;
import com.maznichko.services.common.Replenishment;
import com.maznichko.services.customer.CustomerCommand;
import com.maznichko.services.customer.CustomerContainer;
import com.maznichko.services.filter.Filterable;
import com.maznichko.services.filter.GenerateTableRequests;
import com.maznichko.services.filter.Pagination;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;


@WebServlet(name = "GeneralCustomerServlet", value = "/GeneralCustomerServlet")
public class GeneralCustomerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Filterable getTable = new GenerateTableRequests(new RequestDAOimpl());
        getTable.linkWith(new Pagination());
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
            boolean isReplenishment = new Replenishment(new UserDAOimpl()).replenishment(request);
            if (isReplenishment) {
                path = Path.CUSTOMER_SERVLET;
            } else {
                path = Path.ERROR;
            }
        }
        CustomerCommand command = CustomerContainer.get(request.getParameter("command"));
        if (command != null) {
            path = command.execute(request);
        }
        response.sendRedirect(path + "?result=" + request.getAttribute("result"));
    }
}
