package com.example.Hogwarts.repository;

import com.example.Hogwarts.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findByAgeBetween(Integer min, Integer max);

    List<Student> findByFacultyId(Long facultyId);


}
