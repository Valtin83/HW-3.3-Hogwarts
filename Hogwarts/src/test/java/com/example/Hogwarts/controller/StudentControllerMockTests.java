package com.example.Hogwarts.controller;
import com.example.Hogwarts.exception.FacultyNotFoundException;
import com.example.Hogwarts.model.Faculty;
import com.example.Hogwarts.model.Student;
import com.example.Hogwarts.service.FacultyService;
import com.example.Hogwarts.service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentController.class)
public class StudentControllerMockTests {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacultyService facultyService;

    @MockBean
    private StudentService studentService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void testGetStudentInfo() throws Exception {

       Student student = new Student(1L, "Bob" ,12);

        when(studentService.getStudent(anyLong())).thenReturn(student);

        mockMvc.perform(get("/student/1"))
                .andDo(print())

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Bob"))
                .andExpect(jsonPath("$.age").value(12));

    }

    @Test
    public void testGetStudentInfoThenStudentNotExist() throws Exception {

        when(facultyService.getFaculty(anyLong())).thenThrow(FacultyNotFoundException.class);

        mockMvc.perform(get("/faculty/1"))
                .andDo(print())

                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateStudent() throws Exception {

        Student student = new Student(1L,"Bob", 12);
        when(studentService.createStudent(any(Student.class))).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders.post("/student")
                        .content(new ObjectMapper().writeValueAsString(student)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateStudent() throws Exception {
        Student student = new Student(1L,"Bob", 12);
        when(studentService.updateStudent(any(Student.class))).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders.put("/student")
                        .content(new ObjectMapper().writeValueAsString(student)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.name").value("Ron"))
                .andExpect(jsonPath("$.age").value(11));

    }

    @Test
    public void testRemoveStudent() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/student/1"))
                .andDo(print())

                .andExpect(status().isOk());

    }

  }