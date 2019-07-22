package com.aparovich.barterspot.command.impl;

import com.aparovich.barterspot.command.Command;
import com.aparovich.barterspot.command.util.PageNavigator;
import com.aparovich.barterspot.command.util.ResponseType;
import com.aparovich.barterspot.logic.CategoryLogic;
import com.aparovich.barterspot.logic.exception.LogicException;
import com.aparovich.barterspot.command.util.PageMessage;
import com.aparovich.barterspot.command.util.PageMessageType;
import com.aparovich.barterspot.manager.PagesManager;
import com.aparovich.barterspot.model.bean.Category;
import com.aparovich.barterspot.validator.ParametersValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static com.aparovich.barterspot.command.util.CommandConstant.*;

/**
 * Created by Maxim on 02.05.2017
 */
public class DeleteCategoryCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(DeleteCategoryCommand.class);

    @Override
    public PageNavigator execute(HttpServletRequest request) {
        PageMessage message = new PageMessage((String) request.getSession().getAttribute(LOCALE));
        Category category = null;
        String name = request.getParameter(CATEGORY);

        if(ParametersValidator.checkNameFormat(name)) {
            category = CategoryLogic.findByName(name);
        }

        if (category == null) {
            message.setParameters(NOT_FOUND_CATEGORY, PageMessageType.DANGER);
            request.getSession().setAttribute(MESSAGE, message);
            LOGGER.log(Level.ERROR, "Category was not found.");
            return new PageNavigator(PagesManager.getProperty(GOTO_ADMIN), ResponseType.FORWARD);
        }

        try{
            CategoryLogic.delete(category);
        } catch (LogicException e) {
            message.setParameters(DELETE_FAIL, PageMessageType.DANGER);
            request.getSession().setAttribute(MESSAGE, message);
            LOGGER.log(Level.ERROR, e.getMessage());
        }

        return new PageNavigator(PagesManager.getProperty(GOTO_ADMIN), ResponseType.REDIRECT);
    }
}
