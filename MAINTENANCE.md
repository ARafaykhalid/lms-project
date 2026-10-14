# Maintenance Guide

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
java -cp target/classes com.hitms.lms.Main
```

## Notes for contributors

- All checked exceptions must extend `Exception` and carry a clear message.
- New `LibraryItem` subtypes must be registered in `LibraryItemFactory`.
- `Main` is a demonstration, not a command-line tool. Keep the logic in the
  classes it calls so it stays readable.
- `Loan` has no issue date or due date yet, so `FineCalculator` is not wired
  into the loan lifecycle. `Main` demonstrates the two separately.
