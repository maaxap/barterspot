package com.aparovich.barterspot.dao;

import com.aparovich.barterspot.dao.exception.DaoException;
import com.aparovich.barterspot.model.bean.Info;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by Maxim on 26.04.2017
 */
public abstract class AbstractInfoDao extends AbstractDao<Long, Info> {

    protected static final String SQL_SELECT_ALL =
            "SELECT id, name, surname, birth_date, phone_number, address, " +
            "post_code " +
            "FROM barterspot.info;";

    protected static final String SQL_SELECT_BY_ID =
            "SELECT id, name, surname, birth_date, phone_number, address, " +
            "post_code FROM barterspot.info " +
            "WHERE id = ?;";

    protected static final String SQL_INSERT =
            "INSERT INTO barterspot.info (name, surname, birth_date, " +
            "phone_number, address, post_code) " +
            "VALUES(?, ?, ?, ?, ?, ?);";

    protected static final String SQL_UPDATE =
            "UPDATE barterspot.info SET name = ?, surname = ?, " +
            "birth_date = ?, phone_number = ?, address = ?, post_code = ? " +
            "WHERE id = ?;";

    protected static final String SQL_DELETE =
            "DELETE FROM barterspot.info " +
            "WHERE id = ?;";

    protected static final String ID =                "id";
    protected static final String NAME =              "name";
    protected static final String SURNAME =           "surname";
    protected static final String BIRTH_DATE =        "birth_date";
    protected static final String PHONE_NUMBER =      "phone_number";
    protected static final String ADDRESS =           "address";
    protected static final String POST_CODE =         "post_code";

    /**
     * Selects all rows from the model table.
     *
     * @return List<T> of all models.
     * @throws DaoException if occurred errors in SQL request.
     *                      Examples of errors: no items selected, duplicated elements
     *                      in rows with UNIQUE index and etc.
     */
    @Override
    public abstract List<Info> findAll() throws DaoException;

    /**
     * Selects single row from the model table by its pk.
     *
     * @param pk of the model.
     * @return T instance of selected row.
     * @throws DaoException is more than one record received.
     */
    @Override
    public abstract Info findByPk(Long pk) throws DaoException;

    /**
     * Inserts single row in the model table according to {@param model}.
     *
     * @param model instance, which must be put in DB.
     * @throws DaoException if record with same pk exists.
     */
    @Override
    public abstract Info create(Info model) throws DaoException;

    /**
     * Updates single row in the model table according to {@param model}.
     *
     * @param model instance, which must be updated in DB.
     * @throws DaoException if incorrect number of records modified.
     */
    @Override
    public abstract void update(Info model) throws DaoException;

    /**
     * Deletes single row from the model table by its pk.
     *
     * @param model instance, which must be deleted from DB.
     * @throws DaoException if incorrect number of records modified.
     */
    @Override
    public abstract void delete(Info model) throws DaoException;

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
    protected abstract List<Info> parseResultSet(ResultSet resultSet) throws DaoException;

    /**
     *
     * @param date
     * @return
     */
    protected abstract String toDateString(LocalDate date)  throws DaoException;
}
