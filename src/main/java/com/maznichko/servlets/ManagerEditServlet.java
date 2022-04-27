package com.maznichko.servlets;

import com.maznichko.DAO.entity.Request;
import com.maznichko.services.GetRequest;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ManagerEdinServlet", value = "/ManagerEditServlet")
public class ManagerEditServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        new GetRequest().execute(request,response);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/Manager/managerEdit.jsp");
        dispatcher.forward(request, response);


    }
}
