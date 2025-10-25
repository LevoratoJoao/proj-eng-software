CREATE TABLE Activity (
                           activity_id BIGINT PRIMARY KEY AUTO_INCREMENT,
                           due_date DATE NOT NULL,
                           description VARCHAR(100) NOT NULL,
                           bimester INT NOT NULL
);