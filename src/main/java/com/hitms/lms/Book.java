package com.hitms.lms;

/**
 * A printed book, which may be borrowed for 14 days.
 */
public class Book extends LibraryItem {

    /**
     * Creates a book with the given title and identifier.
     *
     * @param title  the title of the book
     * @param itemId the unique identifier of this book
     */
    public Book(String title, String itemId) {
        super(title, itemId);
    }

    /**
     * Returns the standard loan period for a book.
     *
     * @return 14, the number of days a book may be borrowed for
     */
    @Override
    public int getLoanPeriodDays() {
        return 14;
    }
}
