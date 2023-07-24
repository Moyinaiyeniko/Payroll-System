package com.payroll.management.system.entity;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailDetails {

    private String recipient;
    private String msgBody;
    private String subject;
    private String attachment;
}
