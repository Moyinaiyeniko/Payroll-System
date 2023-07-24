package com.payroll.management.system.repository;

import com.payroll.management.system.entity.Employee;
import com.payroll.management.system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    public Optional<Employee> findByUser_Id(Long id);

    @Query(value="select * from employee e where e.employee_id= :employeeId", nativeQuery=true)
    public Optional<Employee> findByEmployeeId(String employeeId);
}
