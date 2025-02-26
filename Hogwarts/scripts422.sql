CREATE TABLE Person (
    person_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    age INT CHECK (age >= 0),
    has_driving_license BOOLEAN NOT NULL
);

CREATE TABLE Car (
    car_id SERIAL PRIMARY KEY,
    brand VARCHAR(50) NOT NULL,
    model VARCHAR(50) NOT NULL,
    cost DECIMAL(10, 2) NOT NULL
);

CREATE TABLE Person_Car (
    person_id INT,
    car_id INT,
    PRIMARY KEY (person_id, car_id),
    FOREIGN KEY (person_id) REFERENCES Person(person_id) ON DELETE CASCADE,
    FOREIGN KEY (car_id) REFERENCES Car(car_id) ON DELETE CASCADE
);