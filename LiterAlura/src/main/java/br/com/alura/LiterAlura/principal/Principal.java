package br.com.alura.LiterAlura.principal;

import br.com.alura.LiterAlura.Service.LivroService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.InputMismatchException;
import java.util.Scanner;

@Component
public class Principal implements CommandLineRunner {

    private final Scanner leitura = new Scanner(System.in);
    private final LivroService servico;

    public Principal(LivroService servico) {
        this.servico = servico;
    }

    @Override
    public void run(String... args) {
        int opcao = -1;

        while (opcao != 0) {
            try {
                System.out.println("""
                    \n--- MENU ---
                    1 - Buscar livro por título (via API)
                    2 - Listar livros
                    3 - Listar autores
                    4 - Listar autores vivos em um ano
                    5 - Listar livros por idioma
                    0 - Sair
                """);

                System.out.print("Escolha uma opção: ");
                opcao = leitura.nextInt();
                leitura.nextLine();

                switch (opcao) {
                    case 1 -> {
                        System.out.print("Digite o título do livro: ");
                        String titulo = leitura.nextLine();
                        servico.buscarSalvarLivroPorTitulo(titulo);
                    }
                    case 2 -> servico.listarLivros();
                    case 3 -> servico.listarAutores();
                    case 4 -> {
                        System.out.print("Digite o ano desejado: ");
                        int ano = leitura.nextInt();
                        leitura.nextLine();
                        servico.listarAutoresVivosEm(ano);
                    }
                    case 5 -> {
                        System.out.print("Digite o idioma (ex: en, pt, es, fr): ");
                        String idioma = leitura.nextLine();
                        servico.listarLivrosPorIdioma(idioma);
                    }
                    case 0 -> System.out.println("Encerrando...");
                    default -> System.out.println("Opção inválida.");
                }

            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida! Digite um número.");
                leitura.nextLine();
            } catch (Exception e) {
                System.out.println("Erro inesperado: " + e.getMessage());
            }
        }
    }
}
