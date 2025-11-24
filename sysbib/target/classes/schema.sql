-- schema.sql
-- Esquema do banco de dados SQLite para o sistema bibliotecário

-- Tabela de livros
CREATE TABLE IF NOT EXISTS livro (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    isbn TEXT NOT NULL UNIQUE,      -- Identificador único do livro
    titulo TEXT NOT NULL,
    autor TEXT NOT NULL,
    cdd TEXT,                       -- Classificação Decimal de Dewey (opcional)
    cdu TEXT,                       -- Classificação Decimal Universal (opcional)
    disponivel INTEGER DEFAULT 1    -- 1 = Disponível, 0 = Emprestado
);

-- Tabela de usuários
CREATE TABLE IF NOT EXISTS usuario (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nome TEXT NOT NULL,
    email TEXT
);

-- Tabela de empréstimos
CREATE TABLE IF NOT EXISTS emprestimo (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    id_livro INTEGER NOT NULL,
    id_usuario INTEGER NOT NULL,

    -- Dados explícitos para facilitar consulta
    titulo_livro TEXT NOT NULL,
    nome_usuario TEXT NOT NULL,

    data_emprestimo TEXT NOT NULL,           -- formato sugerido: yyyy-MM-dd
    data_devolucao_prevista TEXT NOT NULL,   -- formato sugerido: yyyy-MM-dd
    data_devolucao_real TEXT,                -- preenchido na devolução (pode ser NULL enquanto ativo)

    -- Multas
    multa_atraso_dia REAL NOT NULL DEFAULT 2.50,   -- valor por dia de atraso
    multa_dano REAL NOT NULL DEFAULT 100.00,       -- valor fixo por dano no livro
    valor_multa_total REAL NOT NULL DEFAULT 0.0,   -- valor total calculado na devolução

    FOREIGN KEY (id_livro) REFERENCES livro(id) ON DELETE CASCADE,
    FOREIGN KEY (id_usuario) REFERENCES usuario(id) ON DELETE CASCADE
);
