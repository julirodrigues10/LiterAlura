package br.com.alura.LiterAlura.model;

import br.com.alura.LiterAlura.DTO.DadosLivro;
import jakarta.persistence.*;

@Entity
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String idioma;
    private Integer numeroDownloads;

    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Autor autor;

    public Livro() {
    }

    public Livro(DadosLivro dados) {
        this.titulo = dados.getTitulo();
        this.idioma = (dados.getIdiomas() != null && !dados.getIdiomas().isEmpty())
                ? dados.getIdiomas().get(0)
                : "desconhecido";
        this.numeroDownloads = dados.getNumeroDownloads();
    }


    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Integer getNumeroDownloads() {
        return numeroDownloads;
    }

    public void setNumeroDownloads(Integer numeroDownloads) {
        this.numeroDownloads = numeroDownloads;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    @Override
    public String toString() {
        return "Livro: " + titulo +
                "\nIdioma: " + idioma +
                "\nDownloads: " + numeroDownloads +
                "\nAutor: " + (autor != null ? autor.getNome() : "Desconhecido") +
                "\n---------------------------";
    }
}
