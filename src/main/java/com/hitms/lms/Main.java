package com.hitms.lms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.hitms.lms.util.LibraryUtils;

/**
 * Console front end for the library system.
 *
 * <p>Run it with:</p>
 *
 * <pre>
 * java -cp target/classes com.hitms.lms.Main
 * </pre>
 */
public class Main {

    private final LibraryService library = new LibraryService();
    private final FineCalculator fines = new FineCalculator();
    private final List<Loan> loans = new ArrayList<>();

    /** The notice every loan raises when it goes overdue. */
    private final LoanObserver notice = (item, memberName) -> System.out.println(
            "[EMAIL] " + memberName + ", please return '" + item + "' -- it is overdue.");

    /**
     * Application entry point.
     *
     * @param args not used
     * @throws IOException if the console cannot be read
     */
    public static void main(String[] args) throws IOException {
        new Main().console();
    }

    /**
     * Reads commands until the user quits or the input ends.
     */
    private void console() throws IOException {
        System.out.println("Library Management System");
        System.out.println("Type 'help' for the commands, 'quit' to leave.");

        try (BufferedReader in = new BufferedReader(new InputStreamReader(System.in))) {
            String line;
            while ((line = in.readLine()) != null) {
                List<String> words = tokenize(line);

                if (words.isEmpty()) {
                    continue;
                }

                String command = words.get(0).toLowerCase();

                try {
                    switch (command) {
                        case "add" -> addBook(words);
                        case "issue" -> issueBook(words);
                        case "return" -> returnBook(words);
                        case "remove" -> removeBook(words);
                        case "list", "catalogue" -> listCatalogue();
                        case "copies" -> showCopies(words);
                        case "loan" -> addLoan(words);
                        case "loans" -> listLoans();
                        case "items" -> listItemTypes();
                        case "overdue" -> notifyOverdue();
                        case "fines" -> showFines();
                        case "seed" -> seedData();
                        case "demo" -> Demo.run(library, loans, notice, fines);
                        case "help" -> printHelp();
                        case "quit", "exit" -> {
                            System.out.println("Goodbye.");
                            return;
                        }
                        default -> System.out.println("Unknown command '" + command + "'. Type 'help'.");
                    }
                } catch (BookUnavailableException e) {
                    System.out.println(e.getMessage());
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Missing argument. Type 'help' for the expected form.");
                }
            }
        }

        System.out.println("Input closed. Goodbye.");
    }

    /**
     * Handles {@code add <title> <copies>}. The count is the last word, so the
     * title may contain spaces.
     */
    private void addBook(List<String> words) {
        require(words, 3);

        int copies;
        try {
            copies = Integer.parseInt(words.get(words.size() - 1));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "'" + words.get(words.size() - 1) + "' is not a number of copies.");
        }

        String title = LibraryUtils.formatTitle(join(words, 1, words.size() - 1));

        System.out.println("Copies available for '" + title + "': " + library.addBook(title, copies));
    }

    /**
     * Handles {@code issue <title>}.
     */
    private void issueBook(List<String> words) throws BookUnavailableException {
        require(words, 2);

        String title = join(words, 1, words.size());

        System.out.println("Copies left after issuing '" + title + "': " + library.issueBook(title));
    }

    /**
     * Handles {@code return <title>}.
     */
    private void returnBook(List<String> words) {
        require(words, 2);

        String title = join(words, 1, words.size());

        System.out.println("Copies available for '" + title + "': " + library.returnBook(title));
    }

    /**
     * Handles {@code remove <title>}.
     */
    private void removeBook(List<String> words) {
        require(words, 2);

        String title = join(words, 1, words.size());

        if (library.removeBook(title)) {
            System.out.println("Removed '" + title + "' from the catalogue.");
        } else {
            System.out.println("'" + title + "' is not in the catalogue.");
        }
    }

    /**
     * Handles {@code list}.
     */
    private void listCatalogue() {
        var titles = library.catalogue();

        if (titles.isEmpty()) {
            System.out.println("The catalogue is empty. Try 'add <title> <copies>'.");
            return;
        }

        System.out.println("Catalogue:");

        titles.forEach((title, count) -> System.out.println("  " + title + ": " + count));
    }

    /**
     * Handles {@code copies <title>}.
     */
    private void showCopies(List<String> words) {
        require(words, 2);

        System.out.println("Copies available for '" + join(words, 1, words.size())
                + "': " + library.copiesOf(join(words, 1, words.size())));
    }

    /**
     * Handles {@code loan <type> <title> <id> <member> [daysAgo]}.
     */
    private void addLoan(List<String> words) {
        require(words, 5);

        boolean backdated = words.size() >= 6;
        String member = join(words, 4, words.size() - (backdated ? 1 : 0));
        int daysAgo = backdated ? Integer.parseInt(words.get(words.size() - 1)) : 0;

        LibraryItem item = LibraryItemFactory.createLibraryItem(
                words.get(1), words.get(2), words.get(3));

        Loan loan = new Loan(item, member, LocalDate.now().minusDays(daysAgo));
        loan.addObserver(notice);
        loans.add(loan);

        System.out.println("Recorded: " + member + " borrowed " + item
                + " on " + loan.borrowedOn() + ", due " + loan.dueOn());
    }

    /**
     * Handles {@code loans}.
     */
    private void listLoans() {
        LocalDate today = LocalDate.now();

        if (loans.isEmpty()) {
            System.out.println("No loans recorded yet.");
            return;
        }

        for (Loan loan : loans) {
            System.out.println(loan.memberName() + " has " + loan.item()
                    + ", due " + loan.dueOn()
                    + (loan.isOverdue(today) ? "  [OVERDUE]" : ""));
        }
    }

    /**
     * Handles {@code items}.
     */
    private void listItemTypes() {
        System.out.println("Item types:");

        for (String type : LibraryItemFactory.knownTypes()) {
            LibraryItem sample = LibraryItemFactory.createLibraryItem(type, type, "-");
            System.out.println("  " + type + ": " + sample.getLoanPeriodDays() + " day loan period");
        }
    }

    /**
     * Handles {@code overdue}.
     */
    private void notifyOverdue() {
        LocalDate today = LocalDate.now();
        int count = 0;

        for (Loan loan : loans) {
            if (loan.isOverdue(today)) {
                System.out.println(loan.memberName() + " is overdue by " + loan.daysOverdue(today)
                        + " days on " + loan.item() + ", fine = " + loan.fineAsOf(today, fines));

                loan.notifyOverdue();
                count++;
            }
        }

        if (count == 0) {
            System.out.println("Nothing is overdue.");
        }
    }

    /**
     * Handles {@code fines}.
     */
    private void showFines() {
        LocalDate today = LocalDate.now();
        int count = 0;

        for (Loan loan : loans) {
            if (loan.isOverdue(today)) {
                System.out.println(loan.memberName() + ": " + loan.item()
                        + ", overdue by " + loan.daysOverdue(today)
                        + " days, fine = " + loan.fineAsOf(today, fines));
                count++;
            }
        }

        if (count == 0) {
            System.out.println("Nothing is overdue.");
        }
    }

    /**
     * Handles {@code seed}.
     */
    private void seedData() {
        if (!loans.isEmpty() || !library.catalogue().isEmpty()) {
            System.out.println("Session already has data. Type 'list' or 'loans' to see it.");
            return;
        }

        Demo.seed(library, loans, notice);

        System.out.println("Seeded 3 titles and 3 loans. Try 'list', 'loans', 'fines' or 'overdue'.");
    }

    /**
     * Splits a line into words, keeping double quoted text together.
     */
    private static List<String> tokenize(String line) {
        List<String> words = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean quoted = false;
        boolean started = false;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            if (c == '"') {
                quoted = !quoted;
                started = true;
            } else if (!quoted && Character.isWhitespace(c)) {
                if (started) {
                    words.add(current.toString());
                    current.setLength(0);
                    started = false;
                }
            } else {
                current.append(c);
                started = true;
            }
        }

        if (started) {
            words.add(current.toString());
        }

        return words;
    }

    /**
     * Checks that enough arguments were supplied.
     */
    private static void require(List<String> words, int count) {
        if (words.size() < count) {
            throw new IndexOutOfBoundsException();
        }
    }

    /**
     * Joins words from one index up to, but not including, another.
     */
    private static String join(List<String> words, int from, int to) {
        return String.join(" ", words.subList(from, to)).strip();
    }

    /**
     * Prints the available commands.
     */
    private void printHelp() {
        System.out.println("""
                Catalogue:
                  add <title> <copies>               add copies of a title
                  issue <title>                      issue one copy
                  return <title>                     return one copy
                  remove <title>                     remove a title entirely
                  list                               list every title and its copies
                  copies <title>                     copies available for one title

                Loans:
                  loan <type> <title> <id> <member> [daysAgo]
                                                    record a loan; daysAgo backdates it
                  loans                             list loans with their due dates
                  items                             list item types and loan periods

                Overdue:
                  overdue                           notify about overdue loans, with fines
                  fines                             report overdue fines without notifying

                Other:
                  seed                              load the sample books and loans
                  demo                              seed, then run the guided walkthrough
                  help                              show this list
                  quit                              leave the console

                Item types are: book, dvd, magazine
                Wrap names in double quotes if they contain spaces.
                """);
    }
}
