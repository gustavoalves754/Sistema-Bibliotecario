package br.com.una.sysbib.dao;

import br.com.una.sysbib.model.Livro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LivroDAO {

    public boolean inserir(Livro livro) {
        String sql = "INSERT INTO livro (isbn, titulo, autor, cdd, cdu, disponivel) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, livro.getIsbn());
            stmt.setString(2, livro.getTitulo());
            stmt.setString(3, livro.getAutor());
            stmt.setString(4, livro.getCdd());
            stmt.setString(5, livro.getCdu());
            stmt.setInt(6, livro.isDisponivel() ? 1 : 0);

            int affected = stmt.executeUpdate();
            if (affected > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        livro.setId(rs.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao inserir livro: " + e.getMessage());
        }
        return false;
    }

    public List<Livro> listarTodos() {
        List<Livro> livros = new ArrayList<>();
        String sql = "SELECT id, isbn, titulo, autor, cdd, cdu, disponivel FROM livro ORDER BY titulo";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

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

        } catch (SQLException e) {
            System.err.println("Erro ao listar livros: " + e.getMessage());
        }

        return livros;
    }

    public Livro buscarPorId(int id) {
        String sql = "SELECT id, isbn, titulo, autor, cdd, cdu, disponivel FROM livro WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Livro l = new Livro();
                    l.setId(rs.getInt("id"));
                    l.setIsbn(rs.getString("isbn"));
                    l.setTitulo(rs.getString("titulo"));
                    l.setAutor(rs.getString("autor"));
                    l.setCdd(rs.getString("cdd"));
                    l.setCdu(rs.getString("cdu"));
                    l.setDisponivel(rs.getInt("disponivel") == 1);
                    return l;
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar livro por ID: " + e.getMessage());
        }
        return null;
    }

    public List<Livro> buscarPorAutor(String autor) {
        List<Livro> livros = new ArrayList<>();
        String sql = "SELECT id, isbn, titulo, autor, cdd, cdu, disponivel FROM livro WHERE autor LIKE ? ORDER BY titulo";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + autor + "%");

            try (ResultSet rs = stmt.executeQuery()) {
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
    }

    public boolean atualizar(Livro livro) {
        String sql = "UPDATE livro SET isbn = ?, titulo = ?, autor = ?, cdd = ?, cdu = ?, disponivel = ? WHERE id = ?";
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
            return affected > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar livro: " + e.getMessage());
        }
        return false;
    }

    public boolean deletar(int id) {
        String sql = "DELETE FROM livro WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int affected = stmt.executeUpdate();
            return affected > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao deletar livro: " + e.getMessage());
        }
        return false;
    }

    public boolean atualizarDisponibilidade(int idLivro, boolean disponivel) {
        String sql = "UPDATE livro SET disponivel = ? WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, disponivel ? 1 : 0);
            stmt.setInt(2, idLivro);

            int affected = stmt.executeUpdate();
            return affected > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar disponibilidade do livro: " + e.getMessage());
        }
        return false;
    }
}
