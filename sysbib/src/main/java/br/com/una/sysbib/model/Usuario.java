package br.com.una.sysbib.model; // Pacote onde esta classe está localizada

// Classe pública Usuario — representa um usuário do sistema
public class Usuario {

    // ---------- 1. ATRIBUTOS ----------
    private int id; // ID único do usuário no banco de dados
    private String nome; // Nome completo do usuário
    private String email; // E-mail do usuário

    // ---------- 2. CONSTRUTOR COMPLETO ----------
    // Usado quando buscamos um usuário já existente no banco (ID já existe)
    public Usuario(int id, String nome, String email) {
        this.id = id; // Atribui o ID existente
        this.nome = nome; // Atribui o nome informado
        this.email = email; // Atribui o e-mail informado
    }

    // ---------- 3. CONSTRUTOR PARA NOVO USUÁRIO ----------
    // Usado quando criamos um novo usuário (ID será gerado pelo banco)
    public Usuario(String nome, String email) {
        this.nome = nome; // Define o nome informado
        this.email = email; // Define o e-mail informado
    }

    // ---------- 4. GETTERS E SETTERS ----------

    public int getId() { // Retorna o ID do usuário
        return id;
    }

    public void setId(int id) { // Define o ID após inserção no banco
        this.id = id;
    }

    public String getNome() { // Retorna o nome do usuário
        return nome;
    }

    public void setNome(String nome) { // Altera o nome do usuário
        this.nome = nome;
    }

    public String getEmail() { // Retorna o e-mail do usuário
        return email;
    }

    public void setEmail(String email) { // Altera o e-mail do usuário
        this.email = email;
    }

    // ---------- 5. MÉTODO toString ----------
    // Retorna uma descrição simples do usuário
    @Override
    public String toString() {
        return "ID: " + id + ", Nome: " + nome + ", Email: " + email; // Monta a string de exibição
    }
}
