package com.aparovich.barterspot.command.impl.navigation;

import com.aparovich.barterspot.command.Command;
import com.aparovich.barterspot.command.util.PageNavigator;
import com.aparovich.barterspot.command.util.ResponseType;
import com.aparovich.barterspot.logic.CategoryLogic;
import com.aparovich.barterspot.manager.PagesManager;

import javax.servlet.http.HttpServletRequest;

import static com.aparovich.barterspot.command.util.CommandConstant.*;

/**
 * Class provides navigation to page <i>Add Lot</i>.
 *
 * @author Maxim Aparovich
 * @see Command
 */
public class GotoAddLotCommand implements Command {

    /**
     * Sets {@link PageNavigator} and {@link HttpServletRequest} parameters in order to provide
     * correct navigation to <i>Add Lot</i> page. Sets {@link HttpServletRequest} attribute {@code 'categories'}.
     *
     * @param   request wraps http request from client to server
     * @return  if no errors occurred instance of {@link PageNavigator} with suitable parameters:
     *          <ul>
     *              <li>Response {@code 'url'} the page, taken from pages property file via
     *              {@link PagesManager#getProperty(String)}</li>
     *              <li>Response type {@link ResponseType#FORWARD}</li>
     *          </ul>
     *          <p>suitable error page otherwise.
     *
     * @see PageNavigator
     * @see PagesManager
     * @see ResponseType
     * @see CategoryLogic
     * @see com.aparovich.barterspot.model.bean.Category
     * @see com.aparovich.barterspot.command.util.CommandConstant
     */
    @Override
    public PageNavigator execute(HttpServletRequest request) {
        request.setAttribute(CATEGORIES, CategoryLogic.findAll());
        return new PageNavigator(PagesManager.getProperty(PAGE_ADD_LOT), ResponseType.FORWARD);
    }
}
