package com.payroll.management.system.mapper;



import com.payroll.management.system.dto.LeaveResponseListDTO;
import com.payroll.management.system.entity.Leave;

import java.util.function.Function;

public class LeaveListDTOMapper implements Function<Leave, LeaveResponseListDTO> {

    @Override
    public LeaveResponseListDTO apply(Leave leave){
        return new LeaveResponseListDTO(
                leave.getEmployee().getUser().getFirstName(),
                leave.getEmployee().getUser().getLastName(),
                leave.getLeaveType(),
                leave.getStartDate(),
                leave.getEndDate(),
                leave.getLeaveStatus()
        );
    }
}
