package com.maznichko.servlets;

import com.maznichko.dao.impl.UserDAOimpl;
import com.maznichko.services.Path;
import com.maznichko.services.Register;


import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "RegisterServlet", value = "/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("result", request.getParameter("result"));
        RequestDispatcher dispatcher = request.getRequestDispatcher(Path.REGISTER_JSP);
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = Path.REGISTER_SERVLET;
        Register register = new Register(new UserDAOimpl());
        boolean isRegister = register.register(request, "CUSTOMER");
        if (isRegister) {
            path = Path.CUSTOMER_SERVLET;
        }
        response.sendRedirect(path + "?result=" + request.getAttribute("result"));
    }
}

