package com.maznichko.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Abstract class for set connection to DB
 */
public abstract class ManagerDB {
    /**
     * connection url for create connect to DB
     */
    public String CONNECTION_URL;
    //Singlton
    protected ManagerDB(){

    }

    /**
     * create connection to DB
     * @return - connection
     * @throws SQLException - throw if we are cannot connect to DB
     */
    public abstract Connection connect() throws SQLException;

    /**
     * close connection to DB
     * @param statement - statement of DB
     * @throws DBException - if a database access error occurs
     */

    public void close(Statement statement) throws DBException {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            throw new DBException("SQL Exception", e);
        }
    }
    /**
     * close connection to DB
     * @param connection - connection to DB
     * @throws DBException - if a database access error occurs
     */

    public void close(Connection connection) throws DBException {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new DBException("SQL Exception", e);
        }

    }

    /**
     * cancels a transaction step
     * @param connection - connection to DB
     * @throws DBException - if a database access error occurs
     */

    public void rollback(Connection connection) throws DBException {
        try {
            if (connection != null) {
                connection.rollback();
            }
        } catch (SQLException e) {
            throw new DBException("SQL Exception", e);
        }

    }

    /**
     * final send transaction to DB
     * @param connection - connection to DB
     * @param flag - statement of autocommit
     * @throws DBException - if a database access error occurs
     */
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
