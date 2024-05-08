package com.example.employee_management.service;

import com.example.employee_management.dto.EmployeeDTO;
import com.example.employee_management.exception.EmployeeNotFoundException;
import com.example.employee_management.model.Employee;
import com.example.employee_management.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.example.employee_management.enums.JobTitle.BACKEND_DEVELOPER;
import static com.example.employee_management.enums.JobTitle.FRONTEND_DEVELOPER;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeServiceImpl;

    @Test
    void saveEmployee() {
        // Create an EmployeeDTO object as a template
        EmployeeDTO employeeDTO = new EmployeeDTO(1L, "Tharuka", BACKEND_DEVELOPER,
                LocalDate.of(2002, Month.MARCH, 21), 50000);

        // Convert the EmployeeDTO to an Employee entity object
        Employee mockEmployee = employeeServiceImpl.convertDtoToEntity(employeeDTO);

        // Mock the behavior of the save() method of the EmployeeRepository
        // Return mockEmployee when the save() method is called
        when(employeeRepository.save(any())).thenReturn(mockEmployee);

        // Call the saveEmployee() method with the EmployeeDTO object
        EmployeeDTO savedEmployee = employeeServiceImpl.saveEmployee(employeeDTO);

        // Assertions
        // Verify that the savedEmployee is not null
        assertNotNull(savedEmployee);
        // Verify that the name of the saved employee matches the name in the EmployeeDTO
        assertEquals(employeeDTO.getName(), savedEmployee.getName());
        // Verify that the job title of the saved employee matches the job title in the EmployeeDTO
        assertEquals(employeeDTO.getJobTitle(), savedEmployee.getJobTitle());
        // Verify that the date of birth of the saved employee matches the date of birth in the EmployeeDTO
        assertEquals(employeeDTO.getDateOfBirth(), savedEmployee.getDateOfBirth());
        // Verify that the salary of the saved employee matches the salary in the EmployeeDTO
        assertEquals(employeeDTO.getSalary(), savedEmployee.getSalary());
    }


    @Test
    void getAllEmployee() {
        // Create mock Employee objects
        Employee employee1 = new Employee(1L, "Tharuka", BACKEND_DEVELOPER,
                LocalDate.of(2002, Month.MARCH, 21), 50000);
        Employee employee2 = new Employee(2L, "Dilruk", FRONTEND_DEVELOPER,
                LocalDate.of(1999, Month.APRIL, 12), 70000);

        // Mock the behavior of findAll() method
        when(employeeRepository.findAll()).thenReturn(Arrays.asList(employee1, employee2));

        // Call the getAllEmployee() method
        List<EmployeeDTO> employeeDTOList = employeeServiceImpl.getAllEmployee();

        // Verify the results
        assertNotNull(employeeDTOList);
        assertEquals(2, employeeDTOList.size());

        // Verify the content of the first employee DTO
        EmployeeDTO firstEmployeeDTO = employeeDTOList.get(0);
        assertEquals(employee1.getId(), firstEmployeeDTO.getId());
        assertEquals(employee1.getName(), firstEmployeeDTO.getName());
        assertEquals(employee1.getJobTitle(), firstEmployeeDTO.getJobTitle());
        assertEquals(employee1.getDateOfBirth(), firstEmployeeDTO.getDateOfBirth());
        assertEquals(employee1.getSalary(), firstEmployeeDTO.getSalary());

        // Verify the content of the second employee DTO
        EmployeeDTO secondEmployeeDTO = employeeDTOList.get(1);
        assertEquals(employee2.getId(), secondEmployeeDTO.getId());
        assertEquals(employee2.getName(), secondEmployeeDTO.getName());
        assertEquals(employee2.getJobTitle(), secondEmployeeDTO.getJobTitle());
        assertEquals(employee2.getDateOfBirth(), secondEmployeeDTO.getDateOfBirth());
        assertEquals(employee2.getSalary(), secondEmployeeDTO.getSalary());
    }


    @Test
    void EmployeeNotFoundException() {
        // Mock the behavior of findById() method to return an empty Optional
        when(employeeRepository.findById(any())).thenReturn(Optional.empty());

        // Call the getEmployeeById() method with any employee ID
        assertThrows(EmployeeNotFoundException.class, () -> {
            employeeServiceImpl.getEmployeeById(1L);
        });
    }

    @Test
    void getEmployeeById() throws EmployeeNotFoundException {
        // Create a mock Employee object
        Employee employee = new Employee(1L, "Dilruk", BACKEND_DEVELOPER, LocalDate.of(2002, 2, 25), 10000);

        // Mock the behavior of findById() method
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        // Call the getEmployeeById() method with the employee ID
        EmployeeDTO getEmployeeById = employeeServiceImpl.getEmployeeById(1L);

        assertNotNull(getEmployeeById);
        assertEquals(employee.getId(), getEmployeeById.getId());
        assertEquals(employee.getName(), getEmployeeById.getName());
        assertEquals(employee.getJobTitle(), getEmployeeById.getJobTitle());
        assertEquals(employee.getDateOfBirth(), getEmployeeById.getDateOfBirth());
        assertEquals(employee.getSalary(), getEmployeeById.getSalary());
    }
}