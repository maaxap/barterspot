package com.aparovich.barterspot.command.impl.navigation;

import com.aparovich.barterspot.command.Command;
import com.aparovich.barterspot.command.util.PageNavigator;
import com.aparovich.barterspot.command.util.ResponseType;
import com.aparovich.barterspot.logic.LotLogic;
import com.aparovich.barterspot.logic.UserLogic;
import com.aparovich.barterspot.logic.exception.LogicException;
import com.aparovich.barterspot.manager.MessagesManager;
import com.aparovich.barterspot.model.util.LotStateType;
import com.aparovich.barterspot.model.bean.Lot;
import com.aparovich.barterspot.model.bean.User;
import com.aparovich.barterspot.manager.PagesManager;
import com.aparovich.barterspot.validator.ParametersValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

import static com.aparovich.barterspot.command.util.CommandConstant.*;

/**
 * Class provides navigation to page <i>User</i>.
 *
 * @author Maxim Aparovich
 * @see Command
 */
public class GotoUserCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(GotoUserCommand.class);

    /**
     * Maximal number of items on page.
     */
    private static final int PER_PAGE = 4;

    /**
     * Sets {@link PageNavigator} and {@link HttpServletRequest} properties in order to provide
     * correct navigation to user page.
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
        User user = null;
        String userId = request.getParameter(ID);

        //Checking if user id has compatible format.
        if(ParametersValidator.checkIntegerFormat(userId)) {
            user = UserLogic.findById(Long.valueOf(userId));
        }

        //Checking if user was not found.
        if(user == null) {
            request.getSession().setAttribute(MESSAGE, "");
            LOGGER.log(Level.ERROR, "User not found.");
            return new PageNavigator(PagesManager.getProperty(GOTO_MAIN), ResponseType.FORWARD);
        }

        //Selecting user's selling out lots.
        List<Lot> lots = LotLogic.findByUser(user, LotStateType.SELLING_OUT);
        request.setAttribute(SELLING_OUT_SIZE, lots.size());
        if(lots.size() > PER_PAGE) {
            lots = lots.subList(0, PER_PAGE);
        }

        //Setting page parameters.
        request.setAttribute(USER, user);
        request.setAttribute(SELLING_OUT, lots);

        return new PageNavigator(PagesManager.getProperty(PAGE_USER), ResponseType.FORWARD);
    }
}
