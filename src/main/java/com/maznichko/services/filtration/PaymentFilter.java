package com.maznichko.services.filtration;

import com.maznichko.dao.entity.Request;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PaymentFilter extends Filterable {
    /**
     * filtering list of requests by payment status
     *
     * @param requests - this set of requests who we are got from DB
     * @param request  - our request
     */
    @Override
    public void action(List<Request> requests, HttpServletRequest request) {
        List<Request> payRequests = new ArrayList<>();
        String[] payStatuses = request.getParameterValues("payStatus");
        if (payStatuses != null) {
            for (String status : payStatuses) {
                payRequests.addAll(requests
                        .stream()
                        .filter(x -> x.getPaymentStatus().equals(status))
                        .collect(Collectors.toList()));
            }
        } else {
            payRequests = requests;
        }
        checkNext(payRequests, request);
    }
}
