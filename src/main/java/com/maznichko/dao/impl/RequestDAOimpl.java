package com.maznichko.dao.impl;

import com.maznichko.dao.*;
import com.maznichko.dao.entity.Request;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RequestDAOimpl extends RequestDAO {
    private static final Logger log = Logger.getLogger(RequestDAOimpl.class);

    private Request createRequest(ResultSet resultSet) throws DBException {
        Request request = new Request();
        try {
            request.setRequestID(resultSet.getInt("id_request"));
            request.setDescription(resultSet.getString("description"));
            request.setDate(resultSet.getTimestamp("date").toString().substring(0,19));
            request.setComplicationStatus(resultSet.getString("complication_status"));
            request.setPaymentStatus(resultSet.getString("payment_status"));
            request.setPrice(resultSet.getFloat("price"));
            request.setMasterLogin(resultSet.getString("master_login"));
            log.info("create request was successfully ID: " + resultSet.getInt("id_request"));
        } catch (SQLException e) {
            log.error(e.getMessage() + " failed to create request");
            throw new DBException("SQL Exception: failed to create request", e);
        }
        return request;
    }

    @Override
    public List<Request> findAll() throws DBException {
        List<Request> requests = new ArrayList<>();
        Connection connection = null;
        ResultSet resultSet;
        try {
            connection = dao.connect();
            resultSet = connection
                    .createStatement()
                    .executeQuery(SQLQuery.RequestQuery.SELECT_ALL_FROM_REQUEST);
            while (resultSet.next()) {
                Request request = createRequest(resultSet);
                requests.add(request);
            }
            log.info("find all requests was successfully");
        } catch (SQLException e) {
            log.error(e.getMessage() + " failed to find all requests");
            throw new DBException("SQL Exception: failed to find all requests", e);
        } finally {
            dao.close(connection);
        }
        return requests;
    }


    @Override
    public Request getData(long id) throws DBException {
        Request request;
        Connection connection = null;
        PreparedStatement prGet = null;
        try {
            connection = dao.connect();
            prGet = connection.prepareStatement(SQLQuery.RequestQuery.SELECT_ALL_FROM_REQUEST_WHERE);
            prGet.setLong(1, id);
            ResultSet rs = prGet.executeQuery();
            rs.next();
            request = createRequest(rs);
            log.info("get request by id was successfully ID: " + id);
        } catch (SQLException e) {
            log.error(e.getMessage() + " failed to get request by id");
            throw new DBException("SQL Exception: failed to get request by id", e);
        } finally {
            dao.close(prGet);
            dao.close(connection);
        }
        return request;
    }

    @Override
    public boolean update(Request data) throws DBException {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = dao.connect();
            dao.autocommit(con, false);
            pstmt = con.prepareStatement(SQLQuery.RequestQuery.UPDATE_REQUEST);
            pstmt.setString(1, data.getDescription());
            pstmt.setString(2, data.getComplicationStatus());
            pstmt.setString(3, data.getPaymentStatus());
            pstmt.setFloat(4, data.getPrice());
            pstmt.setString(5, data.getMasterLogin());
            pstmt.setLong(6, data.getRequestID());
            int updateResult = pstmt.executeUpdate();
            if (updateResult == 0) {
                dao.rollback(con);
                return false;
            }
            con.commit();
            log.info("update request was successfully");

        } catch (SQLException e) {
            dao.rollback(con);
            throw new DBException("SQL Exception", e);
        } finally {
            dao.autocommit(con, true);
            dao.close(pstmt);
            dao.close(con);
        }
        return true;
    }

    @Override
    public boolean delete(Request data) throws DBException {
        Connection connection = null;
        PreparedStatement prDelete = null;
        try {
            connection = dao.connect();
            prDelete = connection.prepareStatement(SQLQuery.RequestQuery.DELETE_FROM_REQUEST);
            prDelete.setLong(1, data.getRequestID());
            int delResult = prDelete.executeUpdate();
            if (delResult == 0) {
                return false;
            }
            log.info("delete request was successfully ID:" + data.getRequestID());
        } catch (SQLException e) {
            log.error(e.getMessage() + " failed to delete request");
            throw new DBException("SQL Exception: failed to delete request", e);
        } finally {
            dao.close(prDelete);
            dao.close(connection);
        }
        return true;
    }

    @Override
    public boolean insert(Request data) throws DBException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            connection = dao.connect();
            dao.autocommit(connection, false);
            pstmt = connection.prepareStatement(SQLQuery.RequestQuery.INSERT_REQUEST, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, data.getDescription());
            pstmt.setString(2, data.getComplicationStatus());
            pstmt.setString(3, data.getPaymentStatus());
            pstmt.setFloat(4, data.getPrice());
            int rs = pstmt.executeUpdate();
            if (rs > 0) {
                ResultSet resultSet = pstmt.getGeneratedKeys();
                if (resultSet.next()) {
                    data.setRequestID(resultSet.getInt(1));
                    connection.commit();
                    log.info("insert feedback was successfully");
                    return true;
                }
                dao.rollback(connection);
                return false;
            }
            dao.rollback(connection);
            return false;
        } catch (SQLException e) {
            dao.rollback(connection);
            log.error(e.getMessage() + " failed to insert request");
            throw new DBException("SQL Exception: failed to insert request", e);
        } finally {
            dao.autocommit(connection, true);
            dao.close(pstmt);
            dao.close(connection);
        }
    }

    @Override
    public List<Request> getRequestByLogin(String login) throws DBException {
        Connection connection = null;
        PreparedStatement prGet = null;
        List<Request> requests = new ArrayList<>();
        try {
            connection = dao.connect();
            prGet = connection.prepareStatement(SQLQuery.RequestQuery.SELECT_ALL_FROM_USER_REQUEST);
            prGet.setString(1, login);
            ResultSet rs = prGet.executeQuery();
            while (rs.next()) {
                Request request = getData(rs.getInt("id_request"));
                requests.add(request);
            }
            log.info("get request by login was successfully login: " + login);
        } catch (SQLException e) {
            log.error(e.getMessage() + " failed get request by login");
            throw new DBException("SQL Exception: failed request by login", e);
        } finally {
            dao.close(prGet);
            dao.close(connection);
        }
        return requests;
    }

    @Override
    public boolean insertRequestInUserRequest(String login, long requestID) throws DBException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            connection = dao.connect();
            pstmt = connection.prepareStatement(SQLQuery.RequestQuery.INSERT_INTO_USER_REQUEST);
            pstmt.setString(1, login);
            pstmt.setLong(2, requestID);
            int rs = pstmt.executeUpdate();
            if (rs == 0) {
                return false;
            }
            log.info("insert request in user_request table was successfully (login,id): " + login + " " + requestID);
        } catch (SQLException e) {
            log.error(e.getMessage() + " failed insert request in user_request table");
            throw new DBException("SQL Exception: failed insert request in user_request table", e);
        } finally {
            dao.close(pstmt);
            dao.close(connection);
        }
        return true;
    }
}
