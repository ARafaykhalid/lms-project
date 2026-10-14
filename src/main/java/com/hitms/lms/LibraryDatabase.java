package com.hitms.lms;

import java.util.HashMap;
import java.util.Map;

/**
 * A single, shared in-memory "connection" to the library catalogue.
 *
 * <p>This is a Singleton: no matter how often {@link #getInstance()} is called,
 * every caller receives the same object, so every part of the application sees
 * the same catalogue.</p>
 */
public class LibraryDatabase {

    /** The one and only instance. */
    private static LibraryDatabase instance;

    /** Item identifier mapped to title. */
    public final Map<String, String> records = new HashMap<>();

    /**
     * Hides the constructor so that no other class can create a second instance.
     */
    private LibraryDatabase() {
    }

    /**
     * Returns the shared database, creating it on the first call.
     *
     * @return the single shared database
     */
    public static synchronized LibraryDatabase getInstance() {
        if (instance == null) {
            instance = new LibraryDatabase();
        }
        return instance;
    }
}