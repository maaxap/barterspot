package com.aparovich.barterspot.dao.impl;

import com.aparovich.barterspot.dao.AbstractUserDao;
import com.aparovich.barterspot.dao.exception.DaoException;
import com.aparovich.barterspot.model.bean.Info;
import com.aparovich.barterspot.model.bean.Settings;
import com.aparovich.barterspot.model.bean.User;
import com.aparovich.barterspot.model.util.ModelType;
import com.aparovich.barterspot.model.util.RoleType;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maxim on 24.03.2017
 * Dao layer class for User model.
 */
public class UserDao extends AbstractUserDao {

    /**
     * Selects all rows from the model table.
     *
     * @return List<T> of all models.
     * @throws DaoException if occurred errors in SQL request.
     *                      Examples of errors: no items selected, duplicated elements
     *                      in rows with UNIQUE index and etc.
     */
    @Override
    public List<User> findAll() throws DaoException {
        List<User> list = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL);
             ResultSet resultSet = statement.executeQuery()) {

            list = parseResultSet(resultSet);

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
    public User findByPk(Long pk) throws DaoException {
        User user = null;
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_ID)) {
            statement.setLong(1, pk);
            List<User> list = null;
            try (ResultSet resultSet = statement.executeQuery()) {
                list = parseResultSet(resultSet);
            }
            if (list == null || list.isEmpty()) {
                user = null;
            } else if (list.size() > 1) {
                throw new DaoException("Received more than one record.");
            } else {
                user = list.iterator().next();
            }
        } catch (SQLException e) {
            throw new DaoException("SQLException: Statement cannot be created. " + e.getMessage());
        }
        return user;
    }

    /**
     * Selects single row from the model table by its email.
     *
     * @param email of the model.
     * @return T instance of selected row.
     * @throws DaoException is more than one record received.
     */
    @Override
    public User findByEmail(String email) throws DaoException {
        User user = null;
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_EMAIL)) {
            statement.setString(1, email);
            List<User> list = null;
            try (ResultSet resultSet = statement.executeQuery()) {
                list = parseResultSet(resultSet);
            }
            if (list == null || list.isEmpty()) {
                user = null;
            } else if (list.size() > 1) {
                throw new DaoException("Received more than one record.");
            } else {
                user = list.iterator().next();
            }
        } catch (SQLException e) {
            throw new DaoException("SQLException: Statement cannot be created. " + e.getMessage());
        }
        return user;
    }

    @Override
    public List<User> findByRole(RoleType role) throws DaoException {
        List<User> list;
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_ROLE)) {
            statement.setString(1, role.getRole());
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
    public User create(User model) throws DaoException {
        User user = null;
        try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT, PreparedStatement.RETURN_GENERATED_KEYS)) {
            if(model.getInfo() == null || model.getInfo().getId() == null) {
                throw new DaoException("Model's info dependence is not set.");
            }
            if(model.getSettings() == null || model.getSettings().getId() == null) {
                throw new DaoException("Model's settings dependence is not set.");
            }
            statement.setString(1, model.getEmail());
            statement.setString(2, model.getPassword());
            statement.setLong(3, model.getInfo().getId());
            statement.setLong(4, model.getSettings().getId());
            int count = statement.executeUpdate();
            if(count != 1) {
                throw new DaoException("On create modify invalid number of records: " + count);
            }
            try (ResultSet keys = statement.getGeneratedKeys();) {
                if (keys.next()) {
                    user = findByPk(keys.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new DaoException("SQLException: Statement cannot be created. " + e.getMessage());
        }
        return user;
    }

    /**
     * Updates single row in the model table according to {@param model}.
     *
     * @param model instance, which must be updated in DB.
     * @throws DaoException if incorrect number of records modified.
     */
    @Override
    public void update(User model) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE)) {
            if(model == null || model.getId() == null) {
                throw new DaoException("Model or model's id is not set.");
            }
            if(model.getInfo() == null || model.getInfo().getId() == null) {
                throw new DaoException("Model's info dependence is not set.");
            }
            if(model.getSettings() == null || model.getSettings().getId() == null) {
                throw new DaoException("Model's settings dependence is not set.");
            }
            statement.setString(1, model.getEmail());
            statement.setString(2, model.getPassword());
            statement.setString(3, model.getRole().getRole());
            statement.setBoolean(4, model.getBlocked());
            statement.setString(5, toDateTimeString(model.getCreatedAt()));
            if(model.getDeletedAt() != null) {
                statement.setString(6, toDateTimeString(model.getDeletedAt()));
            } else {
                statement.setNull(6, Types.DATE);
            }
            statement.setLong(7, model.getInfo().getId());
            statement.setLong(8, model.getSettings().getId());
            statement.setLong(9, model.getId());
            int count = statement.executeUpdate();
            if(count != 1) {
                throw new DaoException("On update modify invalid number of records: " + count);
            }
        } catch (SQLException e) {
            throw new DaoException("SQLException: Statement cannot be created. " + e.getMessage());
        }
    }

    /**
     * Deletes single row from the model table by its pk.
     *
     * @param model instance, which must be deleted from DB.
     * @throws DaoException if incorrect number of records modified.
     */
    @Override
    public void delete(User model) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE)) {
            if(model == null || model.getId() == null) {
                throw new DaoException("Model or model's id is not set.");
            }
            if(model.getInfo() == null || model.getInfo().getId() == null) {
                throw new DaoException("Model's info dependence is not set.");
            }
            if(model.getSettings() == null || model.getSettings().getId() == null) {
                throw new DaoException("Model's settings dependence is not set.");
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
    protected List<User> parseResultSet(ResultSet resultSet) throws DaoException {
        ArrayList<User> list = new ArrayList<>();
        try {
            while (resultSet.next()) {
                User user = new User();
                Timestamp deletedAt = resultSet.getTimestamp(DELETED_AT);
                user.setId(resultSet.getLong(ID));
                user.setEmail(resultSet.getString(EMAIL));
                user.setPassword(resultSet.getString(PASSWORD));
                user.setRole(RoleType.valueOf(resultSet.getString(ROLE).toUpperCase()));
                user.setBlocked(resultSet.getBoolean(IS_BLOCKED));
                user.setCreatedAt(resultSet.getTimestamp(CREATED_AT).toLocalDateTime());
                if(deletedAt != null) {
                    user.setDeletedAt(resultSet.getTimestamp(DELETED_AT).toLocalDateTime());
                } else {
                    user.setDeletedAt(null);
                }
                user.setInfo((Info) getDependence(ModelType.INFO, resultSet.getLong(INFO_ID)));
                user.setSettings((Settings) getDependence(ModelType.SETTINGS, resultSet.getLong(SETTINGS_ID)));

                if(user.getInfo() == null || user.getSettings() == null) {
                    throw new DaoException("Info or settings models relative to currents user model have not been found.");
                }
                list.add(user);
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