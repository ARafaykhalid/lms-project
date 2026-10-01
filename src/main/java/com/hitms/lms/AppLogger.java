package com.hitms.lms;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Writes application events to a log file instead of the console.
 */
public class AppLogger {

    private static final Logger LOGGER = Logger.getLogger(AppLogger.class.getName());

    /**
     * Sends a log line to app.log.
     *
     * @param message the event to record
     * @throws IOException if the log file cannot be opened
     */
    public static void log(String message) throws IOException {
        FileHandler handler = new FileHandler("app.log", true);
        handler.setFormatter(new SimpleFormatter());
        LOGGER.addHandler(handler);
        LOGGER.setLevel(Level.INFO);
        LOGGER.info(message);
        handler.close();
    }
}
