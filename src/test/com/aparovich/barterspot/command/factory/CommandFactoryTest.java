package com.aparovich.barterspot.command.factory;

import com.aparovich.barterspot.command.impl.navigation.GotoMainCommand;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.*;

/**
 * @author Maxim
 */
public class CommandFactoryTest {
    private static final String COMMAND = "command";
    private static final String ROLE = "role";

    @Test
    public void defineCommand() throws Exception {
        HttpServletRequest mockedRequest = Mockito.mock(HttpServletRequest.class);
        HttpSession mockedSession = Mockito.mock(HttpSession.class);

        Mockito.when(mockedRequest.getSession()).thenReturn(mockedSession);
        Mockito.when(mockedSession.getAttribute(ROLE)).thenReturn("admin");
        Mockito.when(mockedRequest.getParameter(COMMAND)).thenReturn("goto_main");

        assertTrue(CommandFactory.defineCommand(mockedRequest).getClass().equals(GotoMainCommand.class));
    }

}