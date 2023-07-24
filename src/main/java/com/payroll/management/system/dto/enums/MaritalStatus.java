package com.payroll.management.system.dto.enums;

public enum MaritalStatus {

    MARRIED("married"),
    SINGLE("single"),
    OTHER("other");

    private String value;

    MaritalStatus(String value) {
        this.value = value;
    }
}
