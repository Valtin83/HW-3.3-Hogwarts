package com.example.Hogwarts.service;

import com.example.Hogwarts.exception.StudentNotFoundException;
import com.example.Hogwarts.model.Faculty;
import com.example.Hogwarts.model.Student;
import com.example.Hogwarts.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    @Autowired
    private final StudentRepository studentRepository;


    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student createStudent(Student student) {
        logger.debug("Начинаем создание студента: {}", student);
        student.setId(null);
        Student createdStudent = studentRepository.save(student);
        logger.debug("Студент успешно создан: {}", createdStudent);
        return createdStudent;
    }

    @Override
    public Student getStudent(Long id) {
        logger.debug("Запрос студента с ID: {}", id);
        return studentRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Студент с ID {} отсутствует", id);
                    return new StudentNotFoundException("Студент отсутствует");
                });
    }

    @Override
    public Student updateStudent(Student student) {
        logger.debug("Обновляем студента: {}", student);
        Student updatedStudent = studentRepository.save(student);
        logger.debug("Студент успешно обновлён: {}", updatedStudent);
        return updatedStudent;
    }

    @Override
    public void removeStudent(Long id) {
        logger.debug("Удаление студента с ID: {}", id);
        studentRepository.deleteById(id);
        logger.debug("Студент с ID {} успешно удалён", id);
    }

    @Override
    public Faculty getStudentFaculty(Long studentId) {
        logger.debug("Получение факультета для студента с ID: {}", studentId);
        Student student = getStudent(studentId);
        Faculty faculty = student.getFaculty();
        logger.debug("Факультет студента с ID {}: {}", studentId, faculty);
        return faculty;
    }

    @Override
    public List<Student> getStudentsByAgeRange(int min, int max) {
        logger.debug("Получение студентов в возрастном диапазоне от {} до {}", min, max);
        List<Student> students = studentRepository.findByAgeBetween(min, max);
        logger.debug("Найдены студенты: {}", students);
        return students;
    }

}