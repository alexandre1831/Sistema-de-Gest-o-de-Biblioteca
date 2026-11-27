
package interfaces;

import modelos.Emprestimo;
import servicos.EmprestimoService;
import estruturas.MinhaLista;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TelaEmprestimos extends JPanel {

    private final EmprestimoService emprestimoService;

    private JTextField txtCodigoLivro;
    private JTextField txtCodigoLeitor;
    private JTextField txtDiasPrazo;
    private JTextField txtPesquisa;

    private JLabel lblTotal;
    private JLabel lblAtivos;
    private JLabel lblDevolvidos;

    private JTable tabela;
    private DefaultTableModel modeloTabela;

    private final Color AZUL = new Color(37, 99, 235);
    private final Color FUNDO = new Color(245, 247, 250);
    private final Color TEXTO = new Color(30, 41, 59);
    private final Color VERDE = new Color(22, 163, 74);
    private final Color LARANJA = new Color(217, 119, 6);

    public TelaEmprestimos(EmprestimoService emprestimoService) {

        this.emprestimoService = emprestimoService;

        setLayout(new BorderLayout(20, 20));
        setBackground(FUNDO);
        setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        criarComponentes();
        atualizarTabela();
    }

    private void criarComponentes() {

        JPanel painelSuperior = new JPanel(new BorderLayout(15, 15));
        painelSuperior.setBackground(FUNDO);

        JPanel painelCabecalho = new JPanel();
        painelCabecalho.setLayout(new BoxLayout(painelCabecalho, BoxLayout.Y_AXIS));
        painelCabecalho.setBackground(FUNDO);

        JLabel titulo = new JLabel("Gestão de Empréstimos");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 27));
        titulo.setForeground(TEXTO);

        JLabel descricao = new JLabel(
                "Registe, consulte e acompanhe os empréstimos da biblioteca."
        );
        descricao.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        descricao.setForeground(new Color(100, 116, 139));

        painelCabecalho.add(titulo);
        painelCabecalho.add(Box.createVerticalStrut(5));
        painelCabecalho.add(descricao);

        painelSuperior.add(painelCabecalho, BorderLayout.NORTH);
        painelSuperior.add(criarPainelEstatisticas(), BorderLayout.CENTER);

        add(painelSuperior, BorderLayout.NORTH);

        JPanel painelCentro = new JPanel(new BorderLayout(15, 15));
        painelCentro.setBackground(FUNDO);

        painelCentro.add(criarFormulario(), BorderLayout.NORTH);
        painelCentro.add(criarTabela(), BorderLayout.CENTER);

        add(painelCentro, BorderLayout.CENTER);
    }

    // CARTÕES DE ESTATÍSTICAS

    private JPanel criarPainelEstatisticas() {

        JPanel painel = new JPanel(new GridLayout(1, 3, 15, 0));
        painel.setBackground(FUNDO);
        painel.setBorder(BorderFactory.createEmptyBorder(18, 0, 0, 0));

        lblTotal = criarCartao(painel, "Total de Empréstimos", AZUL);
        lblAtivos = criarCartao(painel, "Empréstimos Activos", LARANJA);
        lblDevolvidos = criarCartao(painel, "Livros Devolvidos", VERDE);

        return painel;
    }

    private JLabel criarCartao(
            JPanel painel,
            String titulo,
            Color cor) {

        JPanel cartao = new JPanel(new BorderLayout(5, 8));
        cartao.setBackground(Color.WHITE);
        cartao.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240)),
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

    // FORMULÁRIO

    private JPanel criarFormulario() {

        JPanel painel = new JPanel(new BorderLayout(10, 15));
        painel.setBackground(Color.WHITE);
        painel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240)),
                BorderFactory.createEmptyBorder(18, 20, 18, 20)
        ));

        JLabel titulo = new JLabel("Registar Novo Empréstimo");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 17));
        titulo.setForeground(TEXTO);

        JPanel campos = new JPanel(new GridLayout(1, 3, 15, 0));
        campos.setBackground(Color.WHITE);

        txtCodigoLivro = new JTextField();
        txtCodigoLeitor = new JTextField();
        txtDiasPrazo = new JTextField("7");

        estilizarCampo(txtCodigoLivro);
        estilizarCampo(txtCodigoLeitor);
        estilizarCampo(txtDiasPrazo);

        campos.add(criarCampo("Código do Livro", txtCodigoLivro));
        campos.add(criarCampo("Código do Leitor", txtCodigoLeitor));
        campos.add(criarCampo("Prazo em dias", txtDiasPrazo));

        JButton btnRegistar = criarBotao("Registar Empréstimo", AZUL);
        btnRegistar.addActionListener(e -> registarEmprestimo());

        JPanel painelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelInferior.setBackground(Color.WHITE);
        painelInferior.add(btnRegistar);

        painel.add(titulo, BorderLayout.NORTH);
        painel.add(campos, BorderLayout.CENTER);
        painel.add(painelInferior, BorderLayout.SOUTH);

        return painel;
    }

    private JPanel criarCampo(String nome, JTextField campo) {

        JPanel painel = new JPanel(new BorderLayout(5, 7));
        painel.setBackground(Color.WHITE);

        JLabel label = new JLabel(nome);
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));
        label.setForeground(new Color(71, 85, 105));

        painel.add(label, BorderLayout.NORTH);
        painel.add(campo, BorderLayout.CENTER);

        return painel;
    }

    private void estilizarCampo(JTextField campo) {

        campo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        campo.setPreferredSize(new Dimension(150, 35));
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(203, 213, 225)),
                BorderFactory.createEmptyBorder(5, 9, 5, 9)
        ));
    }

    // TABELA

    private JPanel criarTabela() {

        JPanel painel = new JPanel(new BorderLayout(10, 12));
        painel.setBackground(Color.WHITE);
        painel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240)),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JPanel painelTopo = new JPanel(new BorderLayout());
        painelTopo.setBackground(Color.WHITE);

        JLabel titulo = new JLabel("Lista de Empréstimos");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 17));
        titulo.setForeground(TEXTO);

        JPanel pesquisa = new JPanel(new FlowLayout(FlowLayout.RIGHT, 7, 0));
        pesquisa.setBackground(Color.WHITE);

        txtPesquisa = new JTextField(12);
        estilizarCampo(txtPesquisa);

        JButton btnPesquisar = criarBotao("Pesquisar", AZUL);
        JButton btnListar = criarBotao("Mostrar Todos", new Color(71, 85, 105));
        JButton btnDevolver = criarBotao("Registar Devolução", VERDE);

        btnPesquisar.addActionListener(e -> pesquisarEmprestimo());
        btnListar.addActionListener(e -> atualizarTabela());
        btnDevolver.addActionListener(e -> registarDevolucao());

        pesquisa.add(txtPesquisa);
        pesquisa.add(btnPesquisar);
        pesquisa.add(btnListar);
        pesquisa.add(btnDevolver);

        painelTopo.add(titulo, BorderLayout.WEST);
        painelTopo.add(pesquisa, BorderLayout.EAST);

        modeloTabela = new DefaultTableModel(
                new String[]{
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
        tabela.setAutoCreateRowSorter(true);

        tabela.getTableHeader().setFont(
                new Font("Segoe UI", Font.BOLD, 12)
        );
        tabela.getTableHeader().setBackground(new Color(241, 245, 249));
        tabela.getTableHeader().setForeground(TEXTO);
        tabela.getTableHeader().setPreferredSize(new Dimension(0, 38));

        // Cores alternadas e estado do empréstimo
        tabela.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {

            @Override
            public Component getTableCellRendererComponent(
                    JTable table,
                    Object value,
                    boolean isSelected,
                    boolean hasFocus,
                    int row,
                    int column) {

                Component componente = super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column
                );

                if (!isSelected) {
                    componente.setBackground(
                            row % 2 == 0
                                    ? Color.WHITE
                                    : new Color(248, 250, 252)
                    );
                    componente.setForeground(TEXTO);
                }

                if (column == 6 && !isSelected) {
                    String estado = value == null ? "" : value.toString();

                    if (estado.equals("Activo")) {
                        componente.setForeground(LARANJA);
                    } else {
                        componente.setForeground(VERDE);
                    }

                    setFont(new Font("Segoe UI", Font.BOLD, 12));
                } else {
                    setFont(new Font("Segoe UI", Font.PLAIN, 12));
                }

                setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));

                return componente;
            }
        });

        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBorder(BorderFactory.createEmptyBorder());

        painel.add(painelTopo, BorderLayout.NORTH);
        painel.add(scroll, BorderLayout.CENTER);

        return painel;
    }


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

        // Escolher o ícone conforme a função
        String tipo;

        if (texto.equalsIgnoreCase("Pesquisar")) {
            tipo = "pesquisar";

        } else if (texto.equalsIgnoreCase("Mostrar Todos")) {
            tipo = "listar";

        } else if (texto.equalsIgnoreCase("Registar Devolução")) {
            tipo = "devolver";

        } else {
            tipo = "adicionar";
        }

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

            if (tipo.equals("pesquisar")) {

                g2.drawOval(3, 3, 12, 12);
                g2.drawLine(14, 14, 20, 20);

            } else if (tipo.equals("listar")) {

                g2.drawLine(5, 5, 19, 5);
                g2.drawLine(5, 11, 19, 11);
                g2.drawLine(5, 17, 19, 17);
                g2.fillOval(1, 4, 2, 2);
                g2.fillOval(1, 10, 2, 2);
                g2.fillOval(1, 16, 2, 2);

            } else if (tipo.equals("devolver")) {

                g2.drawLine(3, 11, 19, 11);
                g2.drawLine(3, 11, 9, 5);
                g2.drawLine(3, 11, 9, 17);
                g2.drawRoundRect(9, 4, 11, 14, 3, 3);

            } else if (tipo.equals("adicionar")) {

                g2.drawLine(11, 3, 11, 19);
                g2.drawLine(3, 11, 19, 11);
            }

            g2.dispose();
        }
    }


    // REGISTAR EMPRÉSTIMO

    private void registarEmprestimo() {

        try {

            String codigoLivro = txtCodigoLivro.getText().trim();
            String codigoLeitor = txtCodigoLeitor.getText().trim();

            if (codigoLivro.isEmpty() || codigoLeitor.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Preencha o código do livro e do leitor.",
                        "Campos obrigatórios",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            int diasPrazo = Integer.parseInt(txtDiasPrazo.getText().trim());

            if (diasPrazo <= 0) {
                JOptionPane.showMessageDialog(
                        this,
                        "O prazo deve ser superior a zero dias."
                );
                return;
            }

            Emprestimo emprestimo = emprestimoService.registarEmprestimo(
                    codigoLivro,
                    codigoLeitor,
                    diasPrazo
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Empréstimo registado com sucesso!\n\n"
                            + "Código: " + emprestimo.getCodigo()
                            + "\nLivro: " + emprestimo.getLivro().getTitulo()
                            + "\nLeitor: " + emprestimo.getLeitor().getNome()
                            + "\nData prevista: "
                            + emprestimo.getDataDevolucaoPrevista(),
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE
            );

            limparCampos();
            atualizarTabela();

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Introduza um prazo válido em dias.",
                    "Prazo inválido",
                    JOptionPane.WARNING_MESSAGE
            );

        } catch (IllegalArgumentException | IllegalStateException e) {

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage(),
                    "Erro no empréstimo",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    // ACTUALIZAR TABELA E ESTATÍSTICAS

    public void atualizarTabela() {

        if (modeloTabela == null) {
            return;
        }

        modeloTabela.setRowCount(0);

        MinhaLista<Emprestimo> emprestimos =
                emprestimoService.getEmprestimos();

        int total = emprestimos.getTamanho();
        int ativos = 0;
        int devolvidos = 0;

        for (int i = 0; i < total; i++) {

            Emprestimo emprestimo = emprestimos.consultar(i);

            if (emprestimo == null) {
                continue;
            }

            adicionarLinha(emprestimo);

            if (emprestimo.isAtivo()) {
                ativos++;
            } else {
                devolvidos++;
            }
        }

        lblTotal.setText(String.valueOf(total));
        lblAtivos.setText(String.valueOf(ativos));
        lblDevolvidos.setText(String.valueOf(devolvidos));
    }

    private void adicionarLinha(Emprestimo emprestimo) {

        modeloTabela.addRow(new Object[]{

                emprestimo.getCodigo(),

                emprestimo.getLivro() != null
                        ? emprestimo.getLivro().getTitulo()
                        : "-",

                emprestimo.getLeitor() != null
                        ? emprestimo.getLeitor().getNome()
                        : "-",

                emprestimo.getDataEmprestimo(),

                emprestimo.getDataDevolucaoPrevista(),

                emprestimo.getDataDevolucaoReal() == null
                        ? "-"
                        : emprestimo.getDataDevolucaoReal(),

                emprestimo.isAtivo()
                        ? "Activo"
                        : "Devolvido"
        });
    }

    // PESQUISAR EMPRÉSTIMO

    private void pesquisarEmprestimo() {

        String codigo = txtPesquisa.getText().trim();

        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Introduza o código do empréstimo."
            );
            return;
        }

        modeloTabela.setRowCount(0);

        Emprestimo emprestimo =
                emprestimoService.buscarEmprestimoPorCodigo(codigo);

        if (emprestimo != null) {
            adicionarLinha(emprestimo);
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Empréstimo não encontrado."
            );
        }
    }

    // REGISTAR DEVOLUÇÃO

    private void registarDevolucao() {

        int linhaSelecionada = tabela.getSelectedRow();

        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione um empréstimo na tabela.",
                    "Nenhum empréstimo seleccionado",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        // Converter o índice visual para o índice correcto da tabela
        int linhaModelo = tabela.convertRowIndexToModel(linhaSelecionada);

        String codigo = modeloTabela.getValueAt(
                linhaModelo, 0
        ).toString();

        int confirmacao = JOptionPane.showConfirmDialog(
                this,
                "Deseja confirmar a devolução deste livro?",
                "Confirmar Devolução",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacao == JOptionPane.YES_OPTION) {

            boolean resultado =
                    emprestimoService.registarDevolucao(codigo);

            if (resultado) {

                JOptionPane.showMessageDialog(
                        this,
                        "Devolução registada com sucesso!",
                        "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE
                );

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Não foi possível registar a devolução.\n"
                                + "Verifique se o empréstimo existe e ainda está activo.",
                        "Erro",
                        JOptionPane.WARNING_MESSAGE
                );
            }

            atualizarTabela();
        }
    }

    // LIMPAR FORMULÁRIO

    private void limparCampos() {

        txtCodigoLivro.setText("");
        txtCodigoLeitor.setText("");
        txtDiasPrazo.setText("7");

        txtCodigoLivro.requestFocus();
    }
}
