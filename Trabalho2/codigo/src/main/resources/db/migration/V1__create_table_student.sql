CREATE TABLE Students (
    student_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    age INT NOT NULL,
    parent_email VARCHAR(100) UNIQUE NOT NULL,
    parent_phone VARCHAR(15) NOT NULL
);

INSERT INTO Students
    (name,
     age,
     parent_email,
     parent_phone
) VALUES (
    'Joao Silva',
    12,
    'responsavel.joao@email.com',
    '11999999999'
), ('Maria Souza',
    10,
    'responsavel.maria@email.com',
    '11888888888'
);

CREATE TABLE Activity (
    activity_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    due_date DATE NOT NULL,
    description VARCHAR(100) NOT NULL,
    bimester INT NOT NULL,
    student_id BIGINT,
    FOREIGN KEY (student_id) REFERENCES Students(student_id)
);

INSERT INTO Activity
    (due_date,
     description,
     bimester,
     student_id
) VALUES (
    '2025-10-26',
    'Math Homework',
    2,
    1
), ('2025-10-26',
    'Science Project',
    2,
    2
);

