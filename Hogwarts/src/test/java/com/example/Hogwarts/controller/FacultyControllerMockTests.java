package com.example.Hogwarts.controller;

import com.example.Hogwarts.exception.FacultyNotFoundException;
import com.example.Hogwarts.model.Faculty;
import com.example.Hogwarts.model.Student;
import com.example.Hogwarts.repository.FacultyRepository;
import com.example.Hogwarts.service.FacultyService;
import com.example.Hogwarts.service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(FacultyController.class)
public class FacultyControllerMockTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacultyService facultyService;

    @MockBean
    private StudentService studentService;

    @MockBean
    private FacultyRepository facultyRepository;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void testGetFacultyInfo() throws Exception{

        Faculty faculty = new Faculty(1L,"Gryffindor", "red");
        when(facultyService.getFaculty(anyLong())).thenReturn(faculty);

        mockMvc.perform(get("/faculty/1"))
                        .andDo(print())

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Gryffindor"))
                .andExpect(jsonPath("$.color").value("red"));

    }

    @Test
    public void testGetFacultyInfoThenFacultyNotExist() throws Exception{

        when(facultyService.getFaculty(anyLong())).thenThrow(FacultyNotFoundException.class);

        mockMvc.perform(get("/faculty/1"))
                .andDo(print())

                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateFaculty() throws Exception {

        Faculty faculty = new Faculty(1L,"Gryffindor", "red");
        when(facultyService.createFaculty(any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders.post("/faculty")
                        .content(new ObjectMapper().writeValueAsString(faculty)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateFaculty() throws Exception {
        Faculty faculty = new Faculty(1L, "Gryffindor", "red");
        when(facultyService.updateFaculty(any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders.put("/faculty")
                        .content(new ObjectMapper().writeValueAsString(faculty)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.name").value("Rfvenklov"))
                .andExpect(jsonPath("$.color").value("blue"));

    }

    @Test
    public void testRemoveFaculty() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/faculty/1"))
                .andDo(print())

                .andExpect(status().isOk());
    }

    @Test
    public void testGetFaculty() throws Exception {

        Faculty faculty1 = new Faculty(1L, "Gryffindor", "red");
        Faculty faculty2 = new Faculty(2L, "Hufflepuff", "yellow");
        List<Faculty> faculties = Arrays.asList(faculty1, faculty2);

        Pageable pageable = PageRequest.of(0, 2);
        Page<Faculty> facultyPage = new PageImpl<>(faculties, pageable, faculties.size());

        when(facultyRepository.findAll(any(Pageable.class))).thenReturn(facultyPage);

        mockMvc.perform(get("/faculties?page=0&size=10"))
                .andExpect(status().isOk()) // Then: Verify the status
                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.totalElements").value(2));
    }
}