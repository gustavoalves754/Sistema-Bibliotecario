package br.com.una.sysbib.dao;
// Define o pacote onde esta classe DAO está armazenada.

import br.com.una.sysbib.model.Emprestimo;
// Importa a classe Emprestimo para criar objetos a partir do banco de dados.

import java.sql.*;
// Importa tudo necessário para trabalhar com JDBC (Connection, PreparedStatement, ResultSet...).

import java.time.LocalDate;
// Importa LocalDate para trabalhar com datas.

import java.time.temporal.ChronoUnit;
// Importa ChronoUnit para calcular diferença entre datas (dias de atraso).

import java.util.ArrayList;
import java.util.List;
// Listas para retornar vários empréstimos.

public class EmprestimoDAO {
    // Classe responsável por todas as operações CRUD da tabela 'emprestimo'.

    private Emprestimo map(ResultSet rs) throws Exception {
        // Método privado que converte (mapeia) uma linha do banco em um objeto Emprestimo.
        // Evita repetição de código nos métodos listar, buscar etc.

        Emprestimo e = new Emprestimo();
        // Cria um empréstimo vazio.

        e.setId(rs.getInt("id"));
        // Pega o ID do empréstimo no banco.

        e.setIdLivro(rs.getInt("id_livro"));
        // Pega o ID do livro.

        e.setIdUsuario(rs.getInt("id_usuario"));
        // Pega o ID do usuário.

        e.setTituloLivro(rs.getString("titulo_livro"));
        // Título salvo no momento do empréstimo.

        e.setNomeUsuario(rs.getString("nome_usuario"));
        // Nome salvo no momento do empréstimo.

        e.setDataEmprestimo(LocalDate.parse(rs.getString("data_emprestimo")));
        // Converte texto do banco (string) para LocalDate.

        e.setDataDevolucaoPrevista(LocalDate.parse(rs.getString("data_devolucao_prevista")));
        // Data prevista convertida para LocalDate.

        String real = rs.getString("data_devolucao_real");
        // Obtém a data real (pode ser null).

        if (real != null) e.setDataDevolucaoReal(LocalDate.parse(real));
        // Se existir, converte para LocalDate.

        e.setMultaAtrasoDia(rs.getDouble("multa_atraso_dia"));
        // Valor da multa diária.

        e.setMultaDano(rs.getDouble("multa_dano"));
        // Valor fixo da multa por dano.

        e.setValorMultaTotal(rs.getDouble("valor_multa_total"));
        // Valor total da multa já calculado.

        return e;
        // Retorna o objeto montado.
    }

    public boolean registrarEmprestimo(Emprestimo e) {
        // Registra um novo empréstimo no banco.

        String sql = """
                INSERT INTO emprestimo (
                 id_livro, id_usuario, titulo_livro, nome_usuario,
                 data_emprestimo, data_devolucao_prevista,
                 multa_atraso_dia, multa_dano, valor_multa_total
                ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;
        // SQL com texto multilinha (Java 15+). Fica mais organizado.

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
             // Abre conexão e prepara a SQL. Também permite pegar o ID criado.

            stmt.setInt(1, e.getIdLivro());
            // Define ID do livro.

            stmt.setInt(2, e.getIdUsuario());
            // Define ID do usuário.

            stmt.setString(3, e.getTituloLivro());
            // Título salvo no momento do empréstimo.

            stmt.setString(4, e.getNomeUsuario());
            // Nome salvo no momento do empréstimo.

            stmt.setString(5, e.getDataEmprestimo().toString());
            // Converte LocalDate para String.

            stmt.setString(6, e.getDataDevolucaoPrevista().toString());
            // Converte LocalDate para String.

            stmt.setDouble(7, e.getMultaAtrasoDia());
            // Valor da multa por atraso.

            stmt.setDouble(8, e.getMultaDano());
            // Valor da multa por dano.

            stmt.setDouble(9, e.getValorMultaTotal());
            // Sempre começa como 0.0 no momento do empréstimo.

            if (stmt.executeUpdate() > 0) {
                // Se inseriu 1 linha, o insert funcionou.

                ResultSet rs = stmt.getGeneratedKeys();
                // Pega o ID gerado automaticamente.

                if (rs.next()) e.setId(rs.getInt(1));
                // Define o ID no objeto.

                return true; // Sucesso
            }

        } catch (Exception ex) {
            System.err.println("Erro ao registrar empréstimo: " + ex.getMessage());
        }
        return false; // Falha
    }

    public Emprestimo buscarPorId(int id) {
        // Busca um empréstimo específico pelo ID.

        String sql = "SELECT * FROM emprestimo WHERE id = ?";
        // SQL simples com filtro.

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            // Preenche o ? com o id.

            ResultSet rs = stmt.executeQuery();
            // Executa consulta.

            if (rs.next()) return map(rs);
            // Se achou, mapeia para objeto Emprestimo.

        } catch (Exception ex) {
            System.err.println("Erro ao buscar empréstimo: " + ex.getMessage());
        }
        return null; // Não encontrado
    }

    public List<Emprestimo> listarAtivos() {
        // Lista todos os empréstimos que ainda NÃO foram devolvidos.

        List<Emprestimo> lista = new ArrayList<>();
        // Cria lista vazia.

        String sql = "SELECT * FROM emprestimo WHERE data_devolucao_real IS NULL ORDER BY data_emprestimo";
        // Consulta somente empréstimos ativos.

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) lista.add(map(rs));
            // Para cada linha → converte e adiciona na lista.

        } catch (Exception ex) {
            System.err.println("Erro ao listar ativos: " + ex.getMessage());
        }

        return lista; // Retorna todos os ativos
    }

    public List<Emprestimo> listarTodos() {
        // Lista todos os empréstimos, ativos ou finalizados.

        List<Emprestimo> lista = new ArrayList<>();

        String sql = "SELECT * FROM emprestimo ORDER BY id DESC";
        // Ordena do mais novo para o mais antigo.

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) lista.add(map(rs));
            // Converte cada linha para Emprestimo.

        } catch (Exception ex) {
            System.err.println("Erro ao listar empréstimos: " + ex.getMessage());
        }

        return lista;
    }

    public boolean deletar(int id) {
        // Remove um empréstimo do banco (geralmente usado para correções).

        String sql = "DELETE FROM emprestimo WHERE id = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            // Define o ID para deletar.

            return stmt.executeUpdate() > 0;
            // TRUE se apagou pelo menos 1 linha.

        } catch (Exception ex) {
            System.err.println("Erro ao deletar empréstimo: " + ex.getMessage());
        }

        return false;
    }

    public boolean finalizarEmprestimo(int id, LocalDate dataReal, boolean dano) {
        // Finaliza um empréstimo:
        // - Registra data real de devolução
        // - Calcula multa por atraso
        // - Soma multa por dano (se houver)

        Emprestimo e = buscarPorId(id);
        // Busca o empréstimo no banco.

        if (e == null) return false;
        // Se não existir, não pode finalizar.

        long diasAtraso = 0;
        // Variável para quantos dias passou do prazo.

        if (dataReal.isAfter(e.getDataDevolucaoPrevista())) {
            // Se devolveu DEPOIS do prazo:

            diasAtraso = ChronoUnit.DAYS.between(
                    e.getDataDevolucaoPrevista(), dataReal
            );
            // Calcula quantos dias passaram do prazo.
        }

        double multa = diasAtraso * e.getMultaAtrasoDia();
        // Multa por atraso = dias * valor da multa diária.

        if (dano) multa += e.getMultaDano();
        // Se o livro teve dano → adiciona a multa.

        e.setValorMultaTotal(multa);
        // Atualiza o objeto com a multa total calculada.

        String sql = "UPDATE emprestimo SET data_devolucao_real = ?, valor_multa_total = ? WHERE id = ?";
        // Atualiza no banco.

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, dataReal.toString());
            // Salva a data real da devolução.

            stmt.setDouble(2, multa);
            // Salva a multa total.

            stmt.setInt(3, id);
            // Qual empréstimo atualizar.

            return stmt.executeUpdate() > 0;
            // TRUE se atualização funcionou.

        } catch (Exception ex) {
            System.err.println("Erro ao finalizar empréstimo: " + ex.getMessage());
        }

        return false;
    }
}
