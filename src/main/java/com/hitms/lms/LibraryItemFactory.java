package com.hitms.lms;

public class LibraryItemFactory {

    /**
     * Returns the correct LibraryItem subclass instance for itemType.
     */
    public static LibraryItem createLibraryItem(String itemType, String title, String itemId) {

        return switch (itemType.toLowerCase()) {

            case "book" ->
                new Book(title, itemId);

            case "dvd" ->
                new DVD(title, itemId);

            case "magazine" ->
                new Magazine(title, itemId);

            default ->
                throw new IllegalArgumentException("Unknown item type: " + itemType);

        };

    }

    public static void main(String[] args) {

        LibraryItem item = createLibraryItem("dvd", "The Matrix", "D-014");

        System.out.println(item.getClass().getSimpleName() + " -> " + item.getLoanPeriodDays() + " days");

    }

}
