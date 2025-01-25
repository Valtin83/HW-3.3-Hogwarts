package com.example.Hogwarts.service;

import com.example.Hogwarts.model.Faculty;

public interface FacultyService {

    Faculty createFaculty(Faculty faculty);

    Faculty getFaculty(Long id);

    Faculty updateFaculty(Faculty faculty);

    void removeFaculty(Long id);

}
