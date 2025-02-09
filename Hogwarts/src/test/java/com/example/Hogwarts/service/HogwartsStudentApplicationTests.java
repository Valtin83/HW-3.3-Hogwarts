package com.example.Hogwarts.service;

import com.example.Hogwarts.controller.StudentController;
import com.example.Hogwarts.model.Student;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HogwartsStudentApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private StudentService studentService;

    @Test
    public void contextLoads() throws Exception {
        Assertions.assertThat(studentController).isNotNull();
    }

    @Test
    public void testGetStudentInfo() throws Exception { //GET
        Assertions.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student", String.class))
                .isNotNull();
    }

    @Test
    public void testCreateStudent() { // POST
        Student newStudent = new Student();
        newStudent.setId(30L);
        newStudent.setName("New Student");
        newStudent.setAge(14);

        Assertions.assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/student", newStudent, String.class))
                .isNotNull();
    }

    @Test
    public void testEditStudent() { // PUT
        Student newStudent = new Student();
        newStudent.setId(3L);
        newStudent.setName("new Student");
        newStudent.setAge(14);

        Assertions.assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/student", newStudent, String.class))
                .isNotNull();
    }

    @Test
    public void testDeleteStudent() { // Delete
        Student student = new Student();
        student.setId(130L);

        Assertions.assertThat(this.restTemplate.exchange(
                "http://localhost:" + port + "/student/130",
                HttpMethod.DELETE,
                null,
                String.class));
    }
}

