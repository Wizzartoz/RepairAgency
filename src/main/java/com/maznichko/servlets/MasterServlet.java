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
        doPost(request,response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Command command = CommandContainer.get(request.getParameter("command"));
        String result = Path.MASTER_JSP;
        if (command != null) {
            result = command.execute(request, response);
        }
        GenerateTable.execute(request,response);
        GetBank.execute(request,response);
        HttpSession httpSession = request.getSession();
        request.setAttribute("bank",httpSession.getAttribute("bank"));
        System.out.println(request.getAttribute("result"));
        RequestDispatcher dispatcher = request.getRequestDispatcher(result);
        dispatcher.forward(request, response);
    }
}
