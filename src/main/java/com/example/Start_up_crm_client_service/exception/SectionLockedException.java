package com.example.Start_up_crm_client_service.exception;

public class SectionLockedException extends RuntimeException {

    private final String section;

    public SectionLockedException(String section) {
        super("Section '" + section + "' is locked. Request HR permission to edit.");
        this.section = section;
    }

    public String getSection() {
        return section;
    }
}