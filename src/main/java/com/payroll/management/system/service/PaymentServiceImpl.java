package com.payroll.management.system.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.payroll.management.system.dto.*;
import com.payroll.management.system.entity.Employee;
import com.payroll.management.system.entity.Leave;
import com.payroll.management.system.entity.Payment;
import com.payroll.management.system.exception.InvalidDateException;
import com.payroll.management.system.exception.NotFoundException;
import com.payroll.management.system.mapper.LeaveDTOMapper;
import com.payroll.management.system.mapper.LeaveListDTOMapper;
import com.payroll.management.system.mapper.PaymentDTOMapper;
import com.payroll.management.system.repository.EmployeeRepository;
import com.payroll.management.system.repository.LeaveRepository;
import com.payroll.management.system.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService{

     private final LeaveRepository leaveRepository;

     private final EmployeeRepository employeeRepository;

     private final PaymentRepository paymentRepository;

     private final UserService userService;

     private final LeaveDTOMapper leaveDTOMapper = new LeaveDTOMapper();

    private final PaymentDTOMapper paymentDTOMapper = new PaymentDTOMapper();

    private final LeaveListDTOMapper leaveListDTOMapper = new LeaveListDTOMapper();

     public void createLeaveRequest(CreateLeaveRequest leaveRequest){
         LocalDate startDate = leaveRequest.startDate();
         LocalDate endDate = leaveRequest.endDate();
         if (startDate.isAfter(endDate) ){
             throw new InvalidDateException("Start date cannot be later than endDate");
         }
         if(startDate.isBefore(LocalDate.now()) || endDate.isBefore(LocalDate.now())){
             throw new InvalidDateException("Start or End date must be later than today");

         }
          Leave leave = Leave.builder()
                  .createdAt(LocalDateTime.now())
                  .startDate(leaveRequest.startDate())
                  .endDate(leaveRequest.endDate())
                  .leaveType(leaveRequest.leaveType().name())
                  .employee(userService.getEmployeeUsingEmail())
                  .build();
          leaveRepository.save(leave);
     }

     public void updateLeaveRequest(Long id, UpdateLeaveRequest updateLeaveRequest){
        Leave leave = leaveRepository.findById(id).orElseThrow(() -> new NotFoundException("Leave Id does not exist"));
        leave.setUpdatedAt(LocalDateTime.now());
        leave.setLeaveStatus(updateLeaveRequest.leaveStatus().name());
        leaveRepository.save(leave);
     }

     public List<LeaveResponseDTO> getEmployeeLeaveRequests(Long employeeId){
          List<Leave> leaveRequests = leaveRepository.findByEmployeeId(employeeId).orElseThrow(() -> new NotFoundException("Employee currently has no leave"));
   return leaveRequests.stream().map(leave -> leaveDTOMapper.apply(leave)).collect(Collectors.toList());
     }

     public LeaveResponseDTO getLeaveRequest(Long id){
        return leaveRepository.findById(id).map(leaveDTOMapper).orElseThrow(() -> new NotFoundException("Leave does not exist"));

     }

     public List<LeaveResponseListDTO> getAllLeaveRequest(){
         List<Leave> leaveRequests = leaveRepository.findAll();
         return leaveRequests.stream().map(leave -> leaveListDTOMapper.apply(leave)).collect(Collectors.toList());

     }

     public void createPayment(CreatePayment createPayment){
    Employee employee = employeeRepository.findByEmployeeId(createPayment.employeeId()).orElseThrow(() -> new NotFoundException("Employee with Id does not exist"));
         BigDecimal netSalary = employee.getNetPay();
         BigDecimal finalSalary = netSalary.subtract(createPayment.amountDeducted());
         Payment payment = Payment.builder().paymentDate(LocalDateTime.now())
                 .amountDeducted(createPayment.amountDeducted())
                 .paymentMonth(createPayment.paymentMonth())
                 .finalAmount(finalSalary)
                 .employee(employee)
                 .build();
         paymentRepository.save(payment);
     }

     public List<PaymentResponseDTO> getEmployeePaymentData(Long employeeId){
         List<Payment> paymentData = paymentRepository.findByEmployeeId(employeeId).orElseThrow(() -> new NotFoundException("Employee currently has no payment data"));
         return paymentData.stream().map(payment -> paymentDTOMapper.apply(payment)).collect(Collectors.toList());

     }

     public void getPaymentPayroll(Long id, HttpServletResponse response) throws IOException, DocumentException {
        Payment payment = paymentRepository.findById(id).orElseThrow(() -> new NotFoundException("Payment does not exist"));
        PaymentResponseDTO paymentResponseDTO = paymentDTOMapper.apply(payment);
        Document document = new Document(PageSize.A4);
         PdfWriter.getInstance(document, response.getOutputStream());
         document.open();
         Font fontTitle = FontFactory.getFont(FontFactory.TIMES_ROMAN);
         fontTitle.setSize(20);
         Paragraph paragraph = new Paragraph("Payroll for this Month", fontTitle);
         paragraph.setAlignment(Paragraph.ALIGN_CENTER);
         document.add(paragraph);
         PdfPTable table = new PdfPTable(4);
         table.setWidthPercentage(100f);
         table.setWidths(new int[] { 3, 3, 3, 3 });
         table.setSpacingBefore(5);
         PdfPCell cell = new PdfPCell();
         cell.setBackgroundColor(CMYKColor.MAGENTA);
         cell.setPadding(5);
         Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
         font.setColor(CMYKColor.WHITE);
         cell.setPhrase(new Phrase("Payment Month", font));
         table.addCell(cell);
         cell.setPhrase(new Phrase("Amount Deducted", font));
         table.addCell(cell);
         cell.setPhrase(new Phrase("Deduction Type", font));
         table.addCell(cell);
         cell.setPhrase(new Phrase("Total Amount Paid", font));
         table.addCell(cell);

         table.addCell(paymentResponseDTO.paymentMonth());
         table.addCell(paymentResponseDTO.deductionType());
         table.addCell(String.valueOf(paymentResponseDTO.amountDeducted()));
         table.addCell(String.valueOf(paymentResponseDTO.finalPayment()));

         document.add(table);
         document.close();
     }

}
