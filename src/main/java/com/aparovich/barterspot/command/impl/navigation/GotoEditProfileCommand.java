package com.aparovich.barterspot.command.impl.navigation;

import com.aparovich.barterspot.command.Command;
import com.aparovich.barterspot.command.util.PageNavigator;
import com.aparovich.barterspot.command.util.ResponseType;
import com.aparovich.barterspot.manager.PagesManager;

import javax.servlet.http.HttpServletRequest;

import static com.aparovich.barterspot.command.util.CommandConstant.*;

/**
 * Class provides navigation to page "Edit Profile".
 *
 * @author Maxim Aparovich
 * @see Command
 */
public class GotoEditProfileCommand implements Command {

    /**
     * Sets {@link PageNavigator} and {@link HttpServletRequest} properties in order to provide
     * correct navigation to "Edit Profile" page.
     *
     * @param   request wraps http request from client to server
     * @return  if no errors occurred instance of {@link PageNavigator} with suitable parameters:
     *          <ul>
     *              <li>Response {@code 'url'} the page, taken from pages property file via
     *              {@link PagesManager#getProperty(String)}</li>
     *              <li>Response type {@link ResponseType#FORWARD}</li>
     *          </ul>
     *          <p>suitable error page otherwise.
     * @see PageNavigator
     * @see PagesManager
     * @see ResponseType
     * @see com.aparovich.barterspot.command.util.CommandConstant
     */
    @Override
    public PageNavigator execute(HttpServletRequest request) {
        return new PageNavigator(PagesManager.getProperty(PAGE_EDIT_PROFILE), ResponseType.FORWARD);
    }
}
