package com.example.Hogwarts.repository;

import com.example.Hogwarts.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findByAgeBetween(Integer min, Integer max);

    List<Student> findByFacultyId(Long facultyId);

    @Query(value = "SELECT COUNT(s) FROM Student s", nativeQuery = true)
    Long countAllStudent();

    @Query(value = "SELECT AVG(s.age) FROM Student s",nativeQuery = true)
    Double findAverageAge();

    @Query(value = "SELECT s FROM Student s ORDER BY s.id DESC LIMIT 5", nativeQuery = true )
   List<Student> findTop5ByOrderByIdDesc(org.springframework.data.domain.Pageable pageable);
}
