package com.example.employee_management.service;

import com.example.employee_management.dto.EmployeeDTO;
import com.example.employee_management.model.Employee;

import java.util.List;

public interface EmployeeService {
    //saveEmployee
    void saveEmployee(EmployeeDTO employeeDTO);

    //getAllEmployee
    List<EmployeeDTO> getAllEmployee(EmployeeDTO employeeDTO);

    //getEmployeeById
    EmployeeDTO getEmployeeById(EmployeeDTO employeeDTO);

    //updateEmployee
    //deleteEmployeeById
    //deleteAll
    //getEmployeeByJobTitle
}
