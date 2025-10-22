CREATE TABLE Alunos (
    aluno_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    idade INT NOT NULL,
    email_responsavel VARCHAR(100) UNIQUE NOT NULL,
    telefone_responsavel VARCHAR(15) NOT NULL
);

INSERT INTO Alunos
    (nome,
     idade,
     email_responsavel,
     telefone_responsavel
) VALUES (
    'João Silva',
    12,
    'responsavel.joao@email.com',
    '11999999999'
), ('Maria Souza',
    10,
    'responsavel.maria@email.com',
    '11888888888'
);