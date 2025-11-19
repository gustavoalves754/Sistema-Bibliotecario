package br.com.una.sysbib.dao; // Pacote onde esta classe está localizada

import java.io.BufferedReader; // Usado para ler o arquivo SQL linha a linha
import java.io.InputStream; // Representa o arquivo schema.sql como fluxo de dados
import java.io.InputStreamReader; // Converte o InputStream em texto legível
import java.sql.Connection; // Objeto que representa a conexão com o banco
import java.sql.DriverManager; // Classe usada para abrir conexão JDBC
import java.sql.Statement; // Usado para executar comandos SQL
import java.util.stream.Collectors; // Usado para juntar várias linhas em uma única String

// Classe responsável pela conexão com o banco SQLite
public class Conexao {

    // Caminho da base SQLite — o arquivo biblioteca.db será criado na pasta do projeto
    private static final String URL = "jdbc:sqlite:biblioteca.db";

    // A conexão será armazenada aqui para reutilização (padrão Singleton simples)
    private static Connection connection = null;

    // Método para obter uma conexão ativa
    public static Connection getConnection() {
        // Se a conexão ainda não foi criada, cria agora
        if (connection == null) {
            try {
                Class.forName("org.sqlite.JDBC"); // Carrega o driver JDBC do SQLite
                connection = DriverManager.getConnection(URL); // Abre a conexão
                System.out.println("Conexão com o banco de dados estabelecida.");
            } catch (Exception e) {
                System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage()); // Mensagem de erro
            }
        }
        return connection; // Retorna a conexão (nova ou existente)
    }

    // Método para fechar uma conexão específica (não é usado normalmente)
    public static void closeConnection(Connection conn) {
        if (conn != null) { // Verifica se existe conexão
            try {
                conn.close(); // Fecha a conexão
            } catch (Exception e) {
                System.err.println("Erro ao fechar conexão: " + e.getMessage()); // Erro ao fechar
            }
        }
    }

    // Método que lê o arquivo schema.sql e cria as tabelas do banco
    public static void inicializarBanco() {
        try (Statement stmt = getConnection().createStatement()) { // Cria comando SQL usando a conexão ativa

            // Carrega o arquivo schema.sql da pasta resources
            InputStream is = Conexao.class.getClassLoader().getResourceAsStream("schema.sql");

            // Se não encontrar o arquivo, exibe erro
            if (is == null) {
                System.err.println("Erro: Arquivo schema.sql não encontrado na pasta resources.");
                return;
            }

            // Lê o arquivo inteiro e converte para uma String
            String sql = new BufferedReader(new InputStreamReader(is))
                    .lines().collect(Collectors.joining("\n"));

            // Executa o script SQL (criação das tabelas)
            stmt.executeUpdate(sql);
            System.out.println("Estrutura do banco de dados inicializada com sucesso.");

        } catch (Exception e) {
            System.err.println("Erro ao inicializar o banco de dados: " + e.getMessage()); // Se der erro, exibe

            // Fecha a conexão se houver erro grave
            closeConnection(connection);
        }
    }
}
