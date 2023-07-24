package com.payroll.management.system.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

public class Department {

    @Id
    @SequenceGenerator(name = "dep_seq", sequenceName = "department_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)

    private Long id;

    @Column(name = "department_name")
    private String departmentName;

    private String description;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
