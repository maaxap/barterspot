package com.aparovich.barterspot.dao.impl;

import com.aparovich.barterspot.dao.AbstractLotDao;
import com.aparovich.barterspot.dao.exception.DaoException;
import com.aparovich.barterspot.model.bean.Category;
import com.aparovich.barterspot.model.bean.Lot;
import com.aparovich.barterspot.model.bean.User;
import com.aparovich.barterspot.model.util.ModelType;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maxim on 09.04.2017
 */
public class LotDao extends AbstractLotDao {

    /**
     * Selects all rows from the model table.
     *
     * @return List<T> of all models.
     * @throws DaoException if occurred errors in SQL request.
     *                      Examples of errors: no items selected, duplicated elements
     *                      in rows with UNIQUE index and etc.
     */
    @Override
    public List<Lot> findAll() throws DaoException {
        List<Lot> list = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL);
             ResultSet resultSet = statement.executeQuery()) {

                list = parseResultSet(resultSet);

        } catch (SQLException e) {
            throw new DaoException("SQLException: Statement cannot be created." + e.getMessage());
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
    public Lot findByPk(Long pk) throws DaoException {
        Lot lot = null;
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_ID)) {
            statement.setLong(1, pk);
            List<Lot> list = null;
            try (ResultSet resultSet = statement.executeQuery()) {
                list = parseResultSet(resultSet);
            }
            if (list == null || list.isEmpty()) {
                lot = null;
            } else if (list.size() > 1) {
                throw new DaoException("Received more than one record.");
            } else {
                lot = list.iterator().next();
            }
        } catch (SQLException e) {
            throw new DaoException("SQLException: Statement cannot be created. " + e.getMessage());
        }
        return lot;
    }

    /**
     * Selects single row from the model table by its pk.
     *
     * @param user@return T instance of selected row.
     * @throws DaoException is more than one record received.
     */
    @Override
    public List<Lot> findReadyByUser(User user) throws DaoException {
        List<Lot> list = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_READY_BY_USER)) {
            statement.setLong(1, user.getId());
            try (ResultSet resultSet = statement.executeQuery()) {
                list = parseResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException("SQLException: Statement cannot be created. " + e.getMessage());
        }
        return list;
    }

    /**
     * Selects single row from the model table by its pk.
     *
     * @param user@return T instance of selected row.
     * @throws DaoException is more than one record received.
     */
    @Override
    public List<Lot> findSellingOutByUser(User user) throws DaoException {
        List<Lot> list = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_SELLING_OUT_BY_USER)) {
            statement.setLong(1, user.getId());
            try (ResultSet resultSet = statement.executeQuery()) {
                list = parseResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException("SQLException: Statement cannot be created. " + e.getMessage());
        }
        return list;
    }

    /**
     * Selects single row from the model table by its pk.
     *
     * @param user@return T instance of selected row.
     * @throws DaoException is more than one record received.
     */
    @Override
    public List<Lot> findPurchasedByUser(User user) throws DaoException {
        List<Lot> list = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_PURCHASED_BY_USER)) {
            statement.setLong(1, user.getId());
            try (ResultSet resultSet = statement.executeQuery()) {
                list = parseResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException("SQLException: Statement cannot be created. " + e.getMessage());
        }
        return list;
    }

    /**
     * Selects single row from the model table by its pk.
     *
     * @param user@return T instance of selected row.
     * @throws DaoException is more than one record received.
     */
    @Override
    public List<Lot> findFinishedByUser(User user) throws DaoException {
        List<Lot> list = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_FINISHED_BY_USER)) {
            statement.setLong(1, user.getId());
            try (ResultSet resultSet = statement.executeQuery()) {
                list = parseResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException("SQLException: Statement cannot be created. " + e.getMessage());
        }
        return list;
    }

    /**
     * Selects single row from the model table by its pk.
     *
     * @param category
     * @return T instance of selected row.
     * @throws DaoException is more than one record received.
     */
    @Override
    public List<Lot> findByCategory(Category category) throws DaoException {
        List<Lot> list = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_CATEGORY)) {
            statement.setLong(1, category.getId());
            try (ResultSet resultSet = statement.executeQuery()) {
                list = parseResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException("SQLException: Statement cannot be created. " + e.getMessage());
        }
        return list;
    }

    /**
     * Inserts single row in the model table according to {@param model}.
     *
     * @param model instance, which must be put in DB.
     * @throws DaoException if record with same pk exists.
     */
    @Override
    public Lot create(Lot model) throws DaoException {
        Lot lot = null;
        try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            if(model.getCategory() == null || model.getCategory().getId() == null) {
                throw new DaoException("Model's category dependence is not set.");
            }
            if(model.getUser() == null || model.getUser().getId() == null) {
                throw new DaoException("Model's user dependence is not set.");
            }
            statement.setString(1, model.getName());
            statement.setString(2, model.getDescription());
            statement.setBigDecimal(3, model.getDefaultPrice());
            statement.setString(4, toDateTimeString(model.getFinishing()));
            statement.setLong(5, model.getCategory().getId());
            statement.setLong(6, model.getUser().getId());
            int count = statement.executeUpdate();
            if(count != 1) {
                throw new DaoException("On create modify invalid number of records: " + count);
            }
            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    lot = findByPk(keys.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new DaoException("SQLException: Statement cannot be created." + e.getMessage());
        }
        return lot;
    }

    /**
     * Updates single row in the model table according to {@param model}.
     *
     * @param model instance, which must be updated in DB.
     * @throws DaoException if incorrect number of records modified.
     */
    @Override
    public void update(Lot model) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE)) {
            if(model == null || model.getId() == null) {
                throw new DaoException("Model or model's id is not set.");
            }
            if(model.getCategory() == null || model.getCategory().getId() == null) {
                throw new DaoException("Model's category dependence is not set.");
            }
            if(model.getUser() == null || model.getUser().getId() == null) {
                throw new DaoException("Model's user dependence is not set.");
            }
            statement.setString(1, model.getName());
            statement.setString(2, model.getDescription());
            statement.setBigDecimal(3, model.getDefaultPrice());
            if(model.getFinalPrice() != null) {
                statement.setBigDecimal(4, model.getFinalPrice());
            } else {
                statement.setNull(4, Types.DECIMAL);
            }
            statement.setString(5, toDateTimeString(model.getFinishing()));
            statement.setString(6, toDateTimeString(model.getCreatedAt()));
            if(model.getDeletedAt() != null) {
                statement.setString(7, toDateTimeString(model.getDeletedAt()));
            } else {
                statement.setNull(7, Types.DATE);
            }
            statement.setLong(8, model.getCategory().getId());
            statement.setLong(9, model.getUser().getId());
            statement.setLong(10, model.getId());
            int count = statement.executeUpdate();
            if(count != 1) {
                throw new DaoException("On update modify invalid number of records: " + count);
            }
        } catch (SQLException e) {
            throw new DaoException("SQLException: Statement cannot be created." + e.getMessage());
        }
    }

    /**
     * Deletes single row from the model table by its pk.
     *
     * @param model instance, which must be deleted from DB.
     * @throws DaoException if incorrect number of records modified.
     */
    @Override
    public void delete(Lot model) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE)) {
            if(model == null || model.getId() == null) {
                throw new DaoException("Model or model's id is not set.");
            }
            if(model.getCategory() == null || model.getCategory().getId() == null) {
                throw new DaoException("Model's category dependence is not set.");
            }
            if(model.getUser() == null || model.getUser().getId() == null) {
                throw new DaoException("Model's user dependence is not set.");
            }
            statement.setLong(1, model.getId());
            int count = statement.executeUpdate();
            if(count != 1) {
                throw new DaoException("On delete modify invalid number of records: " + count);
            }
        } catch (SQLException e) {
            throw new DaoException("SQLException: Statement cannot be created. " + e.getMessage());
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
    protected List<Lot> parseResultSet(ResultSet resultSet) throws DaoException {
        List<Lot> list = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Lot lot = new Lot();
                Timestamp finishing = resultSet.getTimestamp(FINISHING);
                Timestamp deletedAt = resultSet.getTimestamp(DELETED_AT);
                BigDecimal finalPrice = resultSet.getBigDecimal(FINAL_PRICE);
                lot.setId(resultSet.getLong(ID));
                lot.setName(resultSet.getString(NAME));
                lot.setDescription(resultSet.getString(DESCRIPTION));
                lot.setDefaultPrice(resultSet.getBigDecimal(DEFAULT_PRICE));
                if(finalPrice != null) {
                    lot.setFinalPrice(finalPrice);
                } else {
                    lot.setFinalPrice(null);
                }
                lot.setFinishing(finishing.toLocalDateTime());
                lot.setCreatedAt(resultSet.getTimestamp(CREATED_AT).toLocalDateTime());
                if(deletedAt != null) {
                    lot.setDeletedAt(deletedAt.toLocalDateTime());
                }  else {
                    lot.setDeletedAt(null);
                }
                lot.setCategory((Category) getDependence(ModelType.CATEGORY, resultSet.getLong(CATEGORIES_ID)));
                lot.setUser((User) getDependence(ModelType.USER, resultSet.getLong(USERS_ID)));

                if(lot.getCategory() == null || lot.getUser() == null) {
                    throw new DaoException("Category or user models relative to currents lot model have not been found.");
                }
                list.add(lot);
            }
        } catch (SQLException e) {
            throw new DaoException("SQLException: Error occurred while parsing SQL result. " + e.getMessage());
        }
        return list;
    }

    /**
     * Transform LocalDateTime object to string according to "yyyy-MM-dd hh:mm:ss" format.
     * @param date LocalDateTime object
     * @return formatted string.
     */
    @Override
    protected String toDateTimeString(LocalDateTime date) throws DaoException{
        if(date == null) {
            throw new DaoException("Setting date cannot be null.");
        }
        return date.format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
    }
}
