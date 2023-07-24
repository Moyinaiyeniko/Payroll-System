package com.payroll.management.system.dto;

import java.math.BigDecimal;

public record PaymentResponseDTO(String paymentMonth,
                                 String deductionType,
                                 BigDecimal amountDeducted,
                                 BigDecimal finalPayment) {
}
