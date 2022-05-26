package com.maznichko.services.filtration;

import com.maznichko.dao.entity.Request;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public abstract class Filterable {
    //Chain responsibility
    private Filterable next;

    /**
     * This method receive list of requests and by parameters of request process the list
     *
     * @param requests - this set of requests who we are got from DB
     * @param request  - our request
     */
    public abstract void action(List<Request> requests, HttpServletRequest request);

    /**
     * This method allow to add new chains
     *
     * @param next - object which we want to join
     * @return - object next chain
     */
    public Filterable linkWith(Filterable next) {
        this.next = next;
        return next;
    }

    /**
     * @param requests - processed list of request
     * @param request  - our request
     */
    public void checkNext(List<Request> requests, HttpServletRequest request) {
        if (next != null) {
            next.action(requests, request);
        } else {
            request.setAttribute("table", requests);
        }
    }
}
