
package interfaces;

import modelos.Emprestimo;
import servicos.EmprestimoService;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
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

    private final Color FUNDO = new Color(245, 247, 250);
    private final Color AZUL = new Color(40, 90, 160);
    private final Color VERDE = new Color(30, 145, 100);
    private final Color LARANJA = new Color(220, 135, 40);
    private final Color TEXTO = new Color(45, 55, 72);

    public TelaHistoricos(EmprestimoService emprestimoService) {

        this.emprestimoService = emprestimoService;

        configurarPainel();
        criarComponentes();
        atualizarTabela();
    }

    private void configurarPainel() {

        setLayout(new BorderLayout(18, 18));
        setBackground(FUNDO);

        setBorder(BorderFactory.createEmptyBorder(
                22, 25, 22, 25
        ));
    }

    private void criarComponentes() {

        add(criarCabecalho(), BorderLayout.NORTH);

        JPanel painelPrincipal = new JPanel(new BorderLayout(15, 18));
        painelPrincipal.setOpaque(false);

        painelPrincipal.add(criarCartoes(), BorderLayout.NORTH);
        painelPrincipal.add(criarTabela(), BorderLayout.CENTER);

        add(painelPrincipal, BorderLayout.CENTER);
    }

    // CABEÇALHO
    private JPanel criarCabecalho() {

        JPanel painel = new JPanel(new BorderLayout());
        painel.setOpaque(false);

        JPanel textos = new JPanel();
        textos.setLayout(new BoxLayout(textos, BoxLayout.Y_AXIS));
        textos.setOpaque(false);

        JLabel titulo = new JLabel("Histórico de Empréstimos");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 25));
        titulo.setForeground(TEXTO);

        JLabel subtitulo = new JLabel(
                "Consulte e acompanhe todos os registos de empréstimos."
        );
        subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitulo.setForeground(new Color(120, 130, 145));

        textos.add(titulo);
        textos.add(Box.createVerticalStrut(5));
        textos.add(subtitulo);

        painel.add(textos, BorderLayout.WEST);

        return painel;
    }

    // CARTÕES DE ESTATÍSTICAS
    private JPanel criarCartoes() {

        JPanel painel = new JPanel(new GridLayout(1, 3, 15, 0));
        painel.setOpaque(false);
        painel.setPreferredSize(new Dimension(0, 115));

        lblTotal = criarCartao(
                painel,
                "TOTAL DE EMPRÉSTIMOS",
                "Registos realizados",
                "livros",
                AZUL
        );

        lblAtivos = criarCartao(
                painel,
                "EMPRÉSTIMOS ACTIVOS",
                "Ainda não devolvidos",
                "activos",
                LARANJA
        );

        lblDevolvidos = criarCartao(
                painel,
                "LIVROS DEVOLVIDOS",
                "Empréstimos concluídos",
                "devolvidos",
                VERDE
        );

        return painel;
    }

    private JLabel criarCartao(
            JPanel painel,
            String titulo,
            String descricao,
            String tipoIcone,
            Color cor) {

        JPanel cartao = new JPanel(new BorderLayout(12, 5));

        cartao.setBackground(Color.WHITE);
        cartao.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        new Color(225, 230, 236)
                ),
                BorderFactory.createEmptyBorder(15, 17, 15, 17)
        ));

        JPanel conteudo = new JPanel();
        conteudo.setOpaque(false);
        conteudo.setLayout(new BoxLayout(conteudo, BoxLayout.Y_AXIS));

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblTitulo.setForeground(new Color(110, 120, 135));

        JLabel lblValor = new JLabel("0");
        lblValor.setFont(new Font("Segoe UI", Font.BOLD, 27));
        lblValor.setForeground(cor);

        JLabel lblDescricao = new JLabel(descricao);
        lblDescricao.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblDescricao.setForeground(new Color(145, 153, 165));

        conteudo.add(lblTitulo);
        conteudo.add(Box.createVerticalStrut(5));
        conteudo.add(lblValor);
        conteudo.add(Box.createVerticalStrut(3));
        conteudo.add(lblDescricao);

        JLabel icone = new JLabel(new IconeHistorico(tipoIcone, cor));
        icone.setPreferredSize(new Dimension(42, 42));

        cartao.add(conteudo, BorderLayout.CENTER);
        cartao.add(icone, BorderLayout.EAST);

        painel.add(cartao);

        return lblValor;
    }

    // ÁREA DA TABELA E PESQUISA
    private JPanel criarTabela() {

        JPanel painel = new JPanel(new BorderLayout(10, 12));
        painel.setBackground(Color.WHITE);

        painel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        new Color(225, 230, 236)
                ),
                BorderFactory.createEmptyBorder(16, 17, 17, 17)
        ));

        JPanel cabecalho = new JPanel(new BorderLayout());
        cabecalho.setOpaque(false);

        JLabel titulo = new JLabel("Registos de Empréstimos");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titulo.setForeground(TEXTO);

        JLabel descricao = new JLabel(
                "Pesquisa por código, livro ou leitor"
        );
        descricao.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        descricao.setForeground(new Color(130, 140, 150));

        JPanel textos = new JPanel();
        textos.setOpaque(false);
        textos.setLayout(new BoxLayout(textos, BoxLayout.Y_AXIS));
        textos.add(titulo);
        textos.add(Box.createVerticalStrut(4));
        textos.add(descricao);

        cabecalho.add(textos, BorderLayout.WEST);
        cabecalho.add(criarPesquisa(), BorderLayout.EAST);

        painel.add(cabecalho, BorderLayout.NORTH);

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

        tabela.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tabela.setForeground(TEXTO);
        tabela.setRowHeight(36);
        tabela.setShowVerticalLines(false);
        tabela.setShowHorizontalLines(true);
        tabela.setGridColor(new Color(235, 238, 242));
        tabela.setSelectionBackground(new Color(225, 237, 252));
        tabela.setSelectionForeground(TEXTO);
        tabela.setFillsViewportHeight(true);
        tabela.setAutoCreateRowSorter(true);

        tabela.getTableHeader().setFont(
                new Font("Segoe UI", Font.BOLD, 12)
        );
        tabela.getTableHeader().setBackground(
                new Color(240, 243, 247)
        );
        tabela.getTableHeader().setForeground(TEXTO);
        tabela.getTableHeader().setPreferredSize(
                new Dimension(0, 38)
        );
        tabela.getTableHeader().setReorderingAllowed(false);

        // Alternar cores das linhas e destacar o estado
        tabela.setDefaultRenderer(Object.class,
                new DefaultTableCellRenderer() {

                    @Override
                    public Component getTableCellRendererComponent(
                            JTable table,
                            Object value,
                            boolean selected,
                            boolean focus,
                            int row,
                            int column) {

                        Component componente = super.getTableCellRendererComponent(
                                table, value, selected, focus, row, column
                        );

                        setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

                        if (!selected) {
                            setBackground(row % 2 == 0
                                    ? Color.WHITE
                                    : new Color(248, 250, 252));

                            setForeground(TEXTO);
                        }

                        if (column == 6 && value != null && !selected) {

                            String estado = value.toString();

                            if (estado.equals("Devolvido")) {
                                setForeground(VERDE);
                            } else if (estado.equals("Atrasado")) {
                                setForeground(new Color(200, 75, 65));
                            } else {
                                setForeground(AZUL);
                            }

                            setFont(new Font("Segoe UI", Font.BOLD, 12));
                        } else {
                            setFont(new Font("Segoe UI", Font.PLAIN, 12));
                        }

                        return componente;
                    }
                });

        JScrollPane scrollPane = new JScrollPane(tabela);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);

        painel.add(scrollPane, BorderLayout.CENTER);

        return painel;
    }

    private JPanel criarPesquisa() {

        JPanel painel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 7, 0));
        painel.setOpaque(false);

        txtPesquisa = new JTextField(17);
        txtPesquisa.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtPesquisa.setPreferredSize(new Dimension(190, 34));

        JButton btnPesquisar = criarBotao("Pesquisar", AZUL);
        JButton btnActualizar = criarBotao("Actualizar", new Color(85, 100, 120));

        btnPesquisar.addActionListener(e -> pesquisarHistorico());

        btnActualizar.addActionListener(e -> {
            txtPesquisa.setText("");
            atualizarTabela();
        });

        txtPesquisa.addActionListener(e -> pesquisarHistorico());

        painel.add(txtPesquisa);
        painel.add(btnPesquisar);
        painel.add(btnActualizar);

        return painel;
    }

    private JButton criarBotao(String texto, Color cor) {

        JButton botao = new JButton(texto);

        botao.setFont(new Font("Segoe UI", Font.BOLD, 11));
        botao.setForeground(Color.WHITE);
        botao.setBackground(cor);
        botao.setFocusPainted(false);
        botao.setBorderPainted(false);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botao.setPreferredSize(new Dimension(95, 34));

        return botao;
    }

    // ACTUALIZAÇÃO DA TABELA
    public void atualizarTabela() {

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

            String estado = determinarEstado(emprestimo);

            if (emprestimo.isAtivo()) {
                ativos++;
            } else {
                devolvidos++;
            }

            adicionarLinha(emprestimo, estado);
        }

        lblTotal.setText(String.valueOf(total));
        lblAtivos.setText(String.valueOf(ativos));
        lblDevolvidos.setText(String.valueOf(devolvidos));
    }

    // PESQUISA
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

            String codigo = emprestimo.getCodigo() == null
                    ? ""
                    : emprestimo.getCodigo().toLowerCase();

            String livro = emprestimo.getLivro() == null
                    ? ""
                    : emprestimo.getLivro().getTitulo().toLowerCase();

            String leitor = emprestimo.getLeitor() == null
                    ? ""
                    : emprestimo.getLeitor().getNome().toLowerCase();

            if (codigo.contains(pesquisa)
                    || livro.contains(pesquisa)
                    || leitor.contains(pesquisa)) {

                adicionarLinha(
                        emprestimo,
                        determinarEstado(emprestimo)
                );
            }
        }
    }

    // DETERMINAR O ESTADO DO EMPRÉSTIMO
    private String determinarEstado(Emprestimo emprestimo) {

        if (!emprestimo.isAtivo()) {
            return "Devolvido";
        }

        if (emprestimo.getDataDevolucaoPrevista() != null
                && emprestimo.getDataDevolucaoPrevista()
                .isBefore(LocalDate.now())) {

            return "Atrasado";
        }

        return "Activo";
    }

    // ADICIONAR UM REGISTO À TABELA
    private void adicionarLinha(Emprestimo emprestimo, String estado) {

        String livro = emprestimo.getLivro() == null
                ? "-"
                : emprestimo.getLivro().getTitulo();

        String leitor = emprestimo.getLeitor() == null
                ? "-"
                : emprestimo.getLeitor().getNome();

        String dataEmprestimo = emprestimo.getDataEmprestimo() == null
                ? "-"
                : emprestimo.getDataEmprestimo().toString();

        String dataPrevista = emprestimo.getDataDevolucaoPrevista() == null
                ? "-"
                : emprestimo.getDataDevolucaoPrevista().toString();

        String dataReal = emprestimo.getDataDevolucaoReal() == null
                ? "-"
                : emprestimo.getDataDevolucaoReal().toString();

        modeloTabela.addRow(new Object[]{
                emprestimo.getCodigo(),
                livro,
                leitor,
                dataEmprestimo,
                dataPrevista,
                dataReal,
                estado
        });
    }

    // ÍCONES DESENHADOS EM JAVA
    private static class IconeHistorico implements Icon {

        private final String tipo;
        private final Color cor;

        public IconeHistorico(String tipo, Color cor) {
            this.tipo = tipo;
            this.cor = cor;
        }

        @Override
        public int getIconWidth() {
            return 38;
        }

        @Override
        public int getIconHeight() {
            return 38;
        }

        @Override
        public void paintIcon(
                Component componente,
                Graphics g,
                int x,
                int y) {

            Graphics2D g2 = (Graphics2D) g.create();

            g2.translate(x, y);
            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            g2.setColor(new Color(
                    cor.getRed(),
                    cor.getGreen(),
                    cor.getBlue(),
                    25
            ));

            g2.fillRoundRect(0, 0, 38, 38, 12, 12);

            g2.setColor(cor);
            g2.setStroke(new BasicStroke(
                    2f,
                    BasicStroke.CAP_ROUND,
                    BasicStroke.JOIN_ROUND
            ));

            if (tipo.equals("livros")) {

                g2.drawRoundRect(9, 8, 9, 22, 2, 2);
                g2.drawRoundRect(20, 8, 9, 22, 2, 2);
                g2.drawLine(18, 10, 20, 10);

            } else if (tipo.equals("activos")) {

                g2.drawOval(8, 8, 22, 22);
                g2.drawLine(19, 12, 19, 20);
                g2.drawLine(19, 20, 25, 23);

            } else if (tipo.equals("devolvidos")) {

                g2.drawOval(8, 8, 22, 22);
                g2.drawLine(13, 19, 17, 23);
                g2.drawLine(17, 23, 26, 13);
            }

            g2.dispose();
        }
    }
}

