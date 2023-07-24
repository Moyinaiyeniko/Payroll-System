package com.payroll.management.system.service;

import com.payroll.management.system.dto.CreateNewEmployeeRecordDTO;
import com.payroll.management.system.dto.CreateNewUserDTO;
import com.payroll.management.system.dto.EmployeeResponseDTO;
import com.payroll.management.system.dto.UpdateEmployeeRecordDTO;
import com.payroll.management.system.entity.Employee;
import com.payroll.management.system.entity.Role;
import com.payroll.management.system.entity.User;

import java.util.List;

public interface UserService {

    User saveUser(CreateNewUserDTO createNewUserDTO);

    User saveUser1(String role, String firstName, String lastName, String email, String password);

    void saveEmployeeRecord(CreateNewEmployeeRecordDTO createNewEmployeeRecordDTO);

    List<EmployeeResponseDTO>  getAllEmployees();

    EmployeeResponseDTO getEmployeeByEmail();

    Employee getEmployeeUsingEmail();

    Role saveRole(Role role);

    void addRoleToUser ( String userName, String roleName);

    User getUser(String userName);

    List<User> getUsers();

    void updateEmployeeRecord(UpdateEmployeeRecordDTO updateEmployeeRecordDTO, Long id);
}
