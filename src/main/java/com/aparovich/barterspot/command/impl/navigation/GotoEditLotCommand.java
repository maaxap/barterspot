package com.aparovich.barterspot.command.impl.navigation;

import com.aparovich.barterspot.command.Command;
import com.aparovich.barterspot.command.util.PageNavigator;
import com.aparovich.barterspot.command.util.ResponseType;
import com.aparovich.barterspot.logic.CategoryLogic;
import com.aparovich.barterspot.logic.LotLogic;
import com.aparovich.barterspot.command.util.PageMessage;
import com.aparovich.barterspot.command.util.PageMessageType;
import com.aparovich.barterspot.model.util.RoleType;
import com.aparovich.barterspot.model.bean.Lot;
import com.aparovich.barterspot.model.bean.User;
import com.aparovich.barterspot.manager.PagesManager;
import com.aparovich.barterspot.validator.ParametersValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static com.aparovich.barterspot.command.util.CommandConstant.*;

/**
 * Class provides navigation to page "Edit Lot".
 *
 * @author Maxim Aparovich
 * @see Command
 */
public class GotoEditLotCommand implements Command {

    /**
     * Instance of {@link Logger}, used for logging errors, warns and etc.
     */
    private static final Logger LOGGER = LogManager.getLogger(GotoEditLotCommand.class);

    /**
     * Sets {@link PageNavigator} and {@link HttpServletRequest} properties in order to provide
     * correct navigation to "Edit Lot" page.
     * <p>Validates {@link HttpServletRequest} parameter {@code 'id'}. Sets {@link HttpServletRequest}
     * attributes {@code 'lot'} and {@code 'categories'}. Checks if requested lot exists and if
     * access was authorized; put danger {@link PageMessage} on page otherwise.
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
     * @see Lot
     * @see User
     * @see PageMessage
     * @see com.aparovich.barterspot.command.util.CommandConstant
     */
    @Override
    public PageNavigator execute(HttpServletRequest request) {
        PageMessage message = new PageMessage((String) request.getSession().getAttribute(LOCALE));
        Lot lot = null;
        String lotId = request.getParameter(ID);
        User user = (User) request.getSession().getAttribute(USER);


        if(ParametersValidator.checkIntegerFormat(lotId)) {
            lot = LotLogic.findById(Long.valueOf(lotId));
        }

        //Checking if lot exists in request scope.
        if(lot == null) {
            message.setParameters(NOT_FOUND_LOT, PageMessageType.DANGER);
            request.getSession().setAttribute(MESSAGE, message);
            LOGGER.log(Level.ERROR, "Lot not found.");
            return new PageNavigator(PagesManager.getProperty(GOTO_LOTS), ResponseType.FORWARD);
        }

        //Checking if user has permissions to edit lots.
        if(!(user.getId().equals(lot.getUser().getId()) || RoleType.ADMIN.equals(user.getRole()))) {
            LOGGER.log(Level.ERROR, "Unauthorized access attempt.");
            return new PageNavigator(PagesManager.getProperty(GOTO_ERROR_403), ResponseType.FORWARD);
        }

        //Setting page parameters.
        request.setAttribute(CATEGORIES, CategoryLogic.findAll());
        request.setAttribute(LOT, lot);
        return new PageNavigator(PagesManager.getProperty(PAGE_EDIT_LOT), ResponseType.FORWARD);
    }
}
