package br.com.una.sysbib.model;
// Define o pacote onde esta classe está armazenada no projeto.

import java.time.LocalDate;
// Importa a classe LocalDate, usada para representar datas sem horário.

public class Emprestimo {
    // Classe modelo (DTO/Entity) que representa um empréstimo no sistema.

    private int id;
    // Identificador único do empréstimo (chave primária).

    private int idLivro;
    // ID do livro emprestado (chave estrangeira que aponta para a tabela livro).

    private int idUsuario;
    // ID do usuário que emprestou o livro.

    private String tituloLivro;
    // Nome do livro no momento do empréstimo (salvo para evitar consultas complexas).

    private String nomeUsuario;
    // Nome do usuário que fez o empréstimo.

    private LocalDate dataEmprestimo;
    // Data em que o empréstimo foi realizado.

    private LocalDate dataDevolucaoPrevista;
    // Data limite para devolver o livro.

    private LocalDate dataDevolucaoReal; 
    // Data real de devolução. Pode ser null enquanto o livro ainda estiver emprestado.

    private double multaAtrasoDia;
    // Valor cobrado por cada dia de atraso (padrão 2.50).

    private double multaDano;
    // Valor fixo da multa caso o livro volte danificado (padrão 100.00).

    private double valorMultaTotal;
    // Valor final da multa calculada quando o empréstimo é encerrado.

    public Emprestimo() {
        // Construtor padrão (obrigatório para bibliotecas, DAO e frameworks).
    }

    public Emprestimo(
            int id, int idLivro, int idUsuario,
            String tituloLivro, String nomeUsuario,
            LocalDate dataEmprestimo, LocalDate dataDevolucaoPrevista,
            LocalDate dataDevolucaoReal, double multaAtrasoDia,
            double multaDano, double valorMultaTotal) {

        // Construtor completo, inicializa todos os atributos da classe.

        this.id = id;                                 // seta o ID do empréstimo
        this.idLivro = idLivro;                       // seta o ID do livro
        this.idUsuario = idUsuario;                   // seta o ID do usuário
        this.tituloLivro = tituloLivro;               // título do livro
        this.nomeUsuario = nomeUsuario;               // nome do usuário
        this.dataEmprestimo = dataEmprestimo;         // data que pegou o livro
        this.dataDevolucaoPrevista = dataDevolucaoPrevista; // prazo de entrega
        this.dataDevolucaoReal = dataDevolucaoReal;   // data real da entrega
        this.multaAtrasoDia = multaAtrasoDia;         // multa por atraso/dia
        this.multaDano = multaDano;                   // multa por dano
        this.valorMultaTotal = valorMultaTotal;       // valor final da multa
    }

    // ---------------------------------------------------------------
    // GETTERS (retornam valores)
    // ---------------------------------------------------------------

    public int getId() {
        return id; // retorna o ID do empréstimo
    }

    public int getIdLivro() {
        return idLivro; // retorna o ID do livro
    }

    public int getIdUsuario() {
        return idUsuario; // retorna ID do usuário
    }

    public String getTituloLivro() {
        return tituloLivro; // retorna título do livro
    }

    public String getNomeUsuario() {
        return nomeUsuario; // retorna nome do usuário
    }

    public LocalDate getDataEmprestimo() {
        return dataEmprestimo; // retorna data do empréstimo
    }

    public LocalDate getDataDevolucaoPrevista() {
        return dataDevolucaoPrevista; // retorna data do prazo
    }

    public LocalDate getDataDevolucaoReal() {
        return dataDevolucaoReal; // retorna data real da devolução
    }

    public double getMultaAtrasoDia() {
        return multaAtrasoDia; // retorna valor da multa diária
    }

    public double getMultaDano() {
        return multaDano; // retorna multa por dano
    }

    public double getValorMultaTotal() {
        return valorMultaTotal; // retorna valor total da multa
    }

    // ---------------------------------------------------------------
    // SETTERS (alteram valores)
    // ---------------------------------------------------------------

    public void setId(int id) {
        this.id = id; // define o ID
    }

    public void setIdLivro(int idLivro) {
        this.idLivro = idLivro; // define o ID do livro
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario; // define o ID do usuário
    }

    public void setTituloLivro(String tituloLivro) {
        this.tituloLivro = tituloLivro; // define título do livro
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario; // define nome do usuário
    }

    public void setDataEmprestimo(LocalDate dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo; // define data do empréstimo
    }

    public void setDataDevolucaoPrevista(LocalDate dataDevolucaoPrevista) {
        this.dataDevolucaoPrevista = dataDevolucaoPrevista; // define prazo
    }

    public void setDataDevolucaoReal(LocalDate dataDevolucaoReal) {
        this.dataDevolucaoReal = dataDevolucaoReal; // define data da devolução
    }

    public void setMultaAtrasoDia(double multaAtrasoDia) {
        this.multaAtrasoDia = multaAtrasoDia; // define multa por atraso
    }

    public void setMultaDano(double multaDano) {
        this.multaDano = multaDano; // define multa por dano
    }

    public void setValorMultaTotal(double valorMultaTotal) {
        this.valorMultaTotal = valorMultaTotal; // define multa total
    }

    // ----------------------------------------------------------
    // toString() — representa o objeto como texto
    // ----------------------------------------------------------

    @Override
    public String toString() {
        // Método herdado de Object e sobrescrito aqui.
        // Ele monta uma string com todos os atributos do objeto,
        // o que é extremamente útil para debug, logs e prints no console.

        return "Emprestimo{" +                 // Início da representação textual
                "id=" + id +                   // Mostra o id do empréstimo
                ", idLivro=" + idLivro +       // Mostra id do livro
                ", idUsuario=" + idUsuario +   // Mostra id do usuário
                ", tituloLivro='" + tituloLivro + '\'' +   // Adiciona o título do livro
                ", nomeUsuario='" + nomeUsuario + '\'' +   // Adiciona nome do usuário
                ", dataEmprestimo=" + dataEmprestimo +     // Data do empréstimo
                ", dataDevolucaoPrevista=" + dataDevolucaoPrevista + // Data prevista
                ", dataDevolucaoReal=" + dataDevolucaoReal +         // Data real (pode ser null)
                ", multaAtrasoDia=" + multaAtrasoDia +               // Multa diaria
                ", multaDano=" + multaDano +                         // Multa por dano
                ", valorMultaTotal=" + valorMultaTotal +             // Valor final da multa
                '}';                                      // Fecha a string do objeto
    }
}