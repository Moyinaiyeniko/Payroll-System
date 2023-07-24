package com.payroll.management.system.controller;

import com.itextpdf.text.DocumentException;
import com.payroll.management.system.dto.*;
import com.payroll.management.system.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/leave/request/new")
    private void createLeaveRequest(@RequestBody CreateLeaveRequest leaveRequest){
        paymentService.createLeaveRequest(leaveRequest);
    }

    @PutMapping("/leave/request/update/{id}")
    private void updateLeaveRequest(@RequestBody UpdateLeaveRequest updateLeaveRequest, @PathVariable Long id){
        paymentService.updateLeaveRequest(id, updateLeaveRequest);
    }

    @GetMapping("/leave/request/get/{employeeId}")
    private List<LeaveResponseDTO> getEmployeeLeaveRequests(@PathVariable Long employeeId){
       return paymentService.getEmployeeLeaveRequests(employeeId);
    }

    @GetMapping("/leave/request/fetch/{id}")
    private LeaveResponseDTO getLeaveRequest(@PathVariable Long id) {
        return paymentService.getLeaveRequest(id);
    }
    @GetMapping("/leave/requests")
    private List<LeaveResponseListDTO> getAllLeaveRequest(){
        return paymentService.getAllLeaveRequest();
    }

    @PostMapping("/payment/request/new")
    private void createPayment(@RequestBody CreatePayment createPayment){
        paymentService.createPayment(createPayment);
    }

    @GetMapping("/payment/print/payroll/{id}")
    public void generatePdf(@PathVariable Long id, HttpServletResponse response) throws DocumentException, IOException {

        response.setContentType("application/pdf");
        DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD:HH:MM:SS");
        String currentDateTime = dateFormat.format(new Date());
        String headerkey = "Content-Disposition";
        String headervalue = "attachment; filename=pdf_" + currentDateTime + ".pdf";
        response.setHeader(headerkey, headervalue);

        paymentService.getPaymentPayroll(id,response);


    }
}
