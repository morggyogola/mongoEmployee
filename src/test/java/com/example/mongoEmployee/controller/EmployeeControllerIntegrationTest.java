package com.example.mongoEmployee.controller;

import com.example.mongoEmployee.entity.Employee;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;

import java.util.Arrays;
import java.util.List;

import static org.springframework.boot.web.client.ClientHttpRequestFactories.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerIntegrationTest {

    //Mongo Test Container startup

    MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:latest");


    @BeforeEach
    public void setUp() {
        mongoDBContainer.start();
    }

    @AfterEach
    public void tearDown() {
        mongoDBContainer.stop();
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testAddEmployee() throws Exception {
        Employee employee = new Employee("Morgy", 18, 86986.00);

        String employeeJson = objectMapper.writeValueAsString(employee);

        mockMvc.perform(post("/api/v1/addEmployee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employeeJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Alice"));
    }

    @Test
    public void testGetAllEmployees() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/getEmployees"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }


}
