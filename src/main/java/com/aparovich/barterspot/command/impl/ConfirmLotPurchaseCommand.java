package com.aparovich.barterspot.command.impl;

import com.aparovich.barterspot.command.Command;
import com.aparovich.barterspot.command.util.PageNavigator;
import com.aparovich.barterspot.command.util.ResponseType;
import com.aparovich.barterspot.logic.BidLogic;
import com.aparovich.barterspot.logic.LotLogic;
import com.aparovich.barterspot.logic.exception.LogicException;
import com.aparovich.barterspot.command.util.PageMessage;
import com.aparovich.barterspot.command.util.PageMessageType;
import com.aparovich.barterspot.manager.PagesManager;
import com.aparovich.barterspot.model.bean.Bid;
import com.aparovich.barterspot.model.bean.Lot;
import com.aparovich.barterspot.validator.ParametersValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;
import java.util.List;

import static com.aparovich.barterspot.command.util.CommandConstant.*;

/**
 * Created by Maxim on 01.05.2017
 */
public class ConfirmLotPurchaseCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(ConfirmLotPurchaseCommand.class);

    @Override
    public PageNavigator execute(HttpServletRequest request) {
        PageMessage message = new PageMessage((String) request.getSession().getAttribute(LOCALE));
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

        if(bids.isEmpty()) {
            message.setParameters(NOT_FOUND_LOT_BIDS, PageMessageType.DANGER);
            request.getSession().setAttribute(MESSAGE, message);
            LOGGER.log(Level.ERROR, "Lot bids was not found.");
            return new PageNavigator(PagesManager.getProperty(GOTO_PROFILE), ResponseType.REDIRECT);
        }

        try {
            lot.setFinalPrice(bids.iterator().next().getBid());
            lot.setDeletedAt(LocalDateTime.now());
            LotLogic.update(lot);
            BidLogic.deleteAll(bids);
        } catch (LogicException e) {
            message.setParameters(DELETE_FAIL, PageMessageType.DANGER);
            request.getSession().setAttribute(MESSAGE, message);
            LOGGER.log(Level.ERROR, e.getMessage());
            return new PageNavigator(PagesManager.getProperty(GOTO_PROFILE), ResponseType.REDIRECT);
        }
        return new PageNavigator(PagesManager.getProperty(GOTO_PAYMENT), ResponseType.REDIRECT);
    }
}
