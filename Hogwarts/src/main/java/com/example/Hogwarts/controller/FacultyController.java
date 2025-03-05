package com.example.Hogwarts.controller;


import com.example.Hogwarts.model.Faculty;
import com.example.Hogwarts.model.Student;
import com.example.Hogwarts.repository.FacultyRepository;
import com.example.Hogwarts.service.FacultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/faculty")
public class FacultyController {

    private final FacultyService facultyService;
    private final FacultyRepository facultyRepository;

    @Autowired
    public FacultyController(FacultyRepository facultyRepository, FacultyService facultyService) {
        this.facultyRepository = facultyRepository;
        this.facultyService = facultyService;
    }


    @GetMapping("{id}")
    public ResponseEntity<Faculty> getFacultyInfo(@PathVariable Long id) {
        Faculty faculty = facultyService.getFaculty(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    @PostMapping
    public Faculty createFaculty(@RequestBody Faculty faculty) {
        return facultyService.createFaculty(faculty);
    }

    @PutMapping
    public ResponseEntity<Faculty> updateFaculty(@RequestBody Faculty faculty) {
        Faculty foundFaculty = facultyService.updateFaculty(faculty);
        if (foundFaculty == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(foundFaculty);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> removeFaculty(@PathVariable Long id) {
        facultyService.removeFaculty(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/faculty/{facultyId}")
    public List<Student> getStudentsByFaculty(@PathVariable Long facultyId) {
        return facultyService.getStudentsByFaculty(facultyId);
    }

    @GetMapping("/search")
    public List<Faculty> search(@RequestParam(required = false) String color,
                                @RequestParam(required = false) String name) {
        return facultyService.searchByColorOrName(color, name);
    }


    @GetMapping("/faculties")
    public ResponseEntity<Page<Faculty>> getFaculty(
            @RequestParam int page,
            @RequestParam int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Faculty> facultyPage = facultyRepository.findAll(pageable);

        return ResponseEntity.ok(facultyPage);
    }
}