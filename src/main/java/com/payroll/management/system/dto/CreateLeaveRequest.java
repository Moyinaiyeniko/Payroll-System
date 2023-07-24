package com.payroll.management.system.dto;

import com.payroll.management.system.dto.enums.LeaveType;

import java.time.LocalDate;

public record CreateLeaveRequest(
        LeaveType leaveType,
        LocalDate startDate,
        LocalDate endDate
) {
}
