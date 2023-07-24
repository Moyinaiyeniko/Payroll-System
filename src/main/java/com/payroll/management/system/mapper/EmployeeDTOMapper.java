package com.payroll.management.system.mapper;

import com.payroll.management.system.dto.EmployeeResponseDTO;
import com.payroll.management.system.dto.enums.Gender;
import com.payroll.management.system.dto.enums.MaritalStatus;
import com.payroll.management.system.entity.Employee;

import java.util.function.Function;

public class EmployeeDTOMapper implements Function<Employee, EmployeeResponseDTO> {
    @Override
    public EmployeeResponseDTO apply(Employee employee) {
        return new EmployeeResponseDTO(
                employee.getId(),
                employee.getNin(),
                employee.getDateOfBirth().toString(),
                //employee.getSalary(),
                employee.getUser().getFirstName(),
                employee.getUser().getLastName(),
                employee.getUser().getUserName(),
               // MaritalStatus.valueOf(employee.getStatus()),
                employee.getPhoneNumber()
                //Gender.valueOf(employee.getGender())

        );
    }
}
