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
        Command command = new GenerateTable();
        String result = command.execute(request, response);
        request.setAttribute("table", result);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/Customer/customerRequests.jsp");
        dispatcher.forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession();
        Command command = new GenerateTable();
        Command commandDelete = new DeleteRequest();
        Command commandPaid = new Paid();
        Command commandFeedback = new LeaveFeedback();
        if (request.getParameter("id") != null) {
            commandDelete.execute(request, response);
        }
        if (request.getParameter("price") != null) {
            commandPaid.execute(request, response);
        }
        if (request.getParameter("comp") != null) {
            if (request.getParameter("comp").equals("done")) {
                httpSession.setAttribute("feedbackID", request.getParameter("feedbackID"));
                RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/Customer/feedback.jsp");
                dispatcher.forward(request, response);
            }
        }

        if (request.getParameter("rating") != null) {
            commandFeedback.execute(request, response);
        }

        String result = command.execute(request, response);
        request.setAttribute("table", result);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/Customer/customerRequests.jsp");
        dispatcher.forward(request, response);

    }
}
