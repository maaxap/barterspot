package com.aparovich.barterspot.logic;

import com.aparovich.barterspot.dao.TransactionHelper;
import com.aparovich.barterspot.dao.exception.DaoException;
import com.aparovich.barterspot.dao.factory.DaoFactory;
import com.aparovich.barterspot.dao.impl.BidDao;
import com.aparovich.barterspot.logic.exception.LogicException;
import com.aparovich.barterspot.model.bean.Bid;
import com.aparovich.barterspot.model.bean.Lot;
import com.aparovich.barterspot.model.bean.User;
import com.aparovich.barterspot.model.util.ModelType;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maxim on 17.04.2017
 */
public class BidLogic {
    private static final Logger LOGGER = LogManager.getLogger(BidLogic.class);

    public static Bid findById(Long id) {
        Bid bid = null;
        BidDao bidDao = null;
        TransactionHelper helper = new TransactionHelper();
        try {
            bidDao = (BidDao) DaoFactory.getDao(ModelType.BID);
            helper.prepareTransaction(bidDao);
            bid = bidDao.findByPk(id);
            helper.commit();
        } catch (DaoException e) {
            helper.rollback();
            LOGGER.log(Level.ERROR, e.getMessage());
        } finally {
            helper.invalidateTransaction();
        }
        return bid;
    }

    public static List<Bid> findAll() {
        BidDao bidDao = null;
        List<Bid> bids = new ArrayList<>();
        TransactionHelper helper = new TransactionHelper();
        try {
            bidDao = (BidDao) DaoFactory.getDao(ModelType.BID);
            helper.prepareTransaction(bidDao);
            bids = bidDao.findAll();
            helper.commit();
        } catch (DaoException e) {
            helper.rollback();
            LOGGER.log(Level.ERROR, e.getMessage());
        } finally {
            helper.invalidateTransaction();
        }
        return bids;
    }

    public static List<Bid> findByUser(User user) {
        BidDao bidDao = null;
        List<Bid> bids = new ArrayList<>();
        TransactionHelper helper = new TransactionHelper();
        try {
            bidDao = (BidDao) DaoFactory.getDao(ModelType.BID);
            helper.prepareTransaction(bidDao);
            bids = bidDao.findByUser(user);
            helper.commit();
        } catch (DaoException e) {
            helper.rollback();
            LOGGER.log(Level.ERROR, e.getMessage());
        } finally {
            helper.invalidateTransaction();
        }
        return bids;
    }

    public static List<Bid> findByLot(Lot lot) {
        BidDao bidDao = null;
        List<Bid> bids = new ArrayList<>();
        TransactionHelper helper = new TransactionHelper();
        try {
            bidDao = (BidDao) DaoFactory.getDao(ModelType.BID);
            helper.prepareTransaction(bidDao);
            bids = bidDao.findByLot(lot);
            helper.commit();
        } catch (DaoException e) {
            helper.rollback();
            LOGGER.log(Level.ERROR, e.getMessage());
        } finally {
            helper.invalidateTransaction();
        }
        return bids;
    }

    public static Bid create(Bid bid) {
        List<Bid> bids;
        BidDao bidDao;
        TransactionHelper helper = new TransactionHelper();
        try {
            bidDao = (BidDao) DaoFactory.getDao(ModelType.BID);
            helper.prepareTransaction(bidDao);
            bids = bidDao.findByLot(bid.getLot());
            if(bids.isEmpty() || bids.iterator().next().getBid().compareTo(bid.getBid()) < 0) {
                bid = bidDao.create(bid);
            } else {
                helper.rollback();
                bid = null;
            }
            helper.commit();
        } catch (DaoException e) {
            helper.rollback();
            bid = null;
            LOGGER.log(Level.ERROR, e.getMessage());
        } finally {
            helper.invalidateTransaction();
        }
        return bid;
    }

    public static void update(Bid bid) {
        BidDao bidDao = null;
        TransactionHelper helper = new TransactionHelper();
        try {
            bidDao = (BidDao) DaoFactory.getDao(ModelType.BID);
            helper.prepareTransaction(bidDao);
            bidDao.update(bid);
            helper.commit();
        } catch (DaoException e) {
            helper.rollback();
            LOGGER.log(Level.ERROR, e.getMessage());
        } finally {
            helper.invalidateTransaction();
        }
    }

    public static void delete(Bid bid) throws LogicException {
        BidDao bidDao = null;
        TransactionHelper helper = new TransactionHelper();
        try {
            bidDao = (BidDao) DaoFactory.getDao(ModelType.BID);
            helper.prepareTransaction(bidDao);
            bid.setDeletedAt(LocalDateTime.now());
            bidDao.update(bid);
            bid = bidDao.findByPk(bid.getId());
            if(bid != null) {
                throw new LogicException("Bid was not deleted.");
            }
            helper.commit();
        } catch (DaoException e) {
            helper.rollback();
            LOGGER.log(Level.ERROR, e.getMessage());
        } finally {
            helper.invalidateTransaction();
        }
    }

    public static void deleteAll(List<Bid> bids) throws LogicException {
        BidDao bidDao = null;
        TransactionHelper helper = new TransactionHelper();
        try {
            bidDao = (BidDao) DaoFactory.getDao(ModelType.BID);
            helper.prepareTransaction(bidDao);
            for (Bid bid : bids) {
                bid.setDeletedAt(LocalDateTime.now());
                bidDao.update(bid);
                bid = bidDao.findByPk(bid.getId());
                if(bid != null) {
                    throw new LogicException("Bids was not deleted.");
                }
            }
            helper.commit();
        } catch (DaoException e) {
            helper.rollback();
            LOGGER.log(Level.ERROR, e.getMessage());
        } finally {
            helper.invalidateTransaction();
        }
    }
}
