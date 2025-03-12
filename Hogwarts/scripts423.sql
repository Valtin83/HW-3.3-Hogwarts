SELECT student.name, student.age, faculty.faculty_name
FROM Student student
JOIN Faculty faculty ON student.faculty_id = faculty.faculty_id;


SELECT student.name, student.age, faculty.faculty_name
FROM Student student
JOIN Faculty faculty ON student.faculty_id = faculty.faculty_id
WHERE student.has_driving_license = TRUE;