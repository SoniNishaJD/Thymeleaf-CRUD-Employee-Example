package com.nishasoni.controller;

import com.nishasoni.entity.Employee;
import com.nishasoni.service.EmployeeService;
import com.nishasoni.service.Impl.EmployeeServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeServiceImpl employeeService;

    public EmployeeController(EmployeeServiceImpl employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employees")
    public String listEmployees(Model model){
        List<Employee> employees = employeeService.getAllEmployees();
        model.addAttribute("employees", employees);
        return "employees";
    }

    // handler method to handle save student form submit request
    @PostMapping("/employees")
    public void saveEmployee(@RequestBody Map<String, String> employeeData) {
        employeeService.createEmployee(employeeData);
    }
//=========================================

    // handler method to handle new student request
    @GetMapping("/employees/new")
    public String newEmployee(Model model){
        // employee model object to store employee form data
        Employee employee = new Employee();
        model.addAttribute("employee", employee);
        return "redirect:/view_employees";
    }


    @GetMapping("/employees/{id}/edit")
    public String editEmployee(@PathVariable("id") Long id,
                              Model model){
        Employee employee = employeeService.getEmployeeById(id);
        model.addAttribute("employee", employee);
        return "edit_employee";
    }

    // handler method to handle edit student form submit request
    @PutMapping("/employees/{id}")
    public ResponseEntity<String> updateEmployee(
            @PathVariable Long id,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String email) {

        if (firstName == null && lastName == null && email == null) {
            return ResponseEntity.badRequest().body("At least one field (firstName, lastName, email) must be provided for update.");
        }

        try {
            employeeService.updateEmployee(id, firstName, lastName, email);
            return ResponseEntity.ok("Employee updated successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating the employee.");
        }
    }

    // Handler method to handle delete student request
    @DeleteMapping("/employees/{id}/delete")
    public String deleteEmployee(@PathVariable("id") Long id){
        employeeService.deleteEmployee(id);
        return "redirect:/employees";
    }

    // Handler method to handle view student request
    @GetMapping("/employees/{id}/view")
    public String viewEmployee(@PathVariable("id") Long id,
                              Model model){
        Employee employee = employeeService.getEmployeeById(id);
        model.addAttribute("employee", employee);
        return "view_employees";
    }

}
