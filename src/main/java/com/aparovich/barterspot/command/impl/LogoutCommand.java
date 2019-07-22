package com.aparovich.barterspot.command.impl;

import com.aparovich.barterspot.command.Command;
import com.aparovich.barterspot.command.util.PageNavigator;
import com.aparovich.barterspot.command.util.ResponseType;
import com.aparovich.barterspot.manager.PagesManager;

import javax.servlet.http.HttpServletRequest;

import static com.aparovich.barterspot.command.util.CommandConstant.*;

/**
 * Created by Maxim on 14.03.2017.
 */
public class LogoutCommand implements Command {

    @Override
    public PageNavigator execute(HttpServletRequest request) {
        request.getSession().invalidate();
        return new PageNavigator(PagesManager.getProperty(GOTO_MAIN), ResponseType.REDIRECT);
    }
}
