CREATE TABLE IF NOT EXISTS livro (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    isbn TEXT NOT NULL UNIQUE,
    titulo TEXT NOT NULL,
    autor TEXT NOT NULL,
    cdd TEXT NOT NULL,
    cdu TEXT NOT NULL,
    disponivel INTEGER DEFAULT 1
);

CREATE TABLE IF NOT EXISTS usuario (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nome TEXT NOT NULL,
    email TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS emprestimo (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    id_livro INTEGER NOT NULL,
    id_usuario INTEGER NOT NULL,
    titulo_livro TEXT NOT NULL,
    nome_usuario TEXT NOT NULL,
    data_emprestimo TEXT NOT NULL,
    data_devolucao_prevista TEXT NOT NULL,
    data_devolucao_real TEXT,
    multa_atraso_dia REAL NOT NULL DEFAULT 2.50,
    multa_dano REAL NOT NULL DEFAULT 100.00,
    valor_multa_total REAL NOT NULL DEFAULT 0.0,
    FOREIGN KEY (id_livro) REFERENCES livro(id) ON DELETE CASCADE,
    FOREIGN KEY (id_usuario) REFERENCES usuario(id) ON DELETE CASCADE
);
