package com.example.Hogwarts;

import com.example.Hogwarts.exception.FacultyNotFoundException;
import com.example.Hogwarts.model.Faculty;
import com.example.Hogwarts.model.Student;
import com.example.Hogwarts.repository.FacultyRepository;
import com.example.Hogwarts.repository.StudentRepository;
import com.example.Hogwarts.service.FacultyServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class FacultyServiceImplTest {

    @Mock
    private FacultyRepository facultyRepository;

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private FacultyServiceImpl facultyService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddFaculty() {
        Faculty faculty = new Faculty();
        faculty.setName("Факультет магии");

        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);

        Faculty createdFaculty = facultyService.addFaculty(faculty);

        Assertions.assertNotNull(createdFaculty);
        Assertions.assertEquals("Факультет магии", createdFaculty.getName());
        verify(facultyRepository, times(1)).save(faculty);
    }

    @Test
    public void testGetFaculty() {
        Faculty faculty = new Faculty();
        faculty.setId(1L);
        faculty.setName("Факультет магии");

        when(facultyRepository.findById(1L)).thenReturn(Optional.of(faculty));

        Faculty foundFaculty = facultyService.getFaculty(1L);

        Assertions.assertNotNull(foundFaculty);
        Assertions.assertEquals(faculty.getName(), foundFaculty.getName());
    }

    @Test
    public void testGetFacultyNotFound() {
        when(facultyRepository.findById(2L)).thenReturn(Optional.empty());

        Assertions.assertThrows(FacultyNotFoundException.class, () -> {
            facultyService.getFaculty(2L);
        });
    }

    @Test
    public void testUpdateFaculty() {
        Faculty faculty = new Faculty();
        faculty.setId(1L);
        faculty.setName("Факультет магии");

        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);

        Faculty updatedFaculty = facultyService.updateFaculty(faculty);

        Assertions.assertNotNull(updatedFaculty);
        Assertions.assertEquals("Факультет магии", updatedFaculty.getName());
        verify(facultyRepository, times(1)).save(faculty);
    }

    @Test
    public void testRemoveFaculty() {
        Long facultyId = 1L;

        doNothing().when(facultyRepository).deleteById(facultyId);

        facultyService.removeFaculty(facultyId);

        verify(facultyRepository, times(1)).deleteById(facultyId);
    }

    @Test
    public void testGetStudentsByFaculty() {
        Long facultyId = 1L;
        Student student = new Student();
        student.setId(1L);
        student.setName("Гарри Поттер");

        when(studentRepository.findByFacultyId(facultyId)).thenReturn(List.of(student));

        List<Student> students = facultyService.getStudentsByFaculty(facultyId);

        Assertions.assertNotNull(students);
        Assertions.assertEquals(1, students.size());
        Assertions.assertEquals("Гарри Поттер", students.get(0).getName());
    }
}