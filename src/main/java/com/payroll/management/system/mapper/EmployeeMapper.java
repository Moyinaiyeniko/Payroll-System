package com.payroll.management.system.mapper;

import com.payroll.management.system.dto.CreateNewEmployeeRecordDTO;
import com.payroll.management.system.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EmployeeMapper {

    EmployeeMapper INSTANCE = Mappers.getMapper( EmployeeMapper.class );

   // @Mapping(source = "numberOfSeats", target = "seatCount")
    Employee dtoToEmployeeObject(CreateNewEmployeeRecordDTO employeeDTO);
}
