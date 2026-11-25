
package modelos;

import java.time.LocalDate;

public class Emprestimo {

    // Atributos
    private String codigo;
    private Livro livro;
    private Leitor leitor;
    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucaoPrevista;
    private LocalDate dataDevolucaoReal;
    private boolean ativo;

    // Construtor
    public Emprestimo(String codigo, Livro livro,
                      Leitor leitor,
                      LocalDate dataEmprestimo,
                      LocalDate dataDevolucaoPrevista) {

        this.codigo = codigo;
        this.livro = livro;
        this.leitor = leitor;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucaoPrevista = dataDevolucaoPrevista;
        this.dataDevolucaoReal = null;
        this.ativo = true;
    }

    // Getters
    public String getCodigo() {
        return codigo;
    }

    public Livro getLivro() {
        return livro;
    }

    public Leitor getLeitor() {
        return leitor;
    }

    public LocalDate getDataEmprestimo() {
        return dataEmprestimo;
    }

    public LocalDate getDataDevolucaoPrevista() {
        return dataDevolucaoPrevista;
    }

    public LocalDate getDataDevolucaoReal() {
        return dataDevolucaoReal;
    }

    public boolean isAtivo() {
        return ativo;
    }

    // Registar devolução
    public void registarDevolucao(LocalDate data) {

        if (!ativo) {
            throw new IllegalStateException(
                    "Este empréstimo já foi devolvido."
            );
        }

        this.dataDevolucaoReal = data;
        this.ativo = false;
        this.livro.setDisponivel(true);
    }

    @Override
    public String toString() {
        return "Emprestimo{" +
                "codigo='" + codigo + '\'' +
                ", livro=" + livro.getTitulo() +
                ", leitor=" + leitor.getNome() +
                ", dataEmprestimo=" + dataEmprestimo +
                ", dataDevolucaoPrevista=" + dataDevolucaoPrevista +
                ", dataDevolucaoReal=" + dataDevolucaoReal +
                ", ativo=" + ativo +
                '}';
    }
}
