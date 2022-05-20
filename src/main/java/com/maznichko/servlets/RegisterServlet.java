package com.maznichko.servlets;

import com.maznichko.dao.impl.UserDAOimpl;
import com.maznichko.services.Path;
import com.maznichko.services.Register;
import com.maznichko.services.manager.Command;
import com.maznichko.services.manager.CommandContainer;

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
        String result = Path.ERROR;
        Register register = new Register(new UserDAOimpl());
        result = register.execute(request,"CUSTOMER");
        response.sendRedirect(result + "?result=" + request.getAttribute("result"));
    }
}
