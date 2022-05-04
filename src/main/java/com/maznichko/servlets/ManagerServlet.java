package com.maznichko.servlets;

import com.maznichko.services.*;
import com.maznichko.services.commands.Command;
import com.maznichko.services.commands.CommandContainer;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ManagerServlet", value = "/ManagerServlet")
//НАЗНАЧАЕТ МАСТЕРА
//Определяет стоимость работы
//Меняет статус заявки : ждет оплаты , отклонено
//Пополняет баланс клиента *


public class ManagerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Command command = CommandContainer.get(request.getParameter("command"));
        String result = "/jsp/Manager/managerMain.jsp";
        GetBank.execute(request,response);
        GetMasters.execute(request,response);
        if (command != null) {
            result = command.execute(request, response);
        }
        HttpSession httpSession = request.getSession();
        request.setAttribute("bank",httpSession.getAttribute("bank"));
        RequestDispatcher dispatcher = request.getRequestDispatcher(result);
        dispatcher.forward(request, response);
    }
}
