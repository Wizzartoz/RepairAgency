package com.maznichko.servlets;

import com.maznichko.services.Command;
import com.maznichko.services.Login;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "LoginServlet", value = "/LoginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Command command = new Login();
        String result = command.execute(request, response);
        request.setAttribute("result", result);
        switch (result) {
            case "Password or Login is empty":
            case "User didn't exist":
            case "Wrong password":
                RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/login.jsp");
                dispatcher.forward(request, response);
                break;
            case "error":
                RequestDispatcher dispatcherError = request.getRequestDispatcher("/jsp/error.jsp");
                dispatcherError.forward(request, response);
                request.removeAttribute("error");
            default:
                RequestDispatcher dispatcherRole = request.getRequestDispatcher(result);
                dispatcherRole.forward(request, response);
        }

    }
}
