package com.maznichko.servlets;

import com.maznichko.services.*;
import com.maznichko.services.commands.Command;
import com.maznichko.services.commands.CommandContainer;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "MasterServlet", value = "/MasterServlet")
public class MasterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Table.generateTable(request,response);
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
