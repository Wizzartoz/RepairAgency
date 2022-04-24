package com.maznichko.DAO;


public class SQLQuery {
    static class UserQuery {
        static final String SELECT_ALL_FROM_USER = "SELECT * FROM public.user";
        static final String INSERT_USER_INTO_USER = "INSERT INTO public.user VALUES (DEFAULT,?,?,?,?,?,?,?,?)";
        static final String DELETE_FROM_USER = "DELETE FROM public.user WHERE login=?";
        static final String SELECT_ALL_FROM_USERS_WHERE = "SELECT * FROM public.user WHERE Login=?";
        static final String UPDATE_USER = "UPDATE public.user SET login=?,password=?,email=?,phone=?,role=?,bank=?,name=?,surname=? WHERE id_user=?";
    }
    static class RequestQuery{
        static final String INSERT_REQUEST = "INSERT INTO request VALUES(DEFAULT,?,DEFAULT,?,?,?)";
        static final String UPDATE_REQUEST = "UPDATE request SET description=?,complication_status=?,payment_status=?,price=? WHERE id_request=?";
        static final String SELECT_ALL_FROM_REQUEST_WHERE = "SELECT * FROM request WHERE id_request=?";
        static final String SELECT_ALL_FROM_REQUEST = "SELECT * FROM request";
        static final String SELECT_ALL_FROM_USER_REQUEST = "SELECT * FROM user_request WHERE login=?";
        static final String DELETE_FROM_REQUEST = "DELETE FROM request WHERE id_request=?";
        static final String INSERT_INTO_USER_REQUEST = "INSERT INTO user_request VALUES(?,?)";

    }
    static class RequestFeedback{
        static final String INSERT_FEEDBACK = "INSERT INTO feedback VALUES(DEFAULT,?,?,DEFAULT,?,?)";
        static final String DELETE_FROM_FEEDBACK = "DELETE FROM feedback WHERE feedback_id=?";
        static final String SELECT_ALL_FROM_FEEDBACK_WHERE = "SELECT * FROM feedback WHERE id_request=?";
        static final String SELECT_ALL_FROM_FEEDBACK = "SELECT * FROM feedback";
    }

}
