package com.aparovich.barterspot.command.impl.navigation;

import com.aparovich.barterspot.command.Command;
import com.aparovich.barterspot.command.util.PageNavigator;
import com.aparovich.barterspot.command.util.ResponseType;
import com.aparovich.barterspot.logic.CategoryLogic;
import com.aparovich.barterspot.logic.LotLogic;
import com.aparovich.barterspot.command.util.PageMessage;
import com.aparovich.barterspot.model.util.LotStateType;
import com.aparovich.barterspot.model.bean.Category;
import com.aparovich.barterspot.model.bean.Lot;
import com.aparovich.barterspot.manager.PagesManager;
import com.aparovich.barterspot.model.bean.User;
import com.aparovich.barterspot.validator.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static com.aparovich.barterspot.command.util.CommandConstant.*;

/**
 * Class provides navigation to page <i>Lots</i>.
 *
 * @author Maxim Aparovich
 * @see Command
 */
public class GotoLotsCommand implements Command {

    /**
     * Instance of {@link Logger}, used for logging errors, warns and etc.
     */
    private static final Logger LOGGER = LogManager.getLogger(GotoLotsCommand.class);

    /**
     * Maximal number of items on page.
     */
    private static final int PER_PAGE = 4;

    /**
     * Sets {@link PageNavigator} and {@link HttpServletRequest} properties in order to provide
     * correct navigation to <i>Lots</i> page.
     * <p>Validates {@link HttpServletRequest} parameter <i>id</i>. Sets all {@link HttpServletRequest}
     * attributes.
     *
     * @param   request wraps http request from client to server
     * @return  if no errors occurred instance of {@link PageNavigator} with suitable parameters:
     *          <ul>
     *              <li>Response <i>url</i> the page, taken from pages property file via
     *              {@link PagesManager#getProperty(String)}</li>
     *              <li>Response type {@link ResponseType#FORWARD}</li>
     *          </ul>
     *          <p>suitable error page otherwise.
     * @see PageNavigator
     * @see PagesManager
     * @see ResponseType
     * @see Lot
     * @see User
     * @see PageMessage
     * @see com.aparovich.barterspot.command.util.CommandConstant
     */
    @Override
    public PageNavigator execute(HttpServletRequest request) {
        List items = new ArrayList<>();
        User user = (User) request.getSession().getAttribute(USER);
        String type = request.getParameter(TYPE);

        //Checking if type parameter was passed. If yes - selecting users according to type;
        //selecting lots according to category if it is exists or selecting all lots otherwise.
        if (Validator.isEnumContains(type, LotStateType.values())) {
            LotStateType state = LotStateType.valueOf(type.trim().replace('-', '_').toUpperCase());
            items = LotLogic.findByUser(user, state);
            request.setAttribute(TYPE, type);
        } else {
            String categoryName = request.getParameter(CATEGORY);
            Category category = CategoryLogic.findByName(categoryName);
            if (category != null) {
                items = LotLogic.findByCategory(category);
                request.setAttribute(CATEGORY, category.getName());
            } else {
                items = LotLogic.findAll();
            }
            request.setAttribute(CATEGORIES, CategoryLogic.findAll());
        }

        items = paginate(request, items, PER_PAGE);

        //Setting page parameters.
        request.setAttribute(ITEMS, items);

        return new PageNavigator(PagesManager.getProperty(PAGE_LOTS), ResponseType.FORWARD);
    }
}