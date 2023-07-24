package com.payroll.management.system.dto.enums;

public enum LeaveStatus {

    APPROVED("Approved"),
    DENIED("Denied");

    private String value;

    LeaveStatus(String value) {
        this.value = value;
    }
}
