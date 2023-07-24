package com.payroll.management.system.entity;

import lombok.*;
import org.hibernate.annotations.Cascade;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
@Id
//@SequenceGenerator(name = "user_seq", sequenceName = "user_sequence")
//@GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String firstName;

    private String lastName;

    @Column(name = "email")
    private String userName;

    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.MERGE, org.hibernate.annotations.CascadeType.PERSIST})
    private Collection<Role> roles = new ArrayList<>();

    @OneToOne(mappedBy = "user")
    private Employee employee;
}
