package com.payroll.management.system.dto;



import com.payroll.management.system.dto.enums.LeaveStatus;


public record UpdateLeaveRequest(
        LeaveStatus leaveStatus
) {
}
