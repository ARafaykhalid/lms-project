package com.hitms.lms;

/**
 * Provides services for issuing books from the library catalogue.
 */
public class LibraryService {

    /**
     * Issue a single copy; throws BookUnavailableException if none left.
     *
     * @param availableCopies the number of copies available for the title
     * @param title the title of the book to issue
     * @return the number of copies available after issuing one copy
     * @throws BookUnavailableException if no copies are available
     */
    public static int issueBook(int availableCopies, String title)
            throws BookUnavailableException {

        if (availableCopies <= 0) {
            throw new BookUnavailableException(
                    "'" + title + "' has no copies available."
            );
        }

        return availableCopies - 1;
    }

}
