package com.maznichko.DAO;

import com.maznichko.DAO.entity.Request;
import com.maznichko.DAO.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RequestDAO {
    public static void main(String[] args) {
        List<Request> requests;
        try {
            requests = new RequestDAO().findAllRequestByLimit(5,0);
        } catch (DBException e) {
            throw new RuntimeException(e);
        }
        requests.forEach(System.out::println);
    }

    private Request createRequest(ResultSet resultSet) throws DBException {
        Request request = new Request();
        try {
            request.setRequestID(resultSet.getInt("id_request"));
            request.setDescription(resultSet.getString("description"));
            request.setDate(resultSet.getTimestamp("date"));
            request.setComplicationStatus(resultSet.getString("complication_status"));
            request.setPaymentStatus(resultSet.getString("payment_status"));
            request.setPrice(resultSet.getFloat("price"));
        } catch (SQLException e) {
            throw new DBException("Create user was failed!", e);
        }
        return request;
    }

    public List<Request> findAllRequestByLimit(int limit,int offset) throws DBException {
        List<Request> requests = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            connection = DBManager.getInstance().connect();
            pstmt = connection.prepareStatement(SQLQuery.RequestQuery.SELECT_ALL_FROM_REQUEST_LIMIT);
            pstmt.setInt(1,limit);
            pstmt.setInt(2,offset);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Request request = createRequest(rs);
                requests.add(request);
            }
        } catch (SQLException e) {
            throw new DBException("Find all request was failed", e);
        } finally {
            DBManager.getInstance()
                    .close(rs)
                    .close(pstmt)
                    .close(connection);
        }
        return requests;
    }

    public boolean insertRequest(Request request) throws DBException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            connection = DBManager.getInstance().connect();
            connection.setAutoCommit(false);
            pstmt = connection.prepareStatement(SQLQuery.RequestQuery.INSERT_REQUEST, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, request.getDescription());
            pstmt.setString(2, request.getComplicationStatus());
            pstmt.setString(3, request.getPaymentStatus());
            pstmt.setFloat(4, request.getPrice());
            int rs = pstmt.executeUpdate();
            if (rs > 0) {
                ResultSet resultSet = pstmt.getGeneratedKeys();
                if (resultSet.next()) {
                    request.setRequestID(resultSet.getInt(1));
                    connection.commit();
                    return true;
                }
                DBManager.getInstance().rollback(connection);
                return false;
            }
            DBManager.getInstance().rollback(connection);
            return false;
        } catch (SQLException e) {
            DBManager.getInstance().rollback(connection);
            throw new DBException("Insert request didn't failed", e);
        } finally {
            DBManager.getInstance().close(pstmt)
                    .close(connection);

        }
    }

    public boolean insertRequestInUserRequest(String login, Integer requestID) throws DBException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            connection = DBManager.getInstance().connect();
            pstmt = connection.prepareStatement(SQLQuery.RequestQuery.INSERT_INTO_USER_REQUEST);
            pstmt.setString(1, login);
            pstmt.setInt(2, requestID);
            int rs = pstmt.executeUpdate();
            if (rs == 0) {
                return false;
            }
        } catch (SQLException e) {
            throw new DBException("Insert request didn't failed", e);
        } finally {
            DBManager.getInstance().close(pstmt)
                    .close(connection);

        }
        return true;

    }

    public boolean updateRequest(Request request) throws DBException {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = DBManager.getInstance().connect();
            con.setAutoCommit(false);
            pstmt = con.prepareStatement(SQLQuery.RequestQuery.UPDATE_REQUEST);
            pstmt.setString(1, request.getDescription());
            pstmt.setString(2, request.getComplicationStatus());
            pstmt.setString(3, request.getPaymentStatus());
            pstmt.setFloat(4, request.getPrice());
            pstmt.setLong(5, request.getRequestID());
            int updateResult = pstmt.executeUpdate();
            if (updateResult == 0) {
                DBManager.getInstance().rollback(con);
                return false;
            }
            con.commit();

        } catch (SQLException e) {
            DBManager.getInstance().rollback(con);
            throw new DBException("Update request was failed", e);
        } finally {
            DBManager.getInstance().close(pstmt)
                    .close(con);
        }
        return true;
    }

    public List<Request> findAllRequest() throws DBException {
        List<Request> requests = new ArrayList<>();
        Connection connection = null;
        ResultSet resultSet = null;
        try {
            connection = DBManager.getInstance().connect();
            resultSet = connection
                    .createStatement()
                    .executeQuery(SQLQuery.RequestQuery.SELECT_ALL_FROM_REQUEST);
            while (resultSet.next()) {
                Request request = createRequest(resultSet);
                requests.add(request);
            }
        } catch (SQLException e) {
            throw new DBException("Find all request was failed", e);
        } finally {
            DBManager.getInstance().close(resultSet)
                    .close(connection);
        }
        return requests;
    }

    public Request getRequestByID(int requestID) throws DBException {
        Connection connection = null;
        Request request;
        PreparedStatement prGet = null;
        try {
            connection = DBManager.getInstance().connect();
            prGet = connection.prepareStatement(SQLQuery.RequestQuery.SELECT_ALL_FROM_REQUEST_WHERE);
            prGet.setLong(1, requestID);
            ResultSet rs = prGet.executeQuery();
            rs.next();
            request = createRequest(rs);
        } catch (SQLException e) {
            throw new DBException("Get request by id was failed", e);
        } finally {
            DBManager.getInstance().close(prGet)
                    .close(connection);
        }
        return request;
    }

    public List<Request> getRequestByLogin(String login) throws DBException {
        Connection connection = null;
        PreparedStatement prGet = null;
        List<Request> requests = new ArrayList<>();
        try {
            connection = DBManager.getInstance().connect();
            prGet = connection.prepareStatement(SQLQuery.RequestQuery.SELECT_ALL_FROM_USER_REQUEST);
            prGet.setString(1, login);
            ResultSet rs = prGet.executeQuery();
            while (rs.next()) {
                Request request = getRequestByID(rs.getInt("id_request"));
                requests.add(request);

            }
        } catch (SQLException e) {
            throw new DBException("Get request by id was failed", e);
        } finally {
            DBManager.getInstance().close(prGet)
                    .close(connection);
        }
        return requests;
    }

    public boolean deleteRequest(int id) throws DBException {
        Connection connection = null;
        PreparedStatement prDelete = null;
        try {
            connection = DBManager.getInstance().connect();
            prDelete = connection.prepareStatement(SQLQuery.RequestQuery.DELETE_FROM_REQUEST);
            prDelete.setLong(1, id);
            int delResult = prDelete.executeUpdate();
            if (delResult == 0) {
                return false;
            }
        } catch (SQLException e) {
            throw new DBException("Delete user was failed", e);
        } finally {
            DBManager.getInstance().close(prDelete)
                    .close(connection);
        }
        return true;
    }


}
