package br.com.una.sysbib.dao;
// Define o pacote onde esta classe está localizada

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.nio.file.Files;
import java.nio.file.Paths;
// Importações necessárias para conexão com banco e leitura de arquivos

public class Conexao {

    private static final String URL = "jdbc:sqlite:sysbib.db";
    // String de conexão JDBC, indicando que o banco usado é SQLite
    // e o arquivo do banco será "sysbib.db"

    private static boolean tabelasCriadas = false;
    // Variável de controle para evitar recriar tabelas mais de uma vez

    public static Connection getConnection() {
        // Método responsável por abrir a conexão com o banco

        try {
            Connection conn = DriverManager.getConnection(URL);
            // Abre a conexão usando a URL do SQLite

            if (!tabelasCriadas) {
                // Se as tabelas ainda não foram criadas...

                criarTabelas(conn);
                // ... chama o método que cria/verifica as tabelas

                tabelasCriadas = true;
                // Marca que as tabelas já foram criadas, para não repetir
            }

            return conn;
            // Retorna a conexão ativa

        } catch (Exception e) {
            // Se qualquer erro acontecer na conexão

            System.err.println("Erro ao conectar ao banco: " + e.getMessage());
            // Mostra o erro no console

            return null;
            // Retorna null indicando problema na conexão
        }
    }

    private static void criarTabelas(Connection conn) {
        // Método privado para ler o arquivo schema.sql e criar as tabelas

        try {
            // Caminho do schema dentro do projeto
            String path = "src/main/resources/schema.sql";
            // Caminho relativo do arquivo SQL contendo as instruções de criação

            // Lê todo o arquivo schema.sql como texto
            String sql = new String(Files.readAllBytes(Paths.get(path)));
            // Lê o arquivo inteiro e converte para String

            // Executa todas as instruções do schema
            Statement stmt = conn.createStatement();
            // Cria um objeto para executar comandos SQL

            stmt.executeUpdate(sql);
            // Executa o conteúdo do arquivo SQL no banco

            stmt.close();
            // Fecha o Statement (boa prática)

            System.out.println("Tabelas verificadas/criadas com sucesso.");
            // Confirma no console que o schema rodou

        } catch (Exception e) {
            // Caso ocorra qualquer erro na leitura do arquivo ou execução

            System.err.println("Erro ao criar tabelas: " + e.getMessage());
            // Mostra a mensagem de erro
        }
    }
}
