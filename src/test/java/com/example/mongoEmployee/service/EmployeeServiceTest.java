package com.example.mongoEmployee.service;

import com.example.mongoEmployee.entity.Employee;
import com.example.mongoEmployee.models.EmployeeModel;
import com.example.mongoEmployee.repository.EmployeeRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;


public class EmployeeServiceTest {

    @Mock
    EmployeeRepository employeeRepository;

    @InjectMocks
    EmployeeService employeeService;

    public EmployeeServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createEmployee() {
        //arrange
        EmployeeModel employeeModel = new EmployeeModel("Alice", 25, 25000.00);

        Employee employee = new Employee();
        employee.setName(employeeModel.name());
        employee.setSalary(employeeModel.salary());
        employee.setAge(employeeModel.age());

        when(employeeRepository.save(employee)).thenReturn(employee);


        //act
        Employee createdEmployee = employeeService.addEmployee(employeeModel);

        //assert
        Assertions.assertNotNull(createdEmployee);
        Assertions.assertEquals("Alice", createdEmployee.getName());
        Assertions.assertEquals(25000.00, createdEmployee.getSalary());
        Assertions.assertEquals(25, createdEmployee.getAge());


    }


    @Test
    public void getAllEmployees() {
        //Arrange
        ArrayList<Employee> employeeList = new ArrayList<Employee>();
        employeeList.add(new Employee("Alice", 25, 25000.00));
        employeeList.add(new Employee("Morgy", 30, 250000.00));
        when(employeeRepository.findAll()).thenReturn(employeeList);


        //Act
        List<Employee> listedEmployees = employeeService.getAllEmployees();

        //Assert
        Assertions.assertEquals(2, listedEmployees.size());
        Assertions.assertEquals("Alice", listedEmployees.get(0).getName());
        Assertions.assertEquals("Morgy", listedEmployees.get(1).getName());
    }

}
