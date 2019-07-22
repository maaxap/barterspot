package com.aparovich.barterspot.dao;

import com.aparovich.barterspot.dao.exception.DaoException;
import com.aparovich.barterspot.model.bean.Category;

import java.sql.ResultSet;
import java.util.List;

/**
 * Created by Maxim on 26.04.2017
 */
public abstract class AbstractCategoryDao extends AbstractDao<Long, Category> {

    protected static final String SQL_SELECT_ALL =
            "SELECT id, name " +
            "FROM barterspot.categories " +
            "ORDER BY name;";

    protected static final String SQL_SELECT_BY_ID =
            "SELECT id, name " +
            "FROM barterspot.categories " +
            "WHERE id = ?;";

    protected static final String SQL_SELECT_BY_NAME =
            "SELECT id, name " +
            "FROM barterspot.categories " +
            "WHERE name = ?;";

    protected static final String SQL_INSERT =
            "INSERT INTO barterspot.categories (name) " +
            "VALUES(?);";

    protected static final String SQL_UPDATE =
            "UPDATE barterspot.categories " +
            "SET name = ? " +
            "WHERE id = ?;";

    protected static final String SQL_DELETE =
            "DELETE FROM barterspot.categories " +
            "WHERE id = ?;";

    protected static final String ID =      "id";
    protected static final String NAME =    "name";

    /**
     * Selects all rows from the model table.
     *
     * @return List<T> of all models.
     * @throws DaoException if occurred errors in SQL request.
     *                      Examples of errors: no items selected, duplicated elements
     *                      in rows with UNIQUE index and etc.
     */
    @Override
    public abstract List<Category> findAll() throws DaoException;

    /**
     * Selects single row from the model table by its pk.
     *
     * @param pk of the model.
     * @return T instance of selected row.
     * @throws DaoException is more than one record received.
     */
    @Override
    public abstract Category findByPk(Long pk) throws DaoException;

    /**
     * Selects single row from the model table by its pk.
     *
     * @param name of the model.
     * @return T instance of selected row.
     * @throws DaoException if more than one record received.
     */
    public abstract Category findByName(String name) throws DaoException;

    /**
     * Inserts single row in the model table according to {@param model}.
     *
     * @param model instance, which must be put in DB.
     * @throws DaoException if record with same pk exists.
     */
    @Override
    public abstract Category create(Category model) throws DaoException;

    /**
     * Updates single row in the model table according to {@param model}.
     *
     * @param model instance, which must be updated in DB.
     * @throws DaoException if incorrect number of records modified.
     */
    @Override
    public abstract void update(Category model) throws DaoException;

    /**
     * Deletes single row from the model table by its pk.
     *
     * @param model instance, which must be deleted from DB.
     * @throws DaoException if incorrect number of records modified.
     */
    @Override
    public abstract void delete(Category model) throws DaoException;

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
    protected abstract List<Category> parseResultSet(ResultSet resultSet) throws DaoException;
}
