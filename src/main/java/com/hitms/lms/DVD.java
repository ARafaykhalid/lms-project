package com.hitms.lms;

public class DVD extends LibraryItem {

    public DVD(String title, String itemId) {
        super(title, itemId);
    }

    @Override
    public int getLoanPeriodDays() {
        return 7;
    }
}
