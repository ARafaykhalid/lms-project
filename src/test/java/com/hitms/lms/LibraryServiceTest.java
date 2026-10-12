package com.hitms.lms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class LibraryServiceTest {

    @Test
    void addBookIncreasesCopies() {
        LibraryService service = new LibraryService();
        assertEquals(3, service.addBook("Clean Code", 3));
    }

    @Test
    void issueBookDecreasesCopies() throws BookUnavailableException {
        LibraryService service = new LibraryService();
        service.addBook("Clean Code", 2);
        assertEquals(1, service.issueBook("Clean Code"));
    }

    @Test
    void returnBookIncreasesCopies() throws BookUnavailableException {
        LibraryService service = new LibraryService();
        service.addBook("Clean Code", 1);
        service.issueBook("Clean Code");
        assertEquals(1, service.returnBook("Clean Code"));
    }

    @Test
    void issueBookThrowsWhenUnavailable() {
        LibraryService service = new LibraryService();
        service.addBook("Clean Code", 0);
        assertThrows(BookUnavailableException.class, () -> service.issueBook("Clean Code"));
    }

    @Test
    void issueUnknownBookThrows() {
        LibraryService service = new LibraryService();
        assertThrows(BookUnavailableException.class, () -> service.issueBook("Never Added"));
    }
}
