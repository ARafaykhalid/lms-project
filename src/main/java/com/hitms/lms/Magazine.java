package com.hitms.lms;

/**
 * A magazine, which may be borrowed for 3 days.
 */
public class Magazine extends LibraryItem {

    /**
     * Creates a magazine with the given title and identifier.
     *
     * @param title  the title of the magazine
     * @param itemId the unique identifier of this magazine
     */
    public Magazine(String title, String itemId) {
        super(title, itemId);
    }

    /**
     * Returns the standard loan period for a magazine.
     *
     * @return 3, the number of days a magazine may be borrowed for
     */
    @Override
    public int getLoanPeriodDays() {
        return 3;
    }
}
