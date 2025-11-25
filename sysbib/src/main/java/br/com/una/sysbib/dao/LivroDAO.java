package br.com.una.sysbib.dao;
// Define o pacote onde esta classe DAO está localizada no projeto.

import br.com.una.sysbib.model.Livro;
// Importa a classe Livro, pois este DAO cria, altera e retorna objetos Livro.

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
// Importações necessárias para trabalhar com JDBC.

import java.util.ArrayList;
import java.util.List;
// Importações necessárias para criar listas de livros.

public class LivroDAO {
    // Classe responsável por todas as operações CRUD da tabela livro.

    public boolean inserir(Livro livro) {
        // Método que insere um novo livro no banco e retorna true se deu certo.

        String sql = "INSERT INTO livro (isbn, titulo, autor, cdd, cdu, disponivel) VALUES (?, ?, ?, ?, ?, ?)";
        // SQL com marcadores (?) a serem substituídos pelo PreparedStatement.

        try (Connection conn = Conexao.getConnection();
             // Abre conexão com o banco usando try-with-resources.
             // Assim, fecha automaticamente no fim.

             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
             // Prepara a SQL para execução.
             // RETURN_GENERATED_KEYS permite pegar o ID criado automaticamente.

            stmt.setString(1, livro.getIsbn());
            // Preenche o primeiro ? com o ISBN do livro.

            stmt.setString(2, livro.getTitulo());
            // Preenche o segundo ? com o título.

            stmt.setString(3, livro.getAutor());
            // Preenche o terceiro ? com o autor.

            stmt.setString(4, livro.getCdd());
            // Preenche o quarto ? com o CDD.

            stmt.setString(5, livro.getCdu());
            // Preenche o quinto ? com o CDU.

            stmt.setInt(6, livro.isDisponivel() ? 1 : 0);
            // No banco, disponivel é INTEGER (1 = disponível, 0 = não disponível).

            int affected = stmt.executeUpdate();
            // Executa o INSERT. Retorna quantas linhas foram afetadas.

            if (affected > 0) {
                // Se inseriu pelo menos uma linha, funcionou.

                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    // Pega o ID gerado (caso tenha sido criado automaticamente).

                    if (rs.next()) {
                        livro.setId(rs.getInt(1));
                        // Atualiza o objeto Livro com o ID gerado.
                    }
                }
                return true;
                // Retorna sucesso.
            }

        } catch (SQLException e) {
            // Tratamento de erro caso algo dê errado no SQL ou conexão.

            System.err.println("Erro ao inserir livro: " + e.getMessage());
        }

        return false;
        // Caso não tenha inserido nada, retorna false.
    }

    public List<Livro> listarTodos() {
        // Método para listar todos os livros cadastrados no banco.

        List<Livro> livros = new ArrayList<>();
        // Cria uma lista vazia para armazenar os livros.

        String sql = "SELECT id, isbn, titulo, autor, cdd, cdu, disponivel FROM livro ORDER BY titulo";
        // Query SQL para buscar todos os livros ordenados por título.

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            // Executa a consulta e percorre todos os resultados.

            while (rs.next()) {
                // Para cada linha retornada, cria um objeto Livro.

                Livro l = new Livro();
                // Livro criado vazio para ser preenchido.

                l.setId(rs.getInt("id"));
                // Preenche o ID.

                l.setIsbn(rs.getString("isbn"));
                // Preenche o ISBN.

                l.setTitulo(rs.getString("titulo"));
                // Preenche o título.

                l.setAutor(rs.getString("autor"));
                // Preenche o autor.

                l.setCdd(rs.getString("cdd"));
                // Preenche o CDD.

                l.setCdu(rs.getString("cdu"));
                // Preenche o CDU.

                l.setDisponivel(rs.getInt("disponivel") == 1);
                // Se no banco armazenou 1 → disponível.

                livros.add(l);
                // Adiciona o livro preenchido à lista final.
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar livros: " + e.getMessage());
            // Mostra qualquer erro que acontecer.
        }

        return livros;
        // Retorna a lista (pode estar vazia).
    }

    public Livro buscarPorId(int id) {
        // Busca um livro específico pelo ID.

        String sql = "SELECT id, isbn, titulo, autor, cdd, cdu, disponivel FROM livro WHERE id = ?";
        // Query SQL com parâmetro para filtrar pelo ID.

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            // Substitui o ? da query pelo ID recebido.

            try (ResultSet rs = stmt.executeQuery()) {
                // Executa a consulta.

                if (rs.next()) {
                    // Se encontrou o livro...

                    Livro l = new Livro();
                    // Cria um livro vazio.

                    l.setId(rs.getInt("id"));                  // Preenche o ID
                    l.setIsbn(rs.getString("isbn"));           // Preenche ISBN
                    l.setTitulo(rs.getString("titulo"));       // Preenche título
                    l.setAutor(rs.getString("autor"));         // Preenche autor
                    l.setCdd(rs.getString("cdd"));             // Preenche CDD
                    l.setCdu(rs.getString("cdu"));             // Preenche CDU
                    l.setDisponivel(rs.getInt("disponivel") == 1); // Disponível?

                    return l;
                    // Retorna o livro encontrado.
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar livro por ID: " + e.getMessage());
        }

        return null;
        // Se não encontrou o livro, retorna null.
    }

    public List<Livro> buscarPorAutor(String autor) {
        // Busca todos os livros cujo nome do autor contenha o texto informado.

        List<Livro> livros = new ArrayList<>();
        // Lista onde os livros encontrados serão armazenados.

        String sql = "SELECT id, isbn, titulo, autor, cdd, cdu, disponivel FROM livro WHERE autor LIKE ? ORDER BY titulo";
        // Query SQL usando LIKE para busca parcial.

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + autor + "%");
            // Preenche o ? com o texto do autor entre % (busca contendo).

            try (ResultSet rs = stmt.executeQuery()) {
                // Executa e percorre os resultados.

                while (rs.next()) {
                    Livro l = new Livro();

                    l.setId(rs.getInt("id"));
                    l.setIsbn(rs.getString("isbn"));
                    l.setTitulo(rs.getString("titulo"));
                    l.setAutor(rs.getString("autor"));
                    l.setCdd(rs.getString("cdd"));
                    l.setCdu(rs.getString("cdu"));
                    l.setDisponivel(rs.getInt("disponivel") == 1);

                    livros.add(l);
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar livros por autor: " + e.getMessage());
        }

        return livros;
        // Retorna a lista de livros encontrados.
    }

    public boolean atualizar(Livro livro) {
        // Atualiza os dados de um livro já existente no banco.

        String sql = "UPDATE livro SET isbn = ?, titulo = ?, autor = ?, cdd = ?, cdu = ?, disponivel = ? WHERE id = ?";
        // Query SQL de atualização.

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, livro.getIsbn());
            stmt.setString(2, livro.getTitulo());
            stmt.setString(3, livro.getAutor());
            stmt.setString(4, livro.getCdd());
            stmt.setString(5, livro.getCdu());
            stmt.setInt(6, livro.isDisponivel() ? 1 : 0);
            stmt.setInt(7, livro.getId());

            int affected = stmt.executeUpdate();
            // Executa o UPDATE e guarda quantas linhas foram alteradas.

            return affected > 0;
            // Se alterou pelo menos 1 linha, deu certo.

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar livro: " + e.getMessage());
        }

        return false;
    }

    public boolean deletar(int id) {
        // Deleta um livro pelo ID.

        String sql = "DELETE FROM livro WHERE id = ?";
        // Query SQL para excluir.

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            // Preenche o parâmetro com o ID do livro.

            int affected = stmt.executeUpdate();
            // Executa a deleção.

            return affected > 0;
            // Retorna true se deletou pelo menos uma linha.

        } catch (SQLException e) {
            System.err.println("Erro ao deletar livro: " + e.getMessage());
        }

        return false;
        // Se chegou aqui, não deletou.
    }

    public boolean atualizarDisponibilidade(int idLivro, boolean disponivel) {
        // Atualiza apenas o campo "disponivel" de um livro específico.

        String sql = "UPDATE livro SET disponivel = ? WHERE id = ?";
        // Query SQL específica para alterar apenas o status de disponibilidade.

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, disponivel ? 1 : 0);
            // Converte boolean para INTEGER (1 = true, 0 = false).

            stmt.setInt(2, idLivro);
            // Coloca o ID do livro no segundo parâmetro.

            int affected = stmt.executeUpdate();
            // Executa update e retorna quantas linhas foram afetadas.

            return affected > 0;
            // Se alterou pelo menos 1 linha, deu certo.

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar disponibilidade do livro: " + e.getMessage());
        }

        return false;
        // Deu erro → retorna false.
    }
}
