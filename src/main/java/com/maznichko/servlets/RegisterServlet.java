package com.maznichko.servlets;

import com.maznichko.services.Command;
import com.maznichko.services.Register;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "RegisterServlet", value = "/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Command command = new Register();
        String result = command.execute(request, response);
        request.setAttribute("result", result);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/register.jsp");
        switch (result) {
            case "Password or Login is empty":
            case "Name or Surname is empty":
            case "Email is empty":
            case "User exist yet":
                dispatcher.forward(request, response);
                break;
            case "error":
                RequestDispatcher dispatcherError = request.getRequestDispatcher("/jsp/error.jsp");
                dispatcherError.forward(request, response);
                break;
            default:
                RequestDispatcher dispatchers = request.getRequestDispatcher("/CustomerServlet");
                dispatchers.forward(request, response);
                break;

        }
    }
}
