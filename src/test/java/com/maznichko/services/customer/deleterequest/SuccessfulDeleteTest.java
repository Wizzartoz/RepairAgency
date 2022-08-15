package com.maznichko.services.customer.deleterequest;

import com.maznichko.dao.DBException;
import com.maznichko.dao.entity.Request;
import com.maznichko.dao.impl.RequestDAOimpl;
import com.maznichko.services.customer.DeleteRequest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import javax.servlet.http.HttpServletRequest;

import java.util.stream.Stream;

import static org.mockito.Mockito.*;

public class SuccessfulDeleteTest {
    HttpServletRequest request = mock(HttpServletRequest.class);
    RequestDAOimpl requestDAOimpl = mock(RequestDAOimpl.class);
    Request requestEntity = new Request();


    public static Stream<Arguments> testCases() {
        return Stream.of(
                Arguments.of("unpaid", "under consideration"),
                Arguments.of("waiting for payment", "under consideration")
        );
    }

    @ParameterizedTest
    @MethodSource("testCases")
    void successfulDelete(String paymentSt, String complicationSt) {
        requestEntity.setComplicationStatus(complicationSt);
        requestEntity.setPaymentStatus(paymentSt);
        when(request.getParameter("id")).thenReturn("2");
        try {
            when(requestDAOimpl.getData(Long.parseLong(request.getParameter("id")))).thenReturn(requestEntity);
        } catch (DBException e) {
            throw new RuntimeException(e);
        }
        try {
            when(requestDAOimpl.delete(requestEntity)).thenReturn(true);
        } catch (DBException e) {
            throw new RuntimeException(e);
        }
        DeleteRequest deleteRequest = new DeleteRequest(requestDAOimpl);
        deleteRequest.execute(request);
        verify(request).setAttribute("result", "Deletion successful");
    }
}
