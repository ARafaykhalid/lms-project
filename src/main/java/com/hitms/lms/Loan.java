package com.hitms.lms;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.hitms.lms.util.LibraryUtils;

/**
 * A single borrowing of an item by a member, and the subject that notifies
 * registered observers when it goes overdue.
 *
 * <p>This is the Observer pattern: {@link Loan} keeps a list of
 * {@link LoanObserver} listeners and calls each one when the loan goes
 * overdue.</p>
 *
 * <p>A loan knows when it was borrowed, so it can work out its own due date from
 * the item's loan period and report how overdue it is. The money side lives in
 * {@link FineCalculator} and is passed in rather than created here, which keeps
 * the two responsibilities apart.</p>
 */
public class Loan {

    private final LibraryItem item;
    private final String memberName;
    private final LocalDate borrowedOn;
    private final List<LoanObserver> observers = new ArrayList<>();

    /**
     * Creates a loan that starts today.
     *
     * @param item       the borrowed item
     * @param memberName the member who borrowed it
     */
    public Loan(LibraryItem item, String memberName) {
        this(item, memberName, LocalDate.now());
    }

    /**
     * Creates a loan that started on a given date.
     *
     * @param item       the borrowed item
     * @param memberName the member who borrowed it
     * @param borrowedOn the date the item was borrowed
     */
    public Loan(LibraryItem item, String memberName, LocalDate borrowedOn) {
        this.item = item;
        this.memberName = memberName;
        this.borrowedOn = borrowedOn;
    }

    /**
     * Returns the date this loan started.
     *
     * @return the date the item was borrowed
     */
    public LocalDate borrowedOn() {
        return borrowedOn;
    }

    /**
     * Returns the member who borrowed the item.
     *
     * @return the member's name
     */
    public String memberName() {
        return memberName;
    }

    /**
     * Returns the borrowed item.
     *
     * @return the item on loan
     */
    public LibraryItem item() {
        return item;
    }

    /**
     * Returns the date this loan must be settled by, which is the borrow date
     * plus the loan period of the item type.
     *
     * @return the due date of this loan
     */
    public LocalDate dueOn() {
        return borrowedOn.plusDays(item.getLoanPeriodDays());
    }

    /**
     * Returns how many days this loan is overdue as of a given date.
     *
     * @param asOf the date to measure against
     * @return the days past the due date, or 0 if the loan is not yet overdue
     */
    public int daysOverdue(LocalDate asOf) {
        if (asOf.isBefore(dueOn())) {
            return 0;
        }
        return (int) LibraryUtils.daysBetween(dueOn(), asOf);
    }

    /**
     * Reports whether this loan has passed its due date.
     *
     * @param asOf the date to measure against
     * @return true if the loan is overdue on that date
     */
    public boolean isOverdue(LocalDate asOf) {
        return daysOverdue(asOf) > 0;
    }

    /**
     * Returns the fine owed on this loan as of a given date.
     *
     * @param asOf       the date to measure against
     * @param calculator the calculator that applies the library's fine rules
     * @return the fine, which is 0 while the loan is still within its grace period
     */
    public double fineAsOf(LocalDate asOf, FineCalculator calculator) {
        return calculator.calculateFine(daysOverdue(asOf));
    }

    /**
     * Registers an observer to be told when this loan is overdue.
     *
     * @param observer the listener to add
     */
    public void addObserver(LoanObserver observer) {
        observers.add(observer);
    }

    /**
     * Tells every registered observer that this loan is overdue.
     */
    public void notifyOverdue() {
        for (LoanObserver observer : observers) {
            observer.onOverdue(item, memberName);
        }
    }
}