package com.maznichko.dao.impl;


import com.maznichko.dao.DBException;
import com.maznichko.dao.UserDAO;
import com.maznichko.dao.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOimpl extends UserDAO {

    private User createUser(ResultSet resultSet) throws DBException {
        User user = new User();
        try {
            user.setUserID(resultSet.getInt("id_user"));
            user.setLogin(resultSet.getString("login"));
            user.setPassword(resultSet.getString("password"));
            user.setEmail(resultSet.getString("email"));
            user.setPhone(resultSet.getString("phone"));
            user.setRole(resultSet.getString("role"));
            user.setBank(resultSet.getInt("bank"));
            user.setName(resultSet.getString("name"));
            user.setSurname(resultSet.getString("surname"));
        } catch (SQLException e) {
            throw new DBException("SQl Exception", e);
        }
        return user;
    }

    @Override
    public List<User> findAll() throws DBException {
        List<User> users = new ArrayList<>();
        Connection connection = null;
        ResultSet resultSet;
        try {
            connection = dao.connect();
            resultSet = connection
                    .createStatement()
                    .executeQuery(SQLQuery.UserQuery.SELECT_ALL_FROM_USER);
            while (resultSet.next()) {
                User user = createUser(resultSet);
                users.add(user);
            }
        } catch (SQLException e) {
            throw new DBException("SQl Exception", e);
        } finally {
            dao.close(connection);
        }
        return users;
    }

    @Override
    public User getData(long id) throws DBException {
        Connection connection = null;
        User user;
        PreparedStatement prGet = null;
        try {
            connection = dao.connect();
            prGet = connection.prepareStatement(SQLQuery.UserQuery.SELECT_ALL_FROM_USERS_WHERE_ID);
            prGet.setLong(1, id);
            ResultSet rs = prGet.executeQuery();
            rs.next();
            user = createUser(rs);
        } catch (SQLException e) {
            throw new DBException("SQl Exception", e);
        } finally {
            dao.close(prGet);
            dao.close(connection);
        }
        return user;
    }

    @Override
    public boolean update(User data) throws DBException {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = dao.connect();
            con.setAutoCommit(false);
            pstmt = con.prepareStatement(SQLQuery.UserQuery.UPDATE_USER);
            pstmt.setString(1, data.getLogin());
            pstmt.setString(2, data.getPassword());
            pstmt.setString(3, data.getEmail());
            pstmt.setString(4, data.getPhone());
            pstmt.setObject(5, data.getRole());
            pstmt.setInt(6, data.getBank());
            pstmt.setString(7, data.getName());
            pstmt.setString(8, data.getSurname());
            pstmt.setLong(9, data.getUserID());
            int updateResult = pstmt.executeUpdate();
            if (updateResult == 0) {
                return false;
            }
            con.commit();

        } catch (SQLException e) {
            dao.rollback(con);
            throw new DBException("SQl Exception", e);
        } finally {
            dao.close(pstmt);
            dao.close(con);
        }
        return true;
    }

    @Override
    public boolean delete(User data) throws DBException {
        Connection connection = null;
        PreparedStatement prDelete = null;
        try {
            connection = dao.connect();
            prDelete = connection.prepareStatement(SQLQuery.UserQuery.DELETE_FROM_USER);
            prDelete.setString(1, data.getLogin());
            int delResult = prDelete.executeUpdate();
            if (delResult == 0) {
                return false;
            }
        } catch (SQLException e) {
            throw new DBException("SQl Exception", e);
        } finally {
            dao.close(prDelete);
            dao.close(connection);
        }
        return true;
    }

    @Override
    public boolean insert(User data) throws DBException {
        Connection connection = null;
        PreparedStatement prInsert = null;
        ResultSet rs;
        try {
            connection = dao.connect();
            connection.setAutoCommit(false);
            prInsert = connection.prepareStatement(SQLQuery.UserQuery.INSERT_USER_INTO_USER, Statement.RETURN_GENERATED_KEYS);
            prInsert.setString(1, data.getLogin());
            prInsert.setString(2, data.getPassword());
            prInsert.setString(3, data.getEmail());
            prInsert.setString(4, data.getPhone());
            prInsert.setObject(5, data.getRole());
            prInsert.setInt(6, data.getBank());
            prInsert.setString(7, data.getName());
            prInsert.setString(8, data.getSurname());
            int resultInsert = prInsert.executeUpdate();
            if (resultInsert > 0) {
                rs = prInsert.getGeneratedKeys();
                if (rs.next()) {
                    data.setUserID(rs.getInt(1));
                    connection.commit();
                }
            } else {
                dao.rollback(connection);
                return false;
            }
        } catch (SQLException e) {
            dao.rollback(connection);
            throw new DBException("SQl Exception", e);
        } finally {
            dao.close(prInsert);
            dao.close(connection);
        }
        return true;
    }

    @Override
    public User getUserByLogin(String login) throws DBException {
        Connection connection = null;
        User user;
        PreparedStatement prGet = null;
        try {
            connection = dao.connect();
            prGet = connection.prepareStatement(SQLQuery.UserQuery.SELECT_ALL_FROM_USERS_WHERE_LOGIN);
            prGet.setString(1, login);
            ResultSet rs = prGet.executeQuery();
            rs.next();
            user = createUser(rs);
        } catch (SQLException e) {
            throw new DBException("SQl Exception", e);
        } finally {
            dao.close(prGet);
            dao.close(connection);
        }
        return user;
    }
}
