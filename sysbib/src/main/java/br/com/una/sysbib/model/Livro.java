package br.com.una.sysbib.model; // Indica o pacote onde a classe está localizada

// Declaração da classe pública Livro — representa um livro no sistema
public class Livro {

    // ---------- 1. ATRIBUTOS ----------
    // Cada livro possui essas informações armazenadas
    private int id; // ID único do livro (gerado pelo banco)
    private String titulo; // Título do livro
    private String autor; // Autor do livro
    private boolean disponivel; // Indica se o livro está disponível para empréstimo

    // ---------- 2. CONSTRUTOR COMPLETO ----------
    // Usado quando o livro já existe no banco e possui um ID
    public Livro(int id, String titulo, String autor, boolean disponivel) {
        this.id = id; // Armazena o ID
        this.titulo = titulo; // Armazena o título
        this.autor = autor; // Armazena o autor
        this.disponivel = disponivel; // Armazena o status de disponibilidade
    }

    // ---------- 3. CONSTRUTOR PARA CRIAR NOVO LIVRO ----------
    // Usado quando cadastramos um livro novo (ID será gerado pelo banco)
    public Livro(String titulo, String autor) {
        this.titulo = titulo; // Define o título informado
        this.autor = autor; // Define o autor informado
        this.disponivel = true; // Novo livro sempre começa como disponível
    }

    // ---------- 4. GETTERS E SETTERS ----------
    // Métodos para acessar e alterar os valores dos atributos

    public int getId() { // Retorna o ID do livro
        return id;
    }

    public void setId(int id) { // Define o ID após inserção no banco
        this.id = id;
    }

    public String getTitulo() { // Retorna o título do livro
        return titulo;
    }

    public void setTitulo(String titulo) { // Altera o título do livro
        this.titulo = titulo;
    }

    public String getAutor() { // Retorna o autor
        return autor;
    }

    public void setAutor(String autor) { // Altera o autor
        this.autor = autor;
    }

    public boolean isDisponivel() { // Retorna se está disponível
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) { // Altera a disponibilidade
        this.disponivel = disponivel;
    }

    // ---------- 5. MÉTODO toString ----------
    // Retorna uma representação simples e amigável do livro
    @Override
    public String toString() {
        return "ID: " + id + ", Título: " + titulo + ", Autor: " + autor +
               (disponivel ? " [Disponível]" : " [EMPRESTADO]"); // Exibe status
    }
}
