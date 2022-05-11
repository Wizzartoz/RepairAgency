package com.maznichko.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class ManagerDB {

    public String CONNECTION_URL;
    protected ManagerDB(){

    }

    public abstract Connection connect() throws SQLException;

    public void close(Statement statement) throws DBException {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            throw new DBException("SQL Exception", e);
        }
    }

    public void close(Connection connection) throws DBException {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new DBException("SQL Exception", e);
        }

    }

    public void rollback(Connection connection) throws DBException {
        try {
            if (connection != null) {
                connection.rollback();
            }
        } catch (SQLException e) {
            throw new DBException("SQL Exception", e);
        }

    }
    public void autocommit(Connection connection,boolean flag) throws DBException {
        try {
            if (connection != null) {
                connection.setAutoCommit(flag);
            }
        } catch (SQLException e) {
            throw new DBException("SQL Exception", e);
        }
    }

}
