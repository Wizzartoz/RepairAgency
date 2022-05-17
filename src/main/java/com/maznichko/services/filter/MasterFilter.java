package com.maznichko.services.filter;

import com.maznichko.dao.entity.Request;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MasterFilter extends Filterable {
    @Override
    public void action(List<Request> requests, HttpServletRequest request) {
        List<Request> masterRequests = new ArrayList<>();
        String[] setMasterLogins = request.getParameterValues("masterLogin");
        if (setMasterLogins != null) {
            for (String login : setMasterLogins) {
                masterRequests.addAll(requests
                        .stream()
                        .filter(x -> x.getMasterLogin() != null)
                        .filter(x -> x.getMasterLogin().equals(login))
                        .collect(Collectors.toList()));
            }
        } else {
            masterRequests = requests;
        }
        checkNext(masterRequests, request);
    }
}
