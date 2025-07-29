package br.com.alura.LiterAlura.Service;

import br.com.alura.LiterAlura.DTO.DadosResultado;
import br.com.alura.LiterAlura.model.Autor;
import br.com.alura.LiterAlura.model.Livro;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class ConsumoApi implements CommandLineRunner {

    @Autowired
    private LivroService livroService;

    private static final String URL_BASE = "https://gutendex.com/books/?page=";

    @Override
    public void run(String... args) {
        int pagina = 1;
        boolean continuar = true;

        while (continuar) {
            try {
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(URL_BASE + pagina))
                        .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                ObjectMapper mapper = new ObjectMapper();
                DadosResultado resultado = mapper.readValue(response.body(), DadosResultado.class);

                if (resultado.getResults().isEmpty()) {
                    continuar = false;
                    break;
                }

                resultado.getResults().forEach(dadosLivro -> {
                    try {

                        if (livroService.livroJaCadastrado(dadosLivro.getTitulo())) {
                            return;
                        }


                        if (dadosLivro.getAutores() == null || dadosLivro.getAutores().isEmpty()) {
                            System.out.println("Livro ignorado (sem autor): " + dadosLivro.getTitulo());
                            return;
                        }

                        Autor autor = livroService.buscarOuSalvarAutor(
                                new Autor(dadosLivro.getAutores().get(0))
                        );

                        Livro livro = new Livro(dadosLivro);
                        livro.setAutor(autor);
                        livroService.salvarLivro(livro);

                    } catch (Exception e) {
                        System.out.println("Erro ao salvar livro: " + dadosLivro.getTitulo() + " - " + e.getMessage());
                    }
                });

                pagina++;

            } catch (Exception e) {
                System.out.println("Erro ao consumir página " + pagina + ": " + e.getMessage());
                continuar = false;
            }
        }
        System.out.println("Importação finalizada.");
    }
}



