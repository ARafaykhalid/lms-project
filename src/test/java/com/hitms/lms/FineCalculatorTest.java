package com.hitms.lms;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class FineCalculatorTest {

    private final FineCalculator calculator = new FineCalculator();

    @Test
    void shouldReturnZeroWhenDaysOverdueIsFive() {
        assertEquals(0.0, calculator.calculateFine(5));
    }

    @Test
    void shouldReturnZeroWhenDaysOverdueIsLessThanFive() {
        assertEquals(0.0, calculator.calculateFine(3));
    }

    @Test
    void shouldCalculateFineAfterGracePeriod() {
        assertEquals(2.5, calculator.calculateFine(10));
    }

    @Test
    void shouldApplyMaximumFine() {
        assertEquals(20.0, calculator.calculateFine(50));
    }

    @Test
    void shouldReturnMaximumFineAtLimit() {
        assertEquals(20.0, calculator.calculateFine(45));
    }
}
