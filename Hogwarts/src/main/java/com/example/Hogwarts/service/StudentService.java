package com.example.Hogwarts.service;

import com.example.Hogwarts.model.Faculty;
import com.example.Hogwarts.model.Student;

import java.util.List;

public interface StudentService {

    Student createStudent(Student student);

    Student getStudent(Long id);

    Student updateStudent(Student student);

    void removeStudent(Long id);

    Faculty getStudentFaculty(Long studentId);

    List<Student> getStudentsByAgeRange(int min, int max);

}
