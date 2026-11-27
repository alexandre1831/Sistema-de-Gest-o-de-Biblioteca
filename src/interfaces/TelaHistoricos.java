
package interfaces;

import modelos.Emprestimo;
import servicos.EmprestimoService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;

public class TelaHistoricos extends JPanel {

    private EmprestimoService emprestimoService;

    private JTable tabela;
    private DefaultTableModel modeloTabela;

    private JTextField txtPesquisa;

    private JLabel lblTotal;
    private JLabel lblAtivos;
    private JLabel lblDevolvidos;

    public TelaHistoricos(EmprestimoService emprestimoService) {

        this.emprestimoService = emprestimoService;

        configurarPainel();
        criarComponentes();
        atualizarTabela();
    }

    // Configuração principal
    private void configurarPainel() {

        setLayout(new BorderLayout(15, 15));

        setBackground(new Color(245, 247, 250));

        setBorder(
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        );
    }

    // Construção dos componentes
    private void criarComponentes() {

        // Título
        JLabel titulo = new JLabel("Histórico de Empréstimos");

        titulo.setFont(new Font("Arial", Font.BOLD, 25));

        titulo.setForeground(new Color(40, 50, 70));

        add(titulo, BorderLayout.NORTH);

        // Painel central
        JPanel painelCentral = new JPanel(new BorderLayout(10, 15));

        painelCentral.setOpaque(false);

        // Estatísticas
        JPanel painelEstatisticas = new JPanel(
                new GridLayout(1, 3, 15, 10)
        );

        painelEstatisticas.setOpaque(false);

        lblTotal = criarCartao(
                painelEstatisticas,
                "Total de Empréstimos"
        );

        lblAtivos = criarCartao(
                painelEstatisticas,
                "Empréstimos Activos"
        );

        lblDevolvidos = criarCartao(
                painelEstatisticas,
                "Livros Devolvidos"
        );

        // Pesquisa
        JPanel painelPesquisa = new JPanel(
                new FlowLayout(FlowLayout.LEFT)
        );

        painelPesquisa.setOpaque(false);

        txtPesquisa = new JTextField(25);

        JButton btnPesquisar = new JButton("Pesquisar");

        JButton btnAtualizar = new JButton("Actualizar");

        painelPesquisa.add(new JLabel("Pesquisar:"));

        painelPesquisa.add(txtPesquisa);

        painelPesquisa.add(btnPesquisar);

        painelPesquisa.add(btnAtualizar);

        // Tabela
        modeloTabela = new DefaultTableModel(
                new Object[]{
                        "Código",
                        "Livro",
                        "Leitor",
                        "Data Empréstimo",
                        "Devolução Prevista",
                        "Devolução Real",
                        "Estado"
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

        tabela.setAutoCreateRowSorter(true);

        JScrollPane scrollPane = new JScrollPane(tabela);

        scrollPane.setBorder(
                BorderFactory.createTitledBorder(
                        "Registos de Empréstimos"
                )
        );

        // Organizar parte superior
        JPanel painelSuperior = new JPanel(
                new BorderLayout(10, 15)
        );

        painelSuperior.setOpaque(false);

        painelSuperior.add(
                painelEstatisticas,
                BorderLayout.NORTH
        );

        painelSuperior.add(
                painelPesquisa,
                BorderLayout.SOUTH
        );

        painelCentral.add(
                painelSuperior,
                BorderLayout.NORTH
        );

        painelCentral.add(
                scrollPane,
                BorderLayout.CENTER
        );

        add(painelCentral, BorderLayout.CENTER);

        // Eventos
        btnPesquisar.addActionListener(
                e -> pesquisarHistorico()
        );

        btnAtualizar.addActionListener(
                e -> {
                    txtPesquisa.setText("");
                    atualizarTabela();
                }
        );

        txtPesquisa.addActionListener(
                e -> pesquisarHistorico()
        );
    }

    // Criar cartões de estatísticas
    private JLabel criarCartao(
            JPanel painel,
            String titulo) {

        JPanel cartao = new JPanel(new BorderLayout(5, 5));

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

        JLabel lblValor = new JLabel("0");

        lblValor.setFont(
                new Font("Arial", Font.BOLD, 25)
        );

        lblValor.setForeground(
                new Color(40, 90, 160)
        );

        cartao.add(lblTitulo, BorderLayout.NORTH);

        cartao.add(lblValor, BorderLayout.CENTER);

        painel.add(cartao);

        return lblValor;
    }

    // Actualizar a tabela com todos os empréstimos
    private void atualizarTabela() {

        modeloTabela.setRowCount(0);

        int total = 0;
        int ativos = 0;
        int devolvidos = 0;

        for (int i = 0;
             i < emprestimoService.getQuantidadeEmprestimos();
             i++) {

            Emprestimo emprestimo =
                    emprestimoService.getEmprestimos().consultar(i);

            if (emprestimo == null) {
                continue;
            }

            total++;

            String estado;

            if (emprestimo.isAtivo()) {

                ativos++;

                if (emprestimo.getDataDevolucaoPrevista()
                        .isBefore(LocalDate.now())) {

                    estado = "Atrasado";

                } else {

                    estado = "Activo";
                }

            } else {

                devolvidos++;

                estado = "Devolvido";
            }

            String dataReal;

            if (emprestimo.getDataDevolucaoReal() == null) {

                dataReal = "-";

            } else {

                dataReal =
                        emprestimo.getDataDevolucaoReal().toString();
            }

            modeloTabela.addRow(new Object[]{

                    emprestimo.getCodigo(),

                    emprestimo.getLivro().getTitulo(),

                    emprestimo.getLeitor().getNome(),

                    emprestimo.getDataEmprestimo(),

                    emprestimo.getDataDevolucaoPrevista(),

                    dataReal,

                    estado
            });
        }

        // Actualizar cartões
        lblTotal.setText(String.valueOf(total));

        lblAtivos.setText(String.valueOf(ativos));

        lblDevolvidos.setText(String.valueOf(devolvidos));
    }

    // Pesquisar por código, livro ou leitor
    private void pesquisarHistorico() {

        String pesquisa = txtPesquisa.getText()
                .trim()
                .toLowerCase();

        if (pesquisa.isEmpty()) {

            atualizarTabela();

            return;
        }

        modeloTabela.setRowCount(0);

        for (int i = 0;
             i < emprestimoService.getQuantidadeEmprestimos();
             i++) {

            Emprestimo emprestimo =
                    emprestimoService.getEmprestimos().consultar(i);

            if (emprestimo == null) {
                continue;
            }

            String codigo = emprestimo.getCodigo().toLowerCase();

            String livro = emprestimo.getLivro()
                    .getTitulo().toLowerCase();

            String leitor = emprestimo.getLeitor()
                    .getNome().toLowerCase();

            if (codigo.contains(pesquisa)
                    || livro.contains(pesquisa)
                    || leitor.contains(pesquisa)) {

                String estado;

                if (!emprestimo.isAtivo()) {

                    estado = "Devolvido";

                } else if (emprestimo.getDataDevolucaoPrevista()
                        .isBefore(LocalDate.now())) {

                    estado = "Atrasado";

                } else {

                    estado = "Activo";
                }

                String dataReal =
                        emprestimo.getDataDevolucaoReal() == null
                                ? "-"
                                : emprestimo.getDataDevolucaoReal().toString();

                modeloTabela.addRow(new Object[]{

                        emprestimo.getCodigo(),

                        emprestimo.getLivro().getTitulo(),

                        emprestimo.getLeitor().getNome(),

                        emprestimo.getDataEmprestimo(),

                        emprestimo.getDataDevolucaoPrevista(),

                        dataReal,

                        estado
                });
            }
        }
    }
}
