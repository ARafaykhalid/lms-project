package com.hitms.lms;

public abstract class LibraryItem {

    protected final String title;
    protected final String itemId;

    protected LibraryItem(String title, String itemId) {
        this.title = title;
        this.itemId = itemId;
    }

    /**
     * * Returns the number of days this item type may be borrowed for.
     */
    public abstract int getLoanPeriodDays();

    @Override
    public String toString() {
        return title + " (" + itemId + ")";
    }
}