package com.example.mongoEmployee.controller;

import com.example.mongoEmployee.entity.Employee;
import com.example.mongoEmployee.models.EmployeeModel;
import com.example.mongoEmployee.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/addEmployee")
    public ResponseEntity<Employee> createEmployee(@RequestBody EmployeeModel employeeModel) {
        Employee employee = employeeService.addEmployee(employeeModel);
        return new ResponseEntity<>(employee, HttpStatus.CREATED);
    }

    @GetMapping("/getEmployees")
    public ResponseEntity<List<Employee>> getEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/getByAge")
    public ResponseEntity<Optional<List<Employee>>> getEmployeeByAge(@RequestParam int age) {
        Optional<List<Employee>> employees = employeeService.getEmployeesByAge(age);
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/getByLetter")
    public ResponseEntity<Optional<List<Employee>>> getEmployeeByLetter(@RequestParam String letter) {
        Optional<List<Employee>> employees = employeeService.getEmployeesByNameStartingWith(letter);
        return new ResponseEntity<>(employees, HttpStatus.OK);

    }

    @GetMapping("/getSalary")
    public ResponseEntity<Double> getEmployeeBySalary() {
        Double salaryCount = employeeService.calculateSalary();
        return new ResponseEntity<>(salaryCount, HttpStatus.OK);
    }


}
