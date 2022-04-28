package com.maznichko.servlets;

import com.maznichko.services.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "MasterServlet", value = "/MasterServlet")
public class MasterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        new GenerateMasterTable().execute(request, response);
        new GetBank().execute(request,response);
        if (request.getParameter("id") != null){
            new TakeRequest().execute(request,response);
        }
        else if (request.getParameter("doneID") != null){
            new DoneRequest().execute(request,response);
        }
        HttpSession httpSession =  request.getSession();
        request.setAttribute("bank",httpSession.getAttribute("bank"));
        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/Master/masterRequests.jsp");
        dispatcher.forward(request, response);
    }
}
