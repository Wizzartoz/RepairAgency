package com.maznichko.services.filter;

import com.maznichko.dao.entity.Request;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StatusFilter extends Filterable {
    /**
     * filtering list of requests by complication status
     * @param requests - this set of requests who we are got from DB
     * @param request  - our request
     */
    @Override
    public void action(List<Request> requests, HttpServletRequest request) {
        String[] compStatuses = request.getParameterValues("compStatus");
        List<Request> compRequests = new ArrayList<>();
        if (compStatuses != null) {
            for (String status : compStatuses) {
                compRequests.addAll(requests
                        .stream()
                        .filter(x -> x.getComplicationStatus().equals(status))
                        .collect(Collectors.toList()));
            }
        } else {
            compRequests = requests;
        }
        checkNext(compRequests, request);
    }

}
