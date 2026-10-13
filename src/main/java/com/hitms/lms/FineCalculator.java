package com.hitms.lms;

/**
 * Works out the fine owed on an overdue loan.
 */
public class FineCalculator {

    private static final int GRACE_PERIOD_DAYS = 5;
    private static final double FINE_PER_DAY = 0.5;
    private static final double MAX_FINE = 20.0;

    /**
     * Calculates the fine for a loan that is the given number of days overdue.
     *
     * @param daysOverdue how many days past the due date the loan is
     * @return the fine, never above the library maximum
     */
    public double calculateFine(int daysOverdue) {
        int chargeableDays = chargeableDays(daysOverdue);
        double fine = chargeableDays * FINE_PER_DAY;
        return capFine(fine);
    }

    /**
     * Returns the days that actually cost money, ignoring the grace period.
     */
    private int chargeableDays(int daysOverdue) {
        return Math.max(0, daysOverdue - GRACE_PERIOD_DAYS);
    }

    /**
     * Limits a fine to the maximum the library allows.
     */
    private double capFine(double fine) {
        return Math.min(fine, MAX_FINE);
    }
}
