package com.hitms.lms;

import com.hitms.lms.util.LibraryUtils;
import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {

        System.out.println(LibraryUtils.formatTitle(" the great gatsby "));

        System.out.println(LibraryUtils.daysBetween(
                LocalDate.of(2026, 1, 1), LocalDate.of(2026, 1, 15)));

    }

}
