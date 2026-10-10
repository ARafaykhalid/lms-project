package com.hitms.lms;

public class Magazine extends LibraryItem {

    public Magazine(String title, String itemId) {
        super(title, itemId);
    }

    @Override
    public int getLoanPeriodDays() {
        return 3;
    }
}
