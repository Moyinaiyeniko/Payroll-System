package com.payroll.management.system.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "leave")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Leave {

    @Id
    @SequenceGenerator(name = "leave_seq", sequenceName = "leave_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String leaveType;

    private LocalDate startDate;

    private LocalDate endDate;

    private String leaveStatus;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private Employee employee;





}
