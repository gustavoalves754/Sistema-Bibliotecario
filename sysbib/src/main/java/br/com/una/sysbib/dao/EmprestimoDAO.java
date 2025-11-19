package br.com.una.sysbib.dao; // Pacote onde a classe está localizada

import br.com.una.sysbib.model.Emprestimo; // Importa o modelo Emprestimo
import java.sql.Connection; // Representa a conexão com o banco
import java.sql.PreparedStatement; // Usado para comandos SQL com parâmetros
import java.sql.ResultSet; // Resultado de consultas SELECT
import java.sql.Statement; // Usado para executar SQL simples
import java.util.ArrayList; // Lista dinâmica
import java.util.List; // Interface de lista

// Classe responsável por salvar, buscar e deletar empréstimos no banco
public class EmprestimoDAO {

    // =============================================================
    // MÉTODO: registrar
    // Insere um novo empréstimo no banco
    // =============================================================
    public boolean registrar(Emprestimo emprestimo) {

        // SQL para inserir um empréstimo
        String sql = "INSERT INTO emprestimo (id_livro, id_usuario, data_emprestimo, data_devolucao_prevista) VALUES (?, ?, ?, ?)";

        // Obtém a conexão (mantida aberta pelo projeto)
        Connection conn = Conexao.getConnection();

        // try-with-resources garante fechamento automático do PreparedStatement
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Preenche os parâmetros da query
            stmt.setInt(1, emprestimo.getIdLivro()); // ID do livro
            stmt.setInt(2, emprestimo.getIdUsuario()); // ID do usuário
            stmt.setString(3, emprestimo.getDataEmprestimo()); // data do empréstimo
            stmt.setString(4, emprestimo.getDataDevolucaoPrevista()); // devolução prevista

            // Executa a inserção e verifica quantas linhas foram afetadas
            int affectedRows = stmt.executeUpdate();

            // Se inseriu pelo menos uma linha, tenta recuperar o ID gerado
            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) { // pega o ID gerado
                    if (rs.next()) {
                        emprestimo.setId(rs.getInt(1)); // define o ID no objeto
                        return true; // inserção OK
                    }
                }
            }

        } catch (Exception e) {
            System.err.println("Erro ao registrar empréstimo: " + e.getMessage());
        }
        return false; // Se chegou aqui, deu errado
    }


    // =============================================================
    // MÉTODO: buscarTodos
    // Retorna uma lista com todos os empréstimos registrados
    // =============================================================
    public List<Emprestimo> buscarTodos() {
        List<Emprestimo> emprestimos = new ArrayList<>(); // Lista onde serão armazenados os empréstimos

        // SQL para buscar todos os empréstimos
        String sql = "SELECT id, id_livro, id_usuario, data_emprestimo, data_devolucao_prevista FROM emprestimo";

        Connection conn = Conexao.getConnection(); // Obtém a conexão

        // try-with-resources para Statement e ResultSet
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // Percorre cada linha retornada pela consulta
            while (rs.next()) {

                // Cria um objeto Emprestimo preenchendo com dados da linha atual
                Emprestimo e = new Emprestimo(
                    rs.getInt("id"),
                    rs.getInt("id_livro"),
                    rs.getInt("id_usuario"),
                    rs.getString("data_emprestimo"),
                    rs.getString("data_devolucao_prevista")
                );

                emprestimos.add(e); // Adiciona na lista
            }

        } catch (Exception e) {
            System.err.println("Erro ao buscar empréstimos: " + e.getMessage());
        }
        return emprestimos; // Retorna a lista (pode estar vazia)
    }


    // =============================================================
    // MÉTODO: deletar
    // Remove um empréstimo do banco (devolução do livro)
    // =============================================================
    public boolean deletar(int idEmprestimo) {

        // SQL para deletar um empréstimo
        String sql = "DELETE FROM emprestimo WHERE id = ?";

        Connection conn = Conexao.getConnection(); // Obtém a conexão

        // PreparedStatement com try-with-resources
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idEmprestimo); // Define qual empréstimo apagar

            // Se deletou pelo menos 1 linha, retorno será true
            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            System.err.println("Erro ao deletar empréstimo: " + e.getMessage());
            return false; // Deu erro
        }
    }
}
