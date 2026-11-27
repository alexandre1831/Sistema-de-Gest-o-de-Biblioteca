
package interfaces;

import modelos.Emprestimo;
import servicos.EmprestimoService;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;

public class TelaDevolucoes extends JPanel {

    private final EmprestimoService emprestimoService;

    private JTable tabela;
    private DefaultTableModel modeloTabela;

    private JLabel lblCodigo;
    private JLabel lblLivro;
    private JLabel lblLeitor;
    private JLabel lblDataEmprestimo;
    private JLabel lblDataPrevista;
    private JLabel lblSituacao;

    private JLabel lblPendentes;
    private JLabel lblAtrasados;
    private JLabel lblNoPrazo;

    private final Color AZUL = new Color(37, 99, 235);
    private final Color FUNDO = new Color(245, 247, 250);
    private final Color TEXTO = new Color(30, 41, 59);
    private final Color VERDE = new Color(22, 163, 74);
    private final Color LARANJA = new Color(217, 119, 6);
    private final Color VERMELHO = new Color(220, 38, 38);

    public TelaDevolucoes(EmprestimoService emprestimoService) {

        this.emprestimoService = emprestimoService;

        setLayout(new BorderLayout(20, 20));
        setBackground(FUNDO);
        setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        criarComponentes();
        atualizarTabela();
    }

    // COMPONENTES PRINCIPAIS

    private void criarComponentes() {

        JPanel painelSuperior = new JPanel(new BorderLayout(15, 15));
        painelSuperior.setBackground(FUNDO);

        JPanel painelCabecalho = new JPanel();
        painelCabecalho.setLayout(
                new BoxLayout(painelCabecalho, BoxLayout.Y_AXIS)
        );
        painelCabecalho.setBackground(FUNDO);

        JLabel titulo = new JLabel("Gestão de Devoluções");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 27));
        titulo.setForeground(TEXTO);

        JLabel descricao = new JLabel(
                "Acompanhe os livros emprestados e registe as respectivas devoluções."
        );
        descricao.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        descricao.setForeground(new Color(100, 116, 139));

        painelCabecalho.add(titulo);
        painelCabecalho.add(Box.createVerticalStrut(5));
        painelCabecalho.add(descricao);

        painelSuperior.add(painelCabecalho, BorderLayout.NORTH);
        painelSuperior.add(criarPainelEstatisticas(), BorderLayout.CENTER);

        add(painelSuperior, BorderLayout.NORTH);

        JPanel painelCentral = new JPanel(new BorderLayout(15, 15));
        painelCentral.setBackground(FUNDO);

        painelCentral.add(criarPainelInformacoes(), BorderLayout.NORTH);
        painelCentral.add(criarTabela(), BorderLayout.CENTER);

        add(painelCentral, BorderLayout.CENTER);
    }

    // CARTÕES DE ESTATÍSTICAS

    private JPanel criarPainelEstatisticas() {

        JPanel painel = new JPanel(new GridLayout(1, 3, 15, 0));
        painel.setBackground(FUNDO);
        painel.setBorder(BorderFactory.createEmptyBorder(18, 0, 0, 0));

        lblPendentes = criarCartao(
                painel,
                "Empréstimos Pendentes",
                AZUL
        );

        lblAtrasados = criarCartao(
                painel,
                "Devoluções Atrasadas",
                VERMELHO
        );

        lblNoPrazo = criarCartao(
                painel,
                "Dentro do Prazo",
                VERDE
        );

        return painel;
    }

    private JLabel criarCartao(
            JPanel painel,
            String titulo,
            Color cor) {

        JPanel cartao = new JPanel(new BorderLayout(5, 8));
        cartao.setBackground(Color.WHITE);

        cartao.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        new Color(226, 232, 240)
                ),
                BorderFactory.createEmptyBorder(15, 18, 15, 18)
        ));

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblTitulo.setForeground(new Color(100, 116, 139));

        JLabel lblValor = new JLabel("0");
        lblValor.setFont(new Font("Segoe UI", Font.BOLD, 25));
        lblValor.setForeground(cor);

        cartao.add(lblTitulo, BorderLayout.NORTH);
        cartao.add(lblValor, BorderLayout.CENTER);

        painel.add(cartao);

        return lblValor;
    }

    // PAINEL DE INFORMAÇÕES

    private JPanel criarPainelInformacoes() {

        JPanel painel = new JPanel(new BorderLayout(10, 15));
        painel.setBackground(Color.WHITE);

        painel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        new Color(226, 232, 240)
                ),
                BorderFactory.createEmptyBorder(18, 20, 18, 20)
        ));

        JLabel titulo = new JLabel("Informações do Empréstimo Seleccionado");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 17));
        titulo.setForeground(TEXTO);

        JPanel campos = new JPanel(new GridLayout(2, 3, 20, 15));
        campos.setBackground(Color.WHITE);

        lblCodigo = new JLabel("-");
        lblLivro = new JLabel("-");
        lblLeitor = new JLabel("-");
        lblDataEmprestimo = new JLabel("-");
        lblDataPrevista = new JLabel("-");
        lblSituacao = new JLabel("-");

        campos.add(criarInformacao("Código do Empréstimo", lblCodigo));
        campos.add(criarInformacao("Livro", lblLivro));
        campos.add(criarInformacao("Leitor", lblLeitor));
        campos.add(criarInformacao("Data do Empréstimo", lblDataEmprestimo));
        campos.add(criarInformacao("Data Prevista", lblDataPrevista));
        campos.add(criarInformacao("Situação", lblSituacao));

        painel.add(titulo, BorderLayout.NORTH);
        painel.add(campos, BorderLayout.CENTER);

        return painel;
    }

    private JPanel criarInformacao(String titulo, JLabel valor) {

        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBackground(Color.WHITE);

        JLabel label = new JLabel(titulo);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        label.setForeground(new Color(100, 116, 139));

        valor.setFont(new Font("Segoe UI", Font.BOLD, 13));
        valor.setForeground(TEXTO);

        painel.add(label);
        painel.add(Box.createVerticalStrut(5));
        painel.add(valor);

        return painel;
    }

    // TABELA DE EMPRÉSTIMOS ACTIVOS

    private JPanel criarTabela() {

        JPanel painel = new JPanel(new BorderLayout(10, 12));
        painel.setBackground(Color.WHITE);

        painel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        new Color(226, 232, 240)
                ),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JPanel painelTopo = new JPanel(new BorderLayout());
        painelTopo.setBackground(Color.WHITE);

        JLabel titulo = new JLabel("Empréstimos por Devolver");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 17));
        titulo.setForeground(TEXTO);

        JButton btnAtualizar = criarBotao(
                "Actualizar",
                AZUL
        );

        btnAtualizar.addActionListener(e -> atualizarTabela());

        painelTopo.add(titulo, BorderLayout.WEST);
        painelTopo.add(btnAtualizar, BorderLayout.EAST);

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
            public boolean isCellEditable(int linha, int coluna) {
                return false;
            }
        };

        tabela = new JTable(modeloTabela);

        tabela.setRowHeight(32);
        tabela.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tabela.setForeground(TEXTO);
        tabela.setGridColor(new Color(226, 232, 240));
        tabela.setSelectionBackground(new Color(219, 234, 254));
        tabela.setSelectionForeground(TEXTO);
        tabela.setShowVerticalLines(false);
        tabela.setFillsViewportHeight(true);
        tabela.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );
        tabela.setAutoCreateRowSorter(true);

        tabela.getTableHeader().setFont(
                new Font("Segoe UI", Font.BOLD, 12)
        );

        tabela.getTableHeader().setBackground(
                new Color(241, 245, 249)
        );

        tabela.getTableHeader().setForeground(TEXTO);
        tabela.getTableHeader().setPreferredSize(
                new Dimension(0, 38)
        );

        // ESTILIZAÇÃO DAS LINHAS

        tabela.setDefaultRenderer(
                Object.class,
                new DefaultTableCellRenderer() {

                    @Override
                    public Component getTableCellRendererComponent(
                            JTable table,
                            Object value,
                            boolean isSelected,
                            boolean hasFocus,
                            int row,
                            int column) {

                        Component componente =
                                super.getTableCellRendererComponent(
                                        table,
                                        value,
                                        isSelected,
                                        hasFocus,
                                        row,
                                        column
                                );

                        if (!isSelected) {

                            componente.setBackground(
                                    row % 2 == 0
                                            ? Color.WHITE
                                            : new Color(248, 250, 252)
                            );

                            componente.setForeground(TEXTO);
                        }

                        if (column == 5 && !isSelected) {

                            String situacao = value == null
                                    ? ""
                                    : value.toString();

                            if (situacao.equals("Atrasado")) {
                                componente.setForeground(VERMELHO);
                            } else {
                                componente.setForeground(VERDE);
                            }

                            setFont(new Font(
                                    "Segoe UI",
                                    Font.BOLD,
                                    12
                            ));

                        } else {

                            setFont(new Font(
                                    "Segoe UI",
                                    Font.PLAIN,
                                    12
                            ));
                        }

                        setBorder(
                                BorderFactory.createEmptyBorder(0, 8, 0, 8)
                        );

                        return componente;
                    }
                }
        );

        JScrollPane scrollPane = new JScrollPane(tabela);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        JButton btnDevolver = criarBotao(
                "Registar Devolução",
                VERDE
        );

        btnDevolver.addActionListener(
                e -> registarDevolucao()
        );

        JPanel painelInferior = new JPanel(
                new FlowLayout(FlowLayout.RIGHT)
        );
        painelInferior.setBackground(Color.WHITE);
        painelInferior.add(btnDevolver);

        painel.add(painelTopo, BorderLayout.NORTH);
        painel.add(scrollPane, BorderLayout.CENTER);
        painel.add(painelInferior, BorderLayout.SOUTH);

        // Actualizar informações ao seleccionar uma linha

        tabela.getSelectionModel().addListSelectionListener(e -> {

            if (!e.getValueIsAdjusting()) {
                preencherInformacoes();
            }

        });

        return painel;
    }

    // BOTÕES


    private JButton criarBotao(String texto, Color cor) {

        JButton botao = new JButton(texto);

        botao.setFont(new Font("Segoe UI", Font.BOLD, 13));
        botao.setForeground(Color.WHITE);
        botao.setBackground(cor);

        // Remover a aparência padrão do Swing
        botao.setFocusPainted(false);
        botao.setBorderPainted(false);
        botao.setContentAreaFilled(true);
        botao.setOpaque(true);

        // Ícone de acordo com a função do botão
        String tipo = texto.equalsIgnoreCase("Actualizar")
                ? "actualizar"
                : "devolver";

        botao.setIcon(new IconeBotao(tipo, Color.WHITE));
        botao.setIconTextGap(9);

        // Espaçamento interno
        botao.setBorder(BorderFactory.createEmptyBorder(12, 18, 12, 18));

        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return botao;
    }

    // ÍCONES PERSONALIZADOS
    private static class IconeBotao implements Icon {

        private final String tipo;
        private final Color cor;

        public IconeBotao(String tipo, Color cor) {
            this.tipo = tipo;
            this.cor = cor;
        }

        @Override
        public int getIconWidth() {
            return 22;
        }

        @Override
        public int getIconHeight() {
            return 22;
        }

        @Override
        public void paintIcon(Component componente, Graphics g, int x, int y) {

            Graphics2D g2 = (Graphics2D) g.create();

            g2.translate(x, y);
            g2.setColor(cor);
            g2.setStroke(new BasicStroke(
                    2f,
                    BasicStroke.CAP_ROUND,
                    BasicStroke.JOIN_ROUND
            ));

            if (tipo.equals("actualizar")) {

                g2.drawArc(3, 3, 16, 16, 45, 285);
                g2.drawLine(16, 3, 20, 3);
                g2.drawLine(20, 3, 20, 7);

            } else if (tipo.equals("devolver")) {

                g2.drawLine(3, 11, 19, 11);
                g2.drawLine(3, 11, 9, 5);
                g2.drawLine(3, 11, 9, 17);
                g2.drawRoundRect(9, 4, 11, 14, 3, 3);
            }

            g2.dispose();
        }
    }


    // ACTUALIZAR TABELA E ESTATÍSTICAS

    public void atualizarTabela() {

        if (modeloTabela == null) {
            return;
        }

        modeloTabela.setRowCount(0);

        int total = 0;
        int atrasados = 0;
        int noPrazo = 0;

        LocalDate hoje = LocalDate.now();

        for (int i = 0;
             i < emprestimoService.getQuantidadeEmprestimos();
             i++) {

            Emprestimo emprestimo =
                    emprestimoService.getEmprestimos().consultar(i);

            if (emprestimo == null || !emprestimo.isAtivo()) {
                continue;
            }

            total++;

            LocalDate dataPrevista =
                    emprestimo.getDataDevolucaoPrevista();

            String situacao;

            if (dataPrevista != null && dataPrevista.isBefore(hoje)) {

                situacao = "Atrasado";
                atrasados++;

            } else {

                situacao = "Dentro do prazo";
                noPrazo++;
            }

            modeloTabela.addRow(new Object[]{

                    emprestimo.getCodigo(),

                    emprestimo.getLivro() != null
                            ? emprestimo.getLivro().getTitulo()
                            : "-",

                    emprestimo.getLeitor() != null
                            ? emprestimo.getLeitor().getNome()
                            : "-",

                    emprestimo.getDataEmprestimo(),

                    dataPrevista != null
                            ? dataPrevista
                            : "-",

                    situacao
            });
        }

        lblPendentes.setText(String.valueOf(total));
        lblAtrasados.setText(String.valueOf(atrasados));
        lblNoPrazo.setText(String.valueOf(noPrazo));

        limparInformacoes();
    }

    // PREENCHER INFORMAÇÕES DO EMPRÉSTIMO

    private void preencherInformacoes() {

        int linhaSelecionada = tabela.getSelectedRow();

        if (linhaSelecionada == -1) {
            limparInformacoes();
            return;
        }

        int linhaModelo =
                tabela.convertRowIndexToModel(linhaSelecionada);

        lblCodigo.setText(
                modeloTabela.getValueAt(linhaModelo, 0).toString()
        );

        lblLivro.setText(
                modeloTabela.getValueAt(linhaModelo, 1).toString()
        );

        lblLeitor.setText(
                modeloTabela.getValueAt(linhaModelo, 2).toString()
        );

        lblDataEmprestimo.setText(
                modeloTabela.getValueAt(linhaModelo, 3).toString()
        );

        lblDataPrevista.setText(
                modeloTabela.getValueAt(linhaModelo, 4).toString()
        );

        String situacao = modeloTabela.getValueAt(
                linhaModelo, 5
        ).toString();

        lblSituacao.setText(situacao);

        if (situacao.equals("Atrasado")) {
            lblSituacao.setForeground(VERMELHO);
        } else {
            lblSituacao.setForeground(VERDE);
        }
    }

    // REGISTAR DEVOLUÇÃO

    private void registarDevolucao() {

        int linhaSelecionada = tabela.getSelectedRow();

        if (linhaSelecionada == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione um empréstimo na tabela.",
                    "Atenção",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int linhaModelo =
                tabela.convertRowIndexToModel(linhaSelecionada);

        String codigo = modeloTabela.getValueAt(
                linhaModelo, 0
        ).toString();

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
                        "Não foi possível registar a devolução.\n"
                                + "Verifique se o empréstimo existe e ainda está activo.",
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

    // LIMPAR INFORMAÇÕES

    private void limparInformacoes() {

        lblCodigo.setText("-");
        lblLivro.setText("-");
        lblLeitor.setText("-");
        lblDataEmprestimo.setText("-");
        lblDataPrevista.setText("-");
        lblSituacao.setText("-");
        lblSituacao.setForeground(TEXTO);

        if (tabela != null) {
            tabela.clearSelection();
        }
    }
}
