package com.maznichko.servlets;

import com.maznichko.DAO.entity.Feedback;
import com.maznichko.services.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "RequestServlet", value = "/RequestServlet")
public class RequestServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession();
        if (request.getParameter("id") != null) {
            new DeleteRequest().execute(request, response);
        } else if (request.getParameter("price") != null) {
            new Paid().execute(request, response);
        }
        new GenerateTable().execute(request, response);
        request.setAttribute("bank",httpSession.getAttribute("bank"));
        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/Customer/customerMain.jsp");
        dispatcher.forward(request, response);
    }
}
