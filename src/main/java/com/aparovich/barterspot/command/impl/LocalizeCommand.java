package com.aparovich.barterspot.command.impl;

import com.aparovich.barterspot.command.Command;
import com.aparovich.barterspot.command.util.PageNavigator;
import com.aparovich.barterspot.command.util.ResponseType;
import com.aparovich.barterspot.manager.PagesManager;

import javax.servlet.http.HttpServletRequest;

import static com.aparovich.barterspot.command.util.CommandConstant.*;

/**
 * Created by Maxim on 06.04.2017
 */
public class LocalizeCommand implements Command {

    @Override
    public PageNavigator execute(HttpServletRequest request) {
        String locale = request.getParameter(LOCALE);
        request.getSession().setAttribute(LOCALE, locale);
        return new PageNavigator(PagesManager.getProperty(GOTO_MAIN), ResponseType.FORWARD);
    }
}
