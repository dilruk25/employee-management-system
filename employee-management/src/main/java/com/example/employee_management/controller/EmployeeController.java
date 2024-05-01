package com.example.employee_management.controller;

import com.example.employee_management.dto.EmployeeDTO;
import com.example.employee_management.exception.EmployeeNotFoundException;
import com.example.employee_management.model.Employee;
import com.example.employee_management.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    // Inject EmployeeService to the controller class.
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<EmployeeResponse> saveEmployee(@RequestBody EmployeeRequest request) {
        // Convert request object to DTO
        EmployeeDTO employeeDTO = convertRequestToDTO(request);

        // Pass DTO to service and get the processed data
        EmployeeDTO processedEmployeeDTO = employeeService.saveEmployee(employeeDTO);

        // Convert DTO to response object
        EmployeeResponse response = convertDTOToResponse(processedEmployeeDTO);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployee() {
        List<EmployeeDTO> employees = employeeService.getAllEmployee();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable long id) {
        try {
            EmployeeDTO employeeDTO = employeeService.getEmployeeById(id);
            return new ResponseEntity<>(employeeDTO, HttpStatus.OK);
        } catch (EmployeeNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployeeById(@PathVariable long id, @RequestBody EmployeeDTO employeeDTO) {
        try {
            EmployeeDTO updatedEmployee = employeeService.updateEmployeeById(id, employeeDTO);
            return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
        } catch (EmployeeNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EmployeeResponse> deleteEmployeeById(@PathVariable long id) {
        try {
            employeeService.deleteEmployeeById(id);
            return ResponseEntity.ok(new EmployeeResponse("Deleted employee with ID: " + id));
        } catch (EmployeeNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }




    private EmployeeDTO convertRequestToDTO(EmployeeRequest request) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setName(request.getName());
        employeeDTO.setJobTitle(request.getJobTitle());
        employeeDTO.setDateOfBirth(request.getDateOfBirth());
        employeeDTO.setSalary(request.getSalary());
        return employeeDTO;
    }

    private EmployeeResponse convertDTOToResponse(EmployeeDTO employeeDTO) {
        EmployeeResponse response = new EmployeeResponse();
        response.setName(employeeDTO.getName());
        response.setJobTitle(employeeDTO.getJobTitle());
        response.setDateOfBirth(employeeDTO.getDateOfBirth());
        response.setSalary(employeeDTO.getSalary());
        return response;
    }
}
