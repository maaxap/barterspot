package com.aparovich.barterspot.command.impl;

import com.aparovich.barterspot.command.Command;
import com.aparovich.barterspot.command.util.PageNavigator;
import com.aparovich.barterspot.command.util.ResponseType;
import com.aparovich.barterspot.logic.UserLogic;
import com.aparovich.barterspot.command.util.PageMessage;
import com.aparovich.barterspot.command.util.PageMessageType;
import com.aparovich.barterspot.manager.PagesManager;
import com.aparovich.barterspot.model.util.RoleType;
import com.aparovich.barterspot.model.bean.User;
import com.aparovich.barterspot.validator.ParametersValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static com.aparovich.barterspot.command.util.CommandConstant.*;

/**
 * Created by Maxim on 02.05.2017
 */
public class BlockUserCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(BlockUserCommand.class);

    @Override
    public PageNavigator execute(HttpServletRequest request) {
        PageMessage message = new PageMessage((String) request.getSession().getAttribute(LOCALE));
        User user = null;
        String userId = request.getParameter(ID);
        String page = request.getParameter(PAGE);

        if(ParametersValidator.checkIntegerFormat(userId)) {
            user = UserLogic.findById(Long.valueOf(userId));
        }

        //Checking if user was not found.
        if(user == null) {
            message.setParameters(NOT_FOUND_USER, PageMessageType.DANGER);
            request.getSession().setAttribute(MESSAGE, message);
            LOGGER.log(Level.ERROR, "User was not found.");
            return new PageNavigator(PagesManager.getProperty(GOTO_ADMIN), ResponseType.FORWARD);
        }

        //Updating user.
        user.setBlocked(true);
        UserLogic.update(user);

        //Checking if previous page was admin page.
        if(RoleType.ADMIN.getRole().equals(page)) {
            return new PageNavigator(PagesManager.getProperty(GOTO_ADMIN), ResponseType.REDIRECT);
        }

        return new PageNavigator(PagesManager.getProperty(GOTO_USER, user.getId().toString()), ResponseType.REDIRECT);
    }
}
