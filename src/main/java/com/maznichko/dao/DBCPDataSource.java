package com.maznichko.dao;

import com.maznichko.GetProperties;
import org.apache.commons.dbcp.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DBCPDataSource extends ManagerDB {
    private static BasicDataSource ds = new BasicDataSource();
    static Properties properties = GetProperties.getProp("/home/misha/IdeaProjects/RepairAgent/src/main/resources/config.properties");

    static {
        ds.setUrl(properties.getProperty("postgres.url"));
        ds.setUsername(properties.getProperty("postgres.user"));
        ds.setPassword(properties.getProperty("postgres.password"));
        ds.setMinIdle(5);
        ds.setMaxIdle(10);
        ds.setMaxOpenPreparedStatements(100);
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    private DBCPDataSource(){
    }
}
