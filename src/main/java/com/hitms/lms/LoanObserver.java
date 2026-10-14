package com.hitms.lms;

/**
 * Receives a notification when a loan becomes overdue.
 *
 * <p>This is a functional interface, so a listener can be written as a lambda
 * (see {@link Loan#addObserver(LoanObserver)}).</p>
 */
@FunctionalInterface
public interface LoanObserver {
    /**
     * Called once for every registered observer when a loan is overdue.
     *
     * @param item       the item that was not returned on time
     * @param memberName the member who borrowed the item
     */
    void onOverdue(LibraryItem item, String memberName);
}