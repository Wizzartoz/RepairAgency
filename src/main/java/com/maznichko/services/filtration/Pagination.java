package com.maznichko.services.filtration;

import com.maznichko.dao.entity.Request;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

public class Pagination extends Filterable {
    /**
     * calculating count of pages by list of request
     *
     * @param requests - this set of requests who we are got from DB
     * @param request  - our request
     */
    @Override
    public void action(List<Request> requests, HttpServletRequest request) {
        double size = requests.size();
        int countElementOfPage = 8;
        int pages = (int) Math.ceil(size / countElementOfPage);
        if (pages == 0) {
            pages = 1;
        }
        HttpSession httpSession = request.getSession();
        int offset = 0;
        if (request.getParameter("offset") != null) {
            offset = Integer.parseInt(request.getParameter("offset"));
            httpSession.setAttribute("offset", offset);
        } else if (httpSession.getAttribute("offset") != null) {
            offset = (int) httpSession.getAttribute("offset");
        }
        //TODO КОСТЫЛЬ
        if (request.getParameter("payStatus") != null
                || request.getParameter("compStatus") != null
                || request.getParameter("masterLogin") != null){
            httpSession.removeAttribute("offset");
        }
        List<Request> table = requests.stream()
                .skip(offset)
                .limit(countElementOfPage)
                .collect(Collectors.toList());
        request.setAttribute("pages", pages);
        checkNext(table, request);
    }
}

