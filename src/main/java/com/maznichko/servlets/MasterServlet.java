package com.maznichko.servlets;

import com.maznichko.dao.impl.RequestDAOimpl;
import com.maznichko.services.*;
import com.maznichko.services.filtration.Filterable;
import com.maznichko.services.common.GetBank;
import com.maznichko.services.filtration.GenerateTableRequests;
import com.maznichko.services.filtration.Pagination;
import com.maznichko.services.master.MasterCommand;
import com.maznichko.services.master.MasterContainer;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "MasterServlet", value = "/MasterServlet")

public class MasterServlet extends HttpServlet {
    private Filterable filter;
    private static final Logger log = Logger.getLogger(MasterServlet.class);

    @Override
    public void init() {
        filter = new GenerateTableRequests(new RequestDAOimpl());
        filter.linkWith(new Pagination());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        filter.action(new ArrayList<>(), request);
        GetBank.getBank(request);
        String result = request.getParameter("result");
        request.setAttribute("result", result);
        request.setAttribute("bank", request.getSession().getAttribute("bank"));
        RequestDispatcher dispatcher = request.getRequestDispatcher(Path.MASTER_JSP);
        dispatcher.forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MasterCommand command = MasterContainer.get(request.getParameter("command"));
        String path = Path.MASTER_JSP;
        if (command != null) {
            path = command.execute(request, response);
        }
        log.info("<-------------- command: " + command + " is completed");
        response.sendRedirect(path + "?result=" + request.getAttribute("result"));
    }
}
