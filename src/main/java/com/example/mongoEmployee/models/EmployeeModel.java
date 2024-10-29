package com.example.mongoEmployee.models;

import java.math.BigDecimal;

public record EmployeeModel(String name, int age, BigDecimal salary) {
}
