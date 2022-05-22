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
        GetBank.execute(request, response);
        GetMasters.findMasters(request);
        GetUsers.execute(request, response);
        String path = Path.MANAGER_JSP;
        Filterable filter = new GenerateTableRequests(new RequestDAOimpl());
        filter.linkWith(new StatusFilter())
                .linkWith(new PaymentFilter())
                .linkWith(new MasterFilter())
                .linkWith(new Sort())
                .linkWith(new Pagination());
        filter.action(new ArrayList<>(), request);
        Command command = CommandContainer.get(request.getParameter("command"));
        if (command != null) {
            path = command.execute(request, response);
        }
        String result = request.getParameter("result");
        request.setAttribute("result", result);
        request.setAttribute("bank", request.getSession().getAttribute("bank"));
        RequestDispatcher dispatcher = request.getRequestDispatcher(path);
        dispatcher.forward(request, response);


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = Path.MANAGER_JSP;
        if (request.getParameter("login") != null) {
            boolean isReplenishment = new Replenishment(new UserDAOimpl()).replenishment(request);
            if (isReplenishment) {
                path = Path.MANAGER_SERVLET;
            } else {
                path = Path.ERROR;
            }
        }
        Command command = CommandContainer.get(request.getParameter("command"));
        if (command != null) {
            path = command.execute(request, response);
        }
        if (request.getParameter("name") != null) {
            path = Path.MANAGER_SERVLET;
            Register register = new Register(new UserDAOimpl());
            register.register(request, "MASTER");
        }
        response.sendRedirect(path + "?result=" + request.getAttribute("result"));
    }
}
