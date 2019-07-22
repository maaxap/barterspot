package com.aparovich.barterspot.command.impl;

import com.aparovich.barterspot.command.Command;
import com.aparovich.barterspot.command.util.PageNavigator;
import com.aparovich.barterspot.command.util.ResponseType;
import com.aparovich.barterspot.logic.BidLogic;
import com.aparovich.barterspot.logic.LotLogic;
import com.aparovich.barterspot.logic.UserLogic;
import com.aparovich.barterspot.logic.exception.LogicException;
import com.aparovich.barterspot.command.util.PageMessage;
import com.aparovich.barterspot.command.util.PageMessageType;
import com.aparovich.barterspot.manager.PagesManager;
import com.aparovich.barterspot.model.bean.Bid;
import com.aparovich.barterspot.model.bean.Lot;
import com.aparovich.barterspot.model.bean.User;
import com.aparovich.barterspot.model.util.RoleType;
import com.aparovich.barterspot.validator.ParametersValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

import static com.aparovich.barterspot.command.util.CommandConstant.*;

/**
 * Created by Maxim on 01.05.2017
 */
public class DenyLotPurchaseCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(DenyLotPurchaseCommand.class);
    @Override
    public PageNavigator execute(HttpServletRequest request) {
        PageMessage message = new PageMessage((String) request.getSession().getAttribute(LOCALE));
        User user = (User) request.getSession().getAttribute(USER);
        Lot lot = null;
        String lotId = request.getParameter(ID);

        if(ParametersValidator.checkIntegerFormat(lotId)) {
            lot = LotLogic.findById(Long.parseLong(lotId));
        }

        if(lot == null) {
            message.setParameters(NOT_FOUND_LOT, PageMessageType.DANGER);
            request.getSession().setAttribute(MESSAGE, message);
            LOGGER.log(Level.ERROR, "Lot was not found.");
            return new PageNavigator(PagesManager.getProperty(GOTO_PROFILE), ResponseType.REDIRECT);
        }

        List<Bid> bids = BidLogic.findByLot(lot);

        try {
            BidLogic.deleteAll(bids);

            if(user != null && user.getRole() != RoleType.ADMIN) {
                user.setBlocked(true);
                UserLogic.update(user);
            }

        } catch (LogicException e) {
            message.setParameters(DELETE_FAIL, PageMessageType.DANGER);
            request.getSession().setAttribute(MESSAGE, message);
            LOGGER.log(Level.ERROR, e.getMessage());
        }
        return new PageNavigator(PagesManager.getProperty(GOTO_PROFILE), ResponseType.REDIRECT);
    }
}
