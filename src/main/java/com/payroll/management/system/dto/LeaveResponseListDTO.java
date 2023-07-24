package com.payroll.management.system.dto;

import java.time.LocalDate;

public record LeaveResponseListDTO(
        String firstName,
        String lastName,
        String leaveType,
        LocalDate startDate,
        LocalDate endDate,
        String leaveStatus
) {
}
