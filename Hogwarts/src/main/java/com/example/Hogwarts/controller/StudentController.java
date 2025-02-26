package com.example.Hogwarts.controller;

import com.example.Hogwarts.model.Faculty;
import com.example.Hogwarts.model.Student;
import com.example.Hogwarts.repository.StudentRepository;
import com.example.Hogwarts.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;
    private final StudentRepository studentRepository;

    @Autowired
    public StudentController(StudentService studentService, StudentRepository studentRepository) {
        this.studentService = studentService;
        this.studentRepository = studentRepository;
    }


    @GetMapping("{id}")
    public ResponseEntity<Student> getStudentInfo(@PathVariable Long id) {
        Student student = studentService.getStudent(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    @PutMapping
    public ResponseEntity<Student> editStudent(@RequestBody Student student) {
        Student foundStudent = studentService.updateStudent(student);
        if (foundStudent == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(foundStudent);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.removeStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/faculty")
    public ResponseEntity<Faculty> getStudentFaculty(@PathVariable Long id) {
        Faculty faculty = studentService.getStudentFaculty(id);
        return ResponseEntity.ok(faculty);
    }

    @GetMapping("/age")
    public ResponseEntity<List<Student>> getStudentsByAgeRange(@RequestParam int min,
                                                               @RequestParam int max) {
        List<Student> students = studentService.getStudentsByAgeRange(min, max);
        return ResponseEntity.ok(students);
    }

    @GetMapping("/students/count")
    public ResponseEntity<Long> getCountOfStudent() {
        Long count = studentRepository.countAllStudent();
        return ResponseEntity.ok(count);
    }

    @GetMapping("/students/average-age")
    public ResponseEntity<Double> getAverageAgeOfStudent() {
        Double averageAge = studentRepository.findAverageAge();
        return ResponseEntity.ok(averageAge);
    }

    @GetMapping("/students/latest")
    public ResponseEntity<List<Student>> getLastFiveStudent() {
        Pageable pageable = PageRequest.of(0, 5); // первая страница, 5 студентов на страницу
        List<Student> students = studentRepository.findTop5ByOrderByIdDesc(pageable);
        return ResponseEntity.ok(students);
    }

}