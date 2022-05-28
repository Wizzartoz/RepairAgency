package com.maznichko.servlets;

import com.maznichko.dao.impl.UserDAOimpl;
import com.maznichko.services.Login;
import com.maznichko.services.Path;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "LoginServlet", value = "/LoginServlet")
public class LoginServlet extends HttpServlet {
    private Login login;
    private static final Logger log = Logger.getLogger(LoginServlet.class);

    @Override
    public void init() throws ServletException {
        login = new Login(new UserDAOimpl());
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("result", request.getParameter("result"));
        RequestDispatcher dispatcher = request.getRequestDispatcher(Path.LOGIN_JSP);
        dispatcher.forward(request, response);


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = login.execute(request);
        log.info("method login is completed path: " + path);
        response.sendRedirect(path + "?result=" + request.getAttribute("result"));
    }
}

