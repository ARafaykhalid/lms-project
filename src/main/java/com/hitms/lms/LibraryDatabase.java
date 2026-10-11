package com.hitms.lms;

import java.util.HashMap;
import java.util.Map;

/**
 * A single, shared in-memory "connection" to the library catalogue.
 */
public class LibraryDatabase {

    private static LibraryDatabase instance;
    public final Map<String, String> records = new HashMap<>();

    private LibraryDatabase() {
    }

    public static synchronized LibraryDatabase getInstance() {
        if (instance == null) {
            instance = new LibraryDatabase();
        }
        return instance;
    }

    public static void main(String[] args) {
        LibraryDatabase db1 = LibraryDatabase.getInstance();
        LibraryDatabase db2 = LibraryDatabase.getInstance();
        db1.records.put("B-001", "Clean Code");
        System.out.println(db2.records);        // sees the same data 
        System.out.println(db1 == db2);          // true -- same object     
    }
}
