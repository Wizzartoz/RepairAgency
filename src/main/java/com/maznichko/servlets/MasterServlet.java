package com.maznichko.servlets;

import com.maznichko.dao.impl.RequestDAOimpl;
import com.maznichko.services.*;
import com.maznichko.services.manager.Command;
import com.maznichko.services.manager.CommandContainer;
import com.maznichko.services.common.GetBank;
import com.maznichko.services.filter.GenerateTableRequests;
import com.maznichko.services.filter.Pagination;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "MasterServlet", value = "/MasterServlet")
public class MasterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        new GenerateTableRequests(new RequestDAOimpl()).linkWith(new Pagination()).action(new ArrayList<>(), request);
        GetBank.execute(request,response);
        if (request.getParameter("result") != null) {
            String res = request.getParameter("result");
            request.setAttribute("result", res);
        }
        request.setAttribute("bank",request.getSession().getAttribute("bank"));
        RequestDispatcher dispatcher = request.getRequestDispatcher(Path.MASTER_JSP);
        dispatcher.forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Command command = CommandContainer.get(request.getParameter("command"));
        String result = Path.MASTER_JSP;
        if (command != null) {
            result = command.execute(request, response);
        }
        String res = (String) request.getAttribute("result");
        response.sendRedirect(result + "?result=" + res);
    }
}
