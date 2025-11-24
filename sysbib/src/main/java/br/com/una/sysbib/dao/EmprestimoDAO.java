package br.com.una.sysbib.dao;

import br.com.una.sysbib.model.Emprestimo;

import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class EmprestimoDAO {

    private Emprestimo map(ResultSet rs) throws Exception {
        Emprestimo e = new Emprestimo();

        e.setId(rs.getInt("id"));
        e.setIdLivro(rs.getInt("id_livro"));
        e.setIdUsuario(rs.getInt("id_usuario"));
        e.setTituloLivro(rs.getString("titulo_livro"));
        e.setNomeUsuario(rs.getString("nome_usuario"));

        e.setDataEmprestimo(LocalDate.parse(rs.getString("data_emprestimo")));
        e.setDataDevolucaoPrevista(LocalDate.parse(rs.getString("data_devolucao_prevista")));

        String real = rs.getString("data_devolucao_real");
        if (real != null) e.setDataDevolucaoReal(LocalDate.parse(real));

        e.setMultaAtrasoDia(rs.getDouble("multa_atraso_dia"));
        e.setMultaDano(rs.getDouble("multa_dano"));
        e.setValorMultaTotal(rs.getDouble("valor_multa_total"));

        return e;
    }

    public boolean registrarEmprestimo(Emprestimo e) {
        String sql = """
                INSERT INTO emprestimo (
                 id_livro, id_usuario, titulo_livro, nome_usuario,
                 data_emprestimo, data_devolucao_prevista,
                 multa_atraso_dia, multa_dano, valor_multa_total
                ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, e.getIdLivro());
            stmt.setInt(2, e.getIdUsuario());
            stmt.setString(3, e.getTituloLivro());
            stmt.setString(4, e.getNomeUsuario());
            stmt.setString(5, e.getDataEmprestimo().toString());
            stmt.setString(6, e.getDataDevolucaoPrevista().toString());
            stmt.setDouble(7, e.getMultaAtrasoDia());
            stmt.setDouble(8, e.getMultaDano());
            stmt.setDouble(9, e.getValorMultaTotal());

            if (stmt.executeUpdate() > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) e.setId(rs.getInt(1));
                return true;
            }

        } catch (Exception ex) {
            System.err.println("Erro ao registrar empréstimo: " + ex.getMessage());
        }
        return false;
    }

    public Emprestimo buscarPorId(int id) {
        String sql = "SELECT * FROM emprestimo WHERE id = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return map(rs);

        } catch (Exception ex) {
            System.err.println("Erro ao buscar empréstimo: " + ex.getMessage());
        }
        return null;
    }

    public List<Emprestimo> listarAtivos() {
        List<Emprestimo> lista = new ArrayList<>();
        String sql = "SELECT * FROM emprestimo WHERE data_devolucao_real IS NULL ORDER BY data_emprestimo";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) lista.add(map(rs));

        } catch (Exception ex) {
            System.err.println("Erro ao listar ativos: " + ex.getMessage());
        }

        return lista;
    }

    public List<Emprestimo> listarTodos() {
        List<Emprestimo> lista = new ArrayList<>();
        String sql = "SELECT * FROM emprestimo ORDER BY id DESC";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) lista.add(map(rs));

        } catch (Exception ex) {
            System.err.println("Erro ao listar empréstimos: " + ex.getMessage());
        }

        return lista;
    }

    public boolean deletar(int id) {
        String sql = "DELETE FROM emprestimo WHERE id = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (Exception ex) {
            System.err.println("Erro ao deletar empréstimo: " + ex.getMessage());
        }

        return false;
    }

    public boolean finalizarEmprestimo(int id, LocalDate dataReal, boolean dano) {
        Emprestimo e = buscarPorId(id);
        if (e == null) return false;

        long diasAtraso = 0;

        if (dataReal.isAfter(e.getDataDevolucaoPrevista())) {
            diasAtraso = ChronoUnit.DAYS.between(
                    e.getDataDevolucaoPrevista(), dataReal
            );
        }

        double multa = diasAtraso * e.getMultaAtrasoDia();
        if (dano) multa += e.getMultaDano();

        e.setValorMultaTotal(multa);

        String sql = "UPDATE emprestimo SET data_devolucao_real = ?, valor_multa_total = ? WHERE id = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, dataReal.toString());
            stmt.setDouble(2, multa);
            stmt.setInt(3, id);

            return stmt.executeUpdate() > 0;

        } catch (Exception ex) {
            System.err.println("Erro ao finalizar empréstimo: " + ex.getMessage());
        }

        return false;
    }
}
