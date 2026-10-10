package com.hitms.lms;

public class Book extends LibraryItem {

    public Book(String title, String itemId) {
        super(title, itemId);
    }

    @Override
    public int getLoanPeriodDays() {
        return 14;
    }
}
