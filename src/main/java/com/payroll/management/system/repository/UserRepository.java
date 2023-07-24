package com.payroll.management.system.repository;

import com.payroll.management.system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

User findByUserName(String userName);

@Query(value = "select id from users where email = :userName", nativeQuery = true)
Optional<Long> getUserId(String userName);



}
