package com.nishasoni.service.Impl;

import com.nishasoni.entity.Employee;
import com.nishasoni.repository.EmployeeRepository;
import com.nishasoni.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService{


    @Autowired
    private final EmployeeRepository repository;

    public EmployeeServiceImpl(EmployeeRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Employee> getAllEmployees() {
        return repository.findAll();
    }

    @Override
    public void createEmployee(Map<String, String> employeeData) {
        String firstName = employeeData.get("firstName");
        String lastName = employeeData.get("lastName");
        String email = employeeData.get("email");

        // Validate input data
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty.");
        }

        // Create the Employee object and save it
        Employee employee = new Employee(firstName, lastName, email);
        repository.save(employee);


}


    @Override
    public Employee getEmployeeById(Long id) {
        Optional<Employee> studentOptional = repository.findById(id);
        return studentOptional.orElse(null);
    }

    @Override
    public void updateEmployee(Long id, String firstName, String lastName, String email) {
        if (email != null) {
            // Validate email format here if needed
            Employee employee = repository.findById(id).orElse(null);
            if (employee != null) {
                employee.setFirstName(firstName);
                employee.setLastName(lastName);
                employee.setEmail(email);
                repository.save(employee);
            } else {
                // Handle case when employee with given id is not found
            }
        } else {
            // Handle case when email is null
        }

    }

    @Override
    public void deleteEmployee(Long id) {
        repository.deleteById(id);
    }
}
