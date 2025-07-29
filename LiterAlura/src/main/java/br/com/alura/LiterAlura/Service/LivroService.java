package br.com.alura.LiterAlura.Service;

import br.com.alura.LiterAlura.DTO.DadosAutor;
import br.com.alura.LiterAlura.DTO.DadosLivro;
import br.com.alura.LiterAlura.DTO.DadosResultado;
import br.com.alura.LiterAlura.Repository.AutorRepository;
import br.com.alura.LiterAlura.Repository.LivroRepository;
import br.com.alura.LiterAlura.model.Autor;
import br.com.alura.LiterAlura.model.Livro;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class LivroService {

    private final String ENDERECO = "https://gutendex.com/books/?search=";

    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;

    public LivroService(LivroRepository livroRepository, AutorRepository autorRepository) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
    }

    public void buscarSalvarLivroPorTitulo(String titulo) {
        try {
            String tituloEncoded = URLEncoder.encode(titulo, StandardCharsets.UTF_8);
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(ENDERECO + tituloEncoded))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper mapper = new ObjectMapper();
            DadosResultado resultado = mapper.readValue(response.body(), DadosResultado.class);

            if (resultado.getResults().isEmpty()) {
                System.out.println(" Livro não encontrado na API: " + titulo);
                return;
            }

            DadosLivro dados = resultado.getResults().get(0);

            if (dados.getAutores() == null || dados.getAutores().isEmpty()) {
                System.out.println(" Livro ignorado (sem autor): " + dados.getTitulo());
                return;
            }

            DadosAutor dadosAutor = dados.getAutores().get(0);
            Autor autor = buscarOuSalvarAutor(new Autor(dadosAutor));

            if (livroJaCadastrado(dados.getTitulo())) {
                System.out.println(" Livro já cadastrado: " + dados.getTitulo());
                return;
            }

            Livro livro = new Livro(dados);
            livro.setAutor(autor);
            salvarLivro(livro);

            System.out.println(" Livro salvo com sucesso: " + livro.getTitulo());

        } catch (IOException | InterruptedException e) {
            System.out.println("Erro ao buscar livro: " + e.getMessage());
        }
    }

    public void salvarLivro(Livro livro) {
        livroRepository.save(livro);
    }

    public boolean livroJaCadastrado(String titulo) {
        return livroRepository.existsByTituloContainingIgnoreCase(titulo);
    }

    public Autor buscarOuSalvarAutor(Autor autor) {
        return autorRepository.findByNomeContainingIgnoreCase(autor.getNome())
                .orElseGet(() -> autorRepository.save(autor));
    }

    public void listarLivros() {
        List<Livro> livros = livroRepository.findAll();
        if (livros.isEmpty()) {
            System.out.println(" Nenhum livro encontrado.");
        } else {
            livros.forEach(System.out::println);
        }
    }

    public void listarAutores() {
        List<Autor> autores = autorRepository.findAll();
        if (autores.isEmpty()) {
            System.out.println(" Nenhum autor encontrado.");
        } else {
            autores.forEach(System.out::println);
        }
    }

    public void listarAutoresVivosEm(int ano) {
        List<Autor> autores = autorRepository.findAll().stream()
                .filter(a -> a.getAnoNascimento() != null && a.getAnoNascimento() <= ano)
                .filter(a -> a.getAnoFalecimento() == null || a.getAnoFalecimento() >= ano)
                .toList();

        if (autores.isEmpty()) {
            System.out.println("Nenhum autor vivo encontrado no ano " + ano + ".");
        } else {
            autores.forEach(System.out::println);
        }
    }

    public void listarLivrosPorIdioma(String idioma) {
        List<Livro> livros = livroRepository.findByIdiomaIgnoreCase(idioma);
        if (livros.isEmpty()) {
            System.out.println(" Nenhum livro encontrado para o idioma: " + idioma);
        } else {
            livros.forEach(System.out::println);
        }
    }
}
