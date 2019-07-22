package com.aparovich.barterspot.controller;

import com.aparovich.barterspot.command.Command;
import com.aparovich.barterspot.command.util.PageNavigator;
import com.aparovich.barterspot.command.util.ResponseType;
import com.aparovich.barterspot.command.factory.CommandFactory;
import com.aparovich.barterspot.manager.PagesManager;
import com.aparovich.barterspot.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.aparovich.barterspot.controller.util.ControllerConstant.PAGE_MAIN;

/**
 * The class provides controller for client requests at MVC pattern of application.
 *
 * @author Maxim Aparovich
 */
@WebServlet(name = "MainServlet", urlPatterns = {"/main"}, loadOnStartup = 1)
public class MainServlet extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(MainServlet.class);


    /**
     * Processes request sent by POST method.
     *
     * @param request  request from client to get parameters to work with
     * @param response response to client with parameters to work with on client side
     * @throws IOException      if an input or output error is detected when the servlet handles the request
     * @throws ServletException if the request could not be handled
     * @see HttpServletRequest
     * @see HttpServletResponse
     * @see #processRequest(HttpServletRequest, HttpServletResponse)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.processRequest(request, response);
    }

    /**
     * Processes request sent by GET method.
     *
     * @param request  request from client to get parameters to work with
     * @param response response to client with parameters to work with on client side
     * @throws IOException      if an input or output error is detected when the servlet handles the request
     * @throws ServletException if the request could not be handled
     * @see HttpServletRequest
     * @see HttpServletResponse
     * @see #processRequest(HttpServletRequest, HttpServletResponse)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.processRequest(request, response);
    }

    /**
     * Processes requests.
     * Invokes {@link CommandFactory#defineCommand(HttpServletRequest)} and passes {@link HttpServletRequest}
     * for further processing.
     *
     * @param request  request from client to get parameters to work with
     * @param response response to client with parameters to work with on client side
     * @throws IOException      if an input or output error is detected when the servlet handles the request
     * @throws ServletException if the request could not be handled
     * @see CommandFactory#defineCommand(HttpServletRequest)
     */
    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Command command = CommandFactory.defineCommand(request);
        PageNavigator pageNavigator = command.execute(request);

        if(pageNavigator.getPage() == null) {
            pageNavigator.setPage(PagesManager.getProperty(PAGE_MAIN));
            pageNavigator.setType(ResponseType.FORWARD);
        }

        switch (pageNavigator.getType()) {
            case FORWARD:
                RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(pageNavigator.getPage());
                dispatcher.forward(request, response);
                break;
            case REDIRECT:
                response.sendRedirect(pageNavigator.getPage());
                break;
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        ConnectionPool.getInstance().closePool();
    }

}
