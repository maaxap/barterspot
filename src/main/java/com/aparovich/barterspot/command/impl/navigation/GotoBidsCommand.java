package com.aparovich.barterspot.command.impl.navigation;

import com.aparovich.barterspot.command.Command;
import com.aparovich.barterspot.command.util.PageNavigator;
import com.aparovich.barterspot.command.util.ResponseType;
import com.aparovich.barterspot.logic.BidLogic;
import com.aparovich.barterspot.model.bean.Bid;
import com.aparovich.barterspot.model.bean.User;
import com.aparovich.barterspot.manager.PagesManager;
import com.aparovich.barterspot.validator.ParametersValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

import static com.aparovich.barterspot.command.util.CommandConstant.*;

/**
 * Class provides navigation to page "Bids".
 *
 * @author Maxim Aparovich
 * @see Command
 */
public class GotoBidsCommand implements Command {

    /**
     * Instance of {@link Logger}, used for logging errors, warns and etc.
     */
    private static final Logger LOGGER = LogManager.getLogger(GotoBidsCommand.class);

    /**
     * Maximal number of items on "Bids "page.
     */
    private static final int PER_PAGE = 10;

    /**
     * Sets {@link PageNavigator} and {@link HttpServletRequest} properties in order to provide
     * correct navigation to "Bids" page. Sets attribute "items", which contains all user's bids.
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
     * @see Bid
     * @see List
     * @see com.aparovich.barterspot.command.util.CommandConstant
     */
    @Override
    public PageNavigator execute(HttpServletRequest request) {
        String page = request.getParameter(PAGE);
        User user = (User) request.getSession().getAttribute(USER);
        List items = BidLogic.findByUser(user);

        //Pagination.
        items = paginate(request, items, PER_PAGE);

        //Setting page parameters.
        request.setAttribute(ITEMS, items);

        return new PageNavigator(PagesManager.getProperty(PAGE_BIDS), ResponseType.FORWARD);
    }
}
