package com.hitms.lms;

import java.util.List;

public class PolymorphismDemo {

    public static void main(String[] args) {

        List<LibraryItem> catalogue = List.of(
                new Book("Clean Code", "B-001"),
                new DVD("The Matrix", "D-014"),
                new Magazine("National Geographic", "M-102")
        );

        for (LibraryItem item : catalogue) {

            System.out.println(item + ": loan period = " + item.getLoanPeriodDays() + " days");

        }

        // The next line is left commented out on purpose: 
        // LibraryItem untyped = new LibraryItem("Untyped Item", "X-000"); 
        // Compiler error: "LibraryItem is abstract; cannot be instantiated" 
    }

}
