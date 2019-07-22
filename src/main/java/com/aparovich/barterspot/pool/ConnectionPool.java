package com.aparovich.barterspot.pool;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static com.aparovich.barterspot.pool.util.ConnectionPoolConstant.*;

/**
 * Created by Maxim on 19.03.2017
 */
public class ConnectionPool {
    private static final Logger LOGGER = LogManager.getLogger(ConnectionPool.class);

    private static ConnectionPool instance;
    private static Lock lock = new ReentrantLock();
    private static AtomicBoolean isCreated = new AtomicBoolean(false);

    private static ResourceBundle parameters = ResourceBundle.getBundle("database");

    private String url;
    private String login;
    private String password;
    private BlockingQueue<ProxyConnection> connections;

    private ConnectionPool(String url, String login, String password, int poolSize) {
        this.url = url;
        this.login = login;
        this.password = password;
        this.connections = new ArrayBlockingQueue<>(poolSize);

        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        } catch (SQLException e) {
            LOGGER.log(Level.FATAL, "SQLException: Driver cannot be registered. " + e.getMessage());
            throw  new RuntimeException(e);
        }

        for (int i = 0; i < poolSize; i++) {
            connections.add(this.createConnection());
        }

        this.checkConnectionsSize();
    }

    public static ConnectionPool getInstance() {
        if (!isCreated.get()) {
            lock.lock();
            try {
                if(instance == null) {
                    String url = parameters.getString(DB_URL_KEY);
                    String login = parameters.getString(LOGIN_KEY);
                    String password = parameters.getString(PASSWORD_KEY);
                    int poolSize = Integer.valueOf(parameters.getString(POOL_SIZE_KEY));

                    instance = new ConnectionPool(url, login, password, poolSize);
                    isCreated.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    public ProxyConnection getConnection() {
        ProxyConnection connection = null;

        try {
            connection = connections.take();
        } catch (InterruptedException e) {
            LOGGER.log(Level.ERROR, e.getMessage());
        }

        return connection;
    }

    public int getSize() {
        return  connections.size();
    }

    public void returnConnection(ProxyConnection connection) {
        try {
            connections.put(connection);
        } catch (InterruptedException ie) {
            LOGGER.log(Level.ERROR, "InterruptedException: Connection cannot be returned. " + ie.getMessage());
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.log(Level.ERROR, "SQLException: Connection cannot be closed. " + e.getMessage());
            }
        }
    }

    public void closePool() {
        int size = getSize();
        for (int i = 0; i < size; i++) {
            try {
                ProxyConnection connection = connections.poll(5, TimeUnit.SECONDS);
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e1) {
                LOGGER.log(Level.ERROR, "SQLException: Cannot connect to database. " + e1.getMessage());
            } catch (InterruptedException e2) {
                LOGGER.log(Level.ERROR, "InterruptedException: Cannot take connection from storage. " + e2.getMessage());
            }
        }
        try {
            Enumeration<Driver> drivers = DriverManager.getDrivers();
            while (drivers.hasMoreElements()) {
                DriverManager.deregisterDriver(drivers.nextElement());
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "Database error occurred. " + e.getMessage());
        } finally {
            instance = null;
            isCreated.set(false);
        }

    }

    private ProxyConnection createConnection() {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(url, login, password);
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "SQLException: Connection cannot be created. " + e.getMessage());
        }

        return new ProxyConnection(connection);
    }

    private void checkConnectionsSize() {
        int failConnections = connections.remainingCapacity();

        if(failConnections > 0) {
            for (int i = 0; i < failConnections; i++) {
                connections.add(createConnection());
            }
        }

        if(connections.isEmpty()) {
            LOGGER.log(Level.FATAL, "Fatal error: Connection to database cannot be created.");
            throw new RuntimeException();
        }
    }
}
