package com.payroll.management.system.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
 @SequenceGenerator(name = "role_seq", sequenceName = "role_sequence")
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pet_
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;
}
