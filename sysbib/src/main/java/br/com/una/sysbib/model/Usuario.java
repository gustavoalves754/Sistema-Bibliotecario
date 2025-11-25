package br.com.una.sysbib.model;
// Define o pacote onde esta classe está armazenada no projeto.

public class Usuario {
    // Declaração da classe Usuario.
    // Essa classe representa um usuário que pode pegar livros emprestados.

    private int id;
    // Identificador único do usuário no banco de dados.

    private String nome;
    // Nome completo do usuário.

    private String email;
    // E-mail do usuário (opcional).

    public Usuario() {
        // Construtor vazio (padrão).
        // Necessário para frameworks, DAO e criação de objetos sem dados inicialmente.
    }

    public Usuario(int id, String nome, String email) {
        // Construtor completo.
        // Usado quando já temos todos os dados, incluindo o ID do banco.

        this.id = id;         // Atribui o ID recebido ao campo id.
        this.nome = nome;     // Atribui o nome recebido ao campo nome.
        this.email = email;   // Atribui o e-mail recebido ao campo email.
    }

    public Usuario(String nome, String email) {
        // Construtor alternativo.
        // Usado quando um Novo Usuário é criado e ainda não tem ID.
        // O ID será gerado automaticamente pelo banco.

        this.nome = nome;     // Define o nome.
        this.email = email;   // Define o e-mail.
    }

    // --------------------------------------------------------
    // GETTERS — métodos que retornam valores dos atributos
    // --------------------------------------------------------

    public int getId() {
        return id; // Retorna o ID do usuário.
    }

    public String getNome() {
        return nome; // Retorna o nome do usuário.
    }

    public String getEmail() {
        return email; // Retorna o e-mail do usuário.
    }

    // --------------------------------------------------------
    // SETTERS — métodos que modificam atributos
    // --------------------------------------------------------

    public void setId(int id) {
        this.id = id; // Define um novo ID (normalmente usado ao puxar do banco).
    }

    public void setNome(String nome) {
        this.nome = nome; // Define um novo nome para o usuário.
    }

    public void setEmail(String email) {
        this.email = email; // Define um novo e-mail para o usuário.
    }

    // --------------------------------------------------------
    // toString() — representação do objeto em forma de texto
    // --------------------------------------------------------

    @Override
    public String toString() {
        // Sobrescreve o método padrão toString() da classe Object.
        // Retorna uma String que representa o usuário de forma legível.

        return "Usuario{" +        // Início da representação textual.
                "id=" + id +       // Mostra o ID.
                ", nome='" + nome + '\'' + // Mostra o nome entre aspas simples.
                ", email='" + email + '\'' + // Mostra o e-mail.
                '}';               // Fecha a representação do objeto.
    }
}
