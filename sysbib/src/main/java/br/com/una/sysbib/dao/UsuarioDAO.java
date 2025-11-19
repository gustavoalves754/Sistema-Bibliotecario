package br.com.una.sysbib.dao; // Pacote onde a classe está localizada

import br.com.una.sysbib.model.Usuario; // Importa o modelo Usuario
import java.sql.Connection; // Representa a conexão com o banco
import java.sql.PreparedStatement; // Usado para comandos SQL com parâmetros
import java.sql.ResultSet; // Resultado de consultas SELECT
import java.sql.Statement; // Executa SQL simples
import java.util.ArrayList; // Lista dinâmica
import java.util.List; // Interface de lista

// Classe responsável pelas operações de CRUD do usuário no banco de dados
public class UsuarioDAO {

    // =============================================================
    // MÉTODO: inserir
    // Insere um novo usuário na tabela 'usuario'
    // =============================================================
    public boolean inserir(Usuario usuario) {

        String sql = "INSERT INTO usuario (nome, email) VALUES (?, ?)"; // SQL com parâmetros

        Connection conn = Conexao.getConnection(); // Obtém a conexão ativa com o banco

        // PreparedStatement fecha automaticamente com try-with-resources
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, usuario.getNome()); // Define o primeiro parâmetro (nome)
            stmt.setString(2, usuario.getEmail()); // Define o segundo parâmetro (email)

            int affectedRows = stmt.executeUpdate(); // Executa o INSERT

            // Se inseriu pelo menos 1 linha, tenta pegar o ID gerado automaticamente
            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) { // Retorna os IDs gerados
                    if (rs.next()) { // Se houver um ID
                        usuario.setId(rs.getInt(1)); // Atribui ao objeto
                        return true; // Inserção concluída com sucesso
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao inserir usuário: " + e.getMessage());
        }

        return false; // Caso aconteça erro
    }


    // =============================================================
    // MÉTODO: buscarTodos
    // Retorna uma lista com TODOS os usuários cadastrados no banco
    // =============================================================
    public List<Usuario> buscarTodos() {

        List<Usuario> usuarios = new ArrayList<>(); // Lista que armazenará os usuários encontrados

        String sql = "SELECT id, nome, email FROM usuario"; // Busca tudo da tabela usuário

        Connection conn = Conexao.getConnection(); // Obtém a conexão

        // Statement e ResultSet fecham automaticamente
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // Percorre cada linha da tabela
            while (rs.next()) {
                Usuario u = new Usuario(
                        rs.getInt("id"),     // ID
                        rs.getString("nome"), // Nome
                        rs.getString("email") // Email
                );
                usuarios.add(u); // Adiciona na lista
            }

        } catch (Exception e) {
            System.err.println("Erro ao buscar usuários: " + e.getMessage());
        }

        return usuarios; // Retorna a lista (vazia ou cheia)
    }


    // =============================================================
    // MÉTODO: buscarPorId
    // Retorna um usuário específico buscando pelo ID
    // =============================================================
    public Usuario buscarPorId(int id) {

        String sql = "SELECT id, nome, email FROM usuario WHERE id = ?"; // Consulta com filtro

        Connection conn = Conexao.getConnection(); // Obtém conexão

        // PreparedStatement com parâmetro
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id); // Define o valor do ? (ID procurado)

            try (ResultSet rs = stmt.executeQuery()) { // Executa SELECT
                if (rs.next()) { // Se encontrou o ID
                    return new Usuario(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("email")
                    );
                }
            }

        } catch (Exception e) {
            System.err.println("Erro ao buscar usuário por ID: " + e.getMessage());
        }

        return null; // Se não encontrou, retorna null
    }
}
