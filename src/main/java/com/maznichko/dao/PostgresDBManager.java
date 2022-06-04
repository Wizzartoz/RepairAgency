package com.maznichko.dao;

import org.apache.log4j.Logger;

import javax.el.ResourceBundleELResolver;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * Concrete realisation connect to Postgres DB
 */
public class PostgresDBManager extends ManagerDB {

    private static final Logger log = Logger.getLogger(PostgresDBManager.class);
    private static PostgresDBManager instance = null;

    //Singlton
    private PostgresDBManager() {
        super.CONNECTION_URL = "jdbc:postgresql://127.0.0.1:5432/agent?user=maznich40&password=magik455";
    }

    /**
     * return the only one object of class
     * @return - object
     */
    public static synchronized PostgresDBManager getInstance() {
        if (instance == null) {
            instance = new PostgresDBManager();
        }
        return instance;
    }

    @Override
    public Connection connect() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            log.error(e.getMessage() + " postgresql driver didn't find");
            e.printStackTrace();
        }
        Connection con;
        try {
            con = DriverManager.getConnection(
                    CONNECTION_URL);
            con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        } catch (SQLException e) {
            log.error(e.getMessage() + " connection to postgresql is failed");
            throw new RuntimeException(e);
        }
        log.info("connection to DB successful");
        return con;
    }


}

