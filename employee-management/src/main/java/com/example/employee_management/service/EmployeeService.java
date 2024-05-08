package com.example.employee_management.service;

import com.example.employee_management.dto.EmployeeDTO;
import com.example.employee_management.enums.JobTitle;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface EmployeeService {
    //saveEmployee
    EmployeeDTO saveEmployee(EmployeeDTO employeeDTO);

    //getAllEmployee
    List<EmployeeDTO> getAllEmployee();

    //getEmployeeById
    EmployeeDTO getEmployeeById(long id);

    //updateEmployee
    EmployeeDTO updateEmployeeById(long id, EmployeeDTO employeeDTO);

    //deleteEmployeeById
    void deleteEmployeeById(long id);

    //deleteAll
    ResponseEntity<String> deleteAll();

    //getEmployeeByJobTitle
    List<EmployeeDTO> getEmployeeByJobTitle(JobTitle jobTitle);
}
