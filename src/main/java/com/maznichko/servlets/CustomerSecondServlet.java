package com.maznichko.servlets;

import com.maznichko.services.Command;
import com.maznichko.services.GetBank;
import com.maznichko.services.SendRequest;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "CustomerSecondServlet", value = "/CustomerSecondServlet")
public class CustomerSecondServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession();
        request.setAttribute("bank",httpSession.getAttribute("bank"));
        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/Customer/customerMain.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Command commandGetBank = new GetBank();
        Command commandSendRequest = new SendRequest();
        String res = null;
        if (commandGetBank.execute(request,response).equals("false")){
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/Error.jsp");
            dispatcher.forward(request, response);
        }
        if (request.getParameter("user_message") != null) {
             res = commandSendRequest.execute(request,response);
        }
        response.sendRedirect(String.format("CustomerSecondServlet?res=%s&bank=%s",res,request.getAttribute("bank")));
    }
}
