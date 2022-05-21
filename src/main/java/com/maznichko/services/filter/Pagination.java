package com.maznichko.services.filter;

import com.maznichko.dao.entity.Request;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

public class Pagination extends Filterable {
    @Override
    public void action(List<Request> requests, HttpServletRequest request) {
        double size = requests.size();
        int countElementOfPage = 8;
        int pages = (int) Math.ceil(size / countElementOfPage);
        if (pages == 0){
            pages = 1;
        }
        int offset;
        if (request.getParameter("offset") == null) {
            offset = 0;
        } else {
            offset = Integer.parseInt(request.getParameter("offset"));
        }
        List<Request> table = requests.stream()
                .skip(offset)
                .limit(countElementOfPage)
                .collect(Collectors.toList());
        request.setAttribute("pages", pages);
        checkNext(table, request);
    }
}

