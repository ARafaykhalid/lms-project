package com.hitms.lms;

import java.util.List;

/**
 * Creates the right {@link LibraryItem} subclass for a given item type.
 *
 * <p>Callers ask for an item by name and receive a correctly typed object back,
 * so they never need a {@code switch} or a {@code new Book(...)} of their own.</p>
 */
public class LibraryItemFactory {

    /**
     * Returns every item type this factory can build.
     *
     * <p>Keep this in step with the switch in
     * {@link #createLibraryItem(String, String, String)}.</p>
     *
     * @return the supported item type names
     */
    public static List<String> knownTypes() {
        return List.of("book", "dvd", "magazine");
    }

    /**
     * Returns the correct LibraryItem subclass instance for itemType.
     *
     * @param itemType the type of item to create, for example {@code "book"}
     * @param title    the title of the item
     * @param itemId   the unique identifier of the item
     * @return a {@link Book}, {@link DVD}, or {@link Magazine}
     * @throws IllegalArgumentException if itemType is not a known item type
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

}