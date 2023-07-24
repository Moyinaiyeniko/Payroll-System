package com.payroll.management.system.dto;

import java.time.LocalDate;

public record LeaveResponseDTO(
                               String leaveType,
                               LocalDate startDate,
                               LocalDate endDate,
                               String leaveStatus) {
}
