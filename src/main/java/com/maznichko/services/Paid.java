package com.maznichko.services;

import com.maznichko.DAO.DBException;
import com.maznichko.DAO.RequestDAO;
import com.maznichko.DAO.UserDAO;
import com.maznichko.DAO.entity.Request;
import com.maznichko.DAO.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Paid implements Command{
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        //PAYMENT STATUS : unpaid waiting for payment,paid,refused COMPLICATION STATUS : under consideration ,in progress , done
        HttpSession httpSession = req.getSession();
        String login = (String) httpSession.getAttribute("login");
        float price = Float.parseFloat(req.getParameter("price"));
        int id = Integer.parseInt(req.getParameter("paymentID"));
        RequestDAO requestDAO = new RequestDAO();
        UserDAO userDAO = new UserDAO();
        String paidStatus = req.getParameter("payment");
        if (paidStatus.equals("waiting for payment")){
            User user = null;
            try {
                user = userDAO.getUser(login);
            } catch (DBException e) {
                throw new RuntimeException(e);
            }
            if (user.getBank() >= price){
                user.setBank(user.getBank() - (int)price);
                try {
                    userDAO.updateUser(user);
                } catch (DBException e) {
                    throw new RuntimeException(e);
                }
            }
            Request request = null;
            try {
                request = requestDAO.getRequestByID(id);
            } catch (DBException e) {
                throw new RuntimeException(e);
            }
            request.setPaymentStatus("paid");
            try {
               requestDAO.updateRequest(request);
            } catch (DBException e) {
                throw new RuntimeException(e);
            }
            req.setAttribute("paid","payment was successful");
            return "true";
        }
        req.setAttribute("paid","payment failed");
        return "false";
    }
}
