package com.payroll.management.system.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.payroll.management.system.dto.enums.Gender;
import com.payroll.management.system.dto.enums.MaritalStatus;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
@Entity
@Table(name = "employee")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    @Id
//    @SequenceGenerator(name = "emp_seq", sequenceName = "employee_sequence")
//    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String nin;

    private String employeeId;

    private LocalDate dateOfBirth;

    private String phoneNumber;

    private String gender;

    private LocalDate dateJoined;

    private String status;

    private BigDecimal grossPay;

    private BigDecimal netPay;

    private String bankName;

    private String accountName;

    private String accountNumber;

    private String address;



    //@JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

//    @OneToOne(mappedBy = "payment")
//    private Payment payment;

//    @OneToOne(mappedBy = "leaves")
//    private Leave leaves;

}
