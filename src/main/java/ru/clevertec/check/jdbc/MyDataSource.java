package ru.clevertec.check.jdbc;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

public class MyDataSource implements DataSource {
    private static volatile MyDataSource instance;
    private final String driver;
    private final String url;
    private final String name;
    private final String password;

    private MyDataSource(String driver, String url, String name, String password) {
        this.driver = driver;
        this.url = url;
        this.name = name;
        this.password = password;
    }

    public static void initialize(String driver, String url, String name, String password) {
        if (instance == null) {
            synchronized (MyDataSource.class) {
                if (instance == null) {
                    instance = new MyDataSource(driver, url, name, password);
                    try {
                        Class.forName(driver);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static MyDataSource getInstance() {
        if (instance == null) {
            throw new IllegalStateException("MyDataSource is not initialized");
        }
        return instance;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, name, password);
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    public PrintWriter getLogWriter() throws SQLException {
        throw new UnsupportedOperationException("'getLogWriter' is not implemented");
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        throw new UnsupportedOperationException("'setLogWriter' is not implemented");
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        throw new UnsupportedOperationException("'setLoginTimeout' is not implemented");
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        throw new UnsupportedOperationException("'getLoginTimeout' is not implemented");
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        throw new UnsupportedOperationException("'getParentLogger' is not implemented");
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        throw new UnsupportedOperationException("'unwrap' is not implemented");
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        throw new UnsupportedOperationException("'isWrapperFor' is not implemented");
    }
}
