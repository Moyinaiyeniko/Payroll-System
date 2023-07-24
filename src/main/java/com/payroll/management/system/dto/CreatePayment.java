package com.payroll.management.system.dto;

import java.math.BigDecimal;

public record CreatePayment(
       BigDecimal amountDeducted,
       String paymentMonth,
       String deductionType,
       String employeeId
) {
}
