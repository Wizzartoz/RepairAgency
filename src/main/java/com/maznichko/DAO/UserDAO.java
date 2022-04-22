package com.maznichko.DAO;


import com.maznichko.DAO.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserDAO {

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
            throw new DBException("Create user is failed!", e);
        }
        return user;
    }

    public List<User> findAllUsers() throws DBException {
        List<User> users = new ArrayList<>();
        Connection connection = null;
        ResultSet resultSet = null;
        try {
            connection = DBManager.getInstance().connect();
            resultSet = connection
                    .createStatement()
                    .executeQuery(SQLQuery.UserQuery.SELECT_ALL_FROM_USER);
            while (resultSet.next()) {
                User user = createUser(resultSet);
                users.add(user);
            }
        } catch (SQLException e) {
            throw new DBException("Find all users was failed", e);
        } finally {
            DBManager.getInstance().close(resultSet)
                    .close(connection);
        }
        return users;
    }

    public boolean insertUser(User user) throws DBException {
        Connection connection = null;
        PreparedStatement prInsert = null;
        ResultSet rs = null;
        try {
            connection = DBManager.getInstance().connect();
            connection.setAutoCommit(false);
            prInsert = connection.prepareStatement(SQLQuery.UserQuery.INSERT_USER_INTO_USER, Statement.RETURN_GENERATED_KEYS);
            prInsert.setString(1, user.getLogin());
            prInsert.setString(2, user.getPassword());
            prInsert.setString(3, user.getEmail());
            prInsert.setString(4, user.getPhone());
            prInsert.setString(5, user.getRole());
            prInsert.setInt(6, user.getBank());
            prInsert.setString(7, user.getName());
            prInsert.setString(8, user.getSurname());
            int resultInsert = prInsert.executeUpdate();
            if (resultInsert > 0) {
                rs = prInsert.getGeneratedKeys();
                if (rs.next()) {
                    user.setUserID(rs.getInt(1));
                    connection.commit();
                }
            } else {
                DBManager.getInstance().rollback(connection);
                return false;
            }
        } catch (SQLException e) {
            DBManager.getInstance().rollback(connection);
            throw new DBException("Insert user was failed", e);
        } finally {
            DBManager.getInstance().close(rs)
                    .close(prInsert)
                    .close(connection);
        }
        return true;
    }

    public User getUser(String login) throws DBException {
        Connection connection = null;
        User user;
        PreparedStatement prGet = null;
        try {
            connection = DBManager.getInstance().connect();
            prGet = connection.prepareStatement(SQLQuery.UserQuery.SELECT_ALL_FROM_USERS_WHERE);
            prGet.setString(1, login);
            ResultSet rs = prGet.executeQuery();
            rs.next();
            user = createUser(rs);
        } catch (SQLException e) {
            throw new DBException("Get user was failed", e);
        } finally {
            DBManager.getInstance().close(prGet)
                    .close(connection);
        }
        return user;
    }

    public boolean deleteUser(User user) throws DBException {
        Connection connection = null;
        PreparedStatement prDelete = null;
        try {
            connection = DBManager.getInstance().connect();
            prDelete = connection.prepareStatement(SQLQuery.UserQuery.DELETE_FROM_USER);
            prDelete.setString(1, user.getLogin());
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

    public boolean updateUser(User user) throws DBException {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = DBManager.getInstance().connect();
            con.setAutoCommit(false);
            pstmt = con.prepareStatement(SQLQuery.UserQuery.UPDATE_USER);
            pstmt.setString(1, user.getLogin());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getPhone());
            pstmt.setObject(5, user.getRole());
            pstmt.setInt(6, user.getBank());
            pstmt.setString(7, user.getName());
            pstmt.setString(8, user.getSurname());
            pstmt.setLong(9, user.getUserID());
            int updateResult = pstmt.executeUpdate();
            if (updateResult == 0) {
                return false;
            }
            con.commit();

        } catch (SQLException e) {
            DBManager.getInstance().rollback(con);
            throw new DBException("updateUser was failed", e);
        } finally {
            DBManager.getInstance().close(pstmt)
                    .close(con);
        }
        return true;
    }

}
