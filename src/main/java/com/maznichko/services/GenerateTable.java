package com.maznichko.services;

import com.maznichko.DAO.DBException;
import com.maznichko.DAO.RequestDAO;
import com.maznichko.DAO.entity.Request;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class GenerateTable implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession httpSession = req.getSession();
        String login = (String) httpSession.getAttribute("login");
        List<Request> requests;
        try {
            requests = new RequestDAO().getRequestByLogin(login);
        } catch (DBException e) {
            req.setAttribute("result",e.getMessage());
            return "/jsp/Error.jsp";
        }
        String table = requests.stream().map(x -> {
            int id = x.getRequestID();
            float price = x.getPrice();
            String paymentStatus = x.getPaymentStatus();
            String compStatus = x.getComplicationStatus();
            return "<tr>" +
                    "<td>" + x.getRequestID() + "</td>" +
                    "<td>" + x.getDescription() + "</td>" +
                    "<td>" + x.getPrice() + "</td>" +
                    "<td>" + x.getPaymentStatus() + "</td>" +
                    "<td>" + x.getComplicationStatus() + "</td>" +
                    "<td>" + x.getDate().toString() + "</td>" +
                    String.format("<td><form method=\"post\" action=\"/RequestServlet?id=%s\"><input type=\"submit\" value=\"Delete\"/></form></td>", id) +
                    String.format("<td><form method=\"post\" action=\"/RequestServlet?price=%s&payment=%s&paymentID=%s\"><input type=\"submit\" value=\"Paid\"/></form></td>", price, paymentStatus, id) +
                    String.format("<td><form method=\"post\" action=\"/FeedbackServlet?feedbackID=%s&comp=%s\"><input type=\"submit\" value=\"Feedback\"/></form></td>", id, compStatus) +
                    "</tr>";
        }).reduce(String::concat).orElse(null);
        req.setAttribute("table",table);
        return "/jsp/Customer/customerRequests.jsp";
    }
}
