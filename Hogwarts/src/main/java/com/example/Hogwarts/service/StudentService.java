package com.example.Hogwarts.service;

import com.example.Hogwarts.model.Student;

public interface StudentService {

    Student createStudent(Student student);

    Student getStudent(Long id);

    Student updateStudent(Student student);

    void removeStudent(Long id);
}
