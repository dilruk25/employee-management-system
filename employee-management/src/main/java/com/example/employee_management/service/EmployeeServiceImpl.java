package com.example.employee_management.service;

import com.example.employee_management.dto.EmployeeDTO;
import com.example.employee_management.enums.JobTitle;
import com.example.employee_management.exception.EmployeeNotFoundException;
import com.example.employee_management.model.Employee;
import com.example.employee_management.repository.EmployeeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


/**
 * EmployeeServiceImpl class provides implementation for EmployeeService interface.
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    EmployeeRepository employeeRepository;

    /**
     * Injects EmployeeRepository to the service class.
     *
     * @param employeeRepository the employee repository
     */
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    /**
     * Saves an employee to the database.
     *
     * @param employeeDTO the employee data transfer object
     * @return the saved employee DTO
     */
    @Override
    public EmployeeDTO saveEmployee(EmployeeDTO employeeDTO) {
        Employee employee = convertDtoToEntity(employeeDTO);
        Employee persistedEmployee = employeeRepository.save(employee);
        return convertEntityToDto(persistedEmployee);
    }

    /**
     * Retrieves all employees from the database.
     *
     * @return a list of all employee DTOs
     */
    @Override
    public List<EmployeeDTO> getAllEmployee() {
        List<Employee> persistedEmployees = employeeRepository.findAll();
        return persistedEmployees.stream()
                .map(this::convertEntityToDto)
                .toList();
    }

    /**
     * Retrieves an employee by ID from the database.
     *
     * @param id the employee ID
     * @return the employee DTO if found
     * @throws EmployeeNotFoundException if no employee with the given ID is found
     */
    @Override
    public EmployeeDTO getEmployeeById(long id) throws EmployeeNotFoundException {
        return employeeRepository.findById(id)
                .map(this::convertEntityToDto)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + id));
    }

    /**
     * Updates an employee in the database.
     *
     * @return the updated employee DTO
     * @throws EmployeeNotFoundException if the employee to update is not found
     */
    @Override
    public EmployeeDTO updateEmployeeById(long id, EmployeeDTO employeeDTO) {
//        Employee existingEmployee = employeeRepository.findById(id)
//                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + id));

//        // Update the existing employee with data from the DTO
//        existingEmployee.setName(employeeDTO.getName());
//        existingEmployee.setJobTitle(employeeDTO.getJobTitle());
//        existingEmployee.setDateOfBirth(employeeDTO.getDateOfBirth());
//        existingEmployee.setSalary(employeeDTO.getSalary());

        Employee employee = convertDtoToEntity(employeeDTO);

        // Save the updated employee
        Employee updatedEmployee = employeeRepository.save(employee);

        return convertEntityToDto(updatedEmployee);
    }

    /**
     * Deletes an employee with the specified ID from the database.
     *
     * @param id the ID of the employee to delete
     */
    @Override
    public void deleteEmployeeById(long id) throws EmployeeNotFoundException {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isPresent()) {
            employeeRepository.deleteById(id);
        } else {
            throw new EmployeeNotFoundException("Employee not found with id: " + id);
        }
    }


    /**
     * Deletes all employees from the database.
     *
     * @return a ResponseEntity with a success message indicating the number of employees deleted
     */
    @Override
    public ResponseEntity<String> deleteAll() {
        long count = employeeRepository.count();
        employeeRepository.deleteAll();
        employeeRepository.resetEmployeeId();
        return ResponseEntity.ok("Deleted " + count + " employees");
    }

//TODO
    @Override
    public EmployeeDTO getEmployeeByJobTitle(JobTitle jobTitle) {
        return null;
    }

    /**
     * Converts an employee entity to an employee DTO.
     *
     * @param employee the employee entity
     * @return the employee DTO
     */
    public EmployeeDTO convertEntityToDto(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();

        employeeDTO.setId(employee.getId());
        employeeDTO.setName(employee.getName());
        employeeDTO.setJobTitle(employee.getJobTitle());
        employeeDTO.setDateOfBirth(employee.getDateOfBirth());
        employeeDTO.setSalary(employee.getSalary());

        return employeeDTO;
    }

    /**
     * Converts an employee DTO to an employee entity.
     *
     * @param employeeDTO the employee DTO
     * @return the employee entity
     */
    public Employee convertDtoToEntity(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setId(employeeDTO.getId());
        employee.setName(employeeDTO.getName());
        employee.setJobTitle(employeeDTO.getJobTitle());
        employee.setDateOfBirth(employeeDTO.getDateOfBirth());
        employee.setSalary(employeeDTO.getSalary());

        return employee;
    }
}
