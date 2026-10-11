package com.hitms.lms;

import java.util.ArrayList;
import java.util.List;

/**
 * * Subject that notifies registered observers when a loan is overdue.
 */
public class Loan {

    private final LibraryItem item;
    private final String memberName;
    private final List<LoanObserver> observers = new ArrayList<>();

    public Loan(LibraryItem item, String memberName) {
        this.item = item;
        this.memberName = memberName;
    }

    public void addObserver(LoanObserver observer) {
        observers.add(observer);
    }

    public void notifyOverdue() {
        for (LoanObserver observer : observers) {
            observer.onOverdue(item, memberName);
        }
    }

    public static void main(String[] args) {
        LibraryDatabase db = LibraryDatabase.getInstance();
        LibraryItem dvd = LibraryItemFactory.createLibraryItem("dvd", "The Matrix", "D-014");
        Loan loan = new Loan(dvd, "Aisha Khan");
        loan.addObserver((item, memberName) -> System.out.println("[EMAIL] " + memberName + ", please return '" + item + "' -- it is overdue."));
        loan.notifyOverdue();
    }
}
