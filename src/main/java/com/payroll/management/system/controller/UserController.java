package com.payroll.management.system.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.payroll.management.system.dto.AddRoleToUserDTO;
import com.payroll.management.system.dto.CreateNewEmployeeRecordDTO;
import com.payroll.management.system.dto.CreateNewUserDTO;
import com.payroll.management.system.dto.EmployeeResponseDTO;
import com.payroll.management.system.entity.Employee;
import com.payroll.management.system.entity.Role;
import com.payroll.management.system.entity.User;
import com.payroll.management.system.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> users(){
       return ResponseEntity.ok().body(userService.getUsers());
    }
    @GetMapping("/employee/profile")
            public EmployeeResponseDTO getEmployeeByEmail() {
       return userService.getEmployeeByEmail();
    }
    @PostMapping("/user/save")
    public ResponseEntity<User> saveUser(@RequestBody CreateNewUserDTO createNewUserDTO){
        return ResponseEntity.ok().body(userService.saveUser(createNewUserDTO));
    }

    @PostMapping("/employee/new")
    public void createNewEmployee(@RequestBody CreateNewEmployeeRecordDTO createNewEmployeeRecordDTO){
        //return ResponseEntity.ok().body(userService.saveEmployeeRecord(createNewEmployeeRecordDTO));
        userService.saveEmployeeRecord(createNewEmployeeRecordDTO);
    }
    @GetMapping("/employees")
    public ResponseEntity<List<EmployeeResponseDTO>> employees(){
        return ResponseEntity.ok().body(userService.getAllEmployees());
    }

    @PostMapping("/role/save")
    public ResponseEntity<Role> saveRole(@RequestBody Role role){
        return ResponseEntity.ok().body(userService.saveRole(role));
    }

    @PostMapping("/addroletouser")
    public void addRoleToUser(@RequestBody AddRoleToUserDTO addRoleToUserDTO){
         userService.addRoleToUser(addRoleToUserDTO.userName(), addRoleToUserDTO.roleName());
    }

    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String authorizationHeader = request.getHeader(AUTHORIZATION);
        if(authorizationHeader !=null && authorizationHeader.startsWith("Bearer ")){
            try {
                String token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes(StandardCharsets.UTF_8));
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(token);
                String username = decodedJWT.getSubject();
                User user = userService.getUser(username);
                String [] roles = decodedJWT.getClaim("roles").asArray(String.class);
                String refresh_token = JWT.create().withSubject(user.getUserName())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 *60 *1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                        .sign(algorithm);

                Map<String,String> tokens = new HashMap<>();

                tokens.put("refresh_token", refresh_token);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);


            }
            catch (Exception exception){

                response.setHeader("error", exception.getMessage());
                response.setStatus(FORBIDDEN.value());
                // response.sendError(FORBIDDEN.value());
                Map<String,String> error = new HashMap<>();
                error.put("error_message", exception.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }

        }
        else {
           throw new RuntimeException("refresh token is missing");
        }
    }
}
