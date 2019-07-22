package com.aparovich.barterspot.command.impl.navigation;

import com.aparovich.barterspot.command.Command;
import com.aparovich.barterspot.command.util.PageNavigator;
import com.aparovich.barterspot.command.util.ResponseType;
import com.aparovich.barterspot.logic.LotLogic;
import com.aparovich.barterspot.model.bean.Lot;
import com.aparovich.barterspot.manager.PagesManager;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.aparovich.barterspot.command.util.CommandConstant.*;

/**
 * Class provides navigation to page <i>Main</i>.
 *
 * @author Maxim Aparovich
 * @see Command
 */
public class GotoMainCommand implements Command {

    /**
     * Maximal number of items on page.
     */
    private static final int PER_PAGE = 4;

    /**
     * Sets {@link PageNavigator} and {@link HttpServletRequest} properties in order to provide
     * correct navigation to <i>Main</i> page.
     *
     * Gets list of lots, slices it and set {@code HttpServletRequest}'s parameter <i>PER_PAGE</i>
     * to specified by <i>PER_PAGE</i> number of lots.
     *
     * @param request wraps http request from client to server
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
     * @see com.aparovich.barterspot.command.util.CommandConstant
     * @see LotLogic
     */
    @Override
    public PageNavigator execute(HttpServletRequest request) {
        List<Lot> lots = LotLogic.findAll();
        if(lots.size() > PER_PAGE) {
            lots = lots.subList(0, PER_PAGE);
        }
        request.setAttribute(LOTS, lots);
        return new PageNavigator(PagesManager.getProperty(PAGE_MAIN), ResponseType.FORWARD);
    }


}
