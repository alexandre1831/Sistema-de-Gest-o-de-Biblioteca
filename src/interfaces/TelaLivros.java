
package interfaces;

import modelos.Livro;
import servicos.LivroService;
import estruturas.MinhaLista;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TelaLivros extends JPanel {

    private LivroService livroService;

    private JTextField txtCodigo;
    private JTextField txtTitulo;
    private JTextField txtAutor;
    private JTextField txtEditora;
    private JTextField txtAno;
    private JTextField txtPesquisa;

    private JTable tabela;
    private DefaultTableModel modeloTabela;

    private JLabel lblTotal;
    private JLabel lblDisponiveis;

    private final Color FUNDO = new Color(245, 247, 250);
    private final Color AZUL = new Color(40, 90, 160);
    private final Color VERDE = new Color(35, 145, 100);
    private final Color VERMELHO = new Color(195, 75, 70);
    private final Color TEXTO = new Color(45, 55, 72);

    public TelaLivros(LivroService livroService) {

        this.livroService = livroService;

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

        painelPrincipal.add(criarEstatisticas(), BorderLayout.NORTH);
        painelPrincipal.add(criarFormulario(), BorderLayout.CENTER);
        painelPrincipal.add(criarTabela(), BorderLayout.SOUTH);

        add(painelPrincipal, BorderLayout.CENTER);
    }

    // CABEÇALHO
    private JPanel criarCabecalho() {

        JPanel painel = new JPanel(new BorderLayout());
        painel.setOpaque(false);

        JPanel textos = new JPanel();
        textos.setLayout(new BoxLayout(textos, BoxLayout.Y_AXIS));
        textos.setOpaque(false);

        JLabel titulo = new JLabel("Gestão de Livros");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 25));
        titulo.setForeground(TEXTO);

        JLabel descricao = new JLabel(
                "Cadastre, consulte, pesquise e controle os livros da biblioteca."
        );

        descricao.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        descricao.setForeground(new Color(120, 130, 145));

        textos.add(titulo);
        textos.add(Box.createVerticalStrut(5));
        textos.add(descricao);

        painel.add(textos, BorderLayout.WEST);

        return painel;
    }

    // CARTÕES DE ESTATÍSTICAS
    private JPanel criarEstatisticas() {

        JPanel painel = new JPanel(new GridLayout(1, 2, 15, 0));
        painel.setOpaque(false);
        painel.setPreferredSize(new Dimension(0, 100));

        lblTotal = criarCartao(
                painel,
                "TOTAL DE LIVROS",
                "Livros registados na biblioteca",
                "livros",
                AZUL
        );

        lblDisponiveis = criarCartao(
                painel,
                "LIVROS DISPONÍVEIS",
                "Livros que podem ser emprestados",
                "disponiveis",
                VERDE
        );

        return painel;
    }

    private JLabel criarCartao(
            JPanel painel,
            String titulo,
            String descricao,
            String tipo,
            Color cor) {

        JPanel cartao = new JPanel(new BorderLayout(12, 5));
        cartao.setBackground(Color.WHITE);

        cartao.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        new Color(225, 230, 236)
                ),
                BorderFactory.createEmptyBorder(12, 18, 12, 18)
        ));

        JPanel conteudo = new JPanel();
        conteudo.setOpaque(false);
        conteudo.setLayout(new BoxLayout(conteudo, BoxLayout.Y_AXIS));

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblTitulo.setForeground(new Color(110, 120, 135));

        JLabel lblValor = new JLabel("0");
        lblValor.setFont(new Font("Segoe UI", Font.BOLD, 25));
        lblValor.setForeground(cor);

        JLabel lblDescricao = new JLabel(descricao);
        lblDescricao.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblDescricao.setForeground(new Color(145, 153, 165));

        conteudo.add(lblTitulo);
        conteudo.add(Box.createVerticalStrut(3));
        conteudo.add(lblValor);
        conteudo.add(lblDescricao);

        JLabel icone = new JLabel(new IconeLivro(tipo, cor));
        icone.setPreferredSize(new Dimension(42, 42));

        cartao.add(conteudo, BorderLayout.CENTER);
        cartao.add(icone, BorderLayout.EAST);

        painel.add(cartao);

        return lblValor;
    }

    // FORMULÁRIO
    private JPanel criarFormulario() {

        JPanel painel = new JPanel(new BorderLayout(12, 15));
        painel.setBackground(Color.WHITE);

        painel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        new Color(225, 230, 236)
                ),
                BorderFactory.createEmptyBorder(16, 18, 16, 18)
        ));

        JPanel cabecalho = new JPanel();
        cabecalho.setOpaque(false);
        cabecalho.setLayout(new BoxLayout(cabecalho, BoxLayout.Y_AXIS));

        JLabel titulo = new JLabel("Dados do Livro");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titulo.setForeground(TEXTO);

        JLabel descricao = new JLabel(
                "Preencha os campos para registar um novo livro."
        );
        descricao.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        descricao.setForeground(new Color(135, 145, 155));

        cabecalho.add(titulo);
        cabecalho.add(Box.createVerticalStrut(4));
        cabecalho.add(descricao);

        JPanel campos = new JPanel(new GridLayout(1, 5, 12, 0));
        campos.setOpaque(false);

        txtCodigo = criarCampo();
        txtTitulo = criarCampo();
        txtAutor = criarCampo();
        txtEditora = criarCampo();
        txtAno = criarCampo();

        campos.add(criarCampoFormulario("Código", txtCodigo));
        campos.add(criarCampoFormulario("Título", txtTitulo));
        campos.add(criarCampoFormulario("Autor", txtAutor));
        campos.add(criarCampoFormulario("Editora", txtEditora));
        campos.add(criarCampoFormulario("Ano de publicação", txtAno));

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        botoes.setOpaque(false);

        JButton btnCadastrar = criarBotao("Cadastrar Livro", VERDE);
        JButton btnAtualizar = criarBotao("Actualizar", AZUL);
        JButton btnLimpar = criarBotao("Limpar", new Color(115, 125, 140));

        botoes.add(btnCadastrar);
        botoes.add(btnAtualizar);
        botoes.add(btnLimpar);

        painel.add(cabecalho, BorderLayout.NORTH);
        painel.add(campos, BorderLayout.CENTER);
        painel.add(botoes, BorderLayout.SOUTH);

        btnCadastrar.addActionListener(e -> cadastrarLivro());
        btnAtualizar.addActionListener(e -> atualizarLivro());
        btnLimpar.addActionListener(e -> limparCampos());

        return painel;
    }

    private JTextField criarCampo() {

        JTextField campo = new JTextField();

        campo.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        new Color(215, 222, 230)
                ),
                BorderFactory.createEmptyBorder(5, 9, 5, 9)
        ));

        campo.setPreferredSize(new Dimension(130, 35));

        return campo;
    }

    private JPanel criarCampoFormulario(
            String nome,
            JTextField campo) {

        JPanel painel = new JPanel(new BorderLayout(5, 5));
        painel.setOpaque(false);

        JLabel label = new JLabel(nome);
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
        botao.setPreferredSize(new Dimension(120, 35));

        return botao;
    }

    // TABELA
    private JPanel criarTabela() {

        JPanel painel = new JPanel(new BorderLayout(10, 12));
        painel.setBackground(Color.WHITE);

        painel.setPreferredSize(new Dimension(0, 290));

        painel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        new Color(225, 230, 236)
                ),
                BorderFactory.createEmptyBorder(15, 18, 15, 18)
        ));

        JPanel cabecalho = new JPanel(new BorderLayout());
        cabecalho.setOpaque(false);

        JPanel textos = new JPanel();
        textos.setOpaque(false);
        textos.setLayout(new BoxLayout(textos, BoxLayout.Y_AXIS));

        JLabel titulo = new JLabel("Lista de Livros");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titulo.setForeground(TEXTO);

        JLabel descricao = new JLabel(
                "Consulte os livros e verifique a sua disponibilidade."
        );
        descricao.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        descricao.setForeground(new Color(135, 145, 155));

        textos.add(titulo);
        textos.add(Box.createVerticalStrut(4));
        textos.add(descricao);

        cabecalho.add(textos, BorderLayout.WEST);

        modeloTabela = new DefaultTableModel(
                new Object[]{
                        "Código",
                        "Título",
                        "Autor",
                        "Editora",
                        "Ano",
                        "Disponibilidade"
                }, 0
        ) {
            @Override
            public boolean isCellEditable(int linha, int coluna) {
                return false;
            }
        };

        tabela = new JTable(modeloTabela);

        tabela.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tabela.setForeground(TEXTO);
        tabela.setRowHeight(34);
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

                        if (column == 5 && value != null && !selected) {

                            if (value.toString().equals("Disponível")) {
                                setForeground(VERDE);
                            } else {
                                setForeground(VERMELHO);
                            }

                            setFont(new Font("Segoe UI", Font.BOLD, 12));

                        } else {
                            setFont(new Font("Segoe UI", Font.PLAIN, 12));
                        }

                        return componente;
                    }
                });

        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(Color.WHITE);

        JPanel pesquisa = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 5));
        pesquisa.setOpaque(false);

        txtPesquisa = criarCampo();
        txtPesquisa.setPreferredSize(new Dimension(220, 35));

        JButton btnPesquisar = criarBotao("Pesquisar", AZUL);
        JButton btnListar = criarBotao(
                "Mostrar Todos",
                new Color(85, 105, 130)
        );

        JButton btnEliminar = criarBotao("Eliminar Livro", VERMELHO);

        pesquisa.add(new JLabel("Pesquisar:"));
        pesquisa.add(txtPesquisa);
        pesquisa.add(btnPesquisar);
        pesquisa.add(btnListar);
        pesquisa.add(btnEliminar);

        painel.add(cabecalho, BorderLayout.NORTH);
        painel.add(scroll, BorderLayout.CENTER);
        painel.add(pesquisa, BorderLayout.SOUTH);

        btnPesquisar.addActionListener(e -> pesquisarLivro());

        btnListar.addActionListener(e -> {
            txtPesquisa.setText("");
            atualizarTabela();
        });

        btnEliminar.addActionListener(e -> eliminarLivro());

        txtPesquisa.addActionListener(e -> pesquisarLivro());

        tabela.getSelectionModel().addListSelectionListener(e -> {

            if (!e.getValueIsAdjusting()) {
                preencherFormulario();
            }
        });

        return painel;
    }

    // CADASTRAR LIVRO
    private void cadastrarLivro() {

        try {

            String codigo = txtCodigo.getText().trim();
            String titulo = txtTitulo.getText().trim();
            String autor = txtAutor.getText().trim();
            String editora = txtEditora.getText().trim();
            String textoAno = txtAno.getText().trim();

            if (codigo.isEmpty() || titulo.isEmpty()
                    || autor.isEmpty() || editora.isEmpty()
                    || textoAno.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Preencha todos os campos.",
                        "Atenção",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            int ano = Integer.parseInt(textoAno);

            boolean resultado = livroService.cadastrarLivro(
                    codigo, titulo, autor, editora, ano
            );

            if (resultado) {

                JOptionPane.showMessageDialog(
                        this,
                        "Livro cadastrado com sucesso!",
                        "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE
                );

                limparCampos();
                atualizarTabela();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Já existe um livro com esse código.",
                        "Atenção",
                        JOptionPane.WARNING_MESSAGE
                );
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Introduza um ano de publicação válido.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );

        } catch (IllegalArgumentException e) {

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // ACTUALIZAR LIVRO
    private void atualizarLivro() {

        String codigo = txtCodigo.getText().trim();

        if (codigo.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione um livro ou informe o código."
            );

            return;
        }

        try {

            String titulo = txtTitulo.getText().trim();
            String autor = txtAutor.getText().trim();
            String editora = txtEditora.getText().trim();
            String textoAno = txtAno.getText().trim();

            if (titulo.isEmpty() || autor.isEmpty()
                    || editora.isEmpty() || textoAno.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Preencha todos os campos para actualizar."
                );

                return;
            }

            int ano = Integer.parseInt(textoAno);

            boolean resultado = livroService.atualizarLivro(
                    codigo, titulo, autor, editora, ano
            );

            if (resultado) {

                JOptionPane.showMessageDialog(
                        this,
                        "Livro actualizado com sucesso!"
                );

                limparCampos();
                atualizarTabela();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Não foi possível actualizar o livro."
                );
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Introduza um ano válido."
            );

        } catch (IllegalArgumentException e) {

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // PESQUISAR LIVRO
    private void pesquisarLivro() {

        String pesquisa = txtPesquisa.getText().trim();

        if (pesquisa.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Introduza um código ou título para pesquisar."
            );

            return;
        }

        modeloTabela.setRowCount(0);

        Livro livro = livroService.buscarPorCodigo(pesquisa);

        if (livro != null) {

            adicionarLinha(livro);
            return;
        }

        MinhaLista<Livro> resultados =
                livroService.buscarPorTitulo(pesquisa);

        for (int i = 0; i < resultados.getTamanho(); i++) {

            Livro resultado = resultados.consultar(i);

            if (resultado != null) {
                adicionarLinha(resultado);
            }
        }

        if (resultados.getTamanho() == 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Nenhum livro encontrado."
            );
        }
    }

    // ELIMINAR LIVRO
    private void eliminarLivro() {

        int linhaSelecionada = tabela.getSelectedRow();

        if (linhaSelecionada == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione um livro na tabela."
            );

            return;
        }

        int linha = tabela.convertRowIndexToModel(linhaSelecionada);

        String codigo = modeloTabela.getValueAt(linha, 0).toString();

        int confirmacao = JOptionPane.showConfirmDialog(
                this,
                "Tem a certeza de que deseja eliminar este livro?",
                "Confirmar eliminação",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirmacao == JOptionPane.YES_OPTION) {

            boolean resultado = livroService.removerLivro(codigo);

            if (resultado) {

                JOptionPane.showMessageDialog(
                        this,
                        "Livro eliminado com sucesso!"
                );

                limparCampos();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Não foi possível eliminar o livro. "
                                + "Verifique se possui empréstimo activo."
                );
            }

            atualizarTabela();
        }
    }

    // PREENCHER FORMULÁRIO
    private void preencherFormulario() {

        int linhaSelecionada = tabela.getSelectedRow();

        if (linhaSelecionada == -1) {
            return;
        }

        int linha = tabela.convertRowIndexToModel(linhaSelecionada);

        txtCodigo.setText(modeloTabela.getValueAt(linha, 0).toString());
        txtTitulo.setText(modeloTabela.getValueAt(linha, 1).toString());
        txtAutor.setText(modeloTabela.getValueAt(linha, 2).toString());
        txtEditora.setText(modeloTabela.getValueAt(linha, 3).toString());
        txtAno.setText(modeloTabela.getValueAt(linha, 4).toString());
    }

    // LIMPAR FORMULÁRIO
    private void limparCampos() {

        txtCodigo.setText("");
        txtTitulo.setText("");
        txtAutor.setText("");
        txtEditora.setText("");
        txtAno.setText("");
        txtPesquisa.setText("");

        tabela.clearSelection();
        txtCodigo.requestFocus();
    }

    // ADICIONAR LIVRO À TABELA
    private void adicionarLinha(Livro livro) {

        if (livro == null) {
            return;
        }

        modeloTabela.addRow(new Object[]{
                livro.getCodigo(),
                livro.getTitulo(),
                livro.getAutor(),
                livro.getEditora(),
                livro.getAnoPublicacao(),
                livro.isDisponivel() ? "Disponível" : "Emprestado"
        });
    }

    // ACTUALIZAR TABELA
    public void atualizarTabela() {

        modeloTabela.setRowCount(0);

        MinhaLista<Livro> livros = livroService.listarLivros();

        int total = 0;
        int disponiveis = 0;

        for (int i = 0; i < livros.getTamanho(); i++) {

            Livro livro = livros.consultar(i);

            if (livro != null) {

                adicionarLinha(livro);
                total++;

                if (livro.isDisponivel()) {
                    disponiveis++;
                }
            }
        }

        lblTotal.setText(String.valueOf(total));
        lblDisponiveis.setText(String.valueOf(disponiveis));
    }

    // ÍCONES DESENHADOS EM JAVA
    private static class IconeLivro implements Icon {

        private final String tipo;
        private final Color cor;

        public IconeLivro(String tipo, Color cor) {
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

                g2.drawRoundRect(8, 8, 10, 22, 2, 2);
                g2.drawRoundRect(20, 8, 10, 22, 2, 2);
                g2.drawLine(18, 10, 20, 10);

            } else if (tipo.equals("disponiveis")) {

                g2.drawRoundRect(8, 8, 22, 22, 3, 3);
                g2.drawLine(13, 19, 17, 23);
                g2.drawLine(17, 23, 26, 13);
            }

            g2.dispose();
        }
    }
}
