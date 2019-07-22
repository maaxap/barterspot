package com.aparovich.barterspot.command.impl.navigation;

import com.aparovich.barterspot.command.Command;
import com.aparovich.barterspot.command.util.PageNavigator;
import com.aparovich.barterspot.command.util.ResponseType;
import com.aparovich.barterspot.logic.BidLogic;
import com.aparovich.barterspot.logic.CategoryLogic;
import com.aparovich.barterspot.logic.LotLogic;
import com.aparovich.barterspot.logic.UserLogic;
import com.aparovich.barterspot.manager.PagesManager;
import com.aparovich.barterspot.model.bean.Category;
import com.aparovich.barterspot.model.util.ModelType;
import com.aparovich.barterspot.model.util.RoleType;
import com.aparovich.barterspot.model.bean.Bid;
import com.aparovich.barterspot.model.bean.Lot;
import com.aparovich.barterspot.model.bean.User;
import com.aparovich.barterspot.validator.ParametersValidator;
import com.aparovich.barterspot.validator.Validator;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;

import static com.aparovich.barterspot.command.util.CommandConstant.*;

/**
 * Class provides navigation to page "Management".
 *
 * @author Maxim Aparovich
 * @see Command
 */
public class GotoAdminCommand implements Command {

    /**
     * Maximal number of items on page.
     */
    private static final int PER_PAGE = 5;


    /**
     * Sets {@link PageNavigator} and {@link HttpServletRequest} properties in order to provide
     * correct navigation to "Management" page.
     * <p>Gets parameters "model" and "page" and provides it's handling:
     * <ul>
     *     <li>
     *         "model": validates parameter via {@link Validator}. Then, according to model,
     *         sets suitable value to variable items({@link List}), which passed on the page.
     *     </li>
     *     <li>
     *         "page": parameter is used for pagination.
     *     </li>
     * </ul>
     * <p>Also sets parameters "bidsSize", "usersSize" and "lotsSize".
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
     * @see User
     * @see Lot
     * @see Bid
     * @see Validator#isEnumContains(String, Enum[])
     * @see com.aparovich.barterspot.command.util.CommandConstant
     */
    @Override
    public PageNavigator execute(HttpServletRequest request) {
        List items = new ArrayList();
        String model = request.getParameter(MODEL);
        String page = request.getParameter(PAGE);
        List<Bid> bids = BidLogic.findAll();
        List<Lot> lots = LotLogic.findAll();
        List<User> users = UserLogic.findByRole(RoleType.CLIENT);
        List<Category> categories = CategoryLogic.findAll();

        //Getting items.
        if(Validator.isEnumContains(model, ModelType.values())) {
            ModelType modelType = ModelType.valueOf(model.toUpperCase());
            switch (modelType) {
                case USER:
                    items = users;
                    request.setAttribute(MODEL, ModelType.USER.toString().toLowerCase());
                    break;
                case BID:
                    items = bids;
                    request.setAttribute(MODEL, ModelType.BID.toString().toLowerCase());
                    break;
                case CATEGORY:
                    items = categories;
                    request.setAttribute(MODEL, ModelType.CATEGORY.toString().toLowerCase());
                    break;
            }
        }

        //Pagination.
        items = paginate(request, items, PER_PAGE);

        //Setting page parameters.
        request.setAttribute(ITEMS, items);
        request.setAttribute(BIDS_SIZE, bids.size());
        request.setAttribute(LOTS_SIZE, lots.size());
        request.setAttribute(USERS_SIZE, users.size());
        request.setAttribute(CATEGORIES_SIZE, categories.size());

        return new PageNavigator(PagesManager.getProperty(PAGE_ADMIN), ResponseType.FORWARD);
    }


}
