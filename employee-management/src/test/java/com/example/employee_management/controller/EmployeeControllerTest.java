package com.example.employee_management.controller;

import com.example.employee_management.dto.EmployeeDTO;
import com.example.employee_management.model.Employee;
import com.example.employee_management.service.EmployeeService;
import com.example.employee_management.service.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static com.example.employee_management.enums.JobTitle.BACKEND_DEVELOPER;
import static com.example.employee_management.enums.JobTitle.FRONTEND_DEVELOPER;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)

class EmployeeControllerTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks

    private EmployeeController employeeController;


//    @BeforeEach
//    void setUp() {
//        employeeService = mock(EmployeeService.class);
//        employeeController = new EmployeeController(employeeService);
//    }

    @Test
    void testSaveEmployee() {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setName("Tharuka");
        employeeDTO.setJobTitle(BACKEND_DEVELOPER);
        employeeDTO.setDateOfBirth(LocalDate.of(2002,Month.MARCH,21));
        employeeDTO.setSalary(50000);

        when(employeeService.saveEmployee(any(EmployeeDTO.class))).thenReturn(employeeDTO);

        EmployeeRequest request = new EmployeeRequest();
        request.setName("Tharuka");
        request.setJobTitle(BACKEND_DEVELOPER);
        request.setDateOfBirth(LocalDate.of(2002,Month.MARCH,21));
        request.setSalary(50000);

        ResponseEntity<EmployeeResponse> responseEntity = employeeController.saveEmployee(request);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("Tharuka", responseEntity.getBody().getName());
        assertEquals(BACKEND_DEVELOPER, responseEntity.getBody().getJobTitle());
        assertEquals(LocalDate.of(2002,Month.MARCH,21), responseEntity.getBody().getDateOfBirth());
        assertEquals(50000, responseEntity.getBody().getSalary());
    }

    @Test
    void getAllEmployee() {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setName("Tharuka");
        employeeDTO.setJobTitle(BACKEND_DEVELOPER);
        employeeDTO.setDateOfBirth(LocalDate.of(2002,Month.MARCH,21));
        employeeDTO.setSalary(50000);

        EmployeeDTO employeeDTO2 = new EmployeeDTO();
        employeeDTO2.setName("Tharuka");
        employeeDTO2.setJobTitle(FRONTEND_DEVELOPER);
        employeeDTO2.setDateOfBirth(LocalDate.of(2002,Month.MARCH,21));
        employeeDTO2.setSalary(50000);

        when(employeeService.getAllEmployee()).thenReturn(Arrays.asList(employeeDTO,employeeDTO2));

        ResponseEntity<List<EmployeeResponse>> responseEntity = employeeController.getAllEmployee();

        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(2,responseEntity.getBody().size());

    }

    @Test
    void getEmployeeById() {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(1l);
        employeeDTO.setName("dilruk");
        employeeDTO.setJobTitle(BACKEND_DEVELOPER);
        employeeDTO.setDateOfBirth(LocalDate.of(2002,Month.MARCH,21));
        employeeDTO.setSalary(60000);
        long id=1l;
        when(employeeService.getEmployeeById(anyLong())).thenReturn(employeeDTO);

        ResponseEntity<Object> responseEntity = employeeController.getEmployeeById(id);

        assertNotNull(responseEntity.getBody());
        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());




    }

    @Test
    @Disabled
    void updateEmployeeById() {
    }

    @Test
    @Disabled
    void deleteEmployeeById() {
    }

    @Test
    @Disabled
    void deleteAllEmployees() {
    }
}