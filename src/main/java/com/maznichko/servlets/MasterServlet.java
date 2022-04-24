package com.maznichko.servlets;

import com.maznichko.services.GenerateMasterTable;
import com.maznichko.services.GenerateTable;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "MasterServlet", value = "/MasterServlet")
public class MasterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        new GenerateMasterTable().execute(request, response);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/Master/masterMain.jsp");
        dispatcher.forward(request, response);
    }
}
