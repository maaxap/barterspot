package com.aparovich.barterspot.command.impl;

import com.aparovich.barterspot.command.Command;
import com.aparovich.barterspot.command.util.PageNavigator;
import com.aparovich.barterspot.command.util.ResponseType;
import com.aparovich.barterspot.logic.CategoryLogic;
import com.aparovich.barterspot.logic.ImageLogic;
import com.aparovich.barterspot.logic.LotLogic;
import com.aparovich.barterspot.logic.UserLogic;
import com.aparovich.barterspot.logic.exception.LogicException;
import com.aparovich.barterspot.command.util.PageMessage;
import com.aparovich.barterspot.command.util.PageMessageType;
import com.aparovich.barterspot.manager.PagesManager;
import com.aparovich.barterspot.model.bean.*;
import com.aparovich.barterspot.validator.ParametersValidator;
import com.aparovich.barterspot.validator.Validator;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.aparovich.barterspot.command.util.CommandConstant.*;

/**
 * Created by Maxim on 12.05.2017
 */
public class MultipartRequestCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(MultipartRequestCommand.class);

    private static final int MEMORY_THRESHOLD           = 1024 * 1024 * 1;
    private static final int MAX_FILE_SIZE              = 1024 * 1024 * 15;

    private static final String UTF_8_ENCODING          = "UTF-8";
    private static final String REGISTRATION_COMMAND    = "registration";
    private static final String EDIT_PROFILE_COMMAND    = "edit-profile";
    private static final String ADD_LOT_COMMAND         = "add-lot";
    private static final String EDIT_LOT_COMMAND        = "edit-lot";

    private static final String TEMP_REPOSITORY         = "java.io.tmpdir";


    @Override
    public PageNavigator execute(HttpServletRequest request) {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(MEMORY_THRESHOLD);
        factory.setRepository(new File(System.getProperty(TEMP_REPOSITORY)));

        ServletFileUpload uploader = new ServletFileUpload(factory);
        uploader.setSizeMax(MAX_FILE_SIZE);

        String uploadPath = request.getServletContext().getInitParameter(INIT_PARAM_UPLOAD_DIRECTORY);

        try {
            List<FileItem> items = uploader.parseRequest(request);
            for (FileItem item : items) {
                if(COMMAND.equals(item.getFieldName())) {
                    switch (item.getString(UTF_8_ENCODING)) {
                        case REGISTRATION_COMMAND:
                            return registration(request, items, uploadPath);
                        case EDIT_PROFILE_COMMAND:
                            return editProfile(request, items, uploadPath);
                        case ADD_LOT_COMMAND:
                            return addLot(request, items, uploadPath);
                        case EDIT_LOT_COMMAND:
                            return editLot(request, items, uploadPath);
                        default:
                            LOGGER.log(Level.ERROR, "Wrong command type: " + item.getString(UTF_8_ENCODING));
                    }
                }
            }
        } catch (FileUploadException | UnsupportedEncodingException e) {
            LOGGER.log(Level.ERROR, e.getMessage());
        }
        return new PageNavigator(PagesManager.getProperty(GOTO_MAIN), ResponseType.FORWARD);
    }

    private PageNavigator registration(HttpServletRequest request, List<FileItem> items, String uploadPath) throws FileUploadException, UnsupportedEncodingException {
        PageMessage message = new PageMessage((String) request.getSession().getAttribute(LOCALE));
        FileItem imageItem = null;

        User user = new User();
        Info info = new Info();
        Settings settings = new Settings();

        String locale = (String) request.getSession().getAttribute(LOCALE);

        String passwordConfirmation = null;

        int day = 0;
        int month = 0;
        int year = 0;

        //Resolve items and set suitable user/info/settings field
        for (FileItem item : items) {
            if (item.isFormField()) {
                switch (item.getFieldName()) {
                    case EMAIL:
                        user.setEmail(item.getString(UTF_8_ENCODING));
                        break;
                    case PASSWORD:
                        user.setPassword(UserLogic.encrypt(item.getString(UTF_8_ENCODING)));
                        break;
                    case PASSWORD_CONFIRMATION:
                        passwordConfirmation = UserLogic.encrypt(item.getString(UTF_8_ENCODING));
                        break;
                    case NAME:
                        info.setName(item.getString(UTF_8_ENCODING));
                        break;
                    case SURNAME:
                        info.setSurname(item.getString(UTF_8_ENCODING));
                        break;
                    case PHONE_NUMBER:
                        info.setPhoneNumber(item.getString(UTF_8_ENCODING));
                        break;
                    case DAY:
                        String dayString = item.getString(UTF_8_ENCODING);
                        if(ParametersValidator.checkDayFormat(dayString)) {
                            day = Integer.valueOf(dayString);
                        }
                        break;
                    case MONTH:
                        String monthString = item.getString(UTF_8_ENCODING);
                        if(ParametersValidator.checkMonthFormat(monthString)) {
                            month = Integer.valueOf(monthString);
                        }
                        break;
                    case YEAR:
                        String yearString = item.getString(UTF_8_ENCODING);
                        if(ParametersValidator.checkYearFormat(yearString)) {
                            year = Integer.valueOf(yearString);
                        }
                        break;
                    case ADDRESS:
                        info.setAddress(item.getString(UTF_8_ENCODING));
                        break;
                    case POST_CODE:
                        info.setPostCode(item.getString(UTF_8_ENCODING));
                        break;
                }
            } else {
                imageItem = item;
            }
        }

        try {
            info.setBirthDate(LocalDate.of(year, month, day));
            settings.setLocale(locale);
            user.setSettings(settings);
            user.setInfo(info);

            if(UserLogic.validate(user) && Validator.checkEquality(user.getPassword(), passwordConfirmation)) {
                user = UserLogic.create(user);
            } else {
                user = null;
            }

            if(user == null) {
                message.setParameters(REGISTRATION_FAIL, PageMessageType.DANGER);
                request.getSession().setAttribute(MESSAGE, message);
                LOGGER.log(Level.ERROR, "User was not created.");
                return new PageNavigator(PagesManager.getProperty(GOTO_REGISTRATION), ResponseType.FORWARD);
            }

            if (imageItem != null) {
                ImageLogic.upload(imageItem, user, uploadPath);
            }

        } catch (DateTimeException | LogicException e) {
            message.setParameters(WRONG_INPUT_FORMAT, PageMessageType.DANGER);
            request.getSession().setAttribute(MESSAGE, message);
            LOGGER.log(Level.ERROR, e.getMessage());
            return new PageNavigator(PagesManager.getProperty(GOTO_REGISTRATION), ResponseType.FORWARD);
        }

        request.getSession().setAttribute(USER, user);
        request.getSession().setAttribute(LOCALE, user.getSettings().getLocale());
        request.getSession().setAttribute(ROLE, user.getRole().getRole());

        return new PageNavigator(PagesManager.getProperty(GOTO_PROFILE), ResponseType.REDIRECT);
    }

    private PageNavigator editProfile(HttpServletRequest request, List<FileItem> items, String uploadPath)throws FileUploadException, UnsupportedEncodingException  {
        PageMessage message = new PageMessage((String) request.getSession().getAttribute(LOCALE));

        FileItem imageItem = null;

        User user = (User) request.getSession().getAttribute(USER);

        int day = 0;
        int month = 0;
        int year = 0;


        //Resolve items and set suitable user/info/settings field
        for (FileItem item : items) {
            if (item.isFormField()) {
                switch (item.getFieldName()) {
                    case NAME:
                        user.getInfo().setName(item.getString(UTF_8_ENCODING));
                        break;
                    case SURNAME:
                        user.getInfo().setSurname(item.getString(UTF_8_ENCODING));
                        break;
                    case PHONE_NUMBER:
                        user.getInfo().setPhoneNumber(item.getString(UTF_8_ENCODING));
                        break;
                    case DAY:
                        String dayString = item.getString(UTF_8_ENCODING);
                        if(ParametersValidator.checkDayFormat(dayString)) {
                            day = Integer.valueOf(dayString);
                        }
                        break;
                    case MONTH:
                        String monthString = item.getString(UTF_8_ENCODING);
                        if(ParametersValidator.checkMonthFormat(monthString)) {
                            month = Integer.valueOf(monthString);
                        }
                        break;
                    case YEAR:
                        String yearString = item.getString(UTF_8_ENCODING);
                        if(ParametersValidator.checkYearFormat(yearString)) {
                            year = Integer.valueOf(yearString);
                        }
                        break;
                    case ADDRESS:
                        user.getInfo().setAddress(item.getString(UTF_8_ENCODING));
                        break;
                    case POST_CODE:
                        user.getInfo().setPostCode(item.getString(UTF_8_ENCODING));
                        break;
                }
            } else {
                imageItem = item;
            }
        }
        try {
            user.getInfo().setBirthDate(LocalDate.of(year, month, day));

            if(UserLogic.validate(user)) {
                UserLogic.update(user);
            } else {
                message.setParameters(UPDATE_FAIL, PageMessageType.DANGER);
                request.getSession().setAttribute(MESSAGE, message);
                LOGGER.log(Level.ERROR, "User was not updated.");
            }

            if (imageItem != null) {
                ImageLogic.upload(imageItem, user, uploadPath);
            }

        } catch (DateTimeException | LogicException e) {
            message.setParameters(WRONG_INPUT_FORMAT, PageMessageType.DANGER);
            request.getSession().setAttribute(MESSAGE, message);
            LOGGER.log(Level.ERROR, e.getMessage());
            return new PageNavigator(PagesManager.getProperty(GOTO_EDIT_PROFILE), ResponseType.FORWARD);
        }

        request.getSession().setAttribute(USER, user);

        return new PageNavigator(PagesManager.getProperty(GOTO_EDIT_PROFILE), ResponseType.REDIRECT);
    }

    private PageNavigator addLot(HttpServletRequest request, List<FileItem> items, String uploadPath) throws FileUploadException, UnsupportedEncodingException {
        PageMessage message = new PageMessage((String) request.getSession().getAttribute(LOCALE));

        FileItem imageItem = null;

        User user = (User) request.getSession().getAttribute(USER);
        Lot lot = new Lot();
        Category category = null;

        String categoryName = null;

        int day = 0;
        int month = 0;
        int year = 0;

        //Resolve items and set suitable user/info/settings field
        for (FileItem item : items) {
            if (item.isFormField()) {
                switch (item.getFieldName()) {
                    case NAME:
                        lot.setName(item.getString(UTF_8_ENCODING));
                        break;
                    case DESCRIPTION:
                        lot.setDescription(item.getString(UTF_8_ENCODING));
                        break;
                    case CATEGORY:
                        categoryName = item.getString(UTF_8_ENCODING);
                        break;
                    case DEFAULT_PRICE:
                        lot.setDefaultPrice(BigDecimal.valueOf(Double.valueOf(item.getString(UTF_8_ENCODING))));
                        break;
                    case DAY:
                        String dayString = item.getString(UTF_8_ENCODING);
                        if(ParametersValidator.checkDayFormat(dayString)) {
                            day = Integer.valueOf(dayString);
                        }
                        break;
                    case MONTH:
                        String monthString = item.getString(UTF_8_ENCODING);
                        if(ParametersValidator.checkMonthFormat(monthString)) {
                            month = Integer.valueOf(monthString);
                        }
                        break;
                    case YEAR:
                        String yearString = item.getString(UTF_8_ENCODING);
                        if(ParametersValidator.checkYearFormat(yearString)) {
                            year = Integer.valueOf(yearString);
                        }
                        break;
                }
            } else {
                imageItem = item;
            }
        }


        try {
            lot.setFinishing(LocalDateTime.of(year, month, day, 0, 0, 0));
            category = CategoryLogic.findByName(categoryName);
            lot.setCategory(category);
            lot.setUser(user);

            if(LotLogic.validate(lot)) {;
                lot = LotLogic.create(lot);
            } else {
                lot = null;
            }

            if(lot == null) {
                message.setParameters(NOT_CREATED_LOT, PageMessageType.DANGER);
                request.getSession().setAttribute(MESSAGE, message);
                LOGGER.log(Level.ERROR, "Lot was not created.");
                return new PageNavigator(PagesManager.getProperty(GOTO_ADD_LOT), ResponseType.FORWARD);
            }

            if (imageItem != null) {
                ImageLogic.upload(imageItem, lot, uploadPath);
            }

        } catch (DateTimeException | LogicException e) {
            message.setParameters(WRONG_INPUT_FORMAT, PageMessageType.DANGER);
            request.getSession().setAttribute(MESSAGE, message);
            LOGGER.log(Level.ERROR, e.getMessage());
            return new PageNavigator(PagesManager.getProperty(GOTO_ADD_LOT), ResponseType.FORWARD);
        }

        return new PageNavigator(PagesManager.getProperty(GOTO_LOTS), ResponseType.REDIRECT);
    }

    private PageNavigator editLot(HttpServletRequest request, List<FileItem> items, String uploadPath)throws FileUploadException, UnsupportedEncodingException  {
        PageMessage message = new PageMessage((String) request.getSession().getAttribute(LOCALE));

        FileItem imageItem = null;

        Lot lot = new Lot();
        Category category = null;
        String name = null;
        String description = null;

        String categoryName = null;

        int day = 0;
        int month = 0;
        int year = 0;


        //Resolve items and set suitable user/info/settings field
        for (FileItem item : items) {
            if (item.isFormField()) {
                switch (item.getFieldName()) {
                    case ID:
                        lot = LotLogic.findById(Long.valueOf(item.getString(UTF_8_ENCODING)));
                        break;
                    case NAME:
                        name = item.getString(UTF_8_ENCODING);
                        break;
                    case DESCRIPTION:
                        description = item.getString(UTF_8_ENCODING);
                        break;
                    case CATEGORY:
                        categoryName = item.getString(UTF_8_ENCODING);
                        break;
                    case DAY:
                        String dayString = item.getString(UTF_8_ENCODING);
                        if(ParametersValidator.checkDayFormat(dayString)) {
                            day = Integer.valueOf(dayString);
                        }
                        break;
                    case MONTH:
                        String monthString = item.getString(UTF_8_ENCODING);
                        if(ParametersValidator.checkMonthFormat(monthString)) {
                            month = Integer.valueOf(monthString);
                        }
                        break;
                    case YEAR:
                        String yearString = item.getString(UTF_8_ENCODING);
                        if(ParametersValidator.checkYearFormat(yearString)) {
                            year = Integer.valueOf(yearString);
                        }
                        break;
                }
            } else {
                imageItem = item;
            }
        }

        try {
            lot.setName(name);
            lot.setDescription(description);
            lot.setFinishing(LocalDateTime.of(year, month, day, 0, 0, 0));
            category = CategoryLogic.findByName(categoryName);
            lot.setCategory(category);

            if(LotLogic.validate(lot)) {
                LotLogic.update(lot);
            }  else {
                message.setParameters(UPDATE_FAIL, PageMessageType.DANGER);
                request.getSession().setAttribute(MESSAGE, message);
                LOGGER.log(Level.ERROR, "Lot was not updated.");
            }

            if (imageItem != null) {
                ImageLogic.upload(imageItem, lot, uploadPath);
            }
        } catch (DateTimeException | LogicException e) {
            message.setParameters(WRONG_INPUT_FORMAT, PageMessageType.DANGER);
            request.getSession().setAttribute(MESSAGE, message);
            LOGGER.log(Level.ERROR, e.getMessage());
            return new PageNavigator(PagesManager.getProperty(GOTO_EDIT_LOT) + lot.getId(), ResponseType.FORWARD);
        }

        return new PageNavigator(PagesManager.getProperty(GOTO_EDIT_LOT) + lot.getId(), ResponseType.REDIRECT);
    }
}
