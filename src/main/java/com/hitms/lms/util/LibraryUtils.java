package com.hitms.lms.util;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Provides utility methods used by the library application.
 */
public class LibraryUtils {

    /**
     * Prevents instantiation of this utility class.
     */
    private LibraryUtils() {
        // Utility class.
    }

    /**
     * Formats a book title into the standard library title format.
     *
     * @param title the book title to format
     * @return the formatted book title
     */
    public static String formatTitle(String title) {
        String trimmed = title.strip().toLowerCase();
        String[] words = trimmed.split(" ");
        StringBuilder result = new StringBuilder();
        for (String w : words) {
            if (!w.isEmpty()) {
                result.append(Character.toUpperCase(w.charAt(0))).append(w.substring(1)).append(" ");
            }
        }
        return result.toString().strip();
    }

    /**
     * Finds a member by their ID.
     *
     * @param id the ID of the member to find
     */
    public static void findMemberById(int id) {
        System.out.println("Finding member with ID: " + id);
        // Test method which will be completed later (maybe in next labs)
    }

    /**
     * Calculates the number of days between two dates.
     *
     * @param date1 the first date
     * @param date2 the second date
     * @return the absolute number of days between the two dates
     */
    public static long daysBetween(LocalDate date1, LocalDate date2) {
        return Math.abs(ChronoUnit.DAYS.between(date1, date2));
    }

}
