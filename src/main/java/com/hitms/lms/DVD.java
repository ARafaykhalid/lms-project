package com.hitms.lms;

/**
 * A DVD, which may be borrowed for 7 days.
 */
public class DVD extends LibraryItem {

    /**
     * Creates a DVD with the given title and identifier.
     *
     * @param title  the title of the DVD
     * @param itemId the unique identifier of this DVD
     */
    public DVD(String title, String itemId) {
        super(title, itemId);
    }

    /**
     * Returns the standard loan period for a DVD.
     *
     * @return 7, the number of days a DVD may be borrowed for
     */
    @Override
    public int getLoanPeriodDays() {
        return 7;
    }
}
