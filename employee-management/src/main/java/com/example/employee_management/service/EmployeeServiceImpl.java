package com.example.employee_management.service;

import com.example.employee_management.dto.EmployeeDTO;
import com.example.employee_management.model.Employee;
import com.example.employee_management.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    EmployeeRepository employeeRepository;

    // Inject EmployeeRepository to the service class.
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }


    @Override
    public void saveEmployee(EmployeeDTO employeeDTO) {
        Employee employee = convertDtoToEntity(employeeDTO);
        employeeRepository.save(employee);
    }

    @Override
    public List<EmployeeDTO> getAllEmployee(EmployeeDTO employeeDTO) {
        return List.of(employeeDTO);
    }

    @Override
    public EmployeeDTO getEmployeeById(EmployeeDTO employeeDTO) {
        return null;
    }

    public EmployeeDTO convertEntityToDto(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();

        employeeDTO.setId(employee.getId());
        employeeDTO.setName(employee.getName());
        employeeDTO.setJobTitle(employee.getJobTitle());
        employeeDTO.setDateOfBirth(employee.getDateOfBirth());
        employeeDTO.setSalary(employee.getSalary());

        return employeeDTO;
    }

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
