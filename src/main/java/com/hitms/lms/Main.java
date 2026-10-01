package com.hitms.lms;

public class Main {

    public static void main(String[] args) {
        try {
            System.out.println(
                    "Remaining copies: " + LibraryService.issueBook(3, "Clean Code")
            );

            LibraryService.issueBook(0, "Clean Code");

        } catch (BookUnavailableException e) {
            System.out.println("Transaction failed: " + e.getMessage());
        }
    }
}
