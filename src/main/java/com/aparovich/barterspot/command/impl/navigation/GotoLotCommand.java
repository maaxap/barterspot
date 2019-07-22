package com.aparovich.barterspot.command.impl.navigation;

import com.aparovich.barterspot.command.Command;
import com.aparovich.barterspot.command.util.PageNavigator;
import com.aparovich.barterspot.command.util.ResponseType;
import com.aparovich.barterspot.logic.BidLogic;
import com.aparovich.barterspot.logic.LotLogic;
import com.aparovich.barterspot.command.util.PageMessage;
import com.aparovich.barterspot.command.util.PageMessageType;
import com.aparovich.barterspot.model.bean.Bid;
import com.aparovich.barterspot.model.bean.Lot;
import com.aparovich.barterspot.model.bean.User;
import com.aparovich.barterspot.manager.PagesManager;
import com.aparovich.barterspot.validator.ParametersValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.aparovich.barterspot.command.util.CommandConstant.*;

/**
 * Class provides navigation to page "Lot".
 *
 * @author Maxim Aparovich
 * @see Command
 */
public class GotoLotCommand implements Command {

    /**
     * Instance of {@link Logger}, used for logging errors, warns and etc.
     */
    private static final Logger LOGGER = LogManager.getLogger(GotoLotCommand.class);

    /**
     * Border between periods, when {@link com.aparovich.barterspot.model.bean.Lot}
     * is available to be bought and not available.
     */
    private static final int DAYS_LEFT_BORDER = 0;

    /**
     * Sets {@link PageNavigator} and {@link HttpServletRequest} properties in order to provide
     * correct navigation to "Edit Lot" page.
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
        PageMessage message = new PageMessage((String) request.getSession().getAttribute(LOCALE));
        Lot lot = null;
        String lotId = request.getParameter(ID);
        User user = (User) request.getSession().getAttribute(USER);

        //Checking if lot id was passed in compatible format.
        if(ParametersValidator.checkIntegerFormat(lotId)) {
            lot = LotLogic.findById(Long.valueOf(lotId));
        }

        //Checking if lot was found by id.
        if(lot == null) {
            message.setParameters(NOT_FOUND_LOT, PageMessageType.DANGER);
            request.getSession().setAttribute(MESSAGE, message);
            LOGGER.log(Level.ERROR, "Lot not found.");
            return new PageNavigator(PagesManager.getProperty(GOTO_LOTS), ResponseType.FORWARD);
        }

        long daysLeft = ChronoUnit.DAYS.between(LocalDate.now(), lot.getFinishing().toLocalDate());
        List<Bid> lotBids = BidLogic.findByLot(lot);

        //If lot has bids checking if user is a winner and setting suitable page parameters.
        if(!lotBids.isEmpty()) {
            if (daysLeft < DAYS_LEFT_BORDER && user.getId().equals(lotBids.iterator().next().getUser().getId())) {
                request.setAttribute(IS_WINNER, true);
            }
            request.setAttribute(LAST_BID, lotBids.iterator().next().getBid());
            request.setAttribute(BIDS_AMOUNT, lotBids.size());
        }   else {
            request.setAttribute(LAST_BID, lot.getDefaultPrice());
        }

        //Setting page parameters.
        request.setAttribute(DAYS_LEFT, daysLeft);
        request.setAttribute(LOT, lot);

        return new PageNavigator(PagesManager.getProperty(PAGE_LOT), ResponseType.FORWARD);
    }
}
