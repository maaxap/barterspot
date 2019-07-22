package com.aparovich.barterspot.dao.impl;

import com.aparovich.barterspot.dao.AbstractInfoDao;
import com.aparovich.barterspot.dao.exception.DaoException;
import com.aparovich.barterspot.model.bean.Bid;
import com.aparovich.barterspot.model.bean.Info;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maxim on 09.04.2017
 */
public class InfoDao extends AbstractInfoDao {

    /**
     * Selects all rows from the model table.
     *
     * @return List<T> of all models.
     * @throws DaoException if occurred errors in SQL request.
     *                      Examples of errors: no items selected, duplicated elements
     *                      in rows with UNIQUE index and etc.
     */
    @Override
    public List<Info> findAll() throws DaoException {
        List<Info> list = new ArrayList<>();
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
    public Info findByPk(Long pk) throws DaoException {
        Info info = null;
        try (PreparedStatement statement = this.connection.prepareStatement(SQL_SELECT_BY_ID)) {
            statement.setLong(1, pk);
            List<Info> list = null;
            try (ResultSet resultSet = statement.executeQuery()) {
                list = parseResultSet(resultSet);
            }
            if (list == null || list.isEmpty()) {
                info = null;
            } else if (list.size() > 1) {
                throw new DaoException("Received more than one record.");
            } else {
                info = list.iterator().next();
            }
        } catch (SQLException e) {
            throw new DaoException("SQLException: Statement cannot be created. " + e.getMessage());
        }
        return info;
    }

    /**
     * Inserts single row in the model table according to {@param model}.
     *
     * @param model instance, which must be put in DB.
     * @throws DaoException if record with same pk exists.
     */
    @Override
    public Info create(Info model) throws DaoException {
        Info info = null;
        try (PreparedStatement statement = this.connection.prepareStatement(SQL_INSERT, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, model.getName());
            statement.setString(2, model.getSurname());
            statement.setString(3, this.toDateString(model.getBirthDate()));
            statement.setString(4, model.getPhoneNumber());
            statement.setString(5, model.getAddress());
            statement.setString(6, model.getPostCode());
            int count = statement.executeUpdate();
            if(count != 1) {
                throw new DaoException("On create modify invalid number of records: " + count);
            }
            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    info = findByPk(keys.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new DaoException("SQLException: Statement cannot be created. " + e.getMessage());
        }
        return info;
    }

    /**
     * Updates single row in the model table according to {@param model}.
     *
     * @param model instance, which must be updated in DB.
     * @throws DaoException if incorrect number of records modified.
     */
    @Override
    public void update(Info model) throws DaoException {
        try (PreparedStatement statement = this.connection.prepareStatement(SQL_UPDATE)) {
            if(model == null || model.getId() == null) {
                throw new DaoException("Model or model's id is not set.");
            }
            statement.setString(1, model.getName());
            statement.setString(2, model.getSurname());
            statement.setString(3, this.toDateString(model.getBirthDate()));
            statement.setString(4, model.getPhoneNumber());
            statement.setString(5, model.getAddress());
            statement.setString(6, model.getPostCode());
            statement.setLong(7, model.getId());
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
    public void delete(Info model) throws DaoException {
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
    protected List<Info> parseResultSet(ResultSet resultSet) throws DaoException {
        List<Info> list = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Info info = new Info();
                info.setId(resultSet.getLong(ID));
                info.setName(resultSet.getString(NAME));
                info.setSurname(resultSet.getString(SURNAME));
                info.setBirthDate(resultSet.getTimestamp(BIRTH_DATE).toLocalDateTime().toLocalDate());
                info.setPhoneNumber(resultSet.getString(PHONE_NUMBER));
                info.setAddress(resultSet.getString(ADDRESS));
                info.setPostCode(resultSet.getString(POST_CODE));
                list.add(info);
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
    protected String toDateString(LocalDate date) throws DaoException{
        if(date == null) {
            throw new DaoException("Setting date cannot be null.");
        }
        return date.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }
}

