package com.payroll.management.system.dto.enums;

public enum LeaveType {
    SICKLEAVE("Sick Leave"),
    ANNUALLEAVE("Annual Leave"),
    UNPAIDLEAVE("Unpaid Leave");

    private String value;

    LeaveType(String value) {
        this.value = value;
    }
}
