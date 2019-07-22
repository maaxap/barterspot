package com.aparovich.barterspot.dao;

import com.aparovich.barterspot.pool.ConnectionPool;
import com.aparovich.barterspot.pool.ProxyConnection;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;

/**
 * Created by Maxim on 09.04.2017
 */
public class TransactionHelper {
    private static final Logger LOGGER = LogManager.getLogger(TransactionHelper.class);
    private boolean multipleTransaction;
    private ProxyConnection connection = ConnectionPool.getInstance().getConnection();

    public void prepareTransaction(AbstractDao ... daos) {
        if(daos.length > 1) {
            try {
                this.connection.setAutoCommit(false);
            } catch (SQLException e) {
                LOGGER.log(Level.ERROR, "SQLException: Connection autocommit cannot been set to false.");
            }
            multipleTransaction = true;
        }
        for (AbstractDao dao : daos) {
            dao.setConnection(connection);
        }
    }

    public void invalidateTransaction() {
        if(multipleTransaction) {
            try {
                this.connection.setAutoCommit(true);
            } catch (SQLException e) {
                LOGGER.log(Level.ERROR, "SQLException: Connection autocommit cannot been set to true.");
            }
        }
        ConnectionPool.getInstance().returnConnection(connection);
    }

    /**
     * Rollback current transaction.
     *
     * @see com.aparovich.barterspot.pool.ProxyConnection
     * @see java.sql.Connection
     */
    public void rollback() {
        if(multipleTransaction) {
            try {
                this.connection.rollback();
            } catch (SQLException e) {
                LOGGER.log(Level.ERROR, "SQLException: Rollback cannot be done.");
            }
        }
    }

    /**
     * Commit current transaction.
     */
    public void commit() {
        if(multipleTransaction) {
            try {
                this.connection.commit();
            } catch (SQLException e) {
                LOGGER.log(Level.ERROR, "SQLException: Commit cannot be done.");
            }
        }
    }
}
