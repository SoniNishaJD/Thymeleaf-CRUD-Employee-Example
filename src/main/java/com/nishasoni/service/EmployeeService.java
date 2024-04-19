package com.nishasoni.service;

import com.nishasoni.entity.Employee;

import java.util.List;
import java.util.Map;

public interface EmployeeService {

    List<Employee> getAllEmployees();

    void createEmployee(Map<String, String> employeeData);
    Employee getEmployeeById(Long id);

    void updateEmployee(Long id, String firstName, String lastName, String email);

    void deleteEmployee(Long id);
}
