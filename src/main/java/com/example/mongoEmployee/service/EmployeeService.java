package com.example.mongoEmployee.service;

import com.example.mongoEmployee.entity.Employee;
import com.example.mongoEmployee.models.EmployeeModel;
import com.example.mongoEmployee.repository.EmployeeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee addEmployee(EmployeeModel employeeModel) {
        Employee employee = new Employee();
        employee.setName(employeeModel.name());
        employee.setSalary(employeeModel.salary());
        employee.setAge(employeeModel.age());
        employeeRepository.save(employee);
        return employee;

    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();

    }

    public Optional<List<Employee>> getEmployeesAboveAge(int age) {
        return Optional.of(employeeRepository.findAll()
                .stream()
                .filter(employee -> employee.getAge() >= age)
                .collect(Collectors.toList()));
    }

    public Double calculateSalary() {
        return employeeRepository.findAll()
                .stream()
                .mapToDouble(Employee::getSalary)
                .sum();
    }

    public Optional<List<Employee>> getEmployeesByNameStartingWith(String letter) {
        return Optional.of(employeeRepository.findAll().stream()
                .filter(employee -> employee.getName().startsWith(letter))
                .collect(Collectors.toList()));
    }


}
