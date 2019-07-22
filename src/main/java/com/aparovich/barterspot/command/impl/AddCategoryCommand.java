package com.aparovich.barterspot.command.impl;

import com.aparovich.barterspot.command.Command;
import com.aparovich.barterspot.command.util.PageNavigator;
import com.aparovich.barterspot.command.util.ResponseType;
import com.aparovich.barterspot.logic.CategoryLogic;
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
public class AddCategoryCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(AddCategoryCommand.class);

    @Override
    public PageNavigator execute(HttpServletRequest request) {
        PageMessage message = new PageMessage((String) request.getSession().getAttribute(LOCALE));
        Category category = new Category();
        String name = request.getParameter(CATEGORY);

        //Checking if category name has valid format.
        if(ParametersValidator.checkNameFormat(name)) {
            category.setName(name);
            category = CategoryLogic.create(category);
        } else {
            category = null;
        }

        //Checking if category exists.
        if(category == null) {
            message.setParameters(NOT_CREATED_CATEGORY, PageMessageType.DANGER);
            request.getSession().setAttribute(MESSAGE, message);
            LOGGER.log(Level.ERROR, "Category was not created.");
            return new PageNavigator(PagesManager.getProperty(GOTO_ADMIN), ResponseType.FORWARD);
        }

        return new PageNavigator(PagesManager.getProperty(GOTO_ADMIN), ResponseType.REDIRECT);
    }
}
