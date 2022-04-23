package com.maznichko.servlets;
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
        if (request.getParameter("res").equals("true")){

            request.setAttribute("result","payment was successful");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/CustomerSecondServlet");
            dispatcher.forward(request, response);
        }else if (request.getParameter("res").equals("null")){
            RequestDispatcher dispatcher = request.getRequestDispatcher("/CustomerSecondServlet");
            dispatcher.forward(request, response);
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/Error.jsp");
        dispatcher.forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Command commandGetBank = new GetBank();
        Command commandReplenishment = new Replenishment();
        String res = null;
        if (request.getParameter("money") != null){
            res = commandReplenishment.execute(request,response);
        }
        if (commandGetBank.execute(request,response).equals("false")){
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/Error.jsp");
            dispatcher.forward(request, response);
        }
        response.sendRedirect(String.format("CustomerServlet?res=%s",res));
    }
}
