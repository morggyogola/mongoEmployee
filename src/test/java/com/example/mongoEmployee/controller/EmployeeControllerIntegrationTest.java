package com.example.mongoEmployee.controller;

import com.example.mongoEmployee.entity.Employee;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {
        "spring.data.mongodb.host=${testcontainers.mongodb.host}",
        "spring.data.mongodb.port=${testcontainers.mongodb.port}"
})
@AutoConfigureMockMvc
public class EmployeeControllerIntegrationTest {

    //Mongo Test Container startup

    private static MongoDBContainer mongoDBContainer;


    @BeforeAll
    public static void setUp() {
        mongoDBContainer = new MongoDBContainer("mongo:latest");
        mongoDBContainer.start();
        System.setProperty("testcontainers.mongodb.host", mongoDBContainer.getHost());
        System.setProperty("testcontainers.mongodb.port", mongoDBContainer.getFirstMappedPort().toString());
    }

    @AfterAll
    public static void tearDown() {
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
                .andExpect(jsonPath("$.name").value("Morgy"));
    }

    @Test
    public void testGetAllEmployees() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/getEmployees"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }


}
