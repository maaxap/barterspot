package com.aparovich.barterspot.logic;

import com.aparovich.barterspot.dao.TransactionHelper;
import com.aparovich.barterspot.dao.exception.DaoException;
import com.aparovich.barterspot.dao.factory.DaoFactory;
import com.aparovich.barterspot.dao.impl.CategoryDao;
import com.aparovich.barterspot.dao.impl.LotDao;
import com.aparovich.barterspot.logic.exception.LogicException;
import com.aparovich.barterspot.model.bean.Category;
import com.aparovich.barterspot.model.util.ModelType;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sun.rmi.runtime.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maxim on 16.04.2017
 */
public class CategoryLogic {
    private static final Logger LOGGER = LogManager.getLogger(CategoryLogic.class);

    public static Category findByName(String name) {
        Category category = null;
        CategoryDao categoryDao;
        TransactionHelper helper = new TransactionHelper();
        try {
            categoryDao = (CategoryDao) DaoFactory.getDao(ModelType.CATEGORY);
            helper.prepareTransaction(categoryDao);
            category = categoryDao.findByName(name);
            helper.commit();
        } catch (DaoException e) {
            helper.rollback();
            LOGGER.log(Level.ERROR, e.getMessage());
        } finally {
            helper.invalidateTransaction();
        }
        return category;
    }

    public static List<Category> findAll() {
        CategoryDao categoryDao = null;
        List<Category> categories = new ArrayList<>();
        TransactionHelper helper = new TransactionHelper();
        try {
            categoryDao = (CategoryDao) DaoFactory.getDao(ModelType.CATEGORY);
            helper.prepareTransaction(categoryDao);
            categories = categoryDao.findAll();
            helper.commit();
        } catch (DaoException e) {
            helper.rollback();
            LOGGER.log(Level.ERROR, e.getMessage());
        } finally {
            helper.invalidateTransaction();
        }
        return categories;
    }

    public static Category create(Category category) {
        CategoryDao categoryDao = null;
        TransactionHelper helper = new TransactionHelper();
        try {
            categoryDao = (CategoryDao) DaoFactory.getDao(ModelType.CATEGORY);
            helper.prepareTransaction(categoryDao);
            category = categoryDao.create(category);
            if(category == null) {
                helper.rollback();
            }
            helper.commit();
        } catch (DaoException e) {
            helper.rollback();
            category = null;
            LOGGER.log(Level.ERROR, e.getMessage());
        } finally {
            helper.invalidateTransaction();
        }
        return category;
    }

    public static void delete(Category category) throws LogicException {
        LotDao lotDao = null;
        CategoryDao categoryDao = null;
        TransactionHelper helper = new TransactionHelper();
        try {
            lotDao = (LotDao) DaoFactory.getDao(ModelType.LOT);
            categoryDao = (CategoryDao) DaoFactory.getDao(ModelType.CATEGORY);
            helper.prepareTransaction(lotDao, categoryDao);
            if(!lotDao.findByCategory(category).isEmpty()) {
                helper.rollback();
                throw new LogicException("Some lots with such category exist.");
            }
            categoryDao.delete(category);
            category = categoryDao.findByName(category.getName());
            if(category != null) {
                helper.rollback();
                throw new LogicException("Category was not deleted.");
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
