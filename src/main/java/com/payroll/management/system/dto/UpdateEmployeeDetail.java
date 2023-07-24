package com.payroll.management.system.dto;

import com.payroll.management.system.dto.enums.Gender;
import com.payroll.management.system.dto.enums.MaritalStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateEmployeeDetail {

    private String nin;

    private String dateOfBirth;

    private Double salary;

    private String firstName;

    private String lastName;

    private String email;

    private MaritalStatus status;

    private String phoneNumber;

    private Gender gender;
}
