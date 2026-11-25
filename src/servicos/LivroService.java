
package servicos;

import estruturas.MinhaLista;
import modelos.Livro;
import modelos.Emprestimo;

public class LivroService {

    private MinhaLista<Livro> livros;
    private MinhaLista<Emprestimo> emprestimos;

    // Construtor
    public LivroService(
            MinhaLista<Livro> livros,
            MinhaLista<Emprestimo> emprestimos) {

        if (livros == null || emprestimos == null) {
            throw new IllegalArgumentException(
                    "As listas não podem ser nulas."
            );
        }

        this.livros = livros;
        this.emprestimos = emprestimos;
    }

    // 1. Cadastrar um novo livro
    public boolean cadastrarLivro(
            String codigo,
            String titulo,
            String autor,
            String editora,
            int anoPublicacao) {

        if (codigo == null || codigo.trim().isEmpty()
                || titulo == null || titulo.trim().isEmpty()
                || autor == null || autor.trim().isEmpty()
                || editora == null || editora.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Todos os campos são obrigatórios."
            );
        }

        if (anoPublicacao <= 0) {
            throw new IllegalArgumentException(
                    "O ano de publicação deve ser válido."
            );
        }

        if (buscarPorCodigo(codigo) != null) {
            return false;
        }

        Livro livro = new Livro(
                codigo.trim(),
                titulo.trim(),
                autor.trim(),
                editora.trim(),
                anoPublicacao
        );

        livros.adicionarFim(livro);

        return true;
    }

    // 2. Buscar livro pelo código
    public Livro buscarPorCodigo(String codigo) {

        if (codigo == null || codigo.trim().isEmpty()) {
            return null;
        }

        for (int i = 0; i < livros.getTamanho(); i++) {

            Livro livro = livros.consultar(i);

            if (livro.getCodigo().equalsIgnoreCase(codigo.trim())) {
                return livro;
            }
        }

        return null;
    }

    // 3. Buscar livros pelo título
    public MinhaLista<Livro> buscarPorTitulo(String titulo) {

        MinhaLista<Livro> resultado = new MinhaLista<>();

        if (titulo == null || titulo.trim().isEmpty()) {
            return resultado;
        }

        String pesquisa = titulo.trim().toLowerCase();

        for (int i = 0; i < livros.getTamanho(); i++) {

            Livro livro = livros.consultar(i);

            if (livro.getTitulo().toLowerCase().contains(pesquisa)) {
                resultado.adicionarFim(livro);
            }
        }

        return resultado;
    }

    // 4. Atualizar os dados de um livro
    public boolean atualizarLivro(
            String codigo,
            String novoTitulo,
            String novoAutor,
            String novaEditora,
            int novoAnoPublicacao) {

        if (novoTitulo == null || novoTitulo.trim().isEmpty()
                || novoAutor == null || novoAutor.trim().isEmpty()
                || novaEditora == null || novaEditora.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Os novos dados são obrigatórios."
            );
        }

        if (novoAnoPublicacao <= 0) {
            throw new IllegalArgumentException(
                    "O ano de publicação deve ser válido."
            );
        }

        Livro livro = buscarPorCodigo(codigo);

        if (livro == null) {
            return false;
        }

        livro.setTitulo(novoTitulo.trim());
        livro.setAutor(novoAutor.trim());
        livro.setEditora(novaEditora.trim());
        livro.setAnoPublicacao(novoAnoPublicacao);

        return true;
    }

    // 5. Remover um livro
    public boolean removerLivro(String codigo) {

        Livro livro = buscarPorCodigo(codigo);

        if (livro == null) {
            return false;
        }

        // Não permite remover livros com empréstimos activos
        if (temEmprestimoAtivo(livro)) {
            return false;
        }

        return livros.eliminarElemento(livro);
    }

    // 6. Verificar se um livro possui empréstimo activo
    private boolean temEmprestimoAtivo(Livro livro) {

        for (int i = 0; i < emprestimos.getTamanho(); i++) {

            Emprestimo emprestimo = emprestimos.consultar(i);

            if (emprestimo.isAtivo()
                    && emprestimo.getLivro().getCodigo()
                    .equalsIgnoreCase(livro.getCodigo())) {

                return true;
            }
        }

        return false;
    }

    // 7. Verificar se um livro existe
    public boolean existeLivro(String codigo) {
        return buscarPorCodigo(codigo) != null;
    }

    // 8. Devolver todos os livros
    public MinhaLista<Livro> listarLivros() {
        return livros;
    }

    // 9. Listar apenas os livros disponíveis
    public MinhaLista<Livro> listarLivrosDisponiveis() {

        MinhaLista<Livro> disponiveis = new MinhaLista<>();

        for (int i = 0; i < livros.getTamanho(); i++) {

            Livro livro = livros.consultar(i);

            if (livro.isDisponivel()) {
                disponiveis.adicionarFim(livro);
            }
        }

        return disponiveis;
    }

    // 10. Obter a quantidade total de livros
    public int getQuantidadeLivros() {
        return livros.getTamanho();
    }

    // 11. Contar livros disponíveis
    public int contarLivrosDisponiveis() {

        int quantidade = 0;

        for (int i = 0; i < livros.getTamanho(); i++) {

            if (livros.consultar(i).isDisponivel()) {
                quantidade++;
            }
        }

        return quantidade;
    }

    // 12. Verificar se a lista está vazia
    public boolean estaVazio() {
        return livros.estaVazia();
    }

    // 13. Mostrar todos os livros
    public void mostrarLivros() {

        if (livros.estaVazia()) {
            System.out.println("Não existem livros registados.");
            return;
        }

        System.out.println("===== LISTA DE LIVROS =====");

        for (int i = 0; i < livros.getTamanho(); i++) {
            System.out.println(livros.consultar(i));
        }
    }
}
