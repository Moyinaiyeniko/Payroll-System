package com.payroll.management.system.service;

import com.itextpdf.text.DocumentException;
import com.payroll.management.system.dto.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface PaymentService {
   void createLeaveRequest(CreateLeaveRequest leaveRequest);
   void updateLeaveRequest(Long id, UpdateLeaveRequest updateLeaveRequest);
   void createPayment(CreatePayment createPayment);
   List<PaymentResponseDTO> getEmployeePaymentData(Long employeeId);
   void getPaymentPayroll(Long id, HttpServletResponse response) throws IOException, DocumentException;
   List<LeaveResponseDTO> getEmployeeLeaveRequests(Long employeeId);
   LeaveResponseDTO getLeaveRequest(Long id);
   List<LeaveResponseListDTO> getAllLeaveRequest();

}
