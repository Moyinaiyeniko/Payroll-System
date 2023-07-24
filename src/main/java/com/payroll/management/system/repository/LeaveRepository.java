package com.payroll.management.system.repository;

import com.payroll.management.system.entity.Leave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LeaveRepository extends JpaRepository<Leave, Long> {

     Optional<Leave> findById(Long Id);

    @Query(value="select * from leave l where l.employee_id= :employeeId", nativeQuery=true)
    Optional<List<Leave>> findByEmployeeId(Long employeeId);
}
