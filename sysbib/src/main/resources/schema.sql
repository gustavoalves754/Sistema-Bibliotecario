-- schema.sql
-- Arquivo responsável por criar toda a estrutura do banco SQLite
-- usado no sistema bibliotecário SysBib.

--------------------------------------------------------------
-- TABELA: LIVRO
--------------------------------------------------------------

CREATE TABLE IF NOT EXISTS livro (
    -- Cria a tabela "livro" somente se ela ainda não existir.

    id INTEGER PRIMARY KEY AUTOINCREMENT,
    -- ID único do livro, chave primária, gerado automaticamente.

    isbn TEXT NOT NULL UNIQUE,
    -- ISBN do livro, obrigatório e não pode repetir (UNIQUE).

    titulo TEXT NOT NULL,
    -- Título do livro (obrigatório).

    autor TEXT NOT NULL,
    -- Nome do autor do livro (obrigatório).

    cdd TEXT,
    -- Código CDD (Classificação Decimal de Dewey). Opcional.

    cdu TEXT,
    -- Código CDU (Classificação Decimal Universal). Opcional.

    disponivel INTEGER DEFAULT 1
    -- Indica se o livro está disponível:
    -- 1 = disponível para empréstimo
    -- 0 = emprestado
);

--------------------------------------------------------------
-- TABELA: USUÁRIO
--------------------------------------------------------------

CREATE TABLE IF NOT EXISTS usuario (
    -- Cria a tabela "usuario" se não existir.

    id INTEGER PRIMARY KEY AUTOINCREMENT,
    -- ID único do usuário.

    nome TEXT NOT NULL,
    -- Nome do usuário (obrigatório).

    email TEXT NOT NULL
    -- E-mail do usuário (opcional).
);

--------------------------------------------------------------
-- TABELA: EMPRÉSTIMO
--------------------------------------------------------------

CREATE TABLE IF NOT EXISTS emprestimo (
    -- Cria a tabela "emprestimo" se ainda não existir.

    id INTEGER PRIMARY KEY AUTOINCREMENT,
    -- ID único do empréstimo.

    id_livro INTEGER NOT NULL,
    -- Referência ao ID do livro que foi emprestado (obrigatório).

    id_usuario INTEGER NOT NULL,
    -- Referência ao ID do usuário que pegou o livro (obrigatório).

    ----------------------------------------------------------
    -- DADOS COPIADOS PARA FACILITAR CONSULTA
    ----------------------------------------------------------

    titulo_livro TEXT NOT NULL,
    -- Guarda o título do livro no momento do empréstimo.
    -- Serve para evitar JOIN em consultas simples.

    nome_usuario TEXT NOT NULL,
    -- Guarda o nome do usuário que pegou o livro.

    ----------------------------------------------------------
    -- DATAS DO EMPRÉSTIMO
    ----------------------------------------------------------

    data_emprestimo TEXT NOT NULL,
    -- Data em que o livro foi retirado.
    -- Sugerido: "dd/mm/yyyy".

    data_devolucao_prevista TEXT NOT NULL,
    -- Data limite para devolver o livro.

    data_devolucao_real TEXT,
    -- Data em que o livro foi devolvido.
    -- Pode ser NULL enquanto o empréstimo estiver ativo.

    ----------------------------------------------------------
    -- MULTAS
    ----------------------------------------------------------

    multa_atraso_dia REAL NOT NULL DEFAULT 2.50,
    -- Valor cobrado por cada dia de atraso.

    multa_dano REAL NOT NULL DEFAULT 100.00,
    -- Multa aplicada caso o livro seja danificado.

    valor_multa_total REAL NOT NULL DEFAULT 0.0,
    -- Valor total da multa do empréstimo calculado na devolução.

    ----------------------------------------------------------
    -- RELACIONAMENTOS (FOREIGN KEYS)
    ----------------------------------------------------------

    FOREIGN KEY (id_livro)
        REFERENCES livro(id)
        ON DELETE CASCADE,
    -- Se um livro for deletado, todos os empréstimos dele também são apagados.

    FOREIGN KEY (id_usuario)
        REFERENCES usuario(id)
        ON DELETE CASCADE
    -- Se um usuário for deletado, os empréstimos dele também são apagados.
);
