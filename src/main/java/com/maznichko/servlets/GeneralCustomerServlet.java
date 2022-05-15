package com.maznichko.servlets;

import com.maznichko.services.Path;
import com.maznichko.services.commands.Command;
import com.maznichko.services.commands.CommandContainer;
import com.maznichko.services.Table;
import com.maznichko.services.GetBank;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
@WebServlet(name = "GeneralCustomerServlet", value = "/GeneralCustomerServlet")
public class GeneralCustomerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Table.generateTable(request, response);
        GetBank.execute(request, response);
        if (request.getParameter("result") != null) {
            String res = request.getParameter("result");
            request.setAttribute("result", res);
        }
        Integer bank = (Integer) request.getSession().getAttribute("bank");
        request.setAttribute("bank", bank);
        RequestDispatcher dispatcher = request.getRequestDispatcher(Path.CUSTOMER_JSP);
        dispatcher.forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Command command = CommandContainer.get(request.getParameter("command"));
        String result = Path.CUSTOMER_JSP;
        if (command != null) {
            result = command.execute(request, response);
        }
        String res = (String) request.getAttribute("result");
        response.sendRedirect(result + "?result=" + res);
    }
}
