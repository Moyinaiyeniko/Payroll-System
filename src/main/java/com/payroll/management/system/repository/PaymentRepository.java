package com.payroll.management.system.repository;

import com.payroll.management.system.entity.Leave;
import com.payroll.management.system.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    @Query(value="select * from payment p where l.employee_id= :employeeId", nativeQuery=true)
    Optional<List<Payment>> findByEmployeeId(Long employeeId);
}
