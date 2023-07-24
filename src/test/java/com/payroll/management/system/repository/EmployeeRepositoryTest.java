package com.payroll.management.system.repository;

import com.payroll.management.system.entity.Employee;
import com.payroll.management.system.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
@DataJpaTest //necessary for unit testing repo
class EmployeeRepositoryTest {
    @Autowired
    EmployeeRepository underTest;

    void tearDown(){
        underTest.deleteAll();
    }
    @Test
    void itShouldCheckIfEmployeeWithUserIdExists() {

        //given
        User user = User.builder().id(1L).build();
        Employee employee = Employee.builder().id(2L).user(user).build();
        underTest.save(employee);
        //when
Optional <Employee> employee1 = underTest.findByUser_Id(employee.getUser().getId());
Employee emp = employee1.get();
        //then
        assertThat(emp.getId()).isEqualTo(employee.getId());
    }

    @Test
    void findByEmployeeId() {
        //given
        Employee employee = Employee.builder().id(1L).employeeId("PT1234").build();
        underTest.save(employee);
        //when
        Optional <Employee> employee1 = underTest.findByEmployeeId(employee.getEmployeeId());
        //then
        assertThat(employee.getId()).isEqualTo(employee1.get().getId());
    }
}