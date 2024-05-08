package com.example.employee_management;

import com.example.employee_management.dto.EmployeeDTO;
import com.example.employee_management.enums.JobTitle;
import com.example.employee_management.model.Employee;
import com.example.employee_management.repository.EmployeeRepository;
import com.example.employee_management.service.EmployeeService;
import com.example.employee_management.service.EmployeeServiceImpl;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EmployeeManagementApplicationIntegrationTests {

    //Get local server port
    @LocalServerPort
    private int port;

    @Mock
    private TestRestTemplate testRestTemplate;
    @Mock
    private EmployeeService employeeService;
    @Mock
    private EmployeeRepository employeeRepository;
    @Autowired
    private EmployeeServiceImpl employeeServiceImpl;

    /**
     * Constructs an instance of {@code EmployeeManagementApplicationIntegrationTests} with the specified dependencies.
     * Injecting dependencies
     *
     * @param testRestTemplate   The {@link TestRestTemplate} instance to use for performing HTTP requests.
     * @param employeeService    The {@link EmployeeService} instance to use for interacting with employee data.
     * @param employeeRepository The {@link EmployeeRepository} instance to use for accessing employee data in the database.
     */
    public EmployeeManagementApplicationIntegrationTests(TestRestTemplate testRestTemplate, EmployeeService employeeService, EmployeeRepository employeeRepository) {
        this.testRestTemplate =testRestTemplate;
        this.employeeRepository = employeeRepository;
        this.employeeService = employeeService;
    }

    private String getBaseUrl() {
        return "http://localhost:" + port + "/employees";
    }

    @Test
    void createEmployeeTest() {
        // URL of the endpoint
        String url = getBaseUrl();

        // Request Body
        Employee employee1 = new Employee(1,"John Doe", JobTitle.HR_MANAGER, LocalDate.of(1991, 1, 1), 100000);

        // Create a request entity with the body
        HttpEntity<Employee> requestEntity = new HttpEntity<>(employee1);

        // Send the request
        ResponseEntity<Employee> responseEntity = testRestTemplate.postForEntity(url, requestEntity, Employee.class);

        // Assert the response
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());

        // Clean up the created employee
        employeeService.deleteEmployeeById(responseEntity.getBody().getId());
    }


    @Disabled
    @Test
    void getEmployeeByIdTest() {

        // Create a new Employee instance for testing
        Employee employee2 = new Employee(2,"Jane Doe", JobTitle.FRONTEND_DEVELOPER,LocalDate.of(2000,5,7), 60000 );

        // Save the employee to get its ID
        employeeRepository.save(employee2);

        // URL of the endpoint
        String url = "http://localhost:" + port + "/employees/" + 2;

        //Send the request
        ResponseEntity<Employee> responseEntity = testRestTemplate.getForEntity(url, Employee.class);

        // Assert the response
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());

        // Clean up the created employee
        employeeService.deleteEmployeeById(responseEntity.getBody().getId());
    }

    @Disabled
    @Test
    void getAllEmployeesTest() {
        Employee employee1 = new Employee(1,"John Doe", JobTitle.HR_MANAGER, LocalDate.of(1991, 1, 1), 20000);
        Employee employee2 = new Employee(2,"Jane Doe", JobTitle.FRONTEND_DEVELOPER,LocalDate.of(2000,5,7), 60000);

        EmployeeDTO employeeDTO1 = employeeServiceImpl.convertEntityToDto(employee1);
        employeeService.saveEmployee(employeeDTO1);
        EmployeeDTO employeeDTO2 = employeeServiceImpl.convertEntityToDto(employee2);
        employeeService.saveEmployee(employeeDTO2);

        assertEquals(2, employeeService.getAllEmployee().size());

        // Clean up the created employees
        employeeService.deleteEmployeeById(employee1.getId());
        employeeService.deleteEmployeeById(employee2.getId());
    }

    @Disabled
    @Test
    void deleteEmployeeTest() {
        // Create a new Employee instance for testing
        Employee savedEmployee = new Employee(1,"John Doe", JobTitle.HR_MANAGER, LocalDate.of(1991, 1, 1), 20000);

        // Save the employee to get its ID
        employeeRepository.save(savedEmployee);

        // URL of the endpoint
        String url = "http://localhost:" + port + "/employees/" + savedEmployee.getId();

        // Send the DELETE request to delete the employee by ID
        ResponseEntity<Void> responseEntity = testRestTemplate.exchange(url, HttpMethod.DELETE, null, Void.class);

        // Assert the response
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());

        // Verify that the employee has been deleted by trying to retrieve it again
        ResponseEntity<Employee> getResponseEntity = testRestTemplate.getForEntity(url, Employee.class);
        assertEquals(HttpStatus.NOT_FOUND, getResponseEntity.getStatusCode());
    }

    @Disabled
    @Test
    void updateEmployeeTest() {
        // Create a new Employee instance for testing
        Employee savedEmployee = new Employee(2,"Jane Doe", JobTitle.FRONTEND_DEVELOPER,LocalDate.of(2000,5,7), 60000);

        // Save the employee to get its ID
        savedEmployee = employeeRepository.save(savedEmployee);

        // URL of the endpoint
        String url = "http://localhost:" + port + "/employees/" + savedEmployee.getId();

        // Request Body
        Employee updatedEmployee = new Employee(2,"Rian Terran",JobTitle.BACKEND_DEVELOPER, LocalDate.of(2002,2,25), 15000);

        // Create a request entity with the body
        HttpEntity<Employee> requestEntity = new HttpEntity<>(updatedEmployee);

        // Send the PUT request to update the Employee by ID
        ResponseEntity<Employee> responseEntity = testRestTemplate.exchange(url, HttpMethod.PUT, requestEntity, Employee.class);

        // Check if the update was successful by getting the updated employee from the database
        Optional<Employee> updatedEmployeeOptional = employeeRepository.findById(savedEmployee.getId());

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(updatedEmployeeOptional.isPresent());

        Employee fetchedUpdatedEmployee = updatedEmployeeOptional.get();
        assertEquals(updatedEmployee.getName(), fetchedUpdatedEmployee.getName());
        assertEquals(updatedEmployee.getDateOfBirth(), fetchedUpdatedEmployee.getDateOfBirth());
        assertEquals(updatedEmployee.getJobTitle(), fetchedUpdatedEmployee.getJobTitle());
        assertEquals(updatedEmployee.getSalary(), fetchedUpdatedEmployee.getSalary());
    }

}
