package com.example.Hogwarts.service;

import com.example.Hogwarts.model.Faculty;
import com.example.Hogwarts.model.Student;

import java.util.List;

public interface FacultyService {

    Faculty createFaculty(Faculty faculty);

    Faculty getFaculty(Long id);

    Faculty updateFaculty(Faculty faculty);

    void removeFaculty(Long id);

    List<Student> getStudentsByFaculty(Long facultyId);
}



