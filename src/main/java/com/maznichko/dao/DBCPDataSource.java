package com.maznichko.dao;

import org.apache.commons.dbcp.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DBCPDataSource extends UtilDB {

    private static final BasicDataSource ds = new BasicDataSource();
    private static final ResourceBundle properties;

    static {
        properties = ResourceBundle.getBundle("config");
        ds.setUrl(properties.getString("postgres.url"));
        ds.setUsername(properties.getString("postgres.user"));
        ds.setPassword(properties.getString("postgres.password"));
        ds.setMinIdle(5);
        ds.setMaxIdle(10);
        ds.setMaxOpenPreparedStatements(100);
    }

    private DBCPDataSource() {
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}
