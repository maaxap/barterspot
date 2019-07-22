package com.aparovich.barterspot.command.impl;

import com.aparovich.barterspot.command.Command;
import com.aparovich.barterspot.command.util.PageNavigator;
import com.aparovich.barterspot.command.util.ResponseType;
import com.aparovich.barterspot.model.bean.User;
import com.aparovich.barterspot.logic.UserLogic;
import com.aparovich.barterspot.manager.PagesManager;
import com.aparovich.barterspot.command.util.PageMessage;
import com.aparovich.barterspot.command.util.PageMessageType;
import com.aparovich.barterspot.validator.ParametersValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static com.aparovich.barterspot.command.util.CommandConstant.*;

/**
 * Created by Maxim on 14.03.2017.
 */
public class LoginCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(LoginCommand.class);

    @Override
    public PageNavigator execute(HttpServletRequest request) {
        PageMessage message = new PageMessage((String) request.getSession().getAttribute(LOCALE));
        User user = null;
        String email = request.getParameter(EMAIL);
        String password = UserLogic.encrypt(request.getParameter(PASSWORD));

        if(ParametersValidator.checkEmailFormat(email) && ParametersValidator.checkPasswordFormat(password)) {
            user = UserLogic.authorise(email, password);
        }


        if(user == null) {
            message.setParameters(LOGIN_FAIL,  PageMessageType.DANGER);
            request.getSession().setAttribute(MESSAGE, message);
            LOGGER.log(Level.WARN, "User was not authorised.");
            return new PageNavigator(PagesManager.getProperty(GOTO_MAIN), ResponseType.REDIRECT);
        }

        request.getSession().setAttribute(USER, user);
        request.getSession().setAttribute(ROLE, user.getRole().getRole());
        request.getSession().setAttribute(LOCALE, user.getSettings().getLocale());

        return new PageNavigator(PagesManager.getProperty(GOTO_MAIN), ResponseType.REDIRECT);
    }
}
