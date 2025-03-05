-- liquibase formatted sql

-- changeset zhevalentin:1

CREATE INDEX student_name_index ON student(name);

-- changeset zhevalentin:2

CREATE INDEX faculty_name_index ON faculty(name, color);