package br.com.una.sysbib.model;
// Define o pacote onde esta classe está localizada dentro do projeto.

public class Livro {
    // Declaração da classe Livro, representando um livro no sistema.

    private int id;
    // Identificador único do livro (chave primária no banco).

    private String isbn;
    // Código ISBN (identificador internacional do livro).

    private String titulo;
    // Título do livro.

    private String autor;
    // Nome do autor.

    private String cdd;
    // Classificação decimal de Dewey (opcional).

    private String cdu;
    // Classificação decimal universal (opcional).

    private boolean disponivel;
    // Indica se o livro está disponível para empréstimo (true = disponível).

    public Livro() {
        // Construtor vazio. Necessário para frameworks e para inicialização simples.
    }

    public Livro(int id, String isbn, String titulo, String autor, String cdd, String cdu, boolean disponivel) {
        // Construtor completo, recebe todos os atributos do livro.

        this.id = id;                 // Atribui o ID recebido ao atributo id.
        this.isbn = isbn;             // Atribui o ISBN.
        this.titulo = titulo;         // Atribui o título do livro.
        this.autor = autor;           // Atribui o nome do autor.
        this.cdd = cdd;               // Atribui o código CDD.
        this.cdu = cdu;               // Atribui o código CDU.
        this.disponivel = disponivel; // Define se está disponível ou não.
    }

    public Livro(String isbn, String titulo, String autor, String cdd, String cdu) {
        // Construtor alternativo usado quando o livro é novo
        // e ainda não possui ID (pois será gerado pelo banco)
        // disponível = true por padrão.

        this(0, isbn, titulo, autor, cdd, cdu, true);
        // Chama o outro construtor passando ID = 0 (será substituído no banco)
        // e disponivel = true (livro novo sempre disponível).
    }

    // --------------------------------------------------------------
    // GETTERS E SETTERS (métodos para acessar e alterar atributos)
    // --------------------------------------------------------------

    public int getId() {
        return id; // Retorna o ID do livro.
    }

    public void setId(int id) {
        this.id = id; // Define o ID do livro.
    }

    public String getIsbn() {
        return isbn; // Retorna o ISBN.
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn; // Define o ISBN.
    }

    public String getTitulo() {
        return titulo; // Retorna o título.
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo; // Define o título.
    }

    public String getAutor() {
        return autor; // Retorna o nome do autor.
    }

    public void setAutor(String autor) {
        this.autor = autor; // Define o autor.
    }

    public String getCdd() {
        return cdd; // Retorna o código CDD.
    }

    public void setCdd(String cdd) {
        this.cdd = cdd; // Define o código CDD.
    }

    public String getCdu() {
        return cdu; // Retorna o código CDU.
    }

    public void setCdu(String cdu) {
        this.cdu = cdu; // Define o código CDU.
    }

    public boolean isDisponivel() {
        return disponivel; // Retorna true se o livro está disponível.
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel; // Define a disponibilidade do livro.
    }

    @Override
    public String toString() {
        // Método que retorna uma representação textual do objeto Livro.
        // Muito útil para debugar, imprimir listas ou exibir dados no console.

        return "Livro{" +                   // Começo da representação
                "id=" + id +                // Adiciona "id=valor"
                ", isbn='" + isbn + '\'' +  // Exibe o ISBN entre aspas
                ", titulo='" + titulo + '\'' + // Exibe o título do livro
                ", autor='" + autor + '\'' +   // Exibe o nome do autor
                ", cdd='" + cdd + '\'' +       // Exibe a CDD
                ", cdu='" + cdu + '\'' +       // Exibe a CDU
                ", disponivel=" + disponivel + // Exibe se está disponível
                '}';                           // Fecha a representação
    }
}
