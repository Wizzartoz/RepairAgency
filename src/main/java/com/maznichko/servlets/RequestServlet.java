package com.maznichko.servlets;

import com.maznichko.DAO.DBException;
import com.maznichko.DAO.RequestDAO;
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
        String res;
        res = new GenerateTable().execute(request, response);
        if (request.getParameter("id") != null) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/DeleteRequestServlet");
            dispatcher.forward(request, response);
        } else if (request.getParameter("price") != null) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/PaidRequestServlet");
            dispatcher.forward(request, response);
        } else {
            request.setAttribute("result", request.getParameter("result"));
            RequestDispatcher dispatcher = request.getRequestDispatcher(res);
            dispatcher.forward(request, response);
        }


    }
}
