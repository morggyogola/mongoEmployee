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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    public void testCreateEmployee() {
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
        assertEquals("Alice", createdEmployee.getName());
        assertEquals(25000.00, createdEmployee.getSalary());
        assertEquals(25, createdEmployee.getAge());


    }


    @Test
    public void testGetAllEmployees() {
        //Arrange
        ArrayList<Employee> employeeList = new ArrayList<Employee>();
        employeeList.add(new Employee("Alice", 25, 25000.00));
        employeeList.add(new Employee("Morgy", 30, 250000.00));
        when(employeeRepository.findAll()).thenReturn(employeeList);


        //Act
        List<Employee> listedEmployees = employeeService.getAllEmployees();

        //Assert
        assertEquals(2, listedEmployees.size());
        assertEquals("Alice", listedEmployees.get(0).getName());
        assertEquals("Morgy", listedEmployees.get(1).getName());
    }

    @Test
    public void testGetEmployeesAboveAge() {
        //Arrange
        List<Employee> employees = Arrays.asList(
                new Employee("Morgy", 13, 250000.00),
                new Employee("Kitimba", 25, 37000.00),
                new Employee("Julius", 30, 3098.00),
                new Employee("Mackenzie", 40, 946000.00)
        );
        when(employeeRepository.findAll()).thenReturn(employees);

        //Act
        Optional<List<Employee>> employeesByAge = employeeService.getEmployeesAboveAge(25);

        //Assert
        assertEquals(3, employeesByAge.get().size());
        assertEquals("Kitimba", employeesByAge.get().get(0).getName());

    }

    @Test
    public void testGetEmployeesByLetter() {
        //Arrange
        List<Employee> employees = Arrays.asList(
                new Employee("Morgy", 13, 250000.00),
                new Employee("Mutuku", 25, 37000.00),
                new Employee("Waweru", 56, 65657.00)
        );
        when(employeeRepository.findAll()).thenReturn(employees);

        //Act
        Optional<List<Employee>> employeesWithLetter = employeeService.getEmployeesByNameStartingWith("M");

        assertEquals(2, employeesWithLetter.get().size());
        assertEquals("Morgy", employeesWithLetter.get().get(0).getName());
    }

    @Test
    public void testGetSalaryCount() {
        List<Employee> employees = Arrays.asList(
                new Employee("Max", 23, 100.00),
                new Employee("Jeff", 40, 100.00)
        );
        when(employeeRepository.findAll()).thenReturn(employees);

        Double salaryCount = employeeService.calculateSalary();

        assertEquals(200.0, salaryCount);
    }


}
