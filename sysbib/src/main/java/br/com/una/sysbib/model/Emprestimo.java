package br.com.una.sysbib.model; // Define o pacote onde essa classe pertence

// Declara a classe pública chamada Emprestimo — representa um empréstimo de livro
public class Emprestimo {

    // ---------- 1. ATRIBUTOS (CAMPOS) ----------
    // Cada empréstimo terá essas informações armazenadas
    private int id; // ID único do empréstimo (geralmente gerado pelo banco)
    private int idLivro; // ID do livro que foi emprestado (liga ao livro)
    private int idUsuario; // ID do usuário que pegou o livro emprestado
    private String dataEmprestimo; // Data em que o empréstimo foi realizado (ex: "01/01/2025")
    private String dataDevolucaoPrevista; // Data prevista para devolução do livro

    // ---------- 2. CONSTRUTOR DE PERSISTÊNCIA ----------
    // Esse construtor é usado quando buscamos um registro no banco
    // e já temos o ID (ou seja, é um empréstimo que já existe no sistema).
    public Emprestimo(int id, int idLivro, int idUsuario, String dataEmprestimo, String dataDevolucaoPrevista) {
        // Abaixo atribuímos os valores recebidos (parâmetros) aos campos da classe
        this.id = id; // Define o campo id com o valor do parâmetro id
        this.idLivro = idLivro; // Define o campo idLivro
        this.idUsuario = idUsuario; // Define o campo idUsuario
        this.dataEmprestimo = dataEmprestimo; // Define a data do empréstimo
        this.dataDevolucaoPrevista = dataDevolucaoPrevista; // Define a data prevista de devolução
    }

    // ---------- 3. CONSTRUTOR DE REGISTRO ----------
    // Esse construtor é usado quando vamos criar um novo empréstimo no sistema
    // ainda sem ID (o ID será gerado pelo banco depois de inserir o registro).
    public Emprestimo(int idLivro, int idUsuario, String dataEmprestimo, String dataDevolucaoPrevista) {
        // Não recebemos o id aqui porque ainda não existe no banco
        this.idLivro = idLivro; // Atribui o ID do livro ao campo
        this.idUsuario = idUsuario; // Atribui o ID do usuário ao campo
        this.dataEmprestimo = dataEmprestimo; // Atribui a data do empréstimo
        this.dataDevolucaoPrevista = dataDevolucaoPrevista; // Atribui a data prevista de devolução
    }

    // ---------- 4. GETTERS E SETTERS ----------
    // Getters recuperam valores; setters alteram valores. Simples assim.

    // Retorna o ID do empréstimo
    public int getId() {
        return id; // Devolve o valor do campo id
    }

    // Define o ID do empréstimo (usado por exemplo depois de inserir no banco)
    public void setId(int id) {
        this.id = id; // Atribui o valor passado ao campo id
    }

    // Retorna o ID do livro que foi emprestado
    public int getIdLivro() {
        return idLivro; // Devolve o valor do campo idLivro
    }

    // Define o ID do livro (se necessário alterar)
    public void setIdLivro(int idLivro) {
        this.idLivro = idLivro; // Atribui o valor passado ao campo idLivro
    }

    // Retorna o ID do usuário que fez o empréstimo
    public int getIdUsuario() {
        return idUsuario; // Devolve o valor do campo idUsuario
    }

    // Define o ID do usuário (se precisar mudar)
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario; // Atribui o valor passado ao campo idUsuario
    }

    // Retorna a data em que o empréstimo foi realizado
    public String getDataEmprestimo() {
        return dataEmprestimo; // Devolve o valor do campo dataEmprestimo
    }

    // Define a data do empréstimo
    public void setDataEmprestimo(String dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo; // Atribui o valor passado ao campo dataEmprestimo
    }

    // Retorna a data prevista para devolução do livro
    public String getDataDevolucaoPrevista() {
        return dataDevolucaoPrevista; // Devolve o valor do campo dataDevolucaoPrevista
    }

    // Define a data prevista para devolução
    public void setDataDevolucaoPrevista(String dataDevolucaoPrevista) {
        this.dataDevolucaoPrevista = dataDevolucaoPrevista; // Atribui o valor passado ao campo dataDevolucaoPrevista
    }
}
