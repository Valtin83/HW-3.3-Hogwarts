package com.example.Hogwarts;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.Hogwarts.exception.FacultyNotFoundException;
import com.example.Hogwarts.model.Faculty;
import com.example.Hogwarts.repository.FacultyRepository;
import com.example.Hogwarts.service.FacultyServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

public class FacultyServiceImplTest {

    @Mock
    private FacultyRepository facultyRepository;

    @InjectMocks
    private FacultyServiceImpl facultyServiceImpl;

    private Faculty faculty;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        faculty = new Faculty();
        faculty.setId(1L);
        faculty.setName("Engineering");
    }

    @Test
    public void testCreateFaculty() {
        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);

        Faculty createdFaculty = facultyServiceImpl.createFaculty(faculty);

        assertNotNull(createdFaculty);
        assertEquals("Engineering", createdFaculty.getName());
        verify(facultyRepository, times(1)).save(faculty);
    }

    @Test
    public void testGetFaculty_Success() {
        when(facultyRepository.findById(1L)).thenReturn(Optional.of(faculty));

        Faculty retrievedFaculty = facultyServiceImpl.getFaculty(1L);

        assertNotNull(retrievedFaculty);
        assertEquals("Engineering", retrievedFaculty.getName());
        verify(facultyRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetFaculty_NotFound() {
        when(facultyRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(FacultyNotFoundException.class, () -> {
            facultyServiceImpl.getFaculty(1L);
        });

        assertEquals("Факультет отсутствует", exception.getMessage());
        verify(facultyRepository, times(1)).findById(1L);
    }

    @Test
    public void testUpdateFaculty() {
        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);

        Faculty updatedFaculty = facultyServiceImpl.updateFaculty(faculty);

        assertNotNull(updatedFaculty);
        assertEquals("Engineering", updatedFaculty.getName());
        verify(facultyRepository, times(1)).save(faculty);
    }

    @Test
    public void testRemoveFaculty() {
        doNothing().when(facultyRepository).deleteById(1L);

        boolean result = facultyServiceImpl.removeFaculty(1L);

        assertFalse(result);
        verify(facultyRepository, times(1)).deleteById(1L);
    }
}
