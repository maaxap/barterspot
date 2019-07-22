package com.aparovich.barterspot.command.impl;

import com.aparovich.barterspot.command.Command;
import com.aparovich.barterspot.command.util.PageNavigator;
import com.aparovich.barterspot.command.util.ResponseType;
import com.aparovich.barterspot.logic.BidLogic;
import com.aparovich.barterspot.logic.exception.LogicException;
import com.aparovich.barterspot.command.util.PageMessage;
import com.aparovich.barterspot.command.util.PageMessageType;
import com.aparovich.barterspot.model.util.RoleType;
import com.aparovich.barterspot.model.bean.Bid;
import com.aparovich.barterspot.manager.PagesManager;
import com.aparovich.barterspot.validator.ParametersValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static com.aparovich.barterspot.command.util.CommandConstant.*;

/**
 * Created by Maxim on 18.04.2017
 */
public class DeleteBidCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(DeleteBidCommand.class);
    @Override
    public PageNavigator execute(HttpServletRequest request) {
        PageMessage message = new PageMessage((String) request.getSession().getAttribute(LOCALE));
        Bid bid = null;
        String bidId = request.getParameter(ID);

        if(ParametersValidator.checkNameFormat(bidId)) {
            bid = BidLogic.findById(Long.valueOf(bidId));
        }

        if(bid == null) {
            message.setParameters(NOT_FOUND_BID, PageMessageType.DANGER);
            request.getSession().setAttribute(MESSAGE, message);
            LOGGER.log(Level.ERROR, "Bid was not found.");
        } else {
            try {
                BidLogic.delete(bid);
            } catch (LogicException e) {
                request.getSession().setAttribute(MESSAGE, "");
                LOGGER.log(Level.ERROR, e.getMessage());
            }
        }

        String page = request.getParameter(PAGE);
        if (RoleType.ADMIN.getRole().equals(page)) {
            return new PageNavigator(PagesManager.getProperty(GOTO_ADMIN), ResponseType.REDIRECT);
        }

        return new PageNavigator(PagesManager.getProperty(GOTO_BIDS), ResponseType.REDIRECT);
    }
}
