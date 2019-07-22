package com.aparovich.barterspot.dao;

import com.aparovich.barterspot.dao.exception.DaoException;
import com.aparovich.barterspot.model.bean.Bid;
import com.aparovich.barterspot.model.bean.Lot;
import com.aparovich.barterspot.model.bean.User;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Maxim on 26.04.2017
 */
public abstract class AbstractBidDao extends AbstractDao<Long, Bid> {

    protected static final String SQL_SELECT_ALL =
            "SELECT id, bid, created_at, deleted_at, users_id, lots_id " +
            "FROM barterspot.bids " +
            "WHERE ISNULL(deleted_at) " +
            "ORDER BY lots_id ASC, bid DESC;";

    protected static final String SQL_SELECT_BY_ID =
            "SELECT id, bid, created_at, deleted_at, users_id, lots_id " +
            "FROM barterspot.bids " +
            "WHERE id = ? AND ISNULL(deleted_at);";

    protected static final String SQL_SELECT_BY_USERS_ID =
            "SELECT id, bid, created_at, deleted_at, users_id, lots_id " +
            "FROM barterspot.bids " +
            "WHERE users_id = ? AND ISNULL(deleted_at) " +
            "ORDER BY lots_id ASC, bid DESC;";

    protected static final String SQL_SELECT_BY_LOTS_ID =
            "SELECT id, bid, created_at, deleted_at, users_id, lots_id " +
            "FROM barterspot.bids " +
            "WHERE lots_id = ? AND ISNULL(deleted_at) " +
            "ORDER BY bid DESC;";

    protected static final String SQL_INSERT =
            "INSERT INTO barterspot.bids (bid, users_id, lots_id) " +
            "VALUES(?, ?, ?);";

    protected static final String SQL_UPDATE =
            "UPDATE barterspot.bids " +
            "SET bid = ?, created_at = ?, deleted_at = ?, users_id = ?, lots_id = ? " +
            "WHERE id = ?;";

    protected static final String SQL_DELETE =
            "DELETE FROM barterspot.bids " +
            "WHERE id = ?;";


    protected static final String ID =                  "id";
    protected static final String BID =                 "bid";
    protected static final String CREATED_AT =          "created_at";
    protected static final String DELETED_AT =          "deleted_at";
    protected static final String USERS_ID =            "users_id";
    protected static final String LOTS_ID =             "lots_id";

    protected static final String DATE_TIME_FORMAT =    "yyyy-MM-dd hh:mm:ss";

    /**
     * Selects all rows from the model table.
     *
     * @return List<T> of all models.
     * @throws DaoException if occurred errors in SQL request.
     *                      Examples of errors: no items selected, duplicated elements
     *                      in rows with UNIQUE index and etc.
     */
    @Override
    public abstract List<Bid> findAll() throws DaoException;

    /**
     * Selects single row from the model table by its pk.
     *
     * @param pk of the model.
     * @return T instance of selected row.
     * @throws DaoException is more than one record received.
     */
    @Override
    public abstract Bid findByPk(Long pk) throws DaoException;

    /**
     * Selects rows from the model table by lots_id.
     * @param lot
     * @return
     * @throws DaoException
     */
    public abstract List<Bid> findByLot(Lot lot) throws DaoException;

    /**
     * Selects rows from the model table by users_id.
     * @param user
     * @return
     * @throws DaoException
     */
    public abstract List<Bid> findByUser(User user) throws DaoException;

    /**
     * Inserts single row in the model table according to {@param model}.
     *
     * @param model instance, which must be put in DB.
     * @throws DaoException if record with same pk exists.
     */
    @Override
    public abstract Bid create(Bid model) throws DaoException;

    /**
     * Updates single row in the model table according to {@param model}.
     *
     * @param model instance, which must be updated in DB.
     * @throws DaoException if incorrect number of records modified.
     */
    @Override
    public abstract void update(Bid model) throws DaoException;

    /**
     * Deletes single row from the model table by its pk.
     *
     * @param model instance, which must be deleted from DB.
     * @throws DaoException if incorrect number of records modified.
     */
    @Override
    public abstract void delete(Bid model) throws DaoException;

    /**
     * Parse SQL result, stored in ResultSet.
     *
     * @param resultSet SQL select result.
     * @return List<T> of models.
     * @throws DaoException if occurred errors in SQL request.
     *                      Examples of errors: no items selected, duplicated elements
     *                      in rows with UNIQUE index and etc.
     */
    @Override
    protected abstract List<Bid> parseResultSet(ResultSet resultSet) throws DaoException;

    /**
     * Transform LocalDateTime object to string according to "yyyy-MM-dd hh:mm:ss" format.
     * @param date LocalDateTime object.
     * @return formatted string.
     */
    protected abstract String toDateTimeString(LocalDateTime date) throws DaoException;
}
