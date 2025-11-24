package br.com.una.sysbib.model;

public class Livro {

    private int id;
    private String isbn;
    private String titulo;
    private String autor;
    private String cdd;
    private String cdu;
    private boolean disponivel;

    public Livro() {
    }

    public Livro(int id, String isbn, String titulo, String autor, String cdd, String cdu, boolean disponivel) {
        this.id = id;
        this.isbn = isbn;
        this.titulo = titulo;
        this.autor = autor;
        this.cdd = cdd;
        this.cdu = cdu;
        this.disponivel = disponivel;
    }

    public Livro(String isbn, String titulo, String autor, String cdd, String cdu) {
        this(0, isbn, titulo, autor, cdd, cdu, true);
    }

    // Getters e Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getCdd() {
        return cdd;
    }

    public void setCdd(String cdd) {
        this.cdd = cdd;
    }

    public String getCdu() {
        return cdu;
    }

    public void setCdu(String cdu) {
        this.cdu = cdu;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    @Override
    public String toString() {
        return "Livro{" +
                "id=" + id +
                ", isbn='" + isbn + '\'' +
                ", titulo='" + titulo + '\'' +
                ", autor='" + autor + '\'' +
                ", cdd='" + cdd + '\'' +
                ", cdu='" + cdu + '\'' +
                ", disponivel=" + disponivel +
                '}';
    }
}
