
package interfaces;

import modelos.Emprestimo;
import servicos.EmprestimoService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;

public class TelaDevolucoes extends JPanel {

    private EmprestimoService emprestimoService;

    private JTable tabela;
    private DefaultTableModel modeloTabela;

    private JLabel lblCodigo;
    private JLabel lblLivro;
    private JLabel lblLeitor;

    public TelaDevolucoes(EmprestimoService emprestimoService) {

        this.emprestimoService = emprestimoService;

        configurarPainel();
        criarComponentes();
        atualizarTabela();
    }

    // Configuração do painel
    private void configurarPainel() {

        setLayout(new BorderLayout(15, 15));

        setBackground(new Color(245, 247, 250));

        setBorder(
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        );
    }

    // Criar componentes da interface
    private void criarComponentes() {

        // Título
        JLabel titulo = new JLabel("Gestão de Devoluções");

        titulo.setFont(new Font("Arial", Font.BOLD, 25));

        titulo.setForeground(new Color(40, 50, 70));

        add(titulo, BorderLayout.NORTH);

        // Painel central
        JPanel painelCentral = new JPanel(new BorderLayout(10, 15));

        painelCentral.setOpaque(false);

        // Informações do empréstimo seleccionado
        JPanel painelInformacoes = new JPanel(
                new GridLayout(3, 2, 10, 10)
        );

        painelInformacoes.setBorder(
                BorderFactory.createTitledBorder(
                        "Informações do Empréstimo Seleccionado"
                )
        );

        lblCodigo = new JLabel("-");
        lblLivro = new JLabel("-");
        lblLeitor = new JLabel("-");

        painelInformacoes.add(new JLabel("Código:"));
        painelInformacoes.add(lblCodigo);

        painelInformacoes.add(new JLabel("Livro:"));
        painelInformacoes.add(lblLivro);

        painelInformacoes.add(new JLabel("Leitor:"));
        painelInformacoes.add(lblLeitor);

        // Tabela
        modeloTabela = new DefaultTableModel(
                new Object[]{
                        "Código",
                        "Livro",
                        "Leitor",
                        "Data do Empréstimo",
                        "Devolução Prevista",
                        "Situação"
                }, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabela = new JTable(modeloTabela);

        tabela.setRowHeight(28);

        tabela.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        JScrollPane scrollPane = new JScrollPane(tabela);

        scrollPane.setBorder(
                BorderFactory.createTitledBorder(
                        "Empréstimos Activos"
                )
        );

        // Botões
        JPanel painelBotoes = new JPanel(
                new FlowLayout(FlowLayout.RIGHT)
        );

        JButton btnAtualizar = new JButton("Actualizar");

        JButton btnDevolver = new JButton("Registar Devolução");

        painelBotoes.add(btnAtualizar);
        painelBotoes.add(btnDevolver);

        // Organizar painéis
        JPanel painelSuperior = new JPanel(new BorderLayout(10, 10));

        painelSuperior.setOpaque(false);

        painelSuperior.add(
                painelInformacoes,
                BorderLayout.CENTER
        );

        painelCentral.add(
                painelSuperior,
                BorderLayout.NORTH
        );

        painelCentral.add(
                scrollPane,
                BorderLayout.CENTER
        );

        painelCentral.add(
                painelBotoes,
                BorderLayout.SOUTH
        );

        add(painelCentral, BorderLayout.CENTER);

        // Eventos
        btnAtualizar.addActionListener(
                e -> atualizarTabela()
        );

        btnDevolver.addActionListener(
                e -> registarDevolucao()
        );

        tabela.getSelectionModel().addListSelectionListener(e -> {

            if (!e.getValueIsAdjusting()) {
                preencherInformacoes();
            }

        });
    }

    // Mostrar empréstimos activos
    private void atualizarTabela() {

        modeloTabela.setRowCount(0);

        for (int i = 0;
             i < emprestimoService.getQuantidadeEmprestimos();
             i++) {

            Emprestimo emprestimo =
                    emprestimoService.getEmprestimos().consultar(i);

            if (emprestimo != null && emprestimo.isAtivo()) {

                String situacao;

                if (emprestimo.getDataDevolucaoPrevista()
                        .isBefore(LocalDate.now())) {

                    situacao = "Atrasado";

                } else {

                    situacao = "Dentro do prazo";
                }

                modeloTabela.addRow(new Object[]{

                        emprestimo.getCodigo(),

                        emprestimo.getLivro().getTitulo(),

                        emprestimo.getLeitor().getNome(),

                        emprestimo.getDataEmprestimo(),

                        emprestimo.getDataDevolucaoPrevista(),

                        situacao

                });
            }
        }

        limparInformacoes();
    }

    // Apresentar informações do empréstimo seleccionado
    private void preencherInformacoes() {

        int linha = tabela.getSelectedRow();

        if (linha == -1) {
            return;
        }

        lblCodigo.setText(
                modeloTabela.getValueAt(linha, 0).toString()
        );

        lblLivro.setText(
                modeloTabela.getValueAt(linha, 1).toString()
        );

        lblLeitor.setText(
                modeloTabela.getValueAt(linha, 2).toString()
        );
    }

    // Registar devolução
    private void registarDevolucao() {

        int linha = tabela.getSelectedRow();

        if (linha == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione um empréstimo na tabela.",
                    "Atenção",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        String codigo = modeloTabela
                .getValueAt(linha, 0)
                .toString();

        int resposta = JOptionPane.showConfirmDialog(
                this,
                "Deseja realmente registar a devolução do empréstimo "
                        + codigo + "?",
                "Confirmar Devolução",
                JOptionPane.YES_NO_OPTION
        );

        if (resposta != JOptionPane.YES_OPTION) {
            return;
        }

        try {

            boolean sucesso =
                    emprestimoService.registarDevolucao(codigo);

            if (sucesso) {

                JOptionPane.showMessageDialog(
                        this,
                        "Devolução registada com sucesso!",
                        "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE
                );

                atualizarTabela();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Não foi possível registar a devolução. "
                                + "Verifique o empréstimo.",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (IllegalArgumentException |
                 IllegalStateException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // Limpar informações seleccionadas
    private void limparInformacoes() {

        lblCodigo.setText("-");
        lblLivro.setText("-");
        lblLeitor.setText("-");

        tabela.clearSelection();
    }
}
