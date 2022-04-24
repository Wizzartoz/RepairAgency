package com.maznichko.servlets;

import com.maznichko.services.LeaveFeedback;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "FeedbackServlet", value = "/FeedbackServlet")
public class FeedbackServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("comp") != null){
            HttpSession httpSession = request.getSession();
            httpSession.setAttribute("feedbackID",request.getParameter("feedbackID"));
            httpSession.setAttribute("comp",request.getParameter("comp"));
        }else if (request.getParameter("rating") != null) {
            new LeaveFeedback().execute(request,response);
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/Customer/feedback.jsp");
        dispatcher.forward(request, response);
    }
}
