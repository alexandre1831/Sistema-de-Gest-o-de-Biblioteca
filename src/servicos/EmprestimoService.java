
package servicos;

import estruturas.MinhaLista;
import modelos.Emprestimo;
import modelos.Livro;
import modelos.Leitor;

import java.time.LocalDate;

public class EmprestimoService {

    private MinhaLista<Emprestimo> emprestimos;
    private MinhaLista<Livro> livros;
    private MinhaLista<Leitor> leitores;

    private int proximoCodigo;

    // Construtor
    public EmprestimoService(
            MinhaLista<Livro> livros,
            MinhaLista<Leitor> leitores) {

        if (livros == null || leitores == null) {
            throw new IllegalArgumentException(
                    "As listas de livros e leitores são obrigatórias."
            );
        }

        this.emprestimos = new MinhaLista<>();
        this.livros = livros;
        this.leitores = leitores;

        this.proximoCodigo = 1;
    }

    // 1. Registar um empréstimo
    public Emprestimo registarEmprestimo(
            String codigoLivro,
            String codigoLeitor,
            int diasPrazo) {

        if (codigoLivro == null || codigoLivro.trim().isEmpty()
                || codigoLeitor == null
                || codigoLeitor.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Os códigos do livro e do leitor são obrigatórios."
            );
        }

        if (diasPrazo <= 0) {
            throw new IllegalArgumentException(
                    "O prazo de empréstimo deve ser maior que zero."
            );
        }

        // Procurar o livro
        Livro livro = buscarLivroPorCodigo(codigoLivro);

        if (livro == null) {
            throw new IllegalArgumentException(
                    "Livro não encontrado."
            );
        }

        // Procurar o leitor
        Leitor leitor = buscarLeitorPorCodigo(codigoLeitor);

        if (leitor == null) {
            throw new IllegalArgumentException(
                    "Leitor não encontrado."
            );
        }

        // Verificar disponibilidade
        if (!livro.isDisponivel()) {
            throw new IllegalStateException(
                    "Este livro não está disponível."
            );
        }

        // Gerar código do empréstimo
        String codigoEmprestimo =
                String.format("E%03d", proximoCodigo);

        // Definir datas
        LocalDate dataEmprestimo = LocalDate.now();

        LocalDate dataDevolucaoPrevista =
                dataEmprestimo.plusDays(diasPrazo);

        // Criar empréstimo
        Emprestimo novoEmprestimo = new Emprestimo(
                codigoEmprestimo,
                livro,
                leitor,
                dataEmprestimo,
                dataDevolucaoPrevista
        );

        // Guardar empréstimo
        emprestimos.adicionarFim(novoEmprestimo);

        // Atualizar disponibilidade do livro
        livro.setDisponivel(false);

        proximoCodigo++;

        return novoEmprestimo;
    }

    // 2. Registar devolução
    public boolean registarDevolucao(String codigoEmprestimo) {

        Emprestimo emprestimo =
                buscarEmprestimoPorCodigo(codigoEmprestimo);

        if (emprestimo == null) {
            return false;
        }

        if (!emprestimo.isAtivo()) {
            return false;
        }

        // Registar a data real da devolução
        emprestimo.registarDevolucao(LocalDate.now());

        return true;
    }

    // 3. Buscar livro pelo código
    private Livro buscarLivroPorCodigo(String codigo) {

        for (int i = 0; i < livros.getTamanho(); i++) {

            Livro livro = livros.consultar(i);

            if (livro.getCodigo().equals(codigo)) {
                return livro;
            }
        }

        return null;
    }

    // 4. Buscar leitor pelo código
    private Leitor buscarLeitorPorCodigo(String codigo) {

        for (int i = 0; i < leitores.getTamanho(); i++) {

            Leitor leitor = leitores.consultar(i);

            if (leitor.getCodigo().equals(codigo)) {
                return leitor;
            }
        }

        return null;
    }

    // 5. Buscar empréstimo pelo código
    public Emprestimo buscarEmprestimoPorCodigo(String codigo) {

        if (codigo == null) {
            return null;
        }

        for (int i = 0; i < emprestimos.getTamanho(); i++) {

            Emprestimo emprestimo = emprestimos.consultar(i);

            if (emprestimo.getCodigo().equals(codigo)) {
                return emprestimo;
            }
        }

        return null;
    }

    // 6. Consultar todos os empréstimos
    public MinhaLista<Emprestimo> getEmprestimos() {
        return emprestimos;
    }

    // 7. Consultar quantidade de empréstimos
    public int getQuantidadeEmprestimos() {
        return emprestimos.getTamanho();
    }

    // 8. Contar empréstimos ativos
    public int contarEmprestimosAtivos() {

        int quantidade = 0;

        for (int i = 0; i < emprestimos.getTamanho(); i++) {

            Emprestimo emprestimo = emprestimos.consultar(i);

            if (emprestimo.isAtivo()) {
                quantidade++;
            }
        }

        return quantidade;
    }

    // 9. Verificar se existem empréstimos
    public boolean estaVazio() {
        return emprestimos.estaVazia();
    }

    // 10. Mostrar empréstimos
    public void mostrarEmprestimos() {

        if (emprestimos.estaVazia()) {
            System.out.println("Não existem empréstimos registados.");
            return;
        }

        for (int i = 0; i < emprestimos.getTamanho(); i++) {

            System.out.println(emprestimos.consultar(i));
        }
    }
}
