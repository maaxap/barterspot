package com.aparovich.barterspot.dao.impl;

import com.aparovich.barterspot.dao.AbstractCategoryDao;
import com.aparovich.barterspot.dao.exception.DaoException;
import com.aparovich.barterspot.model.bean.Category;
import org.apache.logging.log4j.Level;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maxim on 09.04.2017
 */
public class CategoryDao extends AbstractCategoryDao {

    /**
     * Selects all rows from the model table.
     *
     * @return List<T> of all models.
     * @throws DaoException if occurred errors in SQL request.
     *                      Examples of errors: no items selected, duplicated elements
     *                      in rows with UNIQUE index and etc.
     */
    @Override
    public List<Category> findAll() throws DaoException {
        List<Category> list = new ArrayList<>();
        try (PreparedStatement statement = this.connection.prepareStatement(SQL_SELECT_ALL);
             ResultSet resultSet = statement.executeQuery()) {

            list = this.parseResultSet(resultSet);

        } catch (SQLException e) {
            throw new DaoException("SQLException: Statement cannot be created. " + e.getMessage());
        }
        return list;
    }

    /**
     * Selects single row from the model table by its pk.
     *
     * @param pk of the model.
     * @return T instance of selected row.
     * @throws DaoException is more than one record received.
     */
    @Override
    public Category findByPk(Long pk) throws DaoException {
        Category category = null;
        try (PreparedStatement statement = this.connection.prepareStatement(SQL_SELECT_BY_ID)) {
            statement.setLong(1, pk);
            List<Category> list = null;
            try (ResultSet resultSet = statement.executeQuery()) {
                list = parseResultSet(resultSet);
            }
            if (list == null || list.isEmpty()) {
                category = null;
            } else if (list.size() > 1) {
                throw new DaoException("Received more than one record.");
            } else {
                category = list.iterator().next();
            }
        } catch (SQLException e) {
            throw new DaoException("SQLException: Statement cannot be created. "  + e.getMessage());
        }
        return category;
    }

    /**
     * Selects single row from the model table by its name.
     *
     * @param name of the model.
     * @return T instance of selected row.
     * @throws DaoException is more than one record received.
     */
    @Override
    public Category findByName(String name) throws DaoException {
        Category category = null;
        try (PreparedStatement statement = this.connection.prepareStatement(SQL_SELECT_BY_NAME)) {
            statement.setString(1, name);
            List<Category> list = null;
            try (ResultSet resultSet = statement.executeQuery()) {
                list = parseResultSet(resultSet);
            }
            if (list == null || list.isEmpty()) {
                category = null;
            } else if (list.size() > 1) {
                throw new DaoException("Received more than one record.");
            } else {
                category = list.iterator().next();
            }
        } catch (SQLException e) {
            throw new DaoException("SQLException: Statement cannot be created. " + e.getMessage());
        }
        return category;
    }

    /**
     * Inserts single row in the model table according to {@param model}.
     *
     * @param model instance, which must be put in DB.
     * @throws DaoException if record with same pk exists.
     */
    @Override
    public Category create(Category model) throws DaoException {
        Category category = null;
        try (PreparedStatement statement = this.connection.prepareStatement(SQL_INSERT, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, model.getName());
            int count = statement.executeUpdate();
            if(count != 1) {
                throw new DaoException("On create modify invalid number of records: " + count);
            }
            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    category = findByPk(keys.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new DaoException("SQLException: Statement cannot be created. "  + e.getMessage());
        }
        return category;
    }

    /**
     * Updates single row in the model table according to {@param model}.
     *
     * @param model instance, which must be updated in DB.
     * @throws DaoException if incorrect number of records modified.
     */
    @Override
    public void update(Category model) throws DaoException {
        try (PreparedStatement statement = this.connection.prepareStatement(SQL_UPDATE, PreparedStatement.RETURN_GENERATED_KEYS)) {
            if(model == null || model.getId() == null) {
                throw new DaoException("Model or model's id is not set.");
            }
            statement.setString(1, model.getName());
            statement.setLong(2, model.getId());
            int count = statement.executeUpdate();
            if(count != 1) {
                throw new DaoException("On update modify invalid number of records: " + count);
            }
        } catch (SQLException e) {
            throw new DaoException("SQLException: Statement cannot be created. "  + e.getMessage());
        }
    }

    /**
     * Deletes single row from the model table by its pk.
     *
     * @param model instance, which must be deleted from DB.
     * @throws DaoException if incorrect number of records modified.
     */
    @Override
    public void delete(Category model) throws DaoException {
        try (PreparedStatement statement = this.connection.prepareStatement(SQL_DELETE)) {
            if(model == null || model.getId() == null) {
                throw new DaoException("Model or model's id is not set.");
            }
            statement.setLong(1, model.getId());
            int count = statement.executeUpdate();
            if(count != 1) {
                throw new DaoException("On delete modify invalid number of records: " + count);
            }
        } catch (SQLException e) {
            throw new DaoException("SQLException: Statement cannot be created. "  + e.getMessage());
        }
    }

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
    protected List<Category> parseResultSet(ResultSet resultSet) throws DaoException {
        List<Category> list = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Category category = new Category();
                category.setId(resultSet.getLong(ID));
                category.setName(resultSet.getString(NAME));
                list.add(category);
            }
        } catch (SQLException e) {
            throw new DaoException("SQLException: Error occurred while parsing SQL result. "  + e.getMessage());
        }
        return list;
    }
}
