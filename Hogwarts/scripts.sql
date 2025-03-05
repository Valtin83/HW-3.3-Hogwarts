select * from student where age between 10 and 20;
select name from student;
select name from student order by name asc;
select name from student where faculty_id = 1;
select * from student where name like '%о%';
select * from student where age < id;
select * from student order by age;

select student.name as student_name, faculty.name as faculty_name
from student join faculty on student.faculty_id = faculty.id
where student.id = 1;

select student.id AS student_id,
       student.name AS student_name,
       faculty.name AS faculty_name
from student
join faculty on student.faculty_id = faculty.id;

select student.id AS student_id,
        student.name AS student_name,
        faculty.name AS faculty_name
from student join faculty on student.faculty_id = faculty.id
where faculty.name = 'Гриффиндор';