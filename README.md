# LMS Project

A small library management system built up over the Software Construction
labs. `Main` is an interactive console for using it.

Requires JDK 17 or newer. Everything lives in the `com.hitms.lms` package.

## Build

```
mvn clean install
```

## Test

```
mvn test
```

## Run

```
mvn clean compile
java -cp target/classes com.hitms.lms.Main
```

Then type commands at the prompt. Arguments with spaces go in double quotes.

| Command | Does |
| --- | --- |
| `add <title> <copies>` | Add copies of a title to the catalogue |
| `issue <title>` | Issue one copy |
| `return <title>` | Return one copy |
| `remove <title>` | Remove a title from the catalogue entirely |
| `list` | List every title and its available copies |
| `copies <title>` | Copies available for one title |
| `loan <type> <title> <id> <member> [daysAgo]` | Record a loan. `daysAgo` backdates it so you can try overdue behaviour without waiting |
| `loans` | List loans with their due dates |
| `items` | List the item types and their loan periods |
| `overdue` | Notify about every overdue loan and show the fine |
| `fines` | Report overdue fines without notifying anyone |
| `seed` | Load sample books and loans into the session |
| `demo` | Seed, then run the guided lab walkthrough |
| `help`, `quit` | Show commands, or leave |

State lives in memory for the life of the process.

## What it does

- **Add a book** to the catalogue, with a given number of copies.
- **Issue a book**, reducing the available copies, or raise
  `BookUnavailableException` when no copies are left.
- **Return a book**, adding a copy back to the catalogue.
- **Charge a fine** on an overdue loan, using a grace period and a per-day rate
  with a cap. A loan works out its own due date from the item's loan period.
- **Trigger an overdue notification** to every observer on a loan.

## What is in here

| Class | Idea it demonstrates |
| --- | --- |
| `LibraryItem`, `Book`, `DVD`, `Magazine` | Inheritance and polymorphism: each subtype sets its own loan period |
| `LibraryItemFactory` | Factory: build the right subtype from a type name |
| `LibraryDatabase` | Singleton: one shared catalogue for the whole application |
| `Loan`, `LoanObserver` | Observer: notify listeners when a loan is overdue |
| `FineCalculator` | Grace period, per-day rate, and a cap on the fine |
| `LibraryService` | Checked exceptions, wrapped in `BookUnavailableException` |
| `Demo` | Seeds the session and prints the guided lab walkthrough |
| `util.LibraryUtils` | Static helpers for titles and date maths |

## Tests

| Test class | Covers |
| --- | --- |
| `LibraryServiceTest` | Adding, issuing, returning, and the unavailable-title exception |
| `FineCalculatorTest` | Grace period, per-day rate, and the fine cap |

See [MAINTENANCE.md](MAINTENANCE.md) before changing anything.
