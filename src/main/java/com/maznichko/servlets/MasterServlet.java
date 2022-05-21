package com.maznichko.servlets;

import com.maznichko.dao.impl.RequestDAOimpl;
import com.maznichko.services.*;
import com.maznichko.services.filter.Filterable;
import com.maznichko.services.common.GetBank;
import com.maznichko.services.filter.GenerateTableRequests;
import com.maznichko.services.filter.Pagination;
import com.maznichko.services.master.MasterCommand;
import com.maznichko.services.master.MasterContainer;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "MasterServlet", value = "/MasterServlet")
public class MasterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Filterable filter = new GenerateTableRequests(new RequestDAOimpl());
        filter.linkWith(new Pagination());
        filter.action(new ArrayList<>(), request);
        GetBank.execute(request, response);
        String res = request.getParameter("result");
        System.out.println(res);
        request.setAttribute("result", res);
        request.setAttribute("bank", request.getSession().getAttribute("bank"));
        RequestDispatcher dispatcher = request.getRequestDispatcher(Path.MASTER_JSP);
        dispatcher.forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MasterCommand command = MasterContainer.get(request.getParameter("command"));
        String result = Path.MASTER_JSP;
        if (command != null) {
            result = command.execute(request, response);
        }
        String res = (String) request.getAttribute("result");
        response.sendRedirect(result + "?result=" + res);
    }
}
