package com.payroll.management.system.repository;

import com.payroll.management.system.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName (String name);
}
