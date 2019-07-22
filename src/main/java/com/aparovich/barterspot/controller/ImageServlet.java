package com.aparovich.barterspot.controller;

import com.aparovich.barterspot.logic.ImageLogic;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static com.aparovich.barterspot.command.util.CommandConstant.INIT_PARAM_UPLOAD_DIRECTORY;

/**
 * The class provides showing images on jsp pages.
 *
 * @author Maxim Aparovich
 * @see HttpServlet
 * @see WebServlet
 */
@WebServlet(name = "ImageServlet", urlPatterns = {"/image/*"}, loadOnStartup = 2)
public class ImageServlet extends HttpServlet {

    /**
     * Processes request sent by POST method. Is unsupported in this {@link HttpServlet}.
     *
     * @param request  request from client to get parameters to work with
     * @param response response to client with parameters to work with on client side
     * @see HttpServletRequest
     * @see HttpServletResponse
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        throw new UnsupportedOperationException("Allowed only GET requests for this servlet.");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String filename = request.getPathInfo();
        File file = ImageLogic.findFile(getServletContext().getInitParameter(INIT_PARAM_UPLOAD_DIRECTORY), filename);

        if(!file.exists()) {
            file = ImageLogic.placeholder();
        }

        response.setHeader("Content-Type", getServletContext().getMimeType(filename));
        response.setHeader("Content-Length", String.valueOf(file.length()));
        response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
        Files.copy(file.toPath(), response.getOutputStream());
    }
}
