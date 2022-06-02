package com.maznichko.services.manager;

import com.maznichko.dao.DBException;
import com.maznichko.dao.RequestDAO;
import com.maznichko.dao.entity.Request;
import com.maznichko.dao.entity.User;
import com.maznichko.services.Path;
import com.maznichko.services.common.GetUsers;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserReport {
    private final RequestDAO requestDAO;
    private static final Logger log = Logger.getLogger(UserReport.class);

    public UserReport(RequestDAO requestDAO) {
        this.requestDAO = requestDAO;

    }

    public String getReport(HttpServletRequest request) {
        List<UserEntity> userEntities = new ArrayList<>();
        //Getting users
        List<User> users = GetUsers.execute(request);
        if (users == null){
            return Path.ERROR;
        }
        List<User> customerUsers = users.stream()
                .filter(user -> user.getRole().equals("CUSTOMER") || user.getRole().equals("BLOCK"))
                .collect(Collectors.toList());
        //Set up use entity
        for (User user : customerUsers) {
            UserEntity userEntity = new UserEntity();
            String login = user.getLogin();
            userEntity.setBank(user.getBank());
            userEntity.setLogin(login);
            List<Request> requests = getRequests(login);
            if (requests == null){

                return Path.ERROR;
            }
            userEntity.setRole(user.getRole());
            userEntity.setAmountOfOrders(requests.size());
            userEntity.setAmountOfOrdersDone((int) requests
                    .stream()
                    .filter(req -> req.getComplicationStatus().equals("done"))
                    .count());
            userEntities.add(userEntity);
        }
        request.setAttribute("table", userEntities
                .stream()
                .sorted((entity1,entity2)->-entity1.getLogin().compareTo(entity2.getLogin()))
                .collect(Collectors.toList()));
        return Path.REPORT_JSP;

    }

    private List<Request> getRequests(String login) {
        List<Request> requests;
        try {
            requests = requestDAO.getRequestByLogin(login);
        } catch (DBException e) {
            log.error("<--------- get requests is failed",e);
            return null;
        }
        return requests;
    }
}
