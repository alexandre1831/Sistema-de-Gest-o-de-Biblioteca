
package interfaces;

import modelos.Leitor;
import servicos.LeitorService;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TelaLeitores extends JPanel {

    private LeitorService leitorService;

    private JTextField txtCodigo;
    private JTextField txtNome;
    private JTextField txtTelefone;
    private JTextField txtEmail;
    private JTextField txtEndereco;
    private JTextField txtPesquisa;

    private JTable tabela;
    private DefaultTableModel modeloTabela;

    private JLabel lblTotal;

    private final Color FUNDO = new Color(245, 247, 250);
    private final Color AZUL = new Color(40, 90, 160);
    private final Color VERDE = new Color(35, 145, 100);
    private final Color VERMELHO = new Color(195, 75, 70);
    private final Color TEXTO = new Color(45, 55, 72);

    public TelaLeitores(LeitorService leitorService) {

        this.leitorService = leitorService;

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

        JPanel painelPrincipal = new JPanel(new BorderLayout(15, 15));
        painelPrincipal.setOpaque(false);

        painelPrincipal.add(criarFormulario(), BorderLayout.NORTH);
        painelPrincipal.add(criarAreaTabela(), BorderLayout.CENTER);

        add(painelPrincipal, BorderLayout.CENTER);
    }

    // CABEÇALHO
    private JPanel criarCabecalho() {

        JPanel painel = new JPanel(new BorderLayout());
        painel.setOpaque(false);

        JPanel textos = new JPanel();
        textos.setLayout(new BoxLayout(textos, BoxLayout.Y_AXIS));
        textos.setOpaque(false);

        JLabel titulo = new JLabel("Gestão de Leitores");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 25));
        titulo.setForeground(TEXTO);

        JLabel subtitulo = new JLabel(
                "Registe, consulte e actualize os dados dos leitores da biblioteca."
        );

        subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitulo.setForeground(new Color(120, 130, 145));

        textos.add(titulo);
        textos.add(Box.createVerticalStrut(5));
        textos.add(subtitulo);

        painel.add(textos, BorderLayout.WEST);

        return painel;
    }

    // FORMULÁRIO
    private JPanel criarFormulario() {

        JPanel painel = new JPanel(new BorderLayout(12, 15));
        painel.setBackground(Color.WHITE);

        painel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        new Color(225, 230, 236)
                ),
                BorderFactory.createEmptyBorder(17, 20, 17, 20)
        ));

        JPanel cabecalho = new JPanel(new BorderLayout());
        cabecalho.setOpaque(false);

        JLabel titulo = new JLabel("Dados do Leitor");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titulo.setForeground(TEXTO);

        JLabel descricao = new JLabel(
                "Preencha os campos para gerir o cadastro."
        );
        descricao.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        descricao.setForeground(new Color(135, 145, 155));

        JPanel textos = new JPanel();
        textos.setOpaque(false);
        textos.setLayout(new BoxLayout(textos, BoxLayout.Y_AXIS));

        textos.add(titulo);
        textos.add(Box.createVerticalStrut(4));
        textos.add(descricao);

        cabecalho.add(textos, BorderLayout.WEST);

        JPanel campos = new JPanel(new GridLayout(2, 3, 15, 12));
        campos.setOpaque(false);

        txtCodigo = criarCampo();
        txtNome = criarCampo();
        txtTelefone = criarCampo();
        txtEmail = criarCampo();
        txtEndereco = criarCampo();

        campos.add(criarCampoFormulario("Código", txtCodigo));
        campos.add(criarCampoFormulario("Nome completo", txtNome));
        campos.add(criarCampoFormulario("Telefone", txtTelefone));
        campos.add(criarCampoFormulario("Email", txtEmail));
        campos.add(criarCampoFormulario("Endereço", txtEndereco));

        JPanel vazio = new JPanel();
        vazio.setOpaque(false);
        campos.add(vazio);

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        botoes.setOpaque(false);

        JButton btnCadastrar = criarBotao("Cadastrar", VERDE);
        JButton btnPesquisar = criarBotao("Pesquisar", AZUL);
        JButton btnAtualizar = criarBotao("Actualizar", new Color(85, 105, 130));
        JButton btnRemover = criarBotao("Remover", VERMELHO);
        JButton btnLimpar = criarBotao("Limpar", new Color(115, 125, 140));

        botoes.add(btnCadastrar);
        botoes.add(btnPesquisar);
        botoes.add(btnAtualizar);
        botoes.add(btnRemover);
        botoes.add(btnLimpar);

        painel.add(cabecalho, BorderLayout.NORTH);
        painel.add(campos, BorderLayout.CENTER);
        painel.add(botoes, BorderLayout.SOUTH);

        btnCadastrar.addActionListener(e -> cadastrarLeitor());
        btnPesquisar.addActionListener(e -> pesquisarLeitor());
        btnAtualizar.addActionListener(e -> atualizarLeitor());
        btnRemover.addActionListener(e -> removerLeitor());
        btnLimpar.addActionListener(e -> limparCampos());

        return painel;
    }

    private JTextField criarCampo() {

        JTextField campo = new JTextField();

        campo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        campo.setPreferredSize(new Dimension(150, 34));

        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        new Color(215, 222, 230)
                ),
                BorderFactory.createEmptyBorder(5, 9, 5, 9)
        ));

        return campo;
    }

    private JPanel criarCampoFormulario(
            String titulo,
            JTextField campo) {

        JPanel painel = new JPanel(new BorderLayout(5, 5));
        painel.setOpaque(false);

        JLabel label = new JLabel(titulo);
        label.setFont(new Font("Segoe UI", Font.BOLD, 11));
        label.setForeground(new Color(90, 100, 115));

        painel.add(label, BorderLayout.NORTH);
        painel.add(campo, BorderLayout.CENTER);

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
        botao.setPreferredSize(new Dimension(105, 35));

        return botao;
    }

    // ÁREA DA TABELA
    private JPanel criarAreaTabela() {

        JPanel painel = new JPanel(new BorderLayout(10, 12));
        painel.setBackground(Color.WHITE);

        painel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        new Color(225, 230, 236)
                ),
                BorderFactory.createEmptyBorder(16, 18, 17, 18)
        ));

        JPanel cabecalho = new JPanel(new BorderLayout());
        cabecalho.setOpaque(false);

        JPanel textos = new JPanel();
        textos.setOpaque(false);
        textos.setLayout(new BoxLayout(textos, BoxLayout.Y_AXIS));

        JLabel titulo = new JLabel("Lista de Leitores");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titulo.setForeground(TEXTO);

        JLabel descricao = new JLabel(
                "Consulte os leitores registados na biblioteca."
        );
        descricao.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        descricao.setForeground(new Color(135, 145, 155));

        textos.add(titulo);
        textos.add(Box.createVerticalStrut(4));
        textos.add(descricao);

        lblTotal = new JLabel("Total: 0 leitores");
        lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblTotal.setForeground(AZUL);

        cabecalho.add(textos, BorderLayout.WEST);
        cabecalho.add(lblTotal, BorderLayout.EAST);

        painel.add(cabecalho, BorderLayout.NORTH);

        modeloTabela = new DefaultTableModel(
                new Object[]{
                        "Código",
                        "Nome",
                        "Telefone",
                        "Email",
                        "Endereço"
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
        tabela.setRowHeight(35);
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

                        Component componente =
                                super.getTableCellRendererComponent(
                                        table, value, selected, focus, row, column
                                );

                        setBorder(BorderFactory.createEmptyBorder(
                                0, 10, 0, 10
                        ));

                        if (!selected) {
                            setBackground(row % 2 == 0
                                    ? Color.WHITE
                                    : new Color(248, 250, 252));

                            setForeground(TEXTO);
                        }

                        setFont(new Font("Segoe UI", Font.PLAIN, 12));

                        return componente;
                    }
                });

        JScrollPane scrollPane = new JScrollPane(tabela);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);

        painel.add(scrollPane, BorderLayout.CENTER);

        JPanel pesquisa = criarPainelPesquisa();
        painel.add(pesquisa, BorderLayout.SOUTH);

        tabela.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                preencherFormulario();
            }
        });

        return painel;
    }

    // PESQUISA
    private JPanel criarPainelPesquisa() {

        JPanel painel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 5));
        painel.setOpaque(false);

        txtPesquisa = criarCampo();
        txtPesquisa.setPreferredSize(new Dimension(250, 35));

        JButton btnPesquisar = criarBotao("Pesquisar", AZUL);
        JButton btnMostrarTodos = criarBotao(
                "Mostrar Todos",
                new Color(85, 105, 130)
        );

        JLabel label = new JLabel("Pesquisar leitor:");
        label.setFont(new Font("Segoe UI", Font.BOLD, 11));
        label.setForeground(TEXTO);

        painel.add(label);
        painel.add(txtPesquisa);
        painel.add(btnPesquisar);
        painel.add(btnMostrarTodos);

        btnPesquisar.addActionListener(e -> pesquisarLeitor());

        btnMostrarTodos.addActionListener(e -> {
            txtPesquisa.setText("");
            atualizarTabela();
        });

        txtPesquisa.addActionListener(e -> pesquisarLeitor());

        return painel;
    }

    // CADASTRAR LEITOR
    private void cadastrarLeitor() {

        try {

            String codigo = txtCodigo.getText().trim();
            String nome = txtNome.getText().trim();
            String telefone = txtTelefone.getText().trim();
            String email = txtEmail.getText().trim();
            String endereco = txtEndereco.getText().trim();

            if (codigo.isEmpty() || nome.isEmpty()
                    || telefone.isEmpty() || email.isEmpty()
                    || endereco.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Preencha todos os campos.",
                        "Atenção",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            leitorService.cadastrarLeitor(
                    codigo, nome, telefone, email, endereco
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Leitor cadastrado com sucesso!",
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE
            );

            limparCampos();
            atualizarTabela();

        } catch (IllegalArgumentException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // PESQUISAR LEITOR
    private void pesquisarLeitor() {

        String pesquisa = txtPesquisa.getText()
                .trim()
                .toLowerCase();

        if (pesquisa.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Informe um código, nome, telefone ou email."
            );

            return;
        }

        modeloTabela.setRowCount(0);

        int encontrados = 0;

        for (int i = 0;
             i < leitorService.getQuantidadeLeitores();
             i++) {

            Leitor leitor = leitorService.listarLeitores().consultar(i);

            if (leitor == null) {
                continue;
            }

            if (leitor.getCodigo().toLowerCase().contains(pesquisa)
                    || leitor.getNome().toLowerCase().contains(pesquisa)
                    || leitor.getTelefone().toLowerCase().contains(pesquisa)
                    || leitor.getEmail().toLowerCase().contains(pesquisa)) {

                adicionarLinha(leitor);
                encontrados++;
            }
        }

        if (encontrados == 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Nenhum leitor encontrado."
            );
        }

        lblTotal.setText("Encontrados: " + encontrados);
    }

    // ACTUALIZAR LEITOR
    private void atualizarLeitor() {

        try {

            String codigo = txtCodigo.getText().trim();
            String nome = txtNome.getText().trim();
            String telefone = txtTelefone.getText().trim();
            String email = txtEmail.getText().trim();
            String endereco = txtEndereco.getText().trim();

            if (codigo.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Seleccione um leitor ou informe o código."
                );

                return;
            }

            if (nome.isEmpty() || telefone.isEmpty()
                    || email.isEmpty() || endereco.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Preencha todos os campos para actualizar."
                );

                return;
            }

            leitorService.atualizarLeitor(
                    codigo, nome, telefone, email, endereco
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Dados actualizados com sucesso!"
            );

            limparCampos();
            atualizarTabela();

        } catch (IllegalArgumentException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // REMOVER LEITOR
    private void removerLeitor() {

        String codigo = txtCodigo.getText().trim();

        if (codigo.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione ou informe o código do leitor."
            );

            return;
        }

        int resposta = JOptionPane.showConfirmDialog(
                this,
                "Tem a certeza que deseja remover este leitor?",
                "Confirmar remoção",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (resposta != JOptionPane.YES_OPTION) {
            return;
        }

        try {

            leitorService.removerLeitor(codigo);

            JOptionPane.showMessageDialog(
                    this,
                    "Leitor removido com sucesso!"
            );

            limparCampos();
            atualizarTabela();

        } catch (IllegalArgumentException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // PREENCHER FORMULÁRIO PELA TABELA
    private void preencherFormulario() {

        int linhaSelecionada = tabela.getSelectedRow();

        if (linhaSelecionada == -1) {
            return;
        }

        // Converter a linha visual para a linha real do modelo.
        int linha = tabela.convertRowIndexToModel(linhaSelecionada);

        txtCodigo.setText(
                modeloTabela.getValueAt(linha, 0).toString()
        );

        txtNome.setText(
                modeloTabela.getValueAt(linha, 1).toString()
        );

        txtTelefone.setText(
                modeloTabela.getValueAt(linha, 2).toString()
        );

        txtEmail.setText(
                modeloTabela.getValueAt(linha, 3).toString()
        );

        txtEndereco.setText(
                modeloTabela.getValueAt(linha, 4).toString()
        );
    }

    private void preencherCampos(Leitor leitor) {

        txtCodigo.setText(leitor.getCodigo());
        txtNome.setText(leitor.getNome());
        txtTelefone.setText(leitor.getTelefone());
        txtEmail.setText(leitor.getEmail());
        txtEndereco.setText(leitor.getEndereco());
    }

    // LIMPAR FORMULÁRIO
    private void limparCampos() {

        txtCodigo.setText("");
        txtNome.setText("");
        txtTelefone.setText("");
        txtEmail.setText("");
        txtEndereco.setText("");
        txtPesquisa.setText("");

        tabela.clearSelection();
    }

    // ADICIONAR LEITOR À TABELA
    private void adicionarLinha(Leitor leitor) {

        modeloTabela.addRow(new Object[]{
                leitor.getCodigo(),
                leitor.getNome(),
                leitor.getTelefone(),
                leitor.getEmail(),
                leitor.getEndereco()
        });
    }

    // ACTUALIZAR TABELA
    public void atualizarTabela() {

        modeloTabela.setRowCount(0);

        int total = leitorService.getQuantidadeLeitores();

        for (int i = 0; i < total; i++) {

            Leitor leitor = leitorService.listarLeitores().consultar(i);

            if (leitor != null) {
                adicionarLinha(leitor);
            }
        }

        lblTotal.setText("Total: " + modeloTabela.getRowCount() + " leitores");
    }
}
