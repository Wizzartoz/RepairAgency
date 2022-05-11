package com.maznichko.services;

import com.maznichko.dao.entity.Request;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Sort {
    public static List<Request> sort(String sort, List<Request> request) {
        List<Request> list = null;
        switch (sort) {
            case "date": {
                list = request.stream()
                        .sorted(Comparator.comparing(Request::getDate))
                        .collect(Collectors.toList());
                break;
            }
            case "status": {
                list = request.stream()
                        .sorted(Comparator.comparing(Request::getComplicationStatus))
                        .collect(Collectors.toList());
                break;
            }
            case "payStatus": {
                list = request.stream()
                        .sorted(Comparator.comparing(Request::getPaymentStatus))
                        .collect(Collectors.toList());
                break;
            }
            case "ascending": {
                list = request.stream().
                        sorted((x, y) -> -x.getPrice().compareTo(y.getPrice())).
                        collect(Collectors.toList());

                break;
            }
            case "descending": {
                list = request.stream().
                        sorted(Comparator.comparing(Request::getPrice)).
                        collect(Collectors.toList());
                break;
            }

        }
        return list;
    }
}
