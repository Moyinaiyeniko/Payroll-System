package com.payroll.management.system.mapper;


import com.payroll.management.system.dto.LeaveResponseDTO;
import com.payroll.management.system.entity.Employee;
import com.payroll.management.system.entity.Leave;

import java.util.function.Function;

public class LeaveDTOMapper implements Function<Leave, LeaveResponseDTO> {

    @Override
    public LeaveResponseDTO apply(Leave leave){
        return new LeaveResponseDTO(
                leave.getLeaveType(),
                leave.getStartDate(),
                leave.getEndDate(),
                leave.getLeaveStatus()
        );
    }
}
