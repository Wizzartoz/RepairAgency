package com.maznichko.servlets;

import com.maznichko.DAO.DBException;
import com.maznichko.DAO.RequestDAO;
import com.maznichko.DAO.UserDAO;
import com.maznichko.DAO.entity.Request;
import com.maznichko.DAO.entity.User;
import com.maznichko.services.Command;
import com.maznichko.services.GetBank;
import com.maznichko.services.Replenishment;
import com.maznichko.services.SendRequest;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "CustomerServlet", value = "/CustomerServlet")
public class CustomerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Command commandGetBank = new GetBank();
        Command commandSendRequest = new SendRequest();
        Command commandReplenishment = new Replenishment();
        if (request.getParameter("money") != null){
            String res = commandReplenishment.execute(request,response);
            response.sendRedirect("display?res="+res);

        }
        if (commandGetBank.execute(request,response).equals("false")){
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/Error.jsp");
            dispatcher.forward(request, response);
        }
        System.out.println("добрался 5");
        if (request.getParameter("user_message") != null) {
            commandSendRequest.execute(request,response);
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/Customer/customerMain.jsp");
        dispatcher.forward(request, response);
    }
}
