
package servicos;

import estruturas.MinhaLista;
import modelos.Leitor;
import modelos.Emprestimo;

public class LeitorService {

    private MinhaLista<Leitor> leitores;
    private MinhaLista<Emprestimo> emprestimos;

    // Construtor
    public LeitorService(
            MinhaLista<Leitor> leitores,
            MinhaLista<Emprestimo> emprestimos) {

        if (leitores == null || emprestimos == null) {
            throw new IllegalArgumentException(
                    "As listas de leitores e empréstimos são obrigatórias."
            );
        }

        this.leitores = leitores;
        this.emprestimos = emprestimos;
    }

    // 1. Cadastrar leitor
    public boolean cadastrarLeitor(
            String codigo,
            String nome,
            String telefone,
            String email,
            String endereco) {

        // Validar dados obrigatórios
        if (codigo == null || codigo.trim().isEmpty()
                || nome == null || nome.trim().isEmpty()
                || telefone == null || telefone.trim().isEmpty()
                || email == null || email.trim().isEmpty()
                || endereco == null || endereco.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Todos os campos são obrigatórios."
            );
        }

        // Verificar se o código já existe
        if (buscarPorCodigo(codigo) != null) {
            return false;
        }

        // Criar leitor
        Leitor novoLeitor = new Leitor(
                codigo.trim(),
                nome.trim(),
                telefone.trim(),
                email.trim(),
                endereco.trim()
        );

        // Adicionar à lista
        leitores.adicionarFim(novoLeitor);

        return true;
    }

    // 2. Buscar leitor pelo código
    public Leitor buscarPorCodigo(String codigo) {

        if (codigo == null) {
            return null;
        }

        for (int i = 0; i < leitores.getTamanho(); i++) {

            Leitor leitor = leitores.consultar(i);

            if (leitor.getCodigo().equalsIgnoreCase(codigo.trim())) {
                return leitor;
            }
        }

        return null;
    }

    // 3. Buscar leitores pelo nome
    public MinhaLista<Leitor> buscarPorNome(String nome) {

        MinhaLista<Leitor> resultado = new MinhaLista<>();

        if (nome == null || nome.trim().isEmpty()) {
            return resultado;
        }

        String pesquisa = nome.trim().toLowerCase();

        for (int i = 0; i < leitores.getTamanho(); i++) {

            Leitor leitor = leitores.consultar(i);

            if (leitor.getNome().toLowerCase().contains(pesquisa)) {
                resultado.adicionarFim(leitor);
            }
        }

        return resultado;
    }

    // 4. Atualizar dados do leitor
    public boolean atualizarLeitor(
            String codigo,
            String novoNome,
            String novoTelefone,
            String novoEmail,
            String novoEndereco) {

        if (novoNome == null || novoNome.trim().isEmpty()
                || novoTelefone == null || novoTelefone.trim().isEmpty()
                || novoEmail == null || novoEmail.trim().isEmpty()
                || novoEndereco == null || novoEndereco.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Os novos dados do leitor são obrigatórios."
            );
        }

        Leitor leitor = buscarPorCodigo(codigo);

        if (leitor == null) {
            return false;
        }

        leitor.setNome(novoNome.trim());
        leitor.setTelefone(novoTelefone.trim());
        leitor.setEmail(novoEmail.trim());
        leitor.setEndereco(novoEndereco.trim());

        return true;
    }

    // 5. Remover leitor
    public boolean removerLeitor(String codigo) {

        Leitor leitor = buscarPorCodigo(codigo);

        if (leitor == null) {
            return false;
        }

        // Não permitir remover leitores com empréstimos ativos
        if (temEmprestimoAtivo(leitor)) {
            return false;
        }

        return leitores.eliminarElemento(leitor);
    }

    // 6. Verificar se o leitor tem empréstimos ativos
    private boolean temEmprestimoAtivo(Leitor leitor) {

        for (int i = 0; i < emprestimos.getTamanho(); i++) {

            Emprestimo emprestimo = emprestimos.consultar(i);

            if (emprestimo.isAtivo()
                    && emprestimo.getLeitor().getCodigo()
                    .equalsIgnoreCase(leitor.getCodigo())) {

                return true;
            }
        }

        return false;
    }

    // 7. Verificar se existe um leitor
    public boolean existeLeitor(String codigo) {
        return buscarPorCodigo(codigo) != null;
    }

    // 8. Listar todos os leitores
    public MinhaLista<Leitor> listarLeitores() {
        return leitores;
    }

    // 9. Consultar quantidade de leitores
    public int getQuantidadeLeitores() {
        return leitores.getTamanho();
    }

    // 10. Verificar se a lista está vazia
    public boolean estaVazio() {
        return leitores.estaVazia();
    }

    // 11. Mostrar todos os leitores
    public void mostrarLeitores() {

        if (estaVazio()) {
            System.out.println("Não existem leitores registados.");
            return;
        }

        for (int i = 0; i < leitores.getTamanho(); i++) {

            System.out.println(leitores.consultar(i));
        }
    }
}
