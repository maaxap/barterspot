package com.aparovich.barterspot.dao;

import com.aparovich.barterspot.dao.exception.DaoException;
import com.aparovich.barterspot.model.util.RoleType;
import com.aparovich.barterspot.model.bean.User;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Maxim on 26.04.2017
 */
public abstract class AbstractUserDao extends AbstractDao<Long, User> {

    protected static final String SQL_SELECT_ALL =
            "SELECT id, email, password, role, is_blocked, created_at, deleted_at, info_id, settings_id " +
            "FROM barterspot.users " +
            "WHERE ISNULL(deleted_at);";

    protected static final String SQL_SELECT_BY_ID =
            "SELECT id, email, password, role, is_blocked, created_at, deleted_at, info_id, settings_id " +
            "FROM barterspot.users " +
            "WHERE id = ? AND ISNULL(deleted_at);";

    protected static final String SQL_SELECT_BY_EMAIL =
            "SELECT id, email, password, role, is_blocked, created_at, deleted_at, info_id, settings_id " +
            "FROM barterspot.users " +
            "WHERE email = ? AND ISNULL(deleted_at);";

    protected static final String SQL_SELECT_BY_ROLE =
            "SELECT id, email, password, role, is_blocked, created_at, deleted_at, info_id, settings_id " +
            "FROM barterspot.users " +
            "WHERE role = ? AND ISNULL(deleted_at);";

    protected static final String SQL_INSERT =
            "INSERT INTO barterspot.users (email, password, info_id, settings_id) " +
            "VALUES(?, ?, ?, ?);";

    protected static final String SQL_UPDATE =
            "UPDATE barterspot.users SET email = ?,  password = ?, role = ?, is_blocked = ?, created_at = ?, " +
            "deleted_at = ?, info_id = ?, settings_id = ? " +
            "WHERE id = ?;";

    protected static final String SQL_DELETE =
            "DELETE FROM barterspot.users " +
            "WHERE id = ?";

    protected static final String ID =                  "id";
    protected static final String EMAIL =               "email";
    protected static final String PASSWORD =            "password";
    protected static final String ROLE =                "role";
    protected static final String IS_BLOCKED =          "is_blocked";
    protected static final String CREATED_AT =          "created_at";
    protected static final String DELETED_AT =          "deleted_at";
    protected static final String INFO_ID =             "info_id";
    protected static final String SETTINGS_ID =         "settings_id";

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
    public abstract List<User> findAll() throws DaoException;

    /**
     * Selects single row from the model table by its pk.
     *
     * @param pk of the model.
     * @return T instance of selected row.
     * @throws DaoException is more than one record received.
     */
    @Override
    public abstract User findByPk(Long pk) throws DaoException;

    /**
     * Selects single row from the model table by its email.
     *
     * @param email of the model.
     * @return T instance of selected row.
     * @throws DaoException is more than one record received.
     */
    public abstract User findByEmail(String email) throws DaoException;


    public abstract List<User> findByRole(RoleType role) throws DaoException;

    /**
     * Inserts single row in the model table according to {@param model}.
     *
     * @param model instance, which must be put in DB.
     * @throws DaoException if record with same pk exists.
     */
    @Override
    public abstract User create(User model) throws DaoException;

    /**
     * Updates single row in the model table according to {@param model}.
     *
     * @param model instance, which must be updated in DB.
     * @throws DaoException if incorrect number of records modified.
     */
    @Override
    public abstract void update(User model) throws DaoException;

    /**
     * Deletes single row from the model table by its pk.
     *
     * @param model instance, which must be deleted from DB.
     * @throws DaoException if incorrect number of records modified.
     */
    @Override
    public abstract void delete(User model) throws DaoException;

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
    protected abstract List<User> parseResultSet(ResultSet resultSet) throws DaoException;

    /**
     * Transform LocalDateTime object to string according to "yyyy-MM-dd hh:mm:ss" format.
     * @param date LocalDateTime object
     * @return formatted string.
     */
    protected abstract String toDateTimeString(LocalDateTime date)  throws DaoException;
}
