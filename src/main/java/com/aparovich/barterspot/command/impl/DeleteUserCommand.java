package com.aparovich.barterspot.command.impl;

import com.aparovich.barterspot.command.Command;
import com.aparovich.barterspot.command.util.PageNavigator;
import com.aparovich.barterspot.command.util.ResponseType;
import com.aparovich.barterspot.logic.ImageLogic;
import com.aparovich.barterspot.logic.UserLogic;
import com.aparovich.barterspot.logic.exception.LogicException;
import com.aparovich.barterspot.command.util.PageMessage;
import com.aparovich.barterspot.command.util.PageMessageType;
import com.aparovich.barterspot.manager.PagesManager;
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
public class DeleteUserCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(DeleteUserCommand.class);

    @Override
    public PageNavigator execute(HttpServletRequest request) {
        PageMessage message = new PageMessage((String) request.getSession().getAttribute(LOCALE));
        User user = null;
        String userId = request.getParameter(ID);
        String uploadPath = request.getServletContext().getInitParameter(INIT_PARAM_UPLOAD_DIRECTORY);

        if(ParametersValidator.checkIntegerFormat(userId)) {
            user = UserLogic.findById(Long.valueOf(userId));
        }

        if(user == null) {
            message.setParameters(NOT_FOUND_USER, PageMessageType.DANGER);
            request.getSession().setAttribute(MESSAGE, message);
            LOGGER.log(Level.ERROR, "User was not found.");
            return new PageNavigator(PagesManager.getProperty(GOTO_ERROR_404), ResponseType.REDIRECT);
        }

        try {
            UserLogic.delete(user);
            ImageLogic.delete(user, uploadPath);
        } catch (LogicException e) {
            message.setParameters(DELETE_FAIL, PageMessageType.DANGER);
            request.getSession().setAttribute(MESSAGE, message);
            LOGGER.log(Level.ERROR, e.getMessage());
        }

        return new PageNavigator(PagesManager.getProperty(GOTO_ADMIN), ResponseType.REDIRECT);
    }
}
