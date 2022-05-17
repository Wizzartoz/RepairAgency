package com.maznichko.servlets;

import com.maznichko.services.Path;
import com.maznichko.services.manager.Command;
import com.maznichko.services.manager.CommandContainer;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "LoginServlet", value = "/LoginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("result", request.getParameter("result"));
        RequestDispatcher dispatcher = request.getRequestDispatcher(Path.LOGIN_JSP);
        dispatcher.forward(request, response);


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Command command = CommandContainer.get(request.getParameter("command"));
        String result = Path.ERROR;
        if (command != null) {
            result = CommandContainer.get(request.getParameter("command")).execute(request, response);
        }
        response.sendRedirect(result + "?result=" + request.getAttribute("result"));
    }
}

