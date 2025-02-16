package com.example.Hogwarts.controller;

import com.example.Hogwarts.model.Faculty;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerTests {

    @LocalServerPort
    private int port;

    @Autowired
    private FacultyController facultyController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void contextLoads() throws Exception {
        Assertions.assertThat(facultyController).isNotNull();
    }

    @Test
    public void getFacultyInfo() throws Exception { //GET
        Assertions.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculty", String.class))
                .isNotNull();
    }

    @Test
    public void testCreateFaculty() { // POST
        Faculty newFaculty = new Faculty();
        newFaculty.setId(30L);
        newFaculty.setName("New Faculty");
        newFaculty.setColor("violet");

        Assertions.assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/faculty", newFaculty, String.class))
                .isNotNull();
    }

    @Test
    public void testUpdateFaculty() { // PUT
        Faculty newFaculty = new Faculty();
        newFaculty.setId(5L);
        newFaculty.setName("new Faculty");
        newFaculty.setColor("purple");

        Assertions.assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/faculty", newFaculty, String.class))
                .isNotNull();
    }

    @Test
    public void testRemoveFaculty() { // Delete
        Faculty faculty = new Faculty();
        faculty.setId(5L);

        Assertions.assertThat(this.restTemplate.exchange(
                "http://localhost:" + port + "/faculty/5",
                HttpMethod.DELETE,
                null,
                String.class));
    }

}
