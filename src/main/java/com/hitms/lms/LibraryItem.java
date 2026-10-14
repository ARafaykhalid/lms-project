package com.hitms.lms;

/**
 * Base class for every item that can be borrowed from the library.
 *
 * <p>Each concrete subclass supplies its own loan period through
 * {@link #getLoanPeriodDays()}.</p>
 */
public abstract class LibraryItem {

    /** The title of this item. */
    protected final String title;

    /** The unique identifier assigned to this item. */
    protected final String itemId;

    /**
     * Creates a library item with the given title and identifier.
     *
     * @param title  the title of the item
     * @param itemId the unique identifier of the item
     */
    protected LibraryItem(String title, String itemId) {
        this.title = title;
        this.itemId = itemId;
    }

    /**
     * Returns the number of days this item type may be borrowed for.
     *
     * @return the loan period for this item type, in days
     */
    public abstract int getLoanPeriodDays();

    /**
     * Returns the title and identifier of this item.
     *
     * @return the item rendered as {@code title (itemId)}
     */
    @Override
    public String toString() {
        return title + " (" + itemId + ")";
    }
}
