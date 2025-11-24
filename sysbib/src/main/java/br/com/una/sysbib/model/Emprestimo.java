package br.com.una.sysbib.model;

import java.time.LocalDate;

public class Emprestimo {

    private int id;
    private int idLivro;
    private int idUsuario;

    private String tituloLivro;
    private String nomeUsuario;

    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucaoPrevista;
    private LocalDate dataDevolucaoReal; // pode ser null enquanto não devolvido

    private double multaAtrasoDia;  // normalmente 2.50
    private double multaDano;       // normalmente 100.00
    private double valorMultaTotal; // calculado na devolução

    public Emprestimo() {
    }

    public Emprestimo(int id, int idLivro, int idUsuario, String tituloLivro, String nomeUsuario,
                      LocalDate dataEmprestimo, LocalDate dataDevolucaoPrevista,
                      LocalDate dataDevolucaoReal, double multaAtrasoDia, double multaDano,
                      double valorMultaTotal) {
        this.id = id;
        this.idLivro = idLivro;
        this.idUsuario = idUsuario;
        this.tituloLivro = tituloLivro;
        this.nomeUsuario = nomeUsuario;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucaoPrevista = dataDevolucaoPrevista;
        this.dataDevolucaoReal = dataDevolucaoReal;
        this.multaAtrasoDia = multaAtrasoDia;
        this.multaDano = multaDano;
        this.valorMultaTotal = valorMultaTotal;
    }

    // Getters e Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdLivro() {
        return idLivro;
    }

    public void setIdLivro(int idLivro) {
        this.idLivro = idLivro;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getTituloLivro() {
        return tituloLivro;
    }

    public void setTituloLivro(String tituloLivro) {
        this.tituloLivro = tituloLivro;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public LocalDate getDataEmprestimo() {
        return dataEmprestimo;
    }

    public void setDataEmprestimo(LocalDate dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public LocalDate getDataDevolucaoPrevista() {
        return dataDevolucaoPrevista;
    }

    public void setDataDevolucaoPrevista(LocalDate dataDevolucaoPrevista) {
        this.dataDevolucaoPrevista = dataDevolucaoPrevista;
    }

    public LocalDate getDataDevolucaoReal() {
        return dataDevolucaoReal;
    }

    public void setDataDevolucaoReal(LocalDate dataDevolucaoReal) {
        this.dataDevolucaoReal = dataDevolucaoReal;
    }

    public double getMultaAtrasoDia() {
        return multaAtrasoDia;
    }

    public void setMultaAtrasoDia(double multaAtrasoDia) {
        this.multaAtrasoDia = multaAtrasoDia;
    }

    public double getMultaDano() {
        return multaDano;
    }

    public void setMultaDano(double multaDano) {
        this.multaDano = multaDano;
    }

    public double getValorMultaTotal() {
        return valorMultaTotal;
    }

    public void setValorMultaTotal(double valorMultaTotal) {
        this.valorMultaTotal = valorMultaTotal;
    }

    @Override
    public String toString() {
        return "Emprestimo{" +
                "id=" + id +
                ", idLivro=" + idLivro +
                ", idUsuario=" + idUsuario +
                ", tituloLivro='" + tituloLivro + '\'' +
                ", nomeUsuario='" + nomeUsuario + '\'' +
                ", dataEmprestimo=" + dataEmprestimo +
                ", dataDevolucaoPrevista=" + dataDevolucaoPrevista +
                ", dataDevolucaoReal=" + dataDevolucaoReal +
                ", multaAtrasoDia=" + multaAtrasoDia +
                ", multaDano=" + multaDano +
                ", valorMultaTotal=" + valorMultaTotal +
                '}';
    }
}
