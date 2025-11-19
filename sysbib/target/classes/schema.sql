-- =============================================================
-- TABELA: LIVRO
-- Armazena todos os livros cadastrados no sistema
-- =============================================================
CREATE TABLE IF NOT EXISTS livro (  -- Cria a tabela somente se ela ainda não existir
    id INTEGER PRIMARY KEY AUTOINCREMENT,  -- ID único do livro, gerado automaticamente
    titulo TEXT NOT NULL,                 -- Título do livro (obrigatório)
    autor TEXT NOT NULL,                  -- Autor do livro (obrigatório)
    disponivel INTEGER DEFAULT 1          -- 1 = disponível, 0 = emprestado
);


-- =============================================================
-- TABELA: USUARIO
-- Armazena todas as pessoas que podem pegar livros emprestados
-- =============================================================
CREATE TABLE IF NOT EXISTS usuario (   -- Cria a tabela somente se não existir
    id INTEGER PRIMARY KEY AUTOINCREMENT,  -- ID único do usuário, automático
    nome TEXT NOT NULL,                    -- Nome do usuário (obrigatório)
    email TEXT                             -- E-mail do usuário (pode ser nulo)
);


-- =============================================================
-- TABELA: EMPRESTIMO
-- Liga um LIVRO a um USUARIO com datas de controle
-- =============================================================
CREATE TABLE IF NOT EXISTS emprestimo (   -- Cria a tabela se não existir
    id INTEGER PRIMARY KEY AUTOINCREMENT,  -- ID único do empréstimo

    id_livro INTEGER,                      -- ID do livro emprestado
    id_usuario INTEGER,                    -- ID do usuário que pegou o livro

    data_emprestimo TEXT,                  -- Data em que o empréstimo foi feito
    data_devolucao_prevista TEXT,          -- Data limite para devolução

    FOREIGN KEY(id_livro) REFERENCES livro(id),     -- Garante que o id_livro existe na tabela livro
    FOREIGN KEY(id_usuario) REFERENCES usuario(id)  -- Garante que o id_usuario existe na tabela usuario
);
