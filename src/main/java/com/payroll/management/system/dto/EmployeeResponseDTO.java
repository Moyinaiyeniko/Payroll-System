package com.payroll.management.system.dto;

import com.payroll.management.system.dto.enums.Gender;
import com.payroll.management.system.dto.enums.MaritalStatus;

import java.math.BigDecimal;

public record EmployeeResponseDTO (

     Long id,

     String nin,

     String dateOfBirth,

      //BigDecimal salary,

     String firstName,

     String lastName,

     String email,

     //MaritalStatus status,

     String phoneNumber

     //Gender gender
)
{
}
