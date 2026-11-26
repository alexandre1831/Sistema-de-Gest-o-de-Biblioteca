
import estruturas.MinhaLista;

import modelos.Livro;
import modelos.Leitor;
import modelos.Emprestimo;

import servicos.LivroService;
import servicos.LeitorService;
import servicos.EmprestimoService;

import interfaces.MenuPrincipal;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {

    public static void main(String[] args) {

        // 1. Criar as estruturas de dados
        MinhaLista<Livro> livros = new MinhaLista<>();
        MinhaLista<Leitor> leitores = new MinhaLista<>();
        MinhaLista<Emprestimo> emprestimos = new MinhaLista<>();

        // 2. Criar os serviços
        LivroService livroService =
                new LivroService(livros, emprestimos);

        LeitorService leitorService =
                new LeitorService(leitores, emprestimos);

        EmprestimoService emprestimoService =
                new EmprestimoService(
                        livros,
                        leitores,
                        emprestimos
                );

        // 3. Abrir a aplicação Swing
        SwingUtilities.invokeLater(() -> {

            try {
                UIManager.setLookAndFeel(
                        UIManager.getSystemLookAndFeelClassName()
                );
            } catch (Exception e) {
                System.out.println(
                        "Não foi possível aplicar o tema do sistema."
                );
            }

            MenuPrincipal menu = new MenuPrincipal(
                    livroService,
                    leitorService,
                    emprestimoService
            );

            menu.setVisible(true);
        });
    }
}
