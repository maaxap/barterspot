package com.aparovich.barterspot.command.impl.navigation;

import com.aparovich.barterspot.command.Command;
import com.aparovich.barterspot.command.util.PageNavigator;
import com.aparovich.barterspot.command.util.ResponseType;
import com.aparovich.barterspot.logic.LotLogic;
import com.aparovich.barterspot.model.util.LotStateType;
import com.aparovich.barterspot.model.bean.Lot;
import com.aparovich.barterspot.model.bean.User;
import com.aparovich.barterspot.manager.PagesManager;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

import static com.aparovich.barterspot.command.util.CommandConstant.*;

/**
 * Class provides navigation to page <i>Profile</i>.
 *
 * @author Maxim Aparovich
 * @see Command
 */
public class GotoProfileCommand implements Command {

    /**
     * Maximal number of items on page.
     */
    private static final int PER_PAGE = 4;

    /**
     * Sets {@link PageNavigator} and {@link HttpServletRequest} properties in order to provide
     * correct navigation to profile page.
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
     */
    @Override
    public PageNavigator execute(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(USER);

        //Setting users ready items.
        List<Lot> items = LotLogic.findByUser(user, LotStateType.READY);
        request.setAttribute(READY_SIZE, items.size());
        if(items.size() > PER_PAGE) {
            items = items.subList(0, PER_PAGE);
        }
        request.setAttribute(READY, items);

        //Setting users selling out items.
        items = LotLogic.findByUser(user, LotStateType.SELLING_OUT);
        request.setAttribute(SELLING_OUT_SIZE, items.size());
        if(items.size() > PER_PAGE) {
            items = items.subList(0, PER_PAGE);
        }
        request.setAttribute(SELLING_OUT, items);

        //Setting users purchased items.
        items = LotLogic.findByUser(user, LotStateType.PURCHASED);
        request.setAttribute(PURCHASED_SIZE, items.size());
        if(items.size() > PER_PAGE) {
            items = items.subList(0, PER_PAGE);
        }
        request.setAttribute(PURCHASED, items);

        //Setting users finished items.
        items = LotLogic.findByUser(user, LotStateType.FINISHED);
        request.setAttribute(FINISHED_SIZE, items.size());
        if(items.size() > PER_PAGE) {
            items = items.subList(0, PER_PAGE);
        }
        request.setAttribute(FINISHED, items);

        return new PageNavigator(PagesManager.getProperty(PAGE_PROFILE), ResponseType.FORWARD);
    }
}
