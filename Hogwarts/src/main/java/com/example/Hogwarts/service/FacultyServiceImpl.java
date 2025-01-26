package com.example.Hogwarts.service;

import com.example.Hogwarts.exception.FacultyNotFoundException;
import com.example.Hogwarts.model.Faculty;
import com.example.Hogwarts.repository.FacultyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FacultyServiceImpl implements FacultyService{

    private final FacultyRepository facultyRepository;

    @Autowired
    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
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
    public boolean removeFaculty(Long id) {
        facultyRepository.deleteById(id);
        return false;
    }
}
