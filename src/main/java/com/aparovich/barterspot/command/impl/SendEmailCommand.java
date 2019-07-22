package com.aparovich.barterspot.command.impl;

import com.aparovich.barterspot.command.Command;
import com.aparovich.barterspot.command.util.PageNavigator;
import com.aparovich.barterspot.command.util.ResponseType;
import com.aparovich.barterspot.logic.UserLogic;
import com.aparovich.barterspot.logic.exception.LogicException;
import com.aparovich.barterspot.command.util.PageMessage;
import com.aparovich.barterspot.command.util.PageMessageType;
import com.aparovich.barterspot.manager.PagesManager;
import com.aparovich.barterspot.validator.ParametersValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static com.aparovich.barterspot.command.util.CommandConstant.*;

/**
 * Created by Maxim on 16.05.2017
 */
public class SendEmailCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(SendEmailCommand.class);
    @Override
    public PageNavigator execute(HttpServletRequest request) {
        PageMessage message = new PageMessage((String) request.getSession().getAttribute(LOCALE));
        String receiverEmail = request.getParameter(RECEIVER_EMAIL);
        String password = request.getParameter(PASSWORD);
        String senderEmail = request.getParameter(SENDER_EMAIL);
        String subject = request.getParameter(SUBJECT);
        String text = request.getParameter(TEXT);

        if(!ParametersValidator.checkEmailFormat(senderEmail) || !ParametersValidator.checkEmailFormat(receiverEmail)) {
            message.setParameters(WRONG_INPUT_FORMAT, PageMessageType.DANGER);
            request.getSession().setAttribute(MESSAGE, message);
            LOGGER.log(Level.ERROR, "Wrong email format.");
            return new PageNavigator(PagesManager.getProperty(GOTO_SEND_EMAIL), ResponseType.FORWARD);
        }

        try {
            UserLogic.sendEmail(senderEmail, password, receiverEmail, subject, text);
        } catch (LogicException e) {
            message.setParameters(WRONG_INPUT_FORMAT, PageMessageType.DANGER);
            request.getSession().setAttribute(MESSAGE, message);
            LOGGER.log(Level.ERROR, e.getMessage());
            return new PageNavigator(PagesManager.getProperty(GOTO_SEND_EMAIL), ResponseType.FORWARD);
        }

        //Success message.
        message.setParameters(SEND_EMAIL_SUCCESS, PageMessageType.DANGER);
        request.getSession().setAttribute(MESSAGE, "");

        return new PageNavigator(PagesManager.getProperty(GOTO_SEND_EMAIL), ResponseType.REDIRECT);
    }
}
