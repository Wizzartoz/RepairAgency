package com.maznichko.servlets;

import com.maznichko.services.Command;
import com.maznichko.services.LeaveFeedback;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "FeedbackServlet", value = "/FeedbackServlet")
public class FeedbackServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println(1);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/Customer/feedback.jsp");
        dispatcher.forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println(2);
        Command command = new LeaveFeedback();
        if (request.getParameter("rating") != null) {
            command.execute(request, response);
        }
        response.sendRedirect("/FeedbackServlet");
    }
}
