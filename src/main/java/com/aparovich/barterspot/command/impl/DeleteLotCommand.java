package com.aparovich.barterspot.command.impl;

import com.aparovich.barterspot.command.Command;
import com.aparovich.barterspot.command.util.PageNavigator;
import com.aparovich.barterspot.command.util.ResponseType;
import com.aparovich.barterspot.logic.ImageLogic;
import com.aparovich.barterspot.logic.LotLogic;
import com.aparovich.barterspot.logic.exception.LogicException;
import com.aparovich.barterspot.command.util.PageMessage;
import com.aparovich.barterspot.command.util.PageMessageType;
import com.aparovich.barterspot.model.bean.Lot;
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
public class DeleteLotCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(DeleteLotCommand.class);

    @Override
    public PageNavigator execute(HttpServletRequest request) {
        PageMessage message = new PageMessage((String) request.getSession().getAttribute(LOCALE));
        Lot lot = null;
        String lotId = request.getParameter(ID);
        String uploadPath = request.getServletContext().getInitParameter(INIT_PARAM_UPLOAD_DIRECTORY);

        if(ParametersValidator.checkIntegerFormat(lotId)) {
            lot = LotLogic.findById(Long.valueOf(lotId));
        }

        if(lot == null) {
            message.setParameters(NOT_FOUND_LOT, PageMessageType.DANGER);
            request.getSession().setAttribute(MESSAGE, message);
            LOGGER.log(Level.ERROR, "Lot was not found.");
            return new PageNavigator(PagesManager.getProperty(GOTO_ERROR_404), ResponseType.REDIRECT);
        }

        try {
            LotLogic.delete(lot);
            ImageLogic.delete(lot, uploadPath);
        } catch (LogicException e) {
            message.setParameters(DELETE_FAIL, PageMessageType.DANGER);
            request.getSession().setAttribute(MESSAGE, message);
            LOGGER.log(Level.ERROR, e.getMessage());
            return new PageNavigator(PagesManager.getProperty(GOTO_LOT, lot.getId().toString()), ResponseType.FORWARD);
        }
        return new PageNavigator(PagesManager.getProperty(GOTO_LOTS), ResponseType.REDIRECT);
    }
}
