package com.example.Hogwarts.service;

import com.example.Hogwarts.exception.FacultyNotFoundException;
import com.example.Hogwarts.model.Faculty;
import com.example.Hogwarts.model.Student;
import com.example.Hogwarts.repository.FacultyRepository;
import com.example.Hogwarts.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacultyServiceImpl implements FacultyService {

    @Autowired
    private final FacultyRepository facultyRepository;
    private final StudentRepository studentRepository;


    public FacultyServiceImpl(FacultyRepository facultyRepository, StudentRepository studentRepository) {
        this.facultyRepository = facultyRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public Faculty createFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty getFaculty(Long id) {
        return facultyRepository.findById(id)
                .orElseThrow(()-> new FacultyNotFoundException("Факультет отсутствует"));
    }

    @Override
    public Faculty updateFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    @Override
    public void removeFaculty(Long id) {
        facultyRepository.deleteById(id);

    }

    @Override
    public List<Student> getStudentsByFaculty(Long facultyId) {
        return studentRepository.findByFacultyId(facultyId);
    }

    @Override
    public List<Faculty> searchByColorOrName(String color, String name) {
        return facultyRepository.findByColorIgnoreCaseOrNameIgnoreCase(color, name);
    }
}