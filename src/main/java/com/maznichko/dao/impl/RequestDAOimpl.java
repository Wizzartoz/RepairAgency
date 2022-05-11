package com.maznichko.dao.impl;

import com.maznichko.dao.*;
import com.maznichko.dao.entity.Request;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RequestDAOimpl extends RequestDAO {

    private Request createRequest(ResultSet resultSet) throws DBException {
        Request request = new Request();
        try {
            request.setRequestID(resultSet.getInt("id_request"));
            request.setDescription(resultSet.getString("description"));
            request.setDate(resultSet.getTimestamp("date"));
            request.setComplicationStatus(resultSet.getString("complication_status"));
            request.setPaymentStatus(resultSet.getString("payment_status"));
            request.setPrice(resultSet.getFloat("price"));
            request.setMasterLogin(resultSet.getString("master_login"));
        } catch (SQLException e) {
            throw new DBException("SQL Exception", e);
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
        } catch (SQLException e) {
            throw new DBException("SQL Exception", e);
        } finally {
            dao.close(connection);

        }
        return requests;
    }


    @Override
    public Request getData(long id) throws DBException {
        Connection connection = null;
        Request request;
        PreparedStatement prGet = null;
        try {
            connection = dao.connect();
            prGet = connection.prepareStatement(SQLQuery.RequestQuery.SELECT_ALL_FROM_REQUEST_WHERE);
            prGet.setLong(1, id);
            ResultSet rs = prGet.executeQuery();
            rs.next();
            request = createRequest(rs);
        } catch (SQLException e) {
            throw new DBException("SQL Exception", e);
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
            con.setAutoCommit(false);
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

        } catch (SQLException e) {
            dao.rollback(con);
            throw new DBException("SQL Exception", e);
        } finally {
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
        } catch (SQLException e) {
            throw new DBException("SQL Exception", e);
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
            connection.setAutoCommit(false);
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
                    return true;
                }
                dao.rollback(connection);
                return false;
            }
            dao.rollback(connection);
            return false;
        } catch (SQLException e) {
            dao.rollback(connection);
            throw new DBException("SQL Exception", e);
        } finally {
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
        } catch (SQLException e) {
            throw new DBException("SQL Exception", e);
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
        } catch (SQLException e) {
            throw new DBException("SQL Exception", e);
        } finally {
            dao.close(pstmt);
            dao.close(connection);

        }
        return true;
    }
}
