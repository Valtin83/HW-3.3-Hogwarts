package com.example.Hogwarts.repository;

import com.example.Hogwarts.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {

}
