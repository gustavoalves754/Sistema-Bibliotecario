package br.com.una.sysbib.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Conexao {

    private static final String URL = "jdbc:sqlite:sysbib.db";
    private static boolean tabelasCriadas = false;

    public static Connection getConnection() {
        try {
            Connection conn = DriverManager.getConnection(URL);

            if (!tabelasCriadas) {
                criarTabelas(conn);
                tabelasCriadas = true;
            }

            return conn;

        } catch (Exception e) {
            System.err.println("Erro ao conectar ao banco: " + e.getMessage());
            return null;
        }
    }

    private static void criarTabelas(Connection conn) {
        try {
            // Caminho do schema dentro do projeto
            String path = "src/main/resources/schema.sql";

            // Lê todo o arquivo schema.sql como texto
            String sql = new String(Files.readAllBytes(Paths.get(path)));

            // Executa todas as instruções do schema
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();

            System.out.println("Tabelas verificadas/criadas com sucesso.");

        } catch (Exception e) {
            System.err.println("Erro ao criar tabelas: " + e.getMessage());
        }
    }
}
