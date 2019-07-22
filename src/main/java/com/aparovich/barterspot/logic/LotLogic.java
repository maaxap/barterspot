package com.aparovich.barterspot.logic;

import com.aparovich.barterspot.dao.TransactionHelper;
import com.aparovich.barterspot.dao.exception.DaoException;
import com.aparovich.barterspot.dao.factory.DaoFactory;
import com.aparovich.barterspot.dao.impl.BidDao;
import com.aparovich.barterspot.dao.impl.LotDao;
import com.aparovich.barterspot.logic.exception.LogicException;
import com.aparovich.barterspot.model.util.LotStateType;
import com.aparovich.barterspot.model.bean.Bid;
import com.aparovich.barterspot.model.bean.Category;
import com.aparovich.barterspot.model.bean.Lot;
import com.aparovich.barterspot.model.util.ModelType;
import com.aparovich.barterspot.model.bean.User;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.aparovich.barterspot.validator.ParametersValidator.*;
import static com.aparovich.barterspot.validator.ModelFieldValidator.*;

/**
 * Created by Maxim on 12.04.2017
 */
public class LotLogic {
    private static final Logger LOGGER = LogManager.getLogger(LotLogic.class);

    /**
     * Maximum length for the name string.
     */
    private static final int MAX_NAME_LENGTH = 251;

    public static Lot findById(Long id) {
        Lot lot = null;
        LotDao lotDao = null;
        TransactionHelper helper = new TransactionHelper();
        try {
            lotDao = (LotDao) DaoFactory.getDao(ModelType.LOT);
            helper.prepareTransaction(lotDao);
            lot = lotDao.findByPk(id);
            helper.commit();
        } catch (DaoException e) {
            helper.rollback();
            LOGGER.log(Level.ERROR, e.getMessage());
        } finally {
            helper.invalidateTransaction();
        }
        return lot;
    }

    public static List<Lot> findAll() {
        LotDao lotDao = null;
        List<Lot> lots = new ArrayList<>();
        TransactionHelper helper = new TransactionHelper();
        try {
            lotDao = (LotDao) DaoFactory.getDao(ModelType.LOT);
            helper.prepareTransaction(lotDao);
            lots = lotDao.findAll();
            helper.commit();
        } catch (DaoException e) {
            helper.rollback();
            LOGGER.log(Level.ERROR, e.getMessage());
        } finally {
            helper.invalidateTransaction();
        }
        return lots;
    }

    public static List<Lot> findByUser(User user, LotStateType state) {
        LotDao lotDao = null;
        List<Lot> lots = new ArrayList<>();
        TransactionHelper helper = new TransactionHelper();
        try {
            lotDao = (LotDao) DaoFactory.getDao(ModelType.LOT);
            helper.prepareTransaction(lotDao);
            switch (state) {
                case READY:
                    lots = lotDao.findReadyByUser(user);
                    break;
                case SELLING_OUT:
                    lots = lotDao.findSellingOutByUser(user);
                    break;
                case PURCHASED:
                    lots = lotDao.findPurchasedByUser(user);
                    break;
                case FINISHED:
                    lots = lotDao.findFinishedByUser(user);
                    break;
                default:
                    break;
            }
            helper.commit();
        } catch (DaoException e) {
            helper.rollback();
            LOGGER.log(Level.ERROR, e.getMessage());
        } finally {
            helper.invalidateTransaction();
        }
        return lots;
    }

    public static List<Lot> findByCategory(Category category) {
        LotDao lotDao = null;
        List<Lot> lots = new ArrayList<>();
        TransactionHelper helper = new TransactionHelper();
        try {
            lotDao = (LotDao) DaoFactory.getDao(ModelType.LOT);
            helper.prepareTransaction(lotDao);
            lots = lotDao.findByCategory(category);
            helper.commit();
        } catch (DaoException e) {
            helper.rollback();
            LOGGER.log(Level.ERROR, e.getMessage());
        } finally {
            helper.invalidateTransaction();
        }
        return lots;
    }

    public static Lot create(Lot lot) {
        LotDao lotDao = null;
        TransactionHelper helper = new TransactionHelper();
        try {
            lotDao = (LotDao) DaoFactory.getDao(ModelType.LOT);
            helper.prepareTransaction(lotDao);
            lot = lotDao.create(lot);
            if(lot == null) {
                helper.rollback();
            }
            helper.commit();
        } catch (DaoException e) {
            helper.rollback();
            lot = null;
            LOGGER.log(Level.ERROR, e.getMessage());
        } finally {
            helper.invalidateTransaction();
        }
        return lot;
    }

    public static void update(Lot lot) {
        LotDao lotDao = null;
        TransactionHelper helper = new TransactionHelper();
        try {
            lotDao = (LotDao) DaoFactory.getDao(ModelType.LOT);
            helper.prepareTransaction(lotDao);
            lotDao.update(lot);
            helper.commit();
        } catch (DaoException e) {
            helper.rollback();
            LOGGER.log(Level.ERROR, e.getMessage());
        } finally {
            helper.invalidateTransaction();
        }
    }

    public static void delete(Lot lot) throws LogicException {
        LotDao lotDao = null;
        BidDao bidDao = null;
        TransactionHelper helper = new TransactionHelper();
        try {
            lotDao = (LotDao) DaoFactory.getDao(ModelType.LOT);
            bidDao = (BidDao) DaoFactory.getDao(ModelType.BID);
            helper.prepareTransaction(lotDao, bidDao);
            List<Bid> bids = bidDao.findByLot(lot);
            for (Bid bid : bids) {
                bid.setDeletedAt(LocalDateTime.now());
                bidDao.update(bid);
                bid = bidDao.findByPk(bid.getId());
                if(bid != null) {
                    helper.rollback();
                    throw new LogicException("Lot bids was not deleted.");
                }
            }
            lot.setDeletedAt(LocalDateTime.now());
            lotDao.update(lot);
            lot = lotDao.findByPk(lot.getId());
            if(lot != null) {
                helper.rollback();
                throw new LogicException("Lot was not deleted.");
            }
            helper.commit();
        } catch (DaoException e) {
            helper.rollback();
            LOGGER.log(Level.ERROR, e.getMessage());
        } finally {
            helper.invalidateTransaction();
        }
    }

    public static boolean validate(Lot lot) throws LogicException {
        return  lot != null &&
                lot.getCategory() != null &&
                lot.getUser() != null &&
                isDefaultPrice(lot.getDefaultPrice()) &&
                isFinishing(lot.getFinishing());
    }
}
