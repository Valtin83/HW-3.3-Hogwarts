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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.apache.coyote.http11.Constants.a;

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

    @GetMapping("/names/startWithA")
    public List<String> getNamesStartingWithA() {
        return studentRepository.findAll().stream()
                .map(student -> student.getName().toUpperCase())
                .filter(name -> name.startsWith("А"))
                .sorted()
                .collect(Collectors.toList());
    }

    @GetMapping("/averageAge")
    public double getAverageAge() {
        return studentRepository.findAll().stream()
                .mapToInt(Student::getAge)
                .average()
                .orElse(0.0); // Возвращаем 0, если студентов нет
    }

    @GetMapping("/compare")
    public ResponseEntity<String> compareSumMethods() {

        // Метод 1: Вычисление суммы через Stream API
        long startTimeStream = System.currentTimeMillis();
        int sumStream = Stream.iterate(1, a -> a +1)
                .limit(1_000_000)
                .reduce(0, Integer::sum);// Суммирование через Stream API
        long endTimeStream = System.currentTimeMillis();
        long durationStream = endTimeStream - startTimeStream;

        // Метод 2: Вычисление суммы через математическую формулу Sn=(n*(n+1))/2
        long startTimeFormula = System.currentTimeMillis();
        int n = 1_000_000;
        int sumFormula = n * (n + 1) / 2; // Сумма первых n натуральных чисел
        long endTimeFormula = System.currentTimeMillis();
        long durationFormula = endTimeFormula - startTimeFormula;

        // Формирование ответа
        StringBuilder response = new StringBuilder()
                .append("Результат через математическую формулу: ")
                .append(sumFormula)
                .append(", Время вычисления: ")
                .append(durationFormula)
                .append(" мс\n")
                .append("Результат через Stream API: ")
                .append(sumStream)
                .append(", Время вычисления: ")
                .append(durationStream)
                .append(" мс\n");

        // Сравнение результатов
        if (sumFormula == sumStream) {
            response.append("Оба метода дают одинаковый результат.");
        } else {
            response.append("Результаты различаются!");
        }

        return ResponseEntity.ok(response.toString());
    }

    @GetMapping("/students/print-parallel")
    public void printStudentsInParallel() {
        List<Student> students = studentRepository.findAll();

        System.out.println(Thread.currentThread().getName() + ": " + students.get(0).getName());
        System.out.println(Thread.currentThread().getName() + ": " + students.get(1).getName());

        ExecutorService executor = Executors.newFixedThreadPool(2);

        // Параллельный вывод остальных имен
        for (int i = 2; i < students.size(); i++) {
            final int index = i;
            executor.submit(() -> System.out.println(Thread.currentThread().getName() + ": " + students.get(index).getName()));
        }

        executor.shutdown(); // Закрываем Executor после завершения задач
    }

    @GetMapping("/students/print-synchronous")
    public void printStudentsSynchronous() {
        List<Student> students = studentRepository.findAll();

        for (Student student : students) {
            System.out.println(Thread.currentThread().getName() + ": " + student.getName());
        }
    }


}
