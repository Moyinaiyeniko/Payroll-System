package com.payroll.management.system.service;

import com.payroll.management.system.dto.CreateNewEmployeeRecordDTO;
import com.payroll.management.system.dto.CreateNewUserDTO;
import com.payroll.management.system.dto.EmployeeResponseDTO;
import com.payroll.management.system.dto.UpdateEmployeeRecordDTO;
import com.payroll.management.system.entity.EmailDetails;
import com.payroll.management.system.entity.Employee;
import com.payroll.management.system.entity.Role;
import com.payroll.management.system.entity.User;
import com.payroll.management.system.exception.BadRequestException;
import com.payroll.management.system.exception.NotFoundException;
import com.payroll.management.system.mapper.EmployeeDTOMapper;
import com.payroll.management.system.mapper.EmployeeMapper;
import com.payroll.management.system.repository.EmployeeRepository;
import com.payroll.management.system.repository.RoleRepository;
import com.payroll.management.system.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final EmployeeRepository employeeRepository;
    private final EmployeeDTOMapper employeeDTOMapper = new EmployeeDTOMapper();
    private final EmailService emailService;



    private EmployeeMapper employeeMapper = Mappers.getMapper(EmployeeMapper.class);
  BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
  // BCryptPasswordEncoder passwordEncoder1;


    @Override
    public UserDetails loadUserByUsername(String userName)   {
        User user = userRepository.findByUserName(userName);

        if(user == null){
            log.info("user {} not found" , user.getUserName());
            throw new UsernameNotFoundException("user not found in the db");
        }
        else{
            log.info("user found in the db");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
        log.info("user reach here");
        return new org.springframework.security.core.userdetails.User(user.getUserName(),
                user.getPassword(),authorities);
    }

    @Override
    public User saveUser(CreateNewUserDTO createNewUserDTO) {
        if(userRepository.findByUserName(createNewUserDTO.getEmail()) != null){
            throw new BadRequestException("User with email already exists");
        }
        log.info("saving new user {} to the db", createNewUserDTO.getFirstName());
         User user = new User();
         Collection<Role> roles = user.getRoles();
         roles.add(createNewUserDTO.getRole());
         user.setRoles(roles);
         user.setFirstName(createNewUserDTO.getFirstName());
         user.setLastName(createNewUserDTO.getLastName());
         user.setUserName(createNewUserDTO.getEmail());
         //Randomly generate a password
        //RandomStringUtils.randomAscii(32)
        user.setPassword(passwordEncoder.encode(createNewUserDTO.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User saveUser1(String role, String firstName, String lastName, String email, String password) {
     //   log.info("saving new user {} to the db", createNewUserDTO.getFirstName());
        if(userRepository.findByUserName(email) != null){
            throw new BadRequestException("Employee with email already exists");
        }
        User user = new User();
        Collection<Role> roles = user.getRoles();
        roles.add(Role.builder().name(role).build());
        user.setRoles(roles);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUserName(email);
        //Randomly generate a password
        //RandomStringUtils.randomAscii(32)
        user.setPassword(passwordEncoder.encode(password));
        return userRepository.save(user);
    }

    @Override
    public void saveEmployeeRecord(CreateNewEmployeeRecordDTO createNewEmployeeRecordDTO) {
        log.info("saving new record {} to the db", createNewEmployeeRecordDTO.getFirstName());
        Employee employee = new Employee();
       try {
           String password = generateRandomPassword(10);
           User user = saveUser1(createNewEmployeeRecordDTO.getRole()
                   , createNewEmployeeRecordDTO.getFirstName(), createNewEmployeeRecordDTO.getLastName(),
                   createNewEmployeeRecordDTO.getEmail(), password);
            employee = employeeMapper.dtoToEmployeeObject(createNewEmployeeRecordDTO);
            char firstName = createNewEmployeeRecordDTO.getFirstName().charAt(0);
           char lastName = createNewEmployeeRecordDTO.getLastName().charAt(0);
           employee.setUser(user);
           employee.setEmployeeId(firstName+lastName+generateRandomPassword(6));
           employee.setDateJoined(LocalDate.now());
           employeeRepository.save(employee);

           emailService.sendSimpleMail(new EmailDetails(createNewEmployeeRecordDTO.getEmail(), "Hello, Your username is your email and your password is " +
                   password, "Login Details", null));
       }
       catch (Exception ex){
           ex.getMessage();
       }
     //   return employeeDTOMapper.apply(employee);
    }
@Override
    public void updateEmployeeRecord(UpdateEmployeeRecordDTO updateEmployeeRecordDTO, Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new NotFoundException("Employee does not exist"));
        User user = saveUser1(updateEmployeeRecordDTO.getRole()
                , updateEmployeeRecordDTO.getFirstName(), updateEmployeeRecordDTO.getLastName(),
                updateEmployeeRecordDTO.getEmail(), employee.getUser().getPassword());
        employee.setUser(user);
        employee.setAccountName(updateEmployeeRecordDTO.getAccountName());
        employee.setAddress(updateEmployeeRecordDTO.getAddress());
        employee.setAccountNumber(updateEmployeeRecordDTO.getAccountNumber());
        employee.setDateOfBirth(updateEmployeeRecordDTO.getDateOfBirth());
        employee.setGender(updateEmployeeRecordDTO.getGender().toString());
        employee.setGrossPay(updateEmployeeRecordDTO.getGrossPay());
        employeeRepository.save(employee);
    }
@Override
    public EmployeeResponseDTO getEmployeeByEmail(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
         String userName = auth.getPrincipal().toString();
         Long userId = userRepository.getUserId(userName).orElseThrow(() -> new NotFoundException("User does not exist"));
    return employeeRepository.findByUser_Id(userId)
            .map(employeeDTOMapper)
            .orElseThrow(() -> new NotFoundException("Employee does not exist"));

        //return null;
    }

    public Employee getEmployeeUsingEmail(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getPrincipal().toString();
        Long userId = userRepository.getUserId(userName).orElseThrow(() -> new NotFoundException("User does not exist"));
        return employeeRepository.findByUser_Id(userId)
              .orElseThrow(() -> new NotFoundException("Employee does not exist"));

        //return null;
    }
    @Override
    public List<EmployeeResponseDTO>  getAllEmployees(){
        return employeeRepository.findAll()
                .stream()
                .map(employeeDTOMapper)
                .collect(Collectors.toList());
    }



    @Override
    public Role saveRole(Role role) {
        log.info("saving new role {} to the db", role.getName());
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String userName, String roleName) {
User user = userRepository.findByUserName(userName);
        System.out.println(user.getFirstName());
log.info("user name is {}",user.getUserName());
Role role = roleRepository.findByName(roleName);
user.getRoles().add(role);
    }

    @Override
    public User getUser(String userName) {
      return Optional.of(userRepository.findByUserName(userName))
                .orElseThrow(() -> new NotFoundException("not found"));
       // return userRepository.findByUserName(userName);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }



    public static String generateRandomPassword(int len)
    {
        // ASCII range – alphanumeric (0-9, a-z, A-Z)
        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        SecureRandom random = new SecureRandom();

        // each iteration of the loop randomly chooses a character from the given
        // ASCII range and appends it to the `StringBuilder` instance
        return IntStream.range(0, len)
                .map(i -> random.nextInt(chars.length()))
                .mapToObj(randomIndex -> String.valueOf(chars.charAt(randomIndex)))
                .collect(Collectors.joining());
    }

}
