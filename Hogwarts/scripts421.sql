ALTER TABLE Student
ADD CONSTRAINT chk_age CHECK (age >= 16);

ALTER TABLE Student
ADD CONSTRAINT uq_student_name UNIQUE (name),
ADD CONSTRAINT chk_name_not_null CHECK (name IS NOT NULL);

ALTER TABLE Faculty
ADD CONSTRAINT uq_faculty_name_color UNIQUE (faculty_name, faculty_color);

ALTER TABLE Student
ALTER COLUMN age SET DEFAULT 21;