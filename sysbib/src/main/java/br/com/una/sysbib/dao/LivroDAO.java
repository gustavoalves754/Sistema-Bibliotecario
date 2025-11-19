package br.com.una.sysbib.dao; // Pacote onde a classe está localizada

import br.com.una.sysbib.model.Livro; // Importa o modelo Livro
import java.sql.Connection; // Representa a conexão com o banco
import java.sql.PreparedStatement; // Comandos SQL com parâmetros
import java.sql.ResultSet; // Resultado de SELECT
import java.sql.Statement; // Execução de SQL simples
import java.util.ArrayList; // Lista dinâmica
import java.util.List; // Interface de lista

// Classe responsável por salvar, buscar e atualizar livros no banco
public class LivroDAO {

    // =============================================================
    // MÉTODO: inserir
    // Insere um novo livro no banco
    // =============================================================
    public boolean inserir(Livro livro) {

        // SQL para inserir livro
        String sql = "INSERT INTO livro (titulo, autor, disponivel) VALUES (?, ?, ?)";

        // Obtém a conexão ativa
        Connection conn = Conexao.getConnection();

        // PreparedStatement fecha automaticamente com try-with-resources
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Preenche parâmetros do INSERT
            stmt.setString(1, livro.getTitulo()); // título
            stmt.setString(2, livro.getAutor()); // autor
            stmt.setInt(3, livro.isDisponivel() ? 1 : 0); // status (1 = disponível)

            // Executa o comando e verifica linhas afetadas
            int affectedRows = stmt.executeUpdate();

            // Se inseriu, pega o ID gerado
            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        livro.setId(rs.getInt(1)); // define ID no objeto
                        return true;
                    }
                }
            }

        } catch (Exception e) {
            System.err.println("Erro ao inserir livro: " + e.getMessage());
        }

        return false; // Erro ao inserir
    }


    // =============================================================
    // MÉTODO: buscarTodos
    // Retorna uma lista com todos os livros da tabela
    // =============================================================
    public List<Livro> buscarTodos() {
        List<Livro> livros = new ArrayList<>(); // Lista onde os livros serão armazenados

        // SQL para buscar todos os livros
        String sql = "SELECT id, titulo, autor, disponivel FROM livro";

        Connection conn = Conexao.getConnection(); // obtém conexão

        // Statement e ResultSet fecham automaticamente
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // Percorre todas as linhas da tabela
            while (rs.next()) {

                // Cria um objeto Livro com os dados da linha atual
                Livro l = new Livro(
                    rs.getInt("id"),
                    rs.getString("titulo"),
                    rs.getString("autor"),
                    rs.getInt("disponivel") == 1 // converte 1/0 para boolean
                );

                livros.add(l); // adiciona à lista
            }

        } catch (Exception e) {
            System.err.println("Erro ao buscar livros: " + e.getMessage());
        }

        return livros; // retorna lista preenchida (ou vazia)
    }


    // =============================================================
    // MÉTODO: buscarPorId
    // Retorna um livro específico pelo ID
    // =============================================================
    public Livro buscarPorId(int id) {

        String sql = "SELECT id, titulo, autor, disponivel FROM livro WHERE id = ?";

        Connection conn = Conexao.getConnection();

        // PreparedStatement com parâmetro
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id); // substitui o ? pelo ID informado

            // executa consulta
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) { // se achou o livro
                    return new Livro(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getInt("disponivel") == 1
                    );
                }
            }

        } catch (Exception e) {
            System.err.println("Erro ao buscar livro por ID: " + e.getMessage());
        }

        return null; // se não encontrou, retorna null
    }


    // =============================================================
    // MÉTODO: atualizarDisponibilidade
    // Atualiza a coluna 'disponivel' de um livro (usado em empréstimos)
    // =============================================================
    public boolean atualizarDisponibilidade(int idLivro, boolean disponivel) {

        String sql = "UPDATE livro SET disponivel = ? WHERE id = ?";

        Connection conn = Conexao.getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, disponivel ? 1 : 0); // define novo status
            stmt.setInt(2, idLivro); // especifica qual livro atualizar

            return stmt.executeUpdate() > 0; // true se atualização afetou 1+ linhas

        } catch (Exception e) {
            System.err.println("Erro ao atualizar disponibilidade: " + e.getMessage());
            return false;
        }
    }
}
