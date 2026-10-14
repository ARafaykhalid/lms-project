package com.hitms.lms;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Seeds the console with a realistic starting state and walks through it.
 *
 * <p>Run {@link #seed} to load the sample data on its own, or {@link #run} to
 * seed and then print the walkthrough. Either way the books and loans end up in
 * the live session, so afterwards the {@code list}, {@code loans} and
 * {@code overdue} commands show the same data.</p>
 *
 * <p>See {@link Main} for the console itself.</p>
 */
public final class Demo {

    private Demo() {
        // Seeding helper only.
    }

    /**
     * Loads sample books and loans into the running session.
     *
     * @param library the catalogue to fill
     * @param loans   the loan list to fill
     * @param notice  the observer attached to every seeded loan
     */
    public static void seed(LibraryService library, List<Loan> loans, LoanObserver notice) {
        library.addBook("Clean Code", 3);
        library.addBook("The Matrix", 1);
        library.addBook("National Geographic", 2);

        loans.add(loan(notice, "dvd", "The Matrix", "D-014", "Aisha Khan", 12));
        loans.add(loan(notice, "magazine", "National Geographic", "M-102", "Bilal Ahmed", 1));
        loans.add(loan(notice, "book", "Clean Code", "B-001", "Sana Riaz", 0));
    }

    /**
     * Seeds the session and then walks through what it produced.
     *
     * @param library the catalogue to fill
     * @param loans   the loan list to fill
     * @param notice  the observer attached to every seeded loan
     * @param fines   the calculator used to price overdue loans
     * @throws BookUnavailableException if the walkthrough issues an unavailable title
     */
    public static void run(LibraryService library, List<Loan> loans, LoanObserver notice, FineCalculator fines)
            throws BookUnavailableException {

        if (library.catalogue().isEmpty() && loans.isEmpty()) {
            seed(library, loans, notice);
            System.out.println("Seeded 3 titles and 3 loans into the session.");
        } else {
            System.out.println("Session already has data, so seeding was skipped.");
        }

        System.out.println();

        showItemTypes();
        showCatalogue(library);
        showService(library);
        showLoans(loans);
        showOverdue(loans, fines);
        showSharedDatabase();
    }

    /**
     * Creates one loan, backdated by the given number of days.
     */
    private static Loan loan(LoanObserver notice, String type, String title, String id, String member, int daysAgo) {
        LibraryItem item = LibraryItemFactory.createLibraryItem(type, title, id);
        Loan loan = new Loan(item, member, LocalDate.now().minusDays(daysAgo));

        loan.addObserver(notice);

        return loan;
    }

    /**
     * Prints the item types the factory can build.
     *
     * <p>The caller does not care which subtype is in the list. Each one answers
     * {@code getLoanPeriodDays()} for itself, which is what polymorphism
     * means.</p>
     */
    private static void showItemTypes() {
        System.out.println("--- Item types ---");

        for (String type : LibraryItemFactory.knownTypes()) {
            LibraryItem item = LibraryItemFactory.createLibraryItem(type, type, "-");
            System.out.println("  " + type + ": " + item.getLoanPeriodDays() + " day loan period");
        }

        System.out.println();
    }

    /**
     * Prints the seeded catalogue.
     */
    private static void showCatalogue(LibraryService library) {
        System.out.println("--- Catalogue ---");

        Map<String, Integer> titles = library.catalogue();

        titles.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> System.out.println("  " + entry.getKey() + ": " + entry.getValue()));

        System.out.println();
    }

    /**
     * Issues and returns one copy against the seeded catalogue, then shows the
     * checked exception being caught.
     */
    private static void showService(LibraryService library) throws BookUnavailableException {
        System.out.println("--- Issuing and returning ---");

        System.out.println("Copies after issuing 'Clean Code':   " + library.issueBook("Clean Code"));
        System.out.println("Copies after returning 'Clean Code': " + library.returnBook("Clean Code"));

        try {
            library.issueBook("Never Added");
        } catch (BookUnavailableException e) {
            System.out.println("Issuing an unknown title failed as expected: " + e.getMessage());
        }

        System.out.println();
    }

    /**
     * Prints the seeded loans with their due dates.
     */
    private static void showLoans(List<Loan> loans) {
        LocalDate today = LocalDate.now();

        System.out.println("--- Loans ---");

        for (Loan loan : loans) {
            System.out.println("  " + loan.memberName() + " has " + loan.item()
                    + ", due " + loan.dueOn()
                    + (loan.isOverdue(today) ? "  [OVERDUE]" : ""));
        }

        System.out.println();
    }

    /**
     * Notifies observers about every overdue loan and shows the fine.
     *
     * <p>A loan is built twelve days back on a 7 day DVD, so the notification is
     * not fired blindly.</p>
     */
    private static void showOverdue(List<Loan> loans, FineCalculator fines) {
        LocalDate today = LocalDate.now();

        System.out.println("--- Overdue ---");

        for (Loan loan : loans) {
            if (loan.isOverdue(today)) {
                System.out.println("  " + loan.memberName() + " is overdue by " + loan.daysOverdue(today)
                        + " days on " + loan.item() + ", fine = " + loan.fineAsOf(today, fines));

                loan.notifyOverdue();
            }
        }

        System.out.println();
    }

    /**
     * Asks for the database twice and shows both calls got the same object.
     */
    private static void showSharedDatabase() {
        System.out.println("--- Shared database ---");

        LibraryDatabase first = LibraryDatabase.getInstance();
        LibraryDatabase second = LibraryDatabase.getInstance();

        first.records.put("B-001", "Clean Code");

        System.out.println("Same object?  " + (first == second));
        System.out.println("Records seen:  " + second.records);
    }
}