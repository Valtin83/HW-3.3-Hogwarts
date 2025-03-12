package com.example.Hogwarts.service;

import com.example.Hogwarts.exception.FacultyNotFoundException;
import com.example.Hogwarts.model.Faculty;
import com.example.Hogwarts.model.Student;
import com.example.Hogwarts.repository.FacultyRepository;
import com.example.Hogwarts.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class FacultyServiceImpl implements FacultyService {

    private static final Logger logger = LoggerFactory.getLogger(FacultyServiceImpl.class);

    @Autowired
    private final FacultyRepository facultyRepository;
    private final StudentRepository studentRepository;


    public FacultyServiceImpl(FacultyRepository facultyRepository, StudentRepository studentRepository) {
        this.facultyRepository = facultyRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public Faculty createFaculty(Faculty faculty) {
        logger.debug("Вызываемый метод создает факультет: {}", faculty);
        Faculty savedFaculty = facultyRepository.save(faculty);
        logger.info("Факультет успешно создан: {}", savedFaculty);
        return savedFaculty;
    }

    @Override
    public Faculty getFaculty(Long id) {
        logger.debug("Вызываемый метод получает факультет с ID: {}", id);
        return facultyRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Факультета с ID {} не существует", id);
                    return new FacultyNotFoundException("Факультет отсутствует");
                });
    }

    @Override
    public Faculty updateFaculty(Faculty faculty) {
        logger.debug("Вызванный метод обновляет факультет: {}", faculty);
        if (!facultyRepository.existsById(faculty.getId())) {
            logger.warn("Предпринята попытка обновить несуществующий факультет с помощью ID: {}", faculty.getId());
            throw new FacultyNotFoundException("Факультет не найден для обновления");
        }
        Faculty updatedFaculty = facultyRepository.save(faculty);
        logger.info("Обновленные свойства факультета: {}", updatedFaculty);
        return updatedFaculty;
    }

    @Override
    public void removeFaculty(Long id) {
        logger.debug("Вызванный метод удаляет факультет с ID: {}", id);
        if (!facultyRepository.existsById(id)) {
            logger.warn("Предпринята попытка удалить несуществующий факультет с ID: {}", id);
            throw new FacultyNotFoundException("Факультет не найден для удаления");
        }
        facultyRepository.deleteById(id);
        logger.info("Факультет удалён с ID: {}", id);

    }

    @Override
    public List<Student> getStudentsByFaculty(Long facultyId) {
        logger.info("Вызываемый метод получает студентов по факультетам с указанием идентификатора факультета: {}", facultyId);
        List<Student> students = studentRepository.findByFacultyId(facultyId);
        logger.info("Студенты полученные по идентификатору факультета {}: {}", facultyId, students);
        return students;
    }

    @Override
    public List<Faculty> searchByColorOrName(String color, String name) {
        logger.info("Вызываемый метод выполняет поиск по цвету или названию с помощью color: {}, name: {}", color, name);
        List<Faculty> faculties = facultyRepository.findByColorIgnoreCaseOrNameIgnoreCase(color, name);
        logger.info("Найденные факультеты: {}", faculties);
        return faculties;
    }
}