package com.example.Hogwarts.service;
import com.example.Hogwarts.controller.FacultyController;
import com.example.Hogwarts.model.Faculty;
import com.example.Hogwarts.model.Student;
import com.example.Hogwarts.repository.FacultyRepository;
import com.example.Hogwarts.repository.StudentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FacultyController.class)
public class HogwartsFacultyApplicationMockTests {

    @Autowired
    private MockMvc mockMvc;

    @SpyBean
    private FacultyService facultyService;

    @InjectMocks
    private FacultyController facultyController;

    @MockBean
    private FacultyRepository facultyRepository;

    @MockBean
    private StudentRepository studentRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
    }

    @Test
    void testCreateFaculty() throws Exception {
        Long id = 1L;
        String name = "Engineering";

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("Engineering", name);

        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setName(name);

        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);
        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(faculty));


        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name));
    }

    @Test
    public void testGetFacultyInfo() throws Exception {
        Faculty faculty = new Faculty(); // создайте объект Faculty с необходимыми данными
        faculty.setId(1L); // установите id

        when(facultyService.getFaculty(anyLong())).thenReturn(faculty);

        mockMvc.perform(get("/faculty/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    public void testUpdateFaculty() throws Exception {
        Faculty faculty = new Faculty(); // создание объекта Faculty
        faculty.setId(1L); // установите id

        when(facultyService.updateFaculty(any())).thenReturn(faculty);

        mockMvc.perform(put("/faculty")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(faculty)))
                .andExpect(status().isOk());
    }

    @Test
    public void testRemoveFaculty_Success() throws Exception {
        doNothing().when(facultyService).removeFaculty(anyLong());

        mockMvc.perform(delete("/faculty/{id}", 1L))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetStudentsByFaculty() throws Exception {
        List<Student> students = new ArrayList<>(); // создание списка студентов
        when(facultyService.getStudentsByFaculty(anyLong())).thenReturn(students);

        mockMvc.perform(get("/faculty/faculty/{facultyId}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testSearch() throws Exception {
        Long id = 1L;
        String name = "Engineering";

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("Engineering", name);

        List<Faculty> faculties = new ArrayList<>(); // создание списка факультетов

        when(facultyRepository.findByColorIgnoreCaseOrNameIgnoreCase(any(), any())).thenReturn(faculties);

        mockMvc.perform(get("/faculty/search")
                        .param("color", "blue")
                        .param("name", "Engineering"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}