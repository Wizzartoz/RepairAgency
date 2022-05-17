package com.maznichko.services.filter;

import com.maznichko.dao.entity.Request;

import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Sort extends Filterable {

    @Override
    public void action(List<Request> requests, HttpServletRequest request) {
        String sort = request.getParameter("sort");
        List<Request> table = null;
       if (sort != null) {
           switch (sort) {
               case "date": {
                   table = requests.stream()
                           .sorted(Comparator.comparing(Request::getDate))
                           .collect(Collectors.toList());
                   break;
               }
               case "status": {
                   table = requests.stream()
                           .sorted(Comparator.comparing(Request::getComplicationStatus))
                           .collect(Collectors.toList());
                   break;
               }
               case "payStatus": {
                   table = requests.stream()
                           .sorted(Comparator.comparing(Request::getPaymentStatus))
                           .collect(Collectors.toList());
                   break;
               }
               case "ascending": {
                   table = requests.stream()
                           .sorted((x, y) -> -x.getPrice().compareTo(y.getPrice())).
                           collect(Collectors.toList());

                   break;
               }
               case "descending": {
                   table = requests.stream()
                           .sorted(Comparator.comparing(Request::getPrice)).
                           collect(Collectors.toList());
                   break;
               }
           }
           checkNext(table, request);
       }else {

               checkNext(requests, request);

       }


    }
}
