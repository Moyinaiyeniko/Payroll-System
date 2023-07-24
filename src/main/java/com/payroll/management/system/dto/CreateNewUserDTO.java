package com.payroll.management.system.dto;

import com.payroll.management.system.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateNewUserDTO {

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private Role role;
}
