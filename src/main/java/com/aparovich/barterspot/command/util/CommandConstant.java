package com.aparovich.barterspot.command.util;

/**
 * Util class which aggregates all constants used in {@link com.aparovich.barterspot.command} package.
 *
 * @author Maxim
 */
public class CommandConstant {

    /**
     * Keys for access to page {@code 'url'} in property file via {@link java.util.ResourceBundle}.
     */
    public static final String PAGE_ABOUT_US = "page.about_us";
    public static final String PAGE_ADD_LOT = "page.add_lot";
    public static final String PAGE_BIDS = "page.bids";
    public static final String PAGE_CONTACTS = "page.contacts";
    public static final String PAGE_EDIT_LOT = "page.edit_lot";
    public static final String PAGE_SEND_EMAIL = "page.send_email";
    public static final String PAGE_INDEX = "page.index";
    public static final String PAGE_EDIT_PROFILE = "page.edit_profile";
    public static final String PAGE_LOT = "page.lot";
    public static final String PAGE_LOTS = "page.lots";
    public static final String PAGE_ERROR_404 = "page.error404";
    public static final String PAGE_ERROR_403 = "page.error403";
    public static final String PAGE_ERROR_500 = "page.error500";
    public static final String PAGE_MAIN = "page.main";
    public static final String PAGE_USER = "page.user";
    public static final String PAGE_PROFILE = "page.profile";
    public static final String PAGE_REGISTRATION = "page.registration";
    public static final String PAGE_RULES = "page.rules";
    public static final String PAGE_SETTINGS = "page.settings";
    public static final String PAGE_PAYMENT = "page.payment";
    public static final String PAGE_ADMIN = "page.admin";

    /**
     * Keys for access to {@code 'GET'} request command {@code 'url'} via {@link java.util.ResourceBundle}.
     */
    public static final String GOTO_ERROR_404 = "goto.error404";
    public static final String GOTO_ERROR_403 = "goto.error403";
    public static final String GOTO_LOT = "goto.lot";
    public static final String GOTO_LOTS = "goto.lots";
    public static final String GOTO_BIDS = "goto.bids";
    public static final String GOTO_SETTINGS = "goto.settings";
    public static final String GOTO_ADD_LOT = "goto.add_lot";
    public static final String GOTO_MAIN = "goto.main";
    public static final String GOTO_EDIT_LOT = "goto.edit_lot";
    public static final String GOTO_EDIT_PROFILE = "goto.edit_profile";
    public static final String GOTO_PAYMENT = "goto.payment";
    public static final String GOTO_PROFILE = "goto.profile";
    public static final String GOTO_ADMIN = "goto.admin";
    public static final String GOTO_USER = "goto.user";
    public static final String GOTO_REGISTRATION = "goto.registration";
    public static final String GOTO_SEND_EMAIL = "goto.send_email";

    /**
     * {@link javax.servlet.http.HttpServletRequest} parameters and attributes names.
     */
    public static final String ID = "id";
    public static final String LOT = "lot";
    public static final String LOTS = "lots";
    public static final String BID = "bid";
    public static final String CATEGORY = "category";
    public static final String CATEGORIES = "categories";
    public static final String USER = "user";
    public static final String ROLE = "role";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String PASSWORD_CONFIRMATION = "passwordConfirm";
    public static final String OLD_PASSWORD = "oldPassword";
    public static final String NAME = "name";
    public static final String SURNAME = "surname";
    public static final String PHONE_NUMBER = "phoneNumber";
    public static final String DAY = "day";
    public static final String MONTH = "month";
    public static final String YEAR = "year";
    public static final String ADDRESS = "address";
    public static final String POST_CODE = "postCode";
    public static final String DESCRIPTION = "description";
    public static final String DEFAULT_PRICE = "defaultPrice";
    public static final String LAST_BID = "lastBid";
    public static final String DAYS_LEFT = "daysLeft";
    public static final String BIDS_AMOUNT = "bidsAmount";
    public static final String CUR_PAGE = "curPage";
    public static final String READY = "ready";
    public static final String SELLING_OUT = "sellingOut";
    public static final String PURCHASED = "purchased";
    public static final String FINISHED = "finished";
    public static final String READY_SIZE = "readySize";
    public static final String SELLING_OUT_SIZE = "sellingOutSize";
    public static final String PURCHASED_SIZE = "purchasedSize";
    public static final String FINISHED_SIZE = "finishedSize";
    public static final String MESSAGE = "message";
    public static final String PAGE = "page";
    public static final String TYPE = "type";
    public static final String MODEL = "model";
    public static final String ITEMS = "items";
    public static final String COMMAND = "command";
    public static final String LAST_PAGE = "lastPage";
    public static final String USERS_SIZE = "usersSize";
    public static final String BIDS_SIZE = "bidsSize";
    public static final String LOTS_SIZE = "lotsSize";
    public static final String CATEGORIES_SIZE = "categoriesSize";
    public static final String IS_WINNER = "isWinner";
    public static final String RECEIVER_ID = "receiverId";
    public static final String RECEIVER_EMAIL = "receiverEmail";
    public static final String SENDER_EMAIL = "senderEmail";
    public static final String SUBJECT = "subject";
    public static final String TEXT = "text";
    public static final String LOCALE       = "locale";

    /**
     * Images uploading directory.
     */
    public static final String INIT_PARAM_UPLOAD_DIRECTORY = "upload.directory";

    /**
     * Keys for access to message in property file via {@link java.util.ResourceBundle}.
     */
    public static final String LOGIN_FAIL = "login.fail";
    public static final String DELETE_FAIL = "delete.fail";
    public static final String UPDATE_FAIL = "update.fail";
    public static final String NOT_FOUND_USER = "not_found.user";
    public static final String NOT_FOUND_LOT = "not_found.lot";
    public static final String NOT_FOUND_LOT_BIDS = "not_found.lot_bids";
    public static final String NOT_FOUND_BID = "not_found.bid";
    public static final String NOT_FOUND_CATEGORY = "not_found.category";
    public static final String NOT_CREATED_CATEGORY = "not_created.category";
    public static final String NOT_CREATED_LOT = "not_created.lot";
    public static final String NOT_CREATED_BID = "not_created.bid";
    public static final String REGISTRATION_FAIL = "registration.fail";
    public static final String WRONG_INPUT_FORMAT = "wrong_format.input";
    public static final String SEND_EMAIL_SUCCESS = "send_email.success";
    public static final String PERMISSIONS_ERROR = "permissions.access_denied";

    private CommandConstant(){}
}
