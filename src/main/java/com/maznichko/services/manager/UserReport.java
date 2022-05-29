package com.maznichko.services.manager;

import com.maznichko.dao.DBException;
import com.maznichko.dao.RequestDAO;
import com.maznichko.dao.entity.Request;
import com.maznichko.dao.entity.User;
import com.maznichko.services.Path;
import com.maznichko.services.common.GetUsers;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserReport {
    RequestDAO requestDAO;

    public UserReport(RequestDAO requestDAO) {
        this.requestDAO = requestDAO;

    }

    public String getReport(HttpServletRequest request) {
        List<UserEntity> userEntities = new ArrayList<>();
        List<User> users = GetUsers.execute(request)
                .stream()
                .filter(user -> user.getRole().equals("CUSTOMER"))
                .collect(Collectors.toList());
        for (User user : users) {
            UserEntity userEntity = new UserEntity();
            String login = user.getLogin();
            userEntity.setBank(user.getBank());
            userEntity.setLogin(login);
            List<Request> requests = getRequests(login);
            userEntity.setAmountOfOrders(requests.size());
            userEntity.setAmountOfOrdersDone((int) requests
                    .stream()
                    .filter(req -> req.getComplicationStatus().equals("done"))
                    .count());
            userEntities.add(userEntity);
        }
        request.setAttribute("table", userEntities);
        return Path.REPORT_SERVLET;

    }

    private List<Request> getRequests(String login) {
        List<Request> requests;
        try {
            requests = requestDAO.getRequestByLogin(login);
        } catch (DBException e) {
            throw new RuntimeException(e);
        }
        return requests;
    }
}
