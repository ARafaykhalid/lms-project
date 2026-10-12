package com.hitms.lms;

import java.util.HashMap;
import java.util.Map;

/**
 * Provides services for adding, issuing, and returning books
 * in the library catalogue.
 */
public class LibraryService {

    private final Map<String, Integer> catalogue = new HashMap<>();

    /**
     * Adds copies of a book to the catalogue.
     *
     * @param title the title of the book to add
     * @param copies the number of copies to add
     * @return the total number of copies available
     */
    public int addBook(String title, int copies) {
        catalogue.merge(title, copies, Integer::sum);
        return catalogue.get(title);
    }

    /**
     * Issues one copy of a book from the catalogue.
     *
     * @param title the title of the book to issue
     * @return the number of copies remaining
     * @throws BookUnavailableException if no copies are available
     */
    public int issueBook(String title) throws BookUnavailableException {
        if (catalogue.getOrDefault(title, 0) <= 0) {
            throw new BookUnavailableException("'" + title + "' has no copies available.");
        }

        catalogue.merge(title, -1, Integer::sum);

        return catalogue.get(title);
    }

    /**
     * Returns one copy of a book to the catalogue.
     *
     * @param title the title of the book being returned
     * @return the total number of copies available
     */
    public int returnBook(String title) {
        catalogue.merge(title, 1, Integer::sum);
        return catalogue.get(title);
    }
}
