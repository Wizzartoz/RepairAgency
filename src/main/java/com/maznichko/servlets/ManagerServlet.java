package com.maznichko.servlets;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ManagerServlet", value = "/ManagerServlet")
//НАЗНАЧАЕТ МАСТЕРА
//Определяет стоимость работы
//Меняет статус заявки
//Меняет статус заявки : ждет оплаты , отклонено
//Пополняет баланс клиента
//Менеджер может посмотреть отчет с списком заявок где он может провести сортировку : по дате , по статусу, по стоимости
//Также менеджер имеет возможность фильтровать заявления по статусу , по мастеру


public class ManagerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/Manager/managerMain.jsp");
        dispatcher.forward(request, response);
    }
}
