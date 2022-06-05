package com.maznichko.dao.impl;


import com.maznichko.dao.DBCPDataSource;
import com.maznichko.dao.DBException;
import com.maznichko.dao.UserDAO;
import com.maznichko.dao.entity.User;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOimpl extends UserDAO {
    private static final Logger log = Logger.getLogger(UserDAOimpl.class);

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
            log.info("create user was successfully ID: " + resultSet.getInt("id_user"));
        } catch (SQLException e) {
            log.error(e.getMessage() + " failed to create user");
            throw new DBException("SQl Exception: failed to create user", e);
        }
        return user;
    }

    @Override
    public List<User> findAll() throws DBException {
        List<User> users = new ArrayList<>();
        Connection connection = null;
        ResultSet resultSet;
        try {
            connection = DBCPDataSource.getConnection();
            resultSet = connection
                    .createStatement()
                    .executeQuery(SQLQuery.UserQuery.SELECT_ALL_FROM_USER);
            while (resultSet.next()) {
                User user = createUser(resultSet);
                users.add(user);
            }
            log.info("find all users was successfully");
        } catch (SQLException e) {
            log.error(e.getMessage() + " failed to find all users");
            throw new DBException("SQl Exception: failed to find all users", e);
        } finally {
            DBCPDataSource.close(connection);
        }
        return users;
    }

    @Override
    public User getData(long id) throws DBException {
        User user;
        Connection connection = null;
        PreparedStatement prGet = null;
        try {
            connection = DBCPDataSource.getConnection();
            prGet = connection.prepareStatement(SQLQuery.UserQuery.SELECT_ALL_FROM_USERS_WHERE_ID);
            prGet.setLong(1, id);
            ResultSet rs = prGet.executeQuery();
            rs.next();
            user = createUser(rs);
            log.info("get user by id was successfully ID: " + id);
        } catch (SQLException e) {
            log.error(e.getMessage() + " failed to get user by id");
            throw new DBException("SQl Exception: failed to get user by id", e);
        } finally {
            DBCPDataSource.close(prGet);
            DBCPDataSource.close(connection);
        }
        return user;
    }

    @Override
    public boolean update(User data) throws DBException {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = DBCPDataSource.getConnection();
            DBCPDataSource.autocommit(con, false);
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
            log.info("update user was successfully ID: " + data.getUserID());
            con.commit();

        } catch (SQLException e) {
            DBCPDataSource.rollback(con);
            log.error(e.getMessage() + " failed to update user");
            throw new DBException("SQl Exception: failed to update user", e);
        } finally {
            DBCPDataSource.autocommit(con, true);
            DBCPDataSource.close(pstmt);
            DBCPDataSource.close(con);
        }
        return true;
    }

    @Override
    public boolean delete(User data) throws DBException {
        Connection connection = null;
        PreparedStatement prDelete = null;
        try {
            connection = DBCPDataSource.getConnection();
            prDelete = connection.prepareStatement(SQLQuery.UserQuery.DELETE_FROM_USER);
            prDelete.setString(1, data.getLogin());
            int delResult = prDelete.executeUpdate();
            if (delResult == 0) {
                return false;
            }
            log.info("delete user was successfully login: " + data.getLogin());
        } catch (SQLException e) {
            log.error(e.getMessage() + " failed to delete user");
            throw new DBException("SQl Exception: failed to delete user", e);
        } finally {
            DBCPDataSource.close(prDelete);
            DBCPDataSource.close(connection);
        }
        return true;
    }

    @Override
    public boolean insert(User data) throws DBException {
        Connection connection = null;
        PreparedStatement prInsert = null;
        ResultSet rs;
        try {
            connection = DBCPDataSource.getConnection();
            DBCPDataSource.autocommit(connection, false);
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
                    log.info("insert user was successfully login: " + data.getLogin());
                    connection.commit();
                }
            } else {
                DBCPDataSource.rollback(connection);
                return false;
            }
        } catch (SQLException e) {
            DBCPDataSource.rollback(connection);
            log.error(e.getMessage() + " failed to insert user");
            throw new DBException("SQl Exception: failed to insert user", e);
        } finally {
            DBCPDataSource.autocommit(connection, true);
            DBCPDataSource.close(prInsert);
            DBCPDataSource.close(connection);
        }
        return true;
    }

    @Override
    public User getUserByLogin(String login) throws DBException {
        User user;
        Connection connection = null;
        PreparedStatement prGet = null;
        try {
            connection = DBCPDataSource.getConnection();
            prGet = connection.prepareStatement(SQLQuery.UserQuery.SELECT_ALL_FROM_USERS_WHERE_LOGIN);
            prGet.setString(1, login);
            ResultSet rs = prGet.executeQuery();
            rs.next();
            user = createUser(rs);
            log.info("get request by loin was successfully login: " + login);
        } catch (SQLException e) {
            log.error(e.getMessage() + " failed to get user by login");
            throw new DBException("SQl Exception: failed to get user by login", e);
        } finally {
            DBCPDataSource.close(prGet);
            DBCPDataSource.close(connection);
        }
        return user;
    }
}
