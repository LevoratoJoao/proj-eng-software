CREATE TABLE Lessons (
     lesson_id BIGINT AUTO_INCREMENT PRIMARY KEY,
     discipline VARCHAR(255) NOT NULL,
     group_class VARCHAR(255) NOT NULL,
     lesson_date DATE NOT NULL,
     lesson_time_start TIME NOT NULL,
     lesson_time_end TIME NOT NULL,
     theme VARCHAR(255) NOT NULL,
     objectives VARCHAR(255) NOT NULL
);

INSERT INTO Lessons
    (discipline,
     group_class,
     lesson_date,
     lesson_time_start,
     lesson_time_end,
     theme,
     objectives
) VALUES (
    'Mathematics',
    'Group A',
    '2025-11-01',
    '09:00:00',
    '10:00:00',
    'Algebra Basics',
    'Understand basic algebraic concepts'
);
