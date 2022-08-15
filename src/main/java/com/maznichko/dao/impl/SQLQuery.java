package com.maznichko.dao.impl;

/**
 * Class for SQL requests
 */
public class SQLQuery {
    /**
     * Class for user table SQL requests
     */
    static class UserQuery {
        public static final String SELECT_ALL_FROM_USER = "SELECT * FROM public.user";
        public static final String INSERT_USER_INTO_USER = "INSERT INTO public.user VALUES (DEFAULT,?,?,?,?,?,?,?,?)";
        public static final String DELETE_FROM_USER = "DELETE FROM public.user WHERE login=?";
        public static final String SELECT_ALL_FROM_USERS_WHERE_ID = "SELECT * FROM public.user WHERE id_user=?";
        public static final String SELECT_ALL_FROM_USERS_WHERE_LOGIN = "SELECT * FROM public.user WHERE login=?";
        public static final String UPDATE_USER = "UPDATE public.user SET login=?,password=?,email=?,phone=?,role=?,bank=?,name=?,surname=? WHERE id_user=?";
    }

    /**
     * Class for request table SQL requests
     */
    static class RequestQuery {
        static final String INSERT_REQUEST = "INSERT INTO request VALUES(DEFAULT,?,DEFAULT,?,?,?)";
        static final String UPDATE_REQUEST = "UPDATE request SET description=?,complication_status=?,payment_status=?,price=?,master_login=? WHERE id_request=?";
        static final String SELECT_ALL_FROM_REQUEST_WHERE = "SELECT * FROM request WHERE id_request=?";
        static final String SELECT_ALL_FROM_REQUEST = "SELECT * FROM request";
        static final String SELECT_ALL_FROM_USER_REQUEST = "SELECT * FROM user_request WHERE login=?";
        static final String DELETE_FROM_REQUEST = "DELETE FROM request WHERE id_request=?";
        static final String INSERT_INTO_USER_REQUEST = "INSERT INTO user_request VALUES(?,?)";
    }

    /**
     * Class for feedback table SQL requests
     */
    static class RequestFeedback {
        static final String INSERT_FEEDBACK = "INSERT INTO feedback VALUES(DEFAULT,?,?,DEFAULT,?,?)";
        static final String DELETE_FROM_FEEDBACK = "DELETE FROM feedback WHERE feedback_id=?";
        static final String SELECT_ALL_FROM_FEEDBACK = "SELECT * FROM feedback";
        static final String SELECT_ALL_FROM_FEEDBACK_WHERE = "SELECT * FROM feedback WHERE id_request=?";
    }

}
