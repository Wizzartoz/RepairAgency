package com.maznichko.servlets;


import com.maznichko.dao.impl.RequestDAOimpl;
import com.maznichko.dao.impl.UserDAOimpl;
import com.maznichko.services.*;
import com.maznichko.services.common.Replenishment;
import com.maznichko.services.manager.Command;
import com.maznichko.services.manager.CommandContainer;
import com.maznichko.services.common.GetBank;
import com.maznichko.services.common.GetMasters;
import com.maznichko.services.common.GetUsers;
import com.maznichko.services.filter.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;


@WebServlet(name = "ManagerServlet", value = "/ManagerServlet")

public class ManagerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Command command = CommandContainer.get(request.getParameter("command"));
        GetBank.execute(request, response);
        GetMasters.findMasters(request);
        GetUsers.execute(request, response);
        String result = Path.MANAGER_JSP;
        Filterable filter = new GenerateTableRequests(new RequestDAOimpl());
        filter.linkWith(new StatusFilter())
                .linkWith(new PaymentFilter())
                .linkWith(new MasterFilter())
                .linkWith(new Sort())
                .linkWith(new Pagination());
        filter.action(new ArrayList<>(), request);
        if (command != null) {
            result = command.execute(request, response);
        }
        if (request.getParameter("result") != null) {
            String res = request.getParameter("result");
            request.setAttribute("result", res);
        }
        request.setAttribute("bank", request.getSession().getAttribute("bank"));
        RequestDispatcher dispatcher = request.getRequestDispatcher(result);
        dispatcher.forward(request, response);


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Command command = CommandContainer.get(request.getParameter("command"));
        String result = Path.MANAGER_JSP;
        if (request.getParameter("login") != null) {
            if (new Replenishment(new UserDAOimpl()).replenishment(request)) {
                result = Path.MANAGER_SERVLET;
            }else {
                result = Path.ERROR;
            }
        }
        if (command != null) {
            result = command.execute(request, response);
        }
        String res = (String) request.getAttribute("result");
        response.sendRedirect(result + "?result=" + res);
    }
}
