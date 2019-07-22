package com.aparovich.barterspot.logic;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Maxim
 */
public class ImageLogicTest {

    private static final String EMPTY_STRING = "";

    @Test
    public void findFile() throws Exception {
        assertFalse(ImageLogic.findFile(EMPTY_STRING, EMPTY_STRING).exists());
    }

    @Test
    public void placeholder() throws Exception {
        assertFalse(ImageLogic.placeholder().exists());
    }

}