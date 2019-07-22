package com.aparovich.barterspot.dao.factory;

import com.aparovich.barterspot.dao.AbstractDao;
import com.aparovich.barterspot.dao.impl.*;
import com.aparovich.barterspot.dao.exception.DaoException;
import com.aparovich.barterspot.model.util.ModelType;

import java.util.HashMap;

/**
 * Created by Maxim on 24.03.2017
 */
public class DaoFactory {
    private static final HashMap<ModelType, AbstractDao> creators = new HashMap<>();

    static  {
        creators.put(ModelType.USER, new UserDao());
        creators.put(ModelType.INFO, new InfoDao());
        creators.put(ModelType.SETTINGS, new SettingsDao());
        creators.put(ModelType.CATEGORY, new CategoryDao());
        creators.put(ModelType.LOT, new LotDao());
        creators.put(ModelType.BID, new BidDao());
    }

    public static AbstractDao getDao(ModelType modelType) throws DaoException {
        AbstractDao dao = creators.get(modelType);
        if (dao == null) {
            throw new DaoException("Dao object for " + modelType.toString().toLowerCase() + " not found.");
        }
        return dao;
    }
}
