package com.maznichko.DAO;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.zip.CheckedOutputStream;

public class DBManager {
    public static String CONNECTION_URL = "jdbc:postgresql://127.0.0.1:5432/agent?user=maznich40&password=magik455";
    private DBManager() {
    }

    private static DBManager instance = null;

    public static synchronized DBManager getInstance() {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }
/*
    public Connection connect() throws DBException {
        InitialContext cxt = null;
        DataSource ds = null;
        Connection connection = null;
        try {
            cxt = new InitialContext();
            ds = (DataSource) cxt.lookup("java:/comp/env/jdbc/postgres");
            connection = ds.getConnection();
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }catch (SQLException e){
            throw new DBException("is problem",e);
        }
        return connection;
    }

 */



   /*
    public Connection connect() throws DBException {
        Connection connection;
        try {
            connection = DriverManager.getConnection(CONNECTION_URL);
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            throw new DBException("Fail of connection", e);
        }
        return connection;
    }



    */

   public Connection connect() throws SQLException {
       try {
           Class.forName("org.postgresql.Driver");
       } catch (ClassNotFoundException e) {
           e.printStackTrace();
       }
       Connection con = DriverManager.getConnection(
               CONNECTION_URL);
       con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
       return con;
   }





    public DBManager close(AutoCloseable closeable) throws DBException {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (Exception e) {
            throw new DBException("resource cannot be closed", e);
        }
        return this;
    }

    public void rollback(Connection connection) throws DBException {
        try {
            if (connection != null) {
                connection.rollback();
            }
        } catch (SQLException e) {
            throw new DBException("database access error occurs", e);
        }
    }


}
