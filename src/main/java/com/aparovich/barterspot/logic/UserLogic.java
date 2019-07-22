package com.aparovich.barterspot.logic;

import com.aparovich.barterspot.dao.TransactionHelper;
import com.aparovich.barterspot.dao.factory.DaoFactory;
import com.aparovich.barterspot.dao.impl.*;
import com.aparovich.barterspot.dao.exception.DaoException;
import com.aparovich.barterspot.logic.exception.LogicException;
import com.aparovich.barterspot.logic.util.Encryptor;
import com.aparovich.barterspot.mailer.SSLMailer;
import com.aparovich.barterspot.mailer.exception.MailerException;
import com.aparovich.barterspot.model.util.RoleType;
import com.aparovich.barterspot.model.bean.Bid;
import com.aparovich.barterspot.model.bean.Lot;
import com.aparovich.barterspot.model.bean.User;
import com.aparovich.barterspot.model.util.ModelType;
import com.aparovich.barterspot.validator.Validator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.aparovich.barterspot.validator.ParametersValidator.*;
import static com.aparovich.barterspot.validator.ModelFieldValidator.*;

/**
 * Created by Maxim on 22.03.2017
 */
public class UserLogic {
    private static final Logger LOGGER = LogManager.getLogger(UserLogic.class);

    /**
     * Encrypting salt. Used to hide same passwords.
     */
    private static final String SALT = "L2wZZA";

    public static String encrypt(String password){
        return Encryptor.md5(Encryptor.md5(password) + SALT);
    }

    public static boolean validate(User user) throws LogicException {
        return  user != null &&
                user.getInfo() != null &&
                user.getSettings() != null &&
                checkEmailFormat(user.getEmail()) &&
                checkPasswordFormat(user.getPassword()) &&
                checkNameFormat(user.getInfo().getName()) &&
                checkSurnameFormat(user.getInfo().getSurname()) &&
                checkPhoneNumberFormat(user.getInfo().getPhoneNumber()) &&
                isBirthDate(user.getInfo().getBirthDate()) &&
                checkAddressFormat(user.getInfo().getAddress()) &&
                checkPostCodeFormat(user.getInfo().getPostCode()) &&
                checkLocaleFormat(user.getSettings().getLocale());
    }

    public static User authorise(String email, String password) {
        User user = null;
        TransactionHelper helper = new TransactionHelper();
        try {
            UserDao userDao = (UserDao) DaoFactory.getDao(ModelType.USER);
            helper.prepareTransaction(userDao);
            user = userDao.findByEmail(email);
            if(user == null || !Validator.checkEquality(user.getPassword(), password)) {
                user = null;
            }
            helper.commit();
        } catch (DaoException e) {
            helper.rollback();
            LOGGER.log(Level.ERROR, e.getMessage());
        } finally {
            helper.invalidateTransaction();
        }
        return user;
    }

    public static User findById(Long id) {
        User user = null;
        TransactionHelper helper = new TransactionHelper();
        try {
            UserDao userDao = (UserDao) DaoFactory.getDao(ModelType.USER);
            helper.prepareTransaction(userDao);
            user = userDao.findByPk(id);
            helper.commit();
        } catch (DaoException e) {
            helper.rollback();
            LOGGER.log(Level.ERROR, e.getMessage());
        } finally {
            helper.invalidateTransaction();
        }
        return user;
    }

    public static List<User> findAll() {
        List<User> list = new ArrayList<>();
        TransactionHelper helper = new TransactionHelper();
        try {
            UserDao userDao = (UserDao) DaoFactory.getDao(ModelType.USER);
            helper.prepareTransaction(userDao);
            list = userDao.findAll();
            helper.commit();
        } catch (DaoException e) {
            helper.rollback();
            LOGGER.log(Level.ERROR, e.getMessage());
        } finally {
            helper.invalidateTransaction();
        }
        return list;
    }

    public static List<User> findByRole(RoleType role) {
        List<User> list = new ArrayList<>();
        TransactionHelper helper = new TransactionHelper();
        try {
            UserDao userDao = (UserDao) DaoFactory.getDao(ModelType.USER);
            helper.prepareTransaction(userDao);
            list = userDao.findByRole(role);
            helper.commit();
        } catch (DaoException e) {
            helper.rollback();
            LOGGER.log(Level.ERROR, e.getMessage());
        } finally {
            helper.invalidateTransaction();
        }
        return list;
    }

    public static User create(User user) {
        TransactionHelper helper = new TransactionHelper();
        try {
            UserDao userDao = (UserDao) DaoFactory.getDao(ModelType.USER);
            InfoDao infoDao = (InfoDao) DaoFactory.getDao(ModelType.INFO);
            SettingsDao settingsDao = (SettingsDao) DaoFactory.getDao(ModelType.SETTINGS);
            helper.prepareTransaction(userDao, infoDao, settingsDao);
            user.setInfo(infoDao.create(user.getInfo()));
            user.setSettings(settingsDao.create(user.getSettings()));
            user = userDao.create(user);
            if(user == null || user.getInfo() == null || user.getSettings() == null) {
                user = null;
                helper.rollback();
            }
            helper.commit();
        } catch (DaoException e) {
            helper.rollback();
            if(user != null) {
                user.setInfo(null);
                user.setSettings(null);
            }
            user = null;
            LOGGER.log(Level.ERROR, e.getMessage());
        } finally {
            helper.invalidateTransaction();
        }
        return user;
    }

    public static void update(User user) {
        TransactionHelper helper = new TransactionHelper();
        try {
            UserDao userDao = (UserDao) DaoFactory.getDao(ModelType.USER);
            InfoDao infoDao = (InfoDao) DaoFactory.getDao(ModelType.INFO);
            SettingsDao settingsDao = (SettingsDao) DaoFactory.getDao(ModelType.SETTINGS);
            helper.prepareTransaction(userDao, infoDao, settingsDao);
            infoDao.update(user.getInfo());
            settingsDao.update(user.getSettings());
            userDao.update(user);
            helper.commit();
        } catch (DaoException e) {
            helper.rollback();
            LOGGER.log(Level.ERROR, e.getMessage());
        } finally {
            helper.invalidateTransaction();
        }
    }

    public static void delete(User user) throws LogicException {
        UserDao userDao = null;
        LotDao lotDao = null;
        BidDao bidDao = null;
        TransactionHelper helper = new TransactionHelper();
        try {
            userDao = (UserDao) DaoFactory.getDao(ModelType.USER);
            lotDao = (LotDao) DaoFactory.getDao(ModelType.LOT);
            bidDao = (BidDao) DaoFactory.getDao(ModelType.BID);
            helper.prepareTransaction(userDao, lotDao, bidDao);
            List<Lot> lots = lotDao.findSellingOutByUser(user);
            for (Lot lot : lots) {
                List<Bid> lotBids = bidDao.findByLot(lot);
                for (Bid lotBid : lotBids) {
                    lotBid.setDeletedAt(LocalDateTime.now());
                    bidDao.update(lotBid);
                    lotBid = bidDao.findByPk(lotBid.getId());
                    if(lotBid != null) {
                        helper.rollback();
                        throw new LogicException("User lots bids was not deleted.");
                    }
                }
                lot.setDeletedAt(LocalDateTime.now());
                lotDao.update(lot);
                lot = lotDao.findByPk(lot.getId());
                if(lot != null) {
                    helper.rollback();
                    throw new LogicException("User lots was not deleted.");
                }
            }
            List<Bid> bids = bidDao.findByUser(user);
            for (Bid bid : bids) {
                bid.setDeletedAt(LocalDateTime.now());
                bidDao.update(bid);
                bid = bidDao.findByPk(bid.getId());
                if(bid != null) {
                    helper.rollback();
                    throw new LogicException("User bids was not deleted.");
                }
            }
            user.setDeletedAt(LocalDateTime.now());
            userDao.update(user);
            user = userDao.findByPk(user.getId());
            if(user != null) {
                helper.rollback();
                throw new LogicException("User was not deleted.");
            }
            helper.commit();
        } catch (DaoException e) {
            helper.rollback();
            LOGGER.log(Level.ERROR, e.getMessage());
        } finally {
            helper.invalidateTransaction();
        }
    }

    public static void sendEmail(String sender, String password, String receiver, String subject, String text) throws LogicException {
        TransactionHelper helper = new TransactionHelper();
        try {
            UserDao userDao = (UserDao) DaoFactory.getDao(ModelType.USER);
            helper.prepareTransaction(userDao);
            User test = userDao.findByEmail(sender);
            if(test == null) {
                helper.rollback();
                throw new LogicException("Sender with such email does not exist.");
            }
            test = userDao.findByEmail(receiver);
            if(test == null) {
                helper.rollback();
                throw new LogicException("Receiver with such email does not exist.");
            }
            helper.commit();
            SSLMailer.send(sender, password, receiver, subject, text);
        } catch (DaoException e) {
            helper.rollback();
            LOGGER.log(Level.ERROR, e.getMessage());
        } catch (MailerException e) {
            helper.rollback();
            LOGGER.log(Level.ERROR, "Email wasn't send. " + e.getMessage());
        } finally {
            helper.invalidateTransaction();
        }
    }
}
