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