package com.aparovich.barterspot.dao.impl;

import com.aparovich.barterspot.dao.exception.DaoException;
import com.aparovich.barterspot.dao.AbstractBidDao;
import com.aparovich.barterspot.model.bean.Bid;
import com.aparovich.barterspot.model.bean.Lot;
import com.aparovich.barterspot.model.bean.User;
import com.aparovich.barterspot.model.util.ModelType;

import java.sql.Timestamp;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maxim on 15.04.2017
 */
public class BidDao extends AbstractBidDao {

    /**
     * Selects all rows from the model table.
     *
     * @return List<T> of all models.
     * @throws DaoException if occurred errors in SQL request.
     *                      Examples of errors: no items selected, duplicated elements
     *                      in rows with UNIQUE index and etc.
     */
    @Override
    public List<Bid> findAll() throws DaoException {
        List<Bid> list = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL);
             ResultSet resultSet = statement.executeQuery()) {

            list = parseResultSet(resultSet);

        } catch (SQLException e) {
            throw new DaoException("SQLException: Statement can not be created. " + e.getMessage());
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
    public Bid findByPk(Long pk) throws DaoException {
        Bid bid = null;
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_ID)) {
            statement.setLong(1, pk);
            List<Bid> list = null;
            try (ResultSet resultSet = statement.executeQuery()) {
                list = parseResultSet(resultSet);
            }
            if (list == null || list.isEmpty()) {
                bid = null;
            } else if (list.size() > 1) {
                throw new DaoException("Received more than one record.");
            } else {
                bid = list.iterator().next();
            }
        } catch (SQLException e) {
            throw new DaoException("SQLException: Statement cannot be created. " + e.getMessage());
        }
        return bid;
    }

    /**
     * Selects rows from the model table by lots_id.
     *
     * @param lot
     * @return
     * @throws DaoException
     */
    @Override
    public List<Bid> findByLot(Lot lot) throws DaoException {
        List<Bid> list = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_LOTS_ID)) {
            statement.setLong(1, lot.getId());
            try (ResultSet resultSet = statement.executeQuery()) {
                list = parseResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException("SQLException: Statement cannot be created. " + e.getMessage());
        }
        return list;
    }

    /**
     * Selects rows from the model table by users_id.
     *
     * @param user
     * @return
     * @throws DaoException
     */
    @Override
    public List<Bid> findByUser(User user) throws DaoException {
        List<Bid> list = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_USERS_ID)) {
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
     * Inserts single row in the model table according to {@param model}.
     *
     * @param model instance, which must be put in DB.
     * @throws DaoException if record with same pk exists.
     */
    @Override
    public Bid create(Bid model) throws DaoException {
        Bid bid = null;
        try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT, PreparedStatement.RETURN_GENERATED_KEYS)) {
            if(model.getUser() == null || model.getUser().getId() == null) {
                throw new DaoException("Bid model's user dependence is not set.");
            }
            if(model.getLot() == null || model.getLot().getId() == null) {
                throw new DaoException("Bid model's lot dependence is not set.");
            }
            statement.setBigDecimal(1, model.getBid());
            statement.setLong(2, model.getUser().getId());
            statement.setLong(3, model.getLot().getId());
            int count = statement.executeUpdate();
            if(count != 1) {
                throw new DaoException("On create modify invalid number of records: " + count);
            }
            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    bid = findByPk(keys.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new DaoException("SQLException: Statement cannot be created. " + e.getMessage());
        }
        return bid;
    }

    /**
     * Updates single row in the model table according to {@param model}.
     *
     * @param model instance, which must be updated in DB.
     * @throws DaoException if incorrect number of records modified.
     */
    @Override
    public void update(Bid model) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE)) {
            if(model == null || model.getId() == null) {
                throw new DaoException("Model or model's id is not set.");
            }
            if(model.getUser() == null || model.getUser().getId() == null) {
                throw new DaoException("Model's user dependence is not set.");
            }
            if(model.getLot() == null || model.getLot().getId() == null) {
                throw new DaoException("Model's lot dependence is not set.");
            }
            statement.setBigDecimal(1, model.getBid());
            statement.setString(2, toDateTimeString(model.getCreatedAt()));
            if(model.getDeletedAt() != null) {
                statement.setString(3, toDateTimeString(model.getDeletedAt()));
            } else {
                statement.setNull(3, Types.DATE);
            }
            statement.setLong(4, model.getUser().getId());
            statement.setLong(5, model.getLot().getId());
            statement.setLong(6, model.getId());
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
    public void delete(Bid model) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE)) {
            if(model == null || model.getId() == null) {
                throw new DaoException("Model or model's id is not set.");
            }
            if(model.getUser() == null || model.getUser().getId() == null) {
                throw new DaoException("Model's user dependence is not set.");
            }
            if(model.getLot() == null || model.getLot().getId() == null) {
                throw new DaoException("Model's lot dependence is not set.");
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
    protected List<Bid> parseResultSet(ResultSet resultSet) throws DaoException {
        List<Bid> list = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Bid bid = new Bid();
                Timestamp deletedAt = resultSet.getTimestamp(DELETED_AT);

                bid.setId(resultSet.getLong(ID));
                bid.setBid(resultSet.getBigDecimal(BID));
                bid.setCreatedAt(resultSet.getTimestamp(CREATED_AT).toLocalDateTime());
                if (deletedAt != null) {
                    bid.setDeletedAt(deletedAt.toLocalDateTime());
                } else {
                    bid.setDeletedAt(null);
                }
                bid.setUser((User) getDependence(ModelType.USER, resultSet.getLong(USERS_ID)));
                bid.setLot((Lot) getDependence(ModelType.LOT, resultSet.getLong(LOTS_ID)));
                if (bid.getUser() == null || bid.getLot() == null) {
                    throw new DaoException("User or lot models relative to currents bid model have not been found.");
                }
                list.add(bid);
            }
        } catch (SQLException e) {
            throw new DaoException("SQLException: Error occurred while parsing SQL result. " + e.getMessage());
        }
        return list;
    }

    /**
     * Transform LocalDateTime object to string according to "yyyy-MM-dd hh:mm:ss" format.
     *
     * @param date LocalDateTime object.
     * @return formatted string.
     */
    @Override
    protected String toDateTimeString(LocalDateTime date) throws DaoException {
        if(date == null) {
            throw new DaoException("Setting date cannot be null.");
        }
        return date.format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
    }
}
