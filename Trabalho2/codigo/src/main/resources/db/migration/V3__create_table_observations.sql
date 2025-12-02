CREATE TABLE Observations (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    message VARCHAR(255) NOT NULL,
    date DATE NOT NULL,
    student_id BIGINT,
    FOREIGN KEY (student_id) REFERENCES Students(student_id)
);