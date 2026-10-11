package com.hitms.lms;

@FunctionalInterface 

public interface LoanObserver { 
    void onOverdue(LibraryItem item, String memberName); 
} 
