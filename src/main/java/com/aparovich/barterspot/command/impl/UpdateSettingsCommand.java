package com.aparovich.barterspot.command.impl;

import com.aparovich.barterspot.command.Command;
import com.aparovich.barterspot.command.util.PageNavigator;
import com.aparovich.barterspot.command.util.ResponseType;
import com.aparovich.barterspot.logic.UserLogic;
import com.aparovich.barterspot.command.util.PageMessage;
import com.aparovich.barterspot.command.util.PageMessageType;
import com.aparovich.barterspot.model.bean.User;
import com.aparovich.barterspot.manager.PagesManager;
import com.aparovich.barterspot.validator.ParametersValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static com.aparovich.barterspot.command.util.CommandConstant.*;

/**
 * Created by Maxim on 17.04.2017
 */
public class UpdateSettingsCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(UpdateSettingsCommand.class);

    @Override
    public PageNavigator execute(HttpServletRequest request) {
        PageMessage message = new PageMessage((String) request.getSession().getAttribute(LOCALE));
        String email = request.getParameter(EMAIL);
        String oldPassword = request.getParameter(OLD_PASSWORD);
        String newPassword = request.getParameter(PASSWORD);
        String locale = request.getParameter(LOCALE);
        User user = (User) request.getSession().getAttribute(USER);
//        org.owasp.esapi.filters.RequestRateThrottleFilter
        if(ParametersValidator.checkLocaleFormat(locale)) {
            user.getSettings().setLocale(locale);
            if (ParametersValidator.checkPasswordFormat(oldPassword)) {
                oldPassword = UserLogic.encrypt(oldPassword);

                if (!user.equals(UserLogic.authorise(user.getEmail(), oldPassword))) {
                    message.setParameters(WRONG_INPUT_FORMAT, PageMessageType.DANGER);
                    request.setAttribute(MESSAGE, message);
                    LOGGER.log(Level.WARN, "Entered wrong email or password.");
                    return new PageNavigator(PagesManager.getProperty(GOTO_SETTINGS), ResponseType.FORWARD);
                }

                user.setEmail(email);
                user.setPassword(UserLogic.encrypt(newPassword));
                user.getSettings().setLocale(locale);
            }

            UserLogic.update(user);
            request.getSession().setAttribute(USER, user);
            request.getSession().setAttribute(LOCALE, user.getSettings().getLocale());
        }

        return new PageNavigator(PagesManager.getProperty(GOTO_SETTINGS), ResponseType.REDIRECT);
    }
}
