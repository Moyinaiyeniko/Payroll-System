package com.payroll.management.system.service;

import com.payroll.management.system.entity.EmailDetails;

public interface EmailService {

    String sendSimpleMail(EmailDetails details);

   // String sendMailWithAttachment(EmailDetails details);
}
