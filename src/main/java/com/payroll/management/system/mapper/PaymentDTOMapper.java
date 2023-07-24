package com.payroll.management.system.mapper;


import com.payroll.management.system.dto.LeaveResponseDTO;
import com.payroll.management.system.dto.PaymentResponseDTO;
import com.payroll.management.system.entity.Leave;
import com.payroll.management.system.entity.Payment;

import java.util.function.Function;

public class PaymentDTOMapper implements Function<Payment, PaymentResponseDTO> {

    @Override
    public PaymentResponseDTO apply(Payment payment){
        return new PaymentResponseDTO(
                payment.getPaymentMonth(),
                payment.getDeductionType(),
                payment.getAmountDeducted(),
                payment.getFinalAmount()
        );
    }
}
