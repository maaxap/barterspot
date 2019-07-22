package com.aparovich.barterspot.logic;

import com.aparovich.barterspot.logic.exception.LogicException;
import com.aparovich.barterspot.model.Model;
import com.aparovich.barterspot.model.bean.Lot;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

/**
 * Created by Maxim on 13.05.2017
 */
public class ImageLogic {
    private static final Logger LOGGER = LogManager.getLogger(ImageLogic.class);

    /**
     *  Dot symbol string constant.
     */
    private static final String DOT = ".";

    /**
     *  Array of available image extensions.
     */
    private static final String[] AVAILABLE_EXT = new String[]{"gif", "jpeg", "jpg", "png"};

    /**
     *  Suitable model's folders names.
     */
    private static final String LOTS_PATH = "lots";
    private static final String USERS_PATH = "users";

    /**
     * Placeholder file name.
     */
    private static final String PLACEHOLDER = "placeholder.jpg";

    /**
     * System property user.dir.
     *
     * @see System#getProperty(String)
     */
    private static final String PROPERTY_USER_DIRECTORY = "user.dir";

    public static void upload(FileItem item, Model model, String uploadPath) throws LogicException {
        if (item.getString().isEmpty()) {
            return;
        }

        if (model == null || model.getId() == null) {
            throw new LogicException("Image owning model is not set.");
        }

        String dirName = model.getId().toString();
        String path = null;

        if (model instanceof Lot) {
            path = uploadPath + File.separator + LOTS_PATH + File.separator + dirName;
        } else {
            path = uploadPath + File.separator + USERS_PATH + File.separator + dirName;
        }

        File fileDirectory = new File(path);
        if (!fileDirectory.exists()) {
            fileDirectory.mkdirs();
        }

        File[] files = fileDirectory.listFiles();
        if (files != null) {
            for (File file : files) {
                file.delete();
            }
        }

        String fileExtension = FilenameUtils.getExtension(item.getName());

        if (fileExtension == null || fileExtension.isEmpty() || Arrays.binarySearch(AVAILABLE_EXT, fileExtension.toLowerCase()) == -1) {
            throw new LogicException("Invalid or unsupported image extension.");
        }

        fileExtension = fileExtension.toLowerCase();

        String fileName = model.getId() + DOT + fileExtension;
        String storePath = path + File.separator + fileName;
        File file = new File(storePath);

        try {
            item.write(file);
        } catch (Exception e) {
            throw new LogicException("Error occurred while uploading image. " + e.getMessage());
        }
    }

    public static void delete(Model model, String uploadPath) throws LogicException {
        if (model == null || model.getId() == null) {
            throw new LogicException("Model owns image is not set.");
        }

        String dirName = model.getId().toString();
        String path = null;

        if (model instanceof Lot) {
            path = uploadPath + File.separator + LOTS_PATH + File.separator + dirName;
        } else {
            path = uploadPath + File.separator + USERS_PATH + File.separator + dirName;
        }

        File[] files = new File(path).listFiles();
        if (files != null) {
            for (File file : files) {
                file.delete();
            }
        }

    }

    public static File findFile(String uploadPath, String fileName) {
        int index = 0;
        File file = null;
        do {
            file = new File(uploadPath + "/", fileName + DOT + AVAILABLE_EXT[index++]);
        } while (!file.exists() && index != AVAILABLE_EXT.length);

        return file;
    }

    public static File placeholder() {
        return new File(System.getProperty(PROPERTY_USER_DIRECTORY) + File.separator + PLACEHOLDER);
    }
}
