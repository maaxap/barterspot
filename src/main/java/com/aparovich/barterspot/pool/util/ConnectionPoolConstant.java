package com.aparovich.barterspot.pool.util;

import com.aparovich.barterspot.pool.ConnectionPool;

/**
 * Class which aggregates constants relevant to {@link com.aparovich.barterspot.pool}.
 *
 * @author Maxim
 */
public class ConnectionPoolConstant {

    /**
     * String keys for {@link java.util.ResourceBundle} from database.properties.
     */
    public static final String DB_URL_KEY = "db_url";
    public static final String LOGIN_KEY = "login";
    public static final String PASSWORD_KEY = "password";
    public static final String POOL_SIZE_KEY = "pool_size";

    private ConnectionPoolConstant() {}
}
