package br.com.alura.LiterAlura.Repository;

import br.com.alura.LiterAlura.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LivroRepository extends JpaRepository<Livro, Long> {
    boolean existsByTituloContainingIgnoreCase(String titulo);

    List<Livro> findByIdiomaIgnoreCase(String idioma);
}



