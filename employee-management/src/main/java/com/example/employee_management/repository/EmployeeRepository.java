package com.example.employee_management.repository;

import com.example.employee_management.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    /**
     * To reset Employee ID after deleting the entire Employee list
     */
    @Modifying
    @Transactional
    @Query(value = "TRUNCATE TABLE employees;", nativeQuery = true)
    void resetEmployeeId();

    /**
     *query to find employee by title
     */

    List<Employee> findByTitle(String title);

}
