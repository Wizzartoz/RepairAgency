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
        UserDAO userDAO = new UserDAO();

        if (login.isEmpty() || password.isEmpty()) {
            return "Password or Login is empty";
        }
        if (name.isEmpty() || surname.isEmpty()) {
            return "Name or Surname is empty";
        }
        if (email.isEmpty()) {
            return "Email is empty";
        }
        User user = new User();
        user.setRole("CUSTOMER");
        user.setLogin(login);
        user.setPassword(password);
        user.setName(name);
        user.setSurname(surname);
        user.setPhone(phone);
        user.setEmail(email);
        User userCheck;
        try {
            userCheck = userDAO.getUser(login);
        } catch (DBException e) {
            throw new RuntimeException(e);
        }
        if (userCheck != null)
            return "User exist yet";
        try {
            userDAO.insertUser(user);
        } catch (DBException e) {
            req.setAttribute("error",e.getMessage());
            return "error";
        }
        httpSession.setAttribute("login",login);
        httpSession.setAttribute("role","CUSTOMER");
        return "Registration was completed";
    }
}

