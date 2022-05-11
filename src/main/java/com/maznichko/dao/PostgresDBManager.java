package com.maznichko.dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class PostgresDBManager extends ManagerDB {
    private static PostgresDBManager instance = null;

    //Singlton
    private PostgresDBManager() {
        super.CONNECTION_URL = "jdbc:postgresql://127.0.0.1:5432/agent?user=maznich40&password=magik455";
    }

    public static synchronized PostgresDBManager getInstance() {
        if (instance == null) {
            instance = new PostgresDBManager();
        }
        return instance;
    }

    @Override
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


}

