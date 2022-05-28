package com.maznichko.services.customer.paid;

import com.maznichko.dao.DBException;
import com.maznichko.dao.entity.User;
import com.maznichko.dao.impl.RequestDAOimpl;
import com.maznichko.dao.impl.UserDAOimpl;
import com.maznichko.services.customer.Paid;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;

public class NotEnoughMoneyTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final RequestDAOimpl requestDAOimpl = mock(RequestDAOimpl.class);
    private final User user = new User();
    private final UserDAOimpl userDAOimpl = mock(UserDAOimpl.class);
    private final HttpSession httpSession = request.getSession();

    public static Stream<Arguments> testCases() {
        return Stream.of(
                Arguments.of(100, "consideration"),
                Arguments.of(50, "refuse")
        );
    }

    @ParameterizedTest
    @MethodSource("testCases")
    void cannotDeleteRequest(int bank, String paymentSt) {
        user.setBank(bank);
        when(request.getParameter("price")).thenReturn("1000");
        when(request.getParameter("payment")).thenReturn(paymentSt);
        when(httpSession.getAttribute("login")).thenReturn("login");
        try {
            when(userDAOimpl.getUserByLogin("login")).thenReturn(user);
        } catch (DBException e) {
            throw new RuntimeException(e);
        }
        Paid paid = new Paid(userDAOimpl, requestDAOimpl);
        paid.execute(request);
        verify(request).setAttribute("result", "not enough money");
    }

}
