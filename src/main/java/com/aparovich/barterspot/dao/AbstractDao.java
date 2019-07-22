package com.aparovich.barterspot.dao;

import com.aparovich.barterspot.dao.exception.DaoException;
import com.aparovich.barterspot.dao.factory.DaoFactory;
import com.aparovich.barterspot.model.Model;
import com.aparovich.barterspot.pool.ProxyConnection;
import com.aparovich.barterspot.model.util.ModelType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.util.List;

/**
 * Created by Maxim on 20.03.2017
 * Abstract class which provides base realization of CRUD operations.
 * @param <K> Type of primary key.
 * @param <T> Type of the model, model must be extended from Model.
 */
public abstract class AbstractDao<K, T extends Model> {

    protected static final Logger LOGGER = LogManager.getLogger();

    protected ProxyConnection connection;

    /**
     * Set connection.
     * @param connection
     */
    public  void setConnection(ProxyConnection connection) {
        this.connection = connection;
    }

    /**
     * Selects all rows from the model table.
     *
     * @return List<T> of all models.
     * @throws DaoException if occurred errors in SQL request.
     *          Examples of errors: no items selected, duplicated elements
     *          in rows with UNIQUE index and etc.
     */
    public abstract List<T> findAll() throws DaoException;

    /**
     * Selects single row from the model table by its pk.
     *
     * @param pk of the model.
     * @return T instance of selected row.
     * @throws DaoException is more than one record received.
     */
    public abstract T findByPk(K pk) throws DaoException;

    /**
     * Inserts single row in the model table according to {@param model}.
     *
     * @param model instance, which must be put in DB.
     * @throws DaoException if record with same pk exists.
     */
    public abstract T create(T model) throws DaoException;

    /**
     * Updates single row in the model table according to {@param model}.
     *
     * @param model instance, which must be updated in DB.
     * @throws DaoException if incorrect number of records modified.
     */
    public abstract void update(T model) throws DaoException;

    /**
     * Deletes single row from the model table by its pk.
     *
     * @param model instance, which must be deleted from DB.
     * @throws DaoException if incorrect number of records modified.
     */
    public abstract void delete(T model) throws DaoException;

    /**
     * Parse SQL result, stored in ResultSet.
     *
     * @param resultSet SQL select result.
     * @return List<T> of models.
     * @throws DaoException if occurred errors in SQL request.
     *          Examples of errors: no items selected, duplicated elements
     *          in rows with UNIQUE index and etc.
     */
    protected abstract List<T> parseResultSet(ResultSet resultSet) throws DaoException;

    /**
     * Returns models dependence.
     * @param modelType - dependence model
     * @param pk - dependence pk.
     * @return dependence instance.
     */
    protected Model getDependence(ModelType modelType, K pk) throws DaoException {
        Model model = null;
        AbstractDao dependenceDao =  DaoFactory.getDao(modelType);
        dependenceDao.setConnection(this.connection);
        model = dependenceDao.findByPk(pk);
        return model;
    }
}
