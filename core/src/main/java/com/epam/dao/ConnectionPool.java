package com.epam.dao;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * Created by infinity on 18.02.16.
 */
public class ConnectionPool {

    private static DataSource dataSource;

    public static synchronized DataSource getInstance() {
        if (dataSource == null) {
            try {
                Context ctx = new InitialContext();
                dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/library");

            } catch (NamingException e) {
                e.printStackTrace();
            }
        }
        return dataSource;
    }
}
