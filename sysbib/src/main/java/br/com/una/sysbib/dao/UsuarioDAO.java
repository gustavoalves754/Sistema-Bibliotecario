package br.com.una.sysbib.dao;
// Define o pacote onde essa classe DAO está localizada no projeto.

import br.com.una.sysbib.model.Usuario;
// Importa o modelo Usuario, pois o DAO cria e retorna objetos desse tipo.

import java.sql.*;
// Importa tudo que é necessário para trabalhar com SQL via JDBC
// (Connection, PreparedStatement, ResultSet, Statement).

import java.util.ArrayList;
import java.util.List;
// Importa classes de lista para armazenar vários usuários.

public class UsuarioDAO {
    // Classe responsável por manipular a tabela 'usuario' no banco (CRUD).

    public boolean inserir(Usuario u) {
        // Método que insere um novo usuário no banco.
        // Retorna true se deu certo.

        String sql = "INSERT INTO usuario (nome, email) VALUES (?, ?)";
        // SQL com parâmetros (?) que serão preenchidos depois.

        try (Connection conn = Conexao.getConnection();
             // Abre conexão com o banco. Será fechada automaticamente.

             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
             // Prepara a SQL para execução.
             // RETURN_GENERATED_KEYS permite pegar o ID gerado pelo banco.

            stmt.setString(1, u.getNome());
            // Define o primeiro ? com o nome do usuário.

            stmt.setString(2, u.getEmail());
            // Define o segundo ? com o email do usuário.

            int affected = stmt.executeUpdate();
            // Executa o INSERT.
            // Retorna quantas linhas foram afetadas (deve ser 1 se funcionar).

            if (affected > 0) {
                // Se pelo menos 1 linha foi inserida, então deu certo.

                ResultSet rs = stmt.getGeneratedKeys();
                // Pega o ID que o banco gerou.

                if (rs.next())
                    u.setId(rs.getInt(1));
                    // Pega o ID do resultset e salva no objeto usuario.

                return true;
                // Confirma que funcionou.
            }

        } catch (Exception e) {
            // Se ocorrer qualquer erro, captura aqui.

            System.err.println("Erro ao inserir usuário: " + e.getMessage());
            // Exibe mensagem de erro.
        }

        return false;
        // Se chegou aqui, não inseriu.
    }

    public List<Usuario> listarTodos() {
        // Lista todos os usuários cadastrados no banco.

        List<Usuario> lista = new ArrayList<>();
        // Cria uma lista vazia para armazenar os usuários.

        String sql = "SELECT * FROM usuario ORDER BY nome";
        // SQL para trazer todos os usuários ordenados por nome.

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
             // Executa a consulta e armazena o resultado em 'rs'.

            while (rs.next()) {
                // Percorre cada linha retornada pela consulta SQL.

                Usuario u = new Usuario();
                // Cria um objeto usuario vazio.

                u.setId(rs.getInt("id"));
                // Preenche o ID do usuário.

                u.setNome(rs.getString("nome"));
                // Preenche o nome.

                u.setEmail(rs.getString("email"));
                // Preenche o email.

                lista.add(u);
                // Adiciona o usuário preenchido na lista final.
            }

        } catch (Exception e) {
            System.err.println("Erro ao listar usuários: " + e.getMessage());
            // Exibe erro caso não consiga buscar os dados.
        }

        return lista;
        // Retorna a lista completa de usuários.
    }

    public Usuario buscarPorId(int id) {
        // Busca um usuário pelo seu ID.

        String sql = "SELECT * FROM usuario WHERE id = ?";
        // SQL com parâmetro para filtrar pelo ID.

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            // Coloca o ID recebido no ? da SQL.

            ResultSet rs = stmt.executeQuery();
            // Executa a consulta.

            if (rs.next()) {
                // Se encontrou uma linha correspondente ao ID:

                return new Usuario(
                        rs.getInt("id"),    // cria um novo usuário já com ID
                        rs.getString("nome"),
                        rs.getString("email")
                );
            }

        } catch (Exception e) {
            System.err.println("Erro ao buscar usuário: " + e.getMessage());
            // Exibe erro se falhar.
        }

        return null;
        // Retorna null se não encontrou nenhum usuário com este ID.
    }

    public boolean atualizar(Usuario u) {
        // Atualiza os dados de um usuário existente.

        String sql = "UPDATE usuario SET nome = ?, email = ? WHERE id = ?";
        // SQL para atualizar nome e email baseado no ID.

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, u.getNome());
            // Define o novo nome.

            stmt.setString(2, u.getEmail());
            // Define o novo email.

            stmt.setInt(3, u.getId());
            // Define qual usuário será atualizado (ID).

            return stmt.executeUpdate() > 0;
            // Executa e retorna true se alterou pelo menos 1 linha.

        } catch (Exception e) {
            System.err.println("Erro ao atualizar usuário: " + e.getMessage());
            // Exibe erro caso aconteça.
        }

        return false;
        // Se chegou aqui, não atualizou.
    }

    public boolean deletar(int id) {
        // Deleta um usuário do banco pelo ID.

        String sql = "DELETE FROM usuario WHERE id = ?";
        // SQL para apagar o usuário desejado.

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            // Coloca o ID no ? da SQL.

            return stmt.executeUpdate() > 0;
            // Retorna true se apagou pelo menos uma linha.

        } catch (Exception e) {
            System.err.println("Erro ao deletar usuário: " + e.getMessage());
            // Exibe erro se falhar.
        }

        return false;
        // Retorna false se não conseguiu apagar.
    }
}
