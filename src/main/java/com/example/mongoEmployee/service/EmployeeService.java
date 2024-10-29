package com.example.mongoEmployee.service;

import com.example.mongoEmployee.entity.Employee;
import com.example.mongoEmployee.models.EmployeeModel;
import com.example.mongoEmployee.repository.EmployeeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public ResponseEntity<Employee> addEmployee(EmployeeModel employeeModel) {
        Employee employee = new Employee();
        employee.setName(employeeModel.name());
        employee.setSalary(employeeModel.salary());
        employee.setAge(employeeModel.age());
        employeeRepository.save(employee);
        return new ResponseEntity<>(employee, HttpStatus.CREATED);

    }

    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }


}
