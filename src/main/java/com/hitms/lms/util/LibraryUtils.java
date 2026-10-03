package com.hitms.lms.util;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class LibraryUtils {

    /**
     * Returns a title in Title Case with surrounding whitespace removed.
     */
    public static String formatTitle(String title) {

        String trimmed = title.strip().toLowerCase();

        String[] words = trimmed.split(" ");

        StringBuilder result = new StringBuilder();

        for (String w : words) {

            if (!w.isEmpty()) {

                result.append(Character.toUpperCase(w.charAt(0)))
                        .append(w.substring(1)).append(" ");

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
        // Test method which will be completed later
    }

    /**
     * Returns the number of whole days between two dates.
     */
    public static long daysBetween(LocalDate date1, LocalDate date2) {

        return Math.abs(ChronoUnit.DAYS.between(date1, date2));

    }

}
