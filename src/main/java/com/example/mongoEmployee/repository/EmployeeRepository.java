package com.example.mongoEmployee.repository;

import com.example.mongoEmployee.entity.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmployeeRepository extends MongoRepository<Employee,String> {
}
