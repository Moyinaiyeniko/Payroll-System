package com.payroll.management.system.service;

import com.payroll.management.system.dto.CreateNewEmployeeRecordDTO;
import com.payroll.management.system.dto.enums.Gender;
import com.payroll.management.system.dto.enums.MaritalStatus;
import com.payroll.management.system.entity.Employee;
import com.payroll.management.system.entity.User;
import com.payroll.management.system.mapper.EmployeeMapper;
import com.payroll.management.system.repository.EmployeeRepository;
import com.payroll.management.system.repository.RoleRepository;
import com.payroll.management.system.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
   @Mock
   private EmployeeRepository employeeRepository;
    private  UserRepository userRepository;
    private  RoleRepository roleRepository;
    private  EmailService emailService;
    @Mock
    private UserService underTest;
    private EmployeeMapper employeeMapper = Mappers.getMapper(EmployeeMapper.class);






    @BeforeEach
    void setUp() {
        underTest = new UserServiceImpl(userRepository,roleRepository,employeeRepository,emailService);
    }

    @Test
    void saveEmployeeRecord() {
     //given
     User user = User.builder().id(1L).userName("moyina@gmail.com")
             .lastName("Aiye").firstName("Moyin").build();
//     CreateNewEmployeeRecordDTO employee = Employee.builder().id(2L).user(user)
//             .employeeId("ET1234").gender("Female").accountName("Moyin Aiye")
//             .phoneNumber("09026789625").bankName("GTB").build();

     CreateNewEmployeeRecordDTO employeeRecordDTO = new CreateNewEmployeeRecordDTO
             (
                     "222111",
                     "1997-07-17",
                     "Moyin",
                     "Aiye",
                     "moyina@gmail.com",
                     "Employee",
                     MaritalStatus.SINGLE,
                     "09026789625",
                     Gender.FEMALE,
                     BigDecimal.valueOf(200.0),
                     BigDecimal.valueOf(150.0),
                     "Moyin AIye",
                     "GTB",
                     "123456",
                     "Lagos"

             );
     //when
underTest.saveEmployeeRecord(employeeRecordDTO);

     //then

     ArgumentCaptor<Employee> employeeArgumentCaptor
             =ArgumentCaptor.forClass(Employee.class);
         verify(employeeRepository).save(employeeArgumentCaptor.capture());
        Employee capturedEmployee = employeeArgumentCaptor.getValue();
     assertThat(capturedEmployee).isEqualTo(employeeMapper.dtoToEmployeeObject(employeeRecordDTO));
    }

    @Test
    void updateEmployeeRecord() {
    }

    @Test
    void getEmployeeByEmail() {
    }

    @Test
    @Disabled
    void getEmployeeUsingEmail() {
    }

    @Test
    void getEmployees() {

        //when
underTest.getAllEmployees();
        //then
      verify(employeeRepository).findAll();
    }
}