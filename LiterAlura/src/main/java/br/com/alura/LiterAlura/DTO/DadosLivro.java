package br.com.alura.LiterAlura.DTO;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DadosLivro {

    private Long id;

    @JsonAlias("title")
    private String titulo;

    @JsonAlias("languages")
    private List<String> idiomas;

    @JsonAlias("download_count")
    private int numeroDownloads;

    @JsonAlias("authors")
    private List<DadosAutor> autores;

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public List<String> getIdiomas() {
        return idiomas;
    }

    public int getNumeroDownloads() {
        return numeroDownloads;
    }

    public List<DadosAutor> getAutores() {
        return autores;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setIdiomas(List<String> idiomas) {
        this.idiomas = idiomas;
    }

    public void setNumeroDownloads(int numeroDownloads) {
        this.numeroDownloads = numeroDownloads;
    }

    public void setAutores(List<DadosAutor> autores) {
        this.autores = autores;
    }
}
