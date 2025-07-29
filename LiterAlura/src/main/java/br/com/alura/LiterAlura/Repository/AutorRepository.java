package br.com.alura.LiterAlura.Repository;

import br.com.alura.LiterAlura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    Optional<Autor> findByNomeContainingIgnoreCase(String nome);
}

