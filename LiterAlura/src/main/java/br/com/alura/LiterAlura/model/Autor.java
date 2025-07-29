package br.com.alura.LiterAlura.model;

import br.com.alura.LiterAlura.DTO.DadosAutor;
import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private Integer anoNascimento;
    private Integer anoFalecimento;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Livro> livros;

    public Autor() {
    }

    public Autor(DadosAutor dados) {
        this.nome = dados.getNome() != null ? dados.getNome() : "Autor Desconhecido";
        this.anoNascimento = dados.getAnoNascimento();
        this.anoFalecimento = dados.getAnoFalecimento();
    }

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getAnoNascimento() {
        return anoNascimento;
    }

    public void setAnoNascimento(Integer anoNascimento) {
        this.anoNascimento = anoNascimento;
    }

    public Integer getAnoFalecimento() {
        return anoFalecimento;
    }

    public void setAnoFalecimento(Integer anoFalecimento) {
        this.anoFalecimento = anoFalecimento;
    }

    public List<Livro> getLivros() {
        return livros;
    }

    public void setLivros(List<Livro> livros) {
        this.livros = livros;
    }

    @Override
    public String toString() {
        return "Autor: " + nome +
                (anoNascimento != null ? " | Nascimento: " + anoNascimento : "") +
                (anoFalecimento != null ? " | Falecimento: " + anoFalecimento : "") +
                "\n---------------------------";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Autor autor)) return false;
        return nome != null && autor.nome != null &&
                nome.equalsIgnoreCase(autor.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome != null ? nome.toLowerCase() : "");
    }
}
