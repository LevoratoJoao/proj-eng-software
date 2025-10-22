CREATE TABLE Atividade (
    atividade_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    data_entrega DATE NOT NULL,
    descricao VARCHAR(100) NOT NULL,
    bimestre INT NOT NULL
);