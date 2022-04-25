package com.maznichko.servlets;

import com.maznichko.services.DoneRequest;
import com.maznichko.services.GenerateMasterTable;
import com.maznichko.services.GenerateTable;
import com.maznichko.services.TakeRequest;

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
        if (request.getParameter("id") != null){
            new TakeRequest().execute(request,response);
        }
        else if (request.getParameter("doneID") != null){
            new DoneRequest().execute(request,response);
        }
        new GenerateMasterTable().execute(request, response);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/Master/masterRequests.jsp");
        dispatcher.forward(request, response);
    }
}
