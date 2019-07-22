package com.aparovich.barterspot.dao;

import com.aparovich.barterspot.dao.exception.DaoException;
import com.aparovich.barterspot.model.bean.Category;
import com.aparovich.barterspot.model.bean.Lot;
import com.aparovich.barterspot.model.bean.User;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Maxim on 26.04.2017
 */
public abstract class AbstractLotDao extends AbstractDao<Long, Lot> {

    protected static final String SQL_SELECT_ALL =
                    "SELECT id, name, description, default_price, finishing, " +
                    "final_price, created_at, deleted_at, categories_id, users_id " +
                    "FROM barterspot.lots " +
                    "WHERE ISNULL(deleted_at) " +
                    "AND finishing > NOW() " +
                    "ORDER BY DATEDIFF(finishing, NOW());";

    protected static final String SQL_SELECT_BY_ID =
                    "SELECT id, name, description, default_price, final_price, " +
                    "finishing, created_at, deleted_at, categories_id, users_id " +
                    "FROM barterspot.lots " +
                    "WHERE id = ? AND ISNULL(deleted_at);";

    protected static final String SQL_SELECT_READY_BY_USER =
                    "SELECT l.id, l.name, l.description, l.default_price, l.final_price, l.finishing, " +
                    "l.created_at, l.deleted_at, l.categories_id, l.users_id " +
                    "FROM barterspot.lots AS l " +
                    "WHERE ( " +
                        "SELECT bu.users_id " +
                        "FROM barterspot.bids AS bu " +
                        "WHERE bu.bid = ( " +
                            "SELECT MAX(bb.bid) " +
                            "FROM barterspot.bids AS bb " +
                            "WHERE bb.lots_id = l.id " +
                            "AND ISNULL(bb.deleted_at) " +
                        ") " +
                        "AND bu.lots_id = l.id " +
                        "AND ISNULL(bu.deleted_at) " +
                        "GROUP BY bu.users_id   " +
                    ") = ? " +
                    "AND ISNULL(l.deleted_at) " +
                    "AND l.finishing < NOW() " +
                    "AND ISNULL(l.final_price) " +
                    "ORDER BY DATEDIFF(l.finishing, NOW());";

    protected static final String SQL_SELECT_SELLING_OUT_BY_USER =
                    "SELECT l.id, l.name, l.description, l.default_price, l.final_price, l.finishing, " +
                    "l.created_at, l.deleted_at, l.categories_id, l.users_id " +
                    "FROM barterspot.lots AS l " +
                    "WHERE l.users_id = ? " +
                    "AND l.finishing > NOW() " +
                    "AND ISNULL(l.deleted_at) " +
                    "AND ISNULL(l.final_price) " +
                    "ORDER BY DATEDIFF(l.finishing, NOW());";

    protected static final String SQL_SELECT_PURCHASED_BY_USER =
                    "SELECT l.id, l.name, l.description, l.default_price, l.final_price, l.finishing, " +
                    "l.created_at, l.deleted_at, l.categories_id, l.users_id " +
                    "FROM barterspot.lots AS l " +
                    "WHERE ( " +
                        "SELECT COUNT(b.id) " +
                        "FROM barterspot.bids AS b " +
                        "WHERE b.users_id = ? " +
                        "AND b.lots_id = l.id " +
                        "AND ISNULL(b.deleted_at) " +
                    ") > 0 " +
                    "AND l.finishing > NOW() " +
                    "AND ISNULL(l.deleted_at) " +
                    "AND ISNULL(l.final_price) " +
                    "ORDER BY DATEDIFF(l.finishing, NOW());";

    protected static final String SQL_SELECT_FINISHED_BY_USER =
                    "SELECT l.id, l.name, l.description, l.default_price, l.final_price, l.finishing, " +
                    "l.created_at, l.deleted_at, l.categories_id, l.users_id " +
                    "FROM barterspot.lots AS l " +
                    "WHERE ( " +
                        "SELECT COUNT(b.id) " +
                        "FROM barterspot.bids AS b " +
                        "WHERE b.lots_id = l.id " +
                        "AND ISNULL(b.deleted_at) " +
                    ") = 0 " +
                    "AND l.users_id = ? " +
                    "AND l.finishing < NOW() " +
                    "AND ISNULL(l.deleted_at) " +
                    "AND ISNULL(l.final_price) " +
                    "ORDER BY DATEDIFF(l.finishing, NOW());";

    protected static final String SQL_SELECT_BY_CATEGORY =
                    "SELECT id, name, description, default_price, final_price, finishing, " +
                    "created_at, deleted_at, categories_id, users_id " +
                    "FROM barterspot.lots " +
                    "WHERE categories_id = ? " +
                    "AND finishing > NOW() " +
                    "AND ISNULL(deleted_at) " +
                    "AND ISNULL(final_price) " +
                    "ORDER BY DATEDIFF(finishing, NOW());";

    protected static final String SQL_INSERT =
                    "INSERT INTO barterspot.lots (name, description, default_price, " +
                    "finishing, categories_id, users_id) " +
                    "VALUES(?, ?, ?, ?, ?, ?);";

    protected static final String SQL_UPDATE =
                    "UPDATE barterspot.lots SET name = ?, description = ?, " +
                    "default_price = ?, final_price = ?, finishing = ?, " +
                    "created_at = ?, deleted_at = ?, categories_id = ?, users_id = ? " +
                    "WHERE id = ?;";

    protected static final String SQL_DELETE =
                    "DELETE FROM barterspot.lots " +
                    "WHERE id = ?";

    protected static final String ID =                  "id";
    protected static final String NAME =                "name";
    protected static final String DESCRIPTION =         "description";
    protected static final String DEFAULT_PRICE =       "default_price";
    protected static final String FINAL_PRICE =         "final_price";
    protected static final String FINISHING =           "finishing";
    protected static final String CREATED_AT =          "created_at";
    protected static final String DELETED_AT =          "deleted_at";
    protected static final String CATEGORIES_ID =       "categories_id";
    protected static final String USERS_ID =            "users_id";

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
    public abstract List<Lot> findAll() throws DaoException;

    /**
     * Selects single row from the model table by its pk.
     *
     * @param pk of the model.
     * @return T instance of selected row.
     * @throws DaoException is more than one record received.
     */
    @Override
    public abstract Lot findByPk(Long pk) throws DaoException;

    /**
     * Selects single row from the model table by its pk.
     *
     * @param user of the model.
     * @return T instance of selected row.
     * @throws DaoException is more than one record received.
     */
    public abstract List<Lot> findReadyByUser(User user) throws DaoException;

    /**
     * Selects single row from the model table by its pk.
     *
     * @param user of the model.
     * @return T instance of selected row.
     * @throws DaoException is more than one record received.
     */
    public abstract List<Lot> findSellingOutByUser(User user) throws DaoException;

    /**
     * Selects single row from the model table by its pk.
     *
     * @param user of the model.
     * @return T instance of selected row.
     * @throws DaoException is more than one record received.
     */
    public abstract List<Lot> findPurchasedByUser(User user) throws DaoException;

    /**
     * Selects single row from the model table by its pk.
     *
     * @param user of the model.
     * @return T instance of selected row.
     * @throws DaoException is more than one record received.
     */
    public abstract List<Lot> findFinishedByUser(User user) throws DaoException;

    /**
     * Selects single row from the model table by its pk.
     *
     * @param category of the model.
     * @return T instance of selected row.
     * @throws DaoException is more than one record received.
     */
    public abstract List<Lot> findByCategory(Category category) throws DaoException;

    /**
     * Inserts single row in the model table according to {@param model}.
     *
     * @param model instance, which must be put in DB.
     * @throws DaoException if record with same pk exists.
     */
    @Override
    public abstract Lot create(Lot model) throws DaoException;

    /**
     * Updates single row in the model table according to {@param model}.
     *
     * @param model instance, which must be updated in DB.
     * @throws DaoException if incorrect number of records modified.
     */
    @Override
    public abstract void update(Lot model) throws DaoException;

    /**
     * Deletes single row from the model table by its pk.
     *
     * @param model instance, which must be deleted from DB.
     * @throws DaoException if incorrect number of records modified.
     */
    @Override
    public abstract void delete(Lot model) throws DaoException;

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
    protected abstract List<Lot> parseResultSet(ResultSet resultSet) throws DaoException;


    /**
     * Transform LocalDateTime object to string according to "yyyy-MM-dd hh:mm:ss" format.
     * @param date LocalDateTime object
     * @return formatted string.
     */
    protected abstract String toDateTimeString(LocalDateTime date)  throws DaoException;
}
