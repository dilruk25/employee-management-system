package com.example.employee_management.enums;

public enum JobTitle {
    FRONTEND_DEVELOPER("Frontend Developer"),
    BACKEND_DEVELOPER("Backend Developer"),
    FULLSTACK_DEVELOPER("Fullstack Developer"),
    QUALITY_ASSURANCE_ENGINEER("QA Engineer"),
    HR_MANAGER("HR Manager");

    public String getValue() {
        return value;
    }

    private final String value;

    JobTitle(String value) {
        this.value = value;
    }
}
