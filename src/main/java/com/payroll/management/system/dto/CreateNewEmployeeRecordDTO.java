package com.payroll.management.system.dto;

import com.payroll.management.system.dto.enums.Gender;
import com.payroll.management.system.dto.enums.MaritalStatus;
import com.payroll.management.system.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateNewEmployeeRecordDTO {

    @NotEmpty(message = "Nin cannot be empty")
    private String nin;
    @NotEmpty(message = "D.O.B cannot be empty")
    private String dateOfBirth;
    @NotEmpty(message = "First name cannot be empty")
    private String firstName;
    @NotEmpty(message = "Last name cannot be empty")
    private String lastName;
    @Email(message = "Email is not valid")
    @NotEmpty(message = "Email cannot be empty")
    private String email;
    @NotEmpty(message = "Role cannot be empty")
    private String role;
    @NotEmpty(message = "Marital status cannot be empty")
    private MaritalStatus status;
    @NotEmpty(message = "Phone number cannot be empty")
    private String phoneNumber;
    @NotEmpty(message = "Gender cannot be empty")
    private Gender gender;
    @DecimalMin(value = "0.0", inclusive = false)
    @NotEmpty(message = "Gross pay cannot be empty")
    private BigDecimal grossPay;
    @DecimalMin(value = "0.0", inclusive = false)
    @NotEmpty(message = "Net pay cannot be empty")
    private BigDecimal netPay;
    @NotEmpty(message = "Bank name cannot be empty")
    private String bankName;
    @NotEmpty(message = "Account name cannot be empty")
    private String accountName;
    @NotEmpty(message = "Account number cannot be empty")
    private String accountNumber;
    @NotEmpty(message = "Address cannot be empty")
    private String address;

}
