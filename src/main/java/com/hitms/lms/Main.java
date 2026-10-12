package com.hitms.lms;

public class Main {

    public static void main(String[] args) throws BookUnavailableException {
        LibraryService service = new LibraryService();

        System.out.println("Remaining copies: " + service.issueBook("Clean Code"));

        try {
            service.issueBook("Never Added");
        } catch (BookUnavailableException e) {
            System.out.println("Transaction failed: " + e.getMessage());
        }
    }
}
