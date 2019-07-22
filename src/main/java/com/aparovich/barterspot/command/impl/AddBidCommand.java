package com.aparovich.barterspot.command.impl;

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
import java.math.BigDecimal;

import static com.aparovich.barterspot.command.util.CommandConstant.*;

/**
 * Created by Maxim on 17.04.2017
 */
public class AddBidCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(AddBidCommand.class);

    @Override
    public PageNavigator execute(HttpServletRequest request) {
        PageMessage message = new PageMessage((String) request.getSession().getAttribute(LOCALE));
        System.out.println((String) request.getSession().getAttribute(LOCALE));
        Lot lot = null;
        Bid bid = new Bid();
        String bidString = request.getParameter(BID);
        String lotId = request.getParameter(ID);
        User user = (User) request.getSession().getAttribute(USER);

        if(ParametersValidator.checkIntegerFormat(lotId)) {
            lot = LotLogic.findById(Long.valueOf(lotId));
        }

        if(lot == null) {
            message.setParameters(NOT_FOUND_USER, PageMessageType.DANGER);
            request.getSession().setAttribute(MESSAGE, message);
            LOGGER.log(Level.ERROR, "Lot was not found.");
            return new PageNavigator(PagesManager.getProperty(GOTO_ERROR_404), ResponseType.REDIRECT);
        }

        //Creating bid if it has valid format and valid value.
        if(ParametersValidator.checkBigDecimalFormat(bidString) && !user.getBlocked()) {
            BigDecimal bidValue = BigDecimal.valueOf(Double.valueOf(bidString));
            bid.setBid(bidValue);
            bid.setLot(lot);
            bid.setUser(user);
            bid = BidLogic.create(bid);
        } else {
            bid = null;
        }

        //Checking if bid was not created.
        if(bid == null) {
            message.setParameters(NOT_CREATED_BID, PageMessageType.DANGER);
            request.getSession().setAttribute(MESSAGE, message);
            LOGGER.log(Level.ERROR, "Bid was not created.");
            return new PageNavigator(PagesManager.getProperty(GOTO_LOT, lot.getId().toString()), ResponseType.REDIRECT);
        }

        return new PageNavigator(PagesManager.getProperty(GOTO_LOT, lot.getId().toString()), ResponseType.REDIRECT);
    }
}
