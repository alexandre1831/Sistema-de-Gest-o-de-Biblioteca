
package interfaces;

import modelos.Emprestimo;
import servicos.LivroService;
import servicos.LeitorService;
import servicos.EmprestimoService;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class TelaRelatorios extends JPanel {

    private LivroService livroService;
    private LeitorService leitorService;
    private EmprestimoService emprestimoService;

    private JLabel lblTotalLivros;
    private JLabel lblLivrosDisponiveis;
    private JLabel lblLivrosEmprestados;
    private JLabel lblTotalLeitores;
    private JLabel lblTotalEmprestimos;
    private JLabel lblEmprestimosAtivos;
    private JLabel lblEmprestimosDevolvidos;
    private JLabel lblEmprestimosAtrasados;
    private JLabel lblDisponibilidade;

    public TelaRelatorios(
            LivroService livroService,
            LeitorService leitorService,
            EmprestimoService emprestimoService) {

        this.livroService = livroService;
        this.leitorService = leitorService;
        this.emprestimoService = emprestimoService;

        configurarPainel();
        criarComponentes();
        atualizarRelatorios();
    }

    // Configurar painel principal
    private void configurarPainel() {

        setLayout(new BorderLayout(15, 15));

        setBackground(new Color(245, 247, 250));

        setBorder(
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        );
    }

    // Criar componentes
    private void criarComponentes() {

        // Cabeçalho
        JPanel painelCabecalho = new JPanel(new BorderLayout());

        painelCabecalho.setOpaque(false);

        JLabel titulo = new JLabel("Relatórios da Biblioteca");

        titulo.setFont(new Font("Arial", Font.BOLD, 25));

        titulo.setForeground(new Color(40, 50, 70));

        JButton btnAtualizar = new JButton("Actualizar Relatórios");

        painelCabecalho.add(titulo, BorderLayout.WEST);

        painelCabecalho.add(btnAtualizar, BorderLayout.EAST);

        add(painelCabecalho, BorderLayout.NORTH);

        // Painel de estatísticas
        JPanel painelEstatisticas = new JPanel(
                new GridLayout(3, 3, 15, 15)
        );

        painelEstatisticas.setOpaque(false);

        lblTotalLivros = criarCartao(
                painelEstatisticas,
                "Total de Livros"
        );

        lblLivrosDisponiveis = criarCartao(
                painelEstatisticas,
                "Livros Disponíveis"
        );

        lblLivrosEmprestados = criarCartao(
                painelEstatisticas,
                "Livros Emprestados"
        );

        lblTotalLeitores = criarCartao(
                painelEstatisticas,
                "Total de Leitores"
        );

        lblTotalEmprestimos = criarCartao(
                painelEstatisticas,
                "Total de Empréstimos"
        );

        lblEmprestimosAtivos = criarCartao(
                painelEstatisticas,
                "Empréstimos Activos"
        );

        lblEmprestimosDevolvidos = criarCartao(
                painelEstatisticas,
                "Empréstimos Devolvidos"
        );

        lblEmprestimosAtrasados = criarCartao(
                painelEstatisticas,
                "Empréstimos Atrasados"
        );

        lblDisponibilidade = criarCartao(
                painelEstatisticas,
                "Disponibilidade dos Livros"
        );

        add(painelEstatisticas, BorderLayout.CENTER);

        // Rodapé
        JLabel rodape = new JLabel(
                "Sistema de Gestão de Bibliotecas - Relatório Geral"
        );

        rodape.setHorizontalAlignment(SwingConstants.CENTER);

        rodape.setForeground(Color.GRAY);

        add(rodape, BorderLayout.SOUTH);

        // Evento
        btnAtualizar.addActionListener(
                e -> atualizarRelatorios()
        );
    }

    // Criar cartões estatísticos
    private JLabel criarCartao(
            JPanel painel,
            String titulo) {

        JPanel cartao = new JPanel(new BorderLayout(5, 10));

        cartao.setBackground(Color.WHITE);

        cartao.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(220, 225, 230)
                        ),
                        BorderFactory.createEmptyBorder(
                                15, 15, 15, 15
                        )
                )
        );

        JLabel lblTitulo = new JLabel(titulo);

        lblTitulo.setFont(
                new Font("Arial", Font.PLAIN, 13)
        );

        lblTitulo.setForeground(new Color(90, 100, 115));

        JLabel lblValor = new JLabel("0");

        lblValor.setFont(
                new Font("Arial", Font.BOLD, 28)
        );

        lblValor.setForeground(new Color(40, 90, 160));

        cartao.add(lblTitulo, BorderLayout.NORTH);

        cartao.add(lblValor, BorderLayout.CENTER);

        painel.add(cartao);

        return lblValor;
    }

    // Actualizar todos os relatórios
    private void atualizarRelatorios() {

        int totalLivros =
                livroService.getQuantidadeLivros();

        int livrosDisponiveis =
                livroService.contarLivrosDisponiveis();

        int totalLeitores =
                leitorService.getQuantidadeLeitores();

        int totalEmprestimos =
                emprestimoService.getQuantidadeEmprestimos();

        int emprestimosAtivos = 0;

        int emprestimosDevolvidos = 0;

        int emprestimosAtrasados = 0;

        // Analisar os empréstimos
        for (int i = 0; i < totalEmprestimos; i++) {

            Emprestimo emprestimo =
                    emprestimoService.getEmprestimos().consultar(i);

            if (emprestimo == null) {
                continue;
            }

            if (emprestimo.isAtivo()) {

                emprestimosAtivos++;

                if (emprestimo.getDataDevolucaoPrevista()
                        .isBefore(LocalDate.now())) {

                    emprestimosAtrasados++;
                }

            } else {

                emprestimosDevolvidos++;
            }
        }

        int livrosEmprestados =
                totalLivros - livrosDisponiveis;

        // Calcular percentagem de disponibilidade
        double percentagem = 0;

        if (totalLivros > 0) {

            percentagem =
                    (livrosDisponiveis * 100.0) / totalLivros;
        }

        // Actualizar cartões
        lblTotalLivros.setText(
                String.valueOf(totalLivros)
        );

        lblLivrosDisponiveis.setText(
                String.valueOf(livrosDisponiveis)
        );

        lblLivrosEmprestados.setText(
                String.valueOf(livrosEmprestados)
        );

        lblTotalLeitores.setText(
                String.valueOf(totalLeitores)
        );

        lblTotalEmprestimos.setText(
                String.valueOf(totalEmprestimos)
        );

        lblEmprestimosAtivos.setText(
                String.valueOf(emprestimosAtivos)
        );

        lblEmprestimosDevolvidos.setText(
                String.valueOf(emprestimosDevolvidos)
        );

        lblEmprestimosAtrasados.setText(
                String.valueOf(emprestimosAtrasados)
        );

        lblDisponibilidade.setText(
                String.format("%.1f%%", percentagem)
        );
    }
}
