package com.aparovich.barterspot.command.impl.navigation;

import com.aparovich.barterspot.command.Command;
import com.aparovich.barterspot.command.util.PageNavigator;
import com.aparovich.barterspot.command.util.ResponseType;
import com.aparovich.barterspot.logic.UserLogic;
import com.aparovich.barterspot.logic.exception.LogicException;
import com.aparovich.barterspot.manager.PagesManager;
import com.aparovich.barterspot.model.util.RoleType;
import com.aparovich.barterspot.model.bean.User;
import com.aparovich.barterspot.validator.ParametersValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static com.aparovich.barterspot.command.util.CommandConstant.*;

/**
 * Class provides navigation to page <i>Send email</i>.
 *
 * @author Maxim Aparovich
 * @see Command
 */
public class GotoSendEmailCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(GotoSendEmailCommand.class);

    /**
     * Sets {@link PageNavigator} and {@link HttpServletRequest} properties in order to provide
     * correct navigation to send email page.
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
        User sender = (User) request.getSession().getAttribute(USER);
        String receiverId = request.getParameter(RECEIVER_ID);
        User receiver = null;

        //Checking if receivers id is null - setting admin as receiver;
        //setting receiver according to id otherwise.
        if (receiverId == null) {
            receiver = UserLogic.findByRole(RoleType.ADMIN).iterator().next();
        } else if(ParametersValidator.checkIntegerFormat(receiverId)) {
            receiver = UserLogic.findById(Long.valueOf(receiverId));
        }

        //Checking if receiver was not found.
        if(receiver == null) {
            request.getSession().setAttribute(MESSAGE, "");
            LOGGER.log(Level.ERROR, "Receiver not found.");
            return new PageNavigator(PagesManager.getProperty(GOTO_MAIN), ResponseType.FORWARD);
        }

        //Setting page parameters.
        request.setAttribute(RECEIVER_EMAIL, receiver.getEmail());
        request.setAttribute(SENDER_EMAIL, sender.getEmail());

        return new PageNavigator(PagesManager.getProperty(PAGE_SEND_EMAIL), ResponseType.FORWARD);
    }
}
