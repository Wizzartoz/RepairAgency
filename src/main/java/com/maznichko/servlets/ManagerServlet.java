package com.maznichko.servlets;


import com.maznichko.SendEmail;
import com.maznichko.dao.UserDAO;
import com.maznichko.dao.impl.RequestDAOimpl;
import com.maznichko.dao.impl.UserDAOimpl;
import com.maznichko.services.*;
import com.maznichko.services.common.Replenishment;
import com.maznichko.services.manager.Command;
import com.maznichko.services.manager.CommandContainer;
import com.maznichko.services.common.GetBank;
import com.maznichko.services.common.GetMasters;
import com.maznichko.services.common.GetUsers;
import com.maznichko.services.filtration.*;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;


@WebServlet(name = "ManagerServlet", value = "/ManagerServlet")

public class ManagerServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(ManagerServlet.class);
    private Filterable filter;
    private Replenishment replenishment;
    private Register register;

    @Override
    public void init() throws ServletException {
        UserDAO userDAO = new UserDAOimpl();
        filter = new GenerateTableRequests(new RequestDAOimpl());
        filter.linkWith(new StatusFilter())
                .linkWith(new PaymentFilter())
                .linkWith(new MasterFilter())
                .linkWith(new Sort())
                .linkWith(new Pagination());
        replenishment = new Replenishment(userDAO);
        register = new Register(userDAO,new SendEmail());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        GetBank.getBank(request);
        GetMasters.findMasters(request);
        GetUsers.execute(request);
        String path = Path.MANAGER_JSP;
        filter.action(new ArrayList<>(), request);
        Command command = CommandContainer.get(request.getParameter("command"));
        if (command != null) {
            path = command.execute(request, response);
        }
        log.info("<----------- command: " + command + " is completed");
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
            boolean isReplenishment = replenishment.replenishment(request);
            if (isReplenishment) {
                path = Path.MANAGER_SERVLET;
            } else {
                log.error("<-------------- replenishment is failed");
                path = Path.ERROR;
            }
        }
        Command command = CommandContainer.get(request.getParameter("command"));
        if (command != null) {
            path = command.execute(request, response);
        }
        log.info("<------------ command: " + command + " is completed");
        if (request.getParameter("name") != null) {
            path = Path.MANAGER_SERVLET;
            register.register(request, "MASTER",false);
            log.info("<------------ registration method was completed");
        }
        response.sendRedirect(path + "?result=" + request.getAttribute("result"));
    }
}
