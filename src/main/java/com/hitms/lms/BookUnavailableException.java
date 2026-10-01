package com.hitms.lms;

/**
 * Exception thrown when a requested book cannot be issued because
 * no copies are currently available.
 */
public class BookUnavailableException extends Exception {

    /**
     * Creates a new BookUnavailableException with the specified message.
     *
     * @param message the detail message describing why the book is unavailable
     */
    public BookUnavailableException(String message) {
        super(message);
    }
}
