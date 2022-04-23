package com.maznichko.services;

import com.maznichko.DAO.DBException;;
import com.maznichko.DAO.UserDAO;
import com.maznichko.DAO.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class Register implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession httpSession = req.getSession();
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String login = req.getParameter("login");
        String password = req.getParameter("pass");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");
        if (login.isEmpty() || password.isEmpty()) {
            req.setAttribute("result","Login or Password is empty");
            return "/jsp/register.jsp";
        }
        if (name.isEmpty() || surname.isEmpty()) {
            req.setAttribute("result","Name or Surname is empty");
            return "/jsp/register.jsp";
        }
        if (email.isEmpty()) {
            req.setAttribute("result","Email is empty");
            return "/jsp/register.jsp";
        }
        User user = new User();
        user.setRole("CUSTOMER");
        user.setLogin(login);
        user.setPassword(password);
        user.setName(name);
        user.setSurname(surname);
        user.setPhone(phone);
        user.setEmail(email);
        try {
            new UserDAO().insertUser(user);
        } catch (DBException e) {
            req.setAttribute("result","user already exists");
            return "/jsp/register.jsp";
        }
        httpSession.setAttribute("login",login);
        httpSession.setAttribute("role","CUSTOMER");
        return "/GeneralCustomerServlet";
    }
}

