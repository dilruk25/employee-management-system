package com.example.employee_management.controller;

import com.example.employee_management.dto.EmployeeDTO;
import com.example.employee_management.exception.EmployeeNotFoundException;
import com.example.employee_management.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for handling employee-related endpoints.
 */
@RestController
@RequestMapping(path = "/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    /**
     * Constructor for EmployeeController.
     * Inject EmployeeService to the controller class.
     *
     * @param employeeService The service class for employee-related operations.
     */
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * Endpoint for creating a new employee.
     *
     * @param request The request body containing employee information.
     * @return ResponseEntity containing the created employee's information.
     */
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

    /**
     * Endpoint for retrieving all employees.
     *
     * @return ResponseEntity containing a list of all employees' information.
     */
    @GetMapping
    public ResponseEntity<List<EmployeeResponse>> getAllEmployee() {
        List<EmployeeDTO> employeeDTOs = employeeService.getAllEmployee();
        List<EmployeeResponse> employeeResponses = employeeDTOs.stream().map(this::convertDTOToResponse).toList();
        return new ResponseEntity<>(employeeResponses, HttpStatus.OK);
    }

    /**
     * Retrieves an employee by ID.
     *
     * @param id the ID of the employee to retrieve
     * @return a ResponseEntity with the employee details if found, or a 404 NOT_FOUND status with an error message if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> getEmployeeById(@PathVariable long id) {
        try {
            EmployeeDTO employeeDTO = employeeService.getEmployeeById(id);
            EmployeeResponse employeeResponse = convertDTOToResponse(employeeDTO);
            return ResponseEntity.ok(employeeResponse);
        } catch (EmployeeNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Employee not found with id: " + id);
        }
    }

    /**
     * Endpoint for updating an employee's information.
     *
     * @param id              The ID of the employee to update.
     * @param employeeRequest The request body containing the updated employee information.
     * @return ResponseEntity containing the updated employee's information if successful, or 404 if not found.
     */
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponse> updateEmployeeById(@PathVariable long id, @RequestBody EmployeeRequest employeeRequest) {
        try {
            // Convert the request to DTO if necessary
            EmployeeDTO employeeDTO = convertRequestToDTO(employeeRequest);

            // Update the employee
            EmployeeDTO updatedEmployeeDTO = employeeService.updateEmployeeById(id, employeeDTO);

            // Convert the updated DTO to response
            EmployeeResponse employeeResponse = convertDTOToResponse(updatedEmployeeDTO);

            return ResponseEntity.ok(employeeResponse);
        } catch (EmployeeNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployeeById(@PathVariable long id) {
        try {
            employeeService.deleteEmployeeById(id);
            return ResponseEntity.ok("Employee deleted successfully");
        } catch (EmployeeNotFoundException e) {
            return new ResponseEntity<>("Employee not available with that ID", HttpStatus.NOT_FOUND);
        }
    }


    /**
     * Deletes all employees.
     *
     * @return a ResponseEntity with a message indicating the number of employees deleted
     */
    @DeleteMapping
    public ResponseEntity<String> deleteAllEmployees() {
        ResponseEntity<String> response = employeeService.deleteAll();
        return ResponseEntity.ok(response.getBody());
    }

    /**
     * Converts an EmployeeRequest object to an EmployeeDTO object.
     *
     * @param request The EmployeeRequest object to convert.
     * @return The converted EmployeeDTO object.
     */
    private EmployeeDTO convertRequestToDTO(EmployeeRequest request) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setName(request.getName());
        employeeDTO.setJobTitle(request.getJobTitle());
        employeeDTO.setDateOfBirth(request.getDateOfBirth());
        employeeDTO.setSalary(request.getSalary());
        return employeeDTO;
    }

    /**
     * Converts an EmployeeDTO object to an EmployeeResponse object.
     *
     * @param employeeDTO The EmployeeDTO object to convert.
     * @return The converted EmployeeResponse object.
     */
    private EmployeeResponse convertDTOToResponse(EmployeeDTO employeeDTO) {
        EmployeeResponse response = new EmployeeResponse();
        response.setName(employeeDTO.getName());
        response.setJobTitle(employeeDTO.getJobTitle());
        response.setDateOfBirth(employeeDTO.getDateOfBirth());
        response.setSalary(employeeDTO.getSalary());
        return response;
    }
}
