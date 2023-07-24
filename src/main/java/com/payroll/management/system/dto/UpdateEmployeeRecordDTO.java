package com.payroll.management.system.dto;

import com.payroll.management.system.dto.enums.Gender;
import com.payroll.management.system.dto.enums.MaritalStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateEmployeeRecordDTO {
    private String nin;

    private LocalDate dateOfBirth;

    private String firstName;

    private String lastName;

    @Email(message = "Email is not valid")
    private String email;

    private MaritalStatus status;

    private String phoneNumber;

    private Gender gender;

    private String role;

    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal grossPay;

    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal netPay;

    private String bankName;

    private String accountName;

    private String accountNumber;

    private String address;

}
