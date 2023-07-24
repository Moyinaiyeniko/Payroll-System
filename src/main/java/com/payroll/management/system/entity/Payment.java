package com.payroll.management.system.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    @SequenceGenerator(name = "payment_seq", sequenceName = "payment_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private BigDecimal amountDeducted;

    private BigDecimal finalAmount;

    private LocalDateTime paymentDate;

    private String paymentMonth;

    private String deductionType;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private Employee employee;



}
