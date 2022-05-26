package com.maznichko.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestDBManager extends ManagerDB {
    private static TestDBManager instance = null;

    //Singlton
    private TestDBManager() {
        super.CONNECTION_URL = "jdbc:postgresql://127.0.0.1:5432/test_agent?user=maznich40&password=magik455";
    }

    /**
     * return the only one object of class
     * @return - object
     */
    public static synchronized TestDBManager getInstance() {
        if (instance == null) {
            instance = new TestDBManager();
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
        Connection con;
        try {
            con = DriverManager.getConnection(
                    CONNECTION_URL);
            con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return con;
    }
}
