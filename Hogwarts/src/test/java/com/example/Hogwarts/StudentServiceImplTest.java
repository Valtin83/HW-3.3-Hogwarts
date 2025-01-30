package com.example.Hogwarts;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.Hogwarts.exception.StudentNotFoundException;
import com.example.Hogwarts.model.Student;
import com.example.Hogwarts.repository.StudentRepository;
import com.example.Hogwarts.service.StudentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

public class StudentServiceImplTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentServiceImpl studentServiceImpl;

    private Student student;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        student = new Student();
        student.setId(1L);
        student.setName("Harry Potter");
    }

    @Test
    public void testCreateStudent() {
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        Student createdStudent = studentServiceImpl.addStudent(student);

        assertNotNull(createdStudent);
        assertEquals("Harry Potter", createdStudent.getName());
        verify(studentRepository, times(1)).save(student);
    }

    @Test
    public void testGetStudentFound() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        Student foundStudent = studentServiceImpl.getStudent(1L);

        assertNotNull(foundStudent);
        assertEquals("Harry Potter", foundStudent.getName());
        verify(studentRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetStudentNotFound() {
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(StudentNotFoundException.class, () -> {
            studentServiceImpl.getStudent(1L);
        });

        assertEquals("Студент отсутствует", exception.getMessage());
        verify(studentRepository, times(1)).findById(1L);
    }

    @Test
    public void testUpdateStudent() {
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        Student updatedStudent = studentServiceImpl.updateStudent(student);

        assertNotNull(updatedStudent);
        assertEquals("Harry Potter", updatedStudent.getName());
        verify(studentRepository, times(1)).save(student);
    }

    @Test
    public void testRemoveStudent() {
        doNothing().when(studentRepository).deleteById(1L);

        studentServiceImpl.removeStudent(1L);

        verify(studentRepository, times(1)).deleteById(1L);
    }
}