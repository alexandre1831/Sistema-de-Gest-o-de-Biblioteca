
package interfaces;

import modelos.Emprestimo;
import servicos.LivroService;
import servicos.LeitorService;
import servicos.EmprestimoService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
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

    private JProgressBar barraDisponibilidade;
    private JProgressBar barraAtivos;
    private JProgressBar barraDevolvidos;
    private JProgressBar barraAtrasados;

    private JLabel lblResumoDisponibilidade;
    private JLabel lblResumoEmprestimos;

    private final Color FUNDO = new Color(245, 247, 250);
    private final Color AZUL = new Color(42, 105, 170);
    private final Color TEXTO = new Color(40, 50, 70);
    private final Color CINZENTO = new Color(105, 115, 130);

    private final Font FONTE_TITULO =
            new Font("Segoe UI", Font.BOLD, 25);

    private final Font FONTE_NORMAL =
            new Font("Segoe UI", Font.PLAIN, 14);

    private final Font FONTE_NEGRITO =
            new Font("Segoe UI", Font.BOLD, 14);

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
        setBackground(FUNDO);

        setBorder(
                new EmptyBorder(25, 25, 20, 25)
        );
    }

    // Construir interface
    private void criarComponentes() {

        criarCabecalho();

        JPanel conteudo = new JPanel();

        conteudo.setLayout(
                new BoxLayout(conteudo, BoxLayout.Y_AXIS)
        );

        conteudo.setBackground(FUNDO);

        conteudo.setBorder(
                new EmptyBorder(20, 0, 10, 5)
        );

        criarTituloSecao(conteudo, "Indicadores gerais");

        criarCartoesEstatisticos(conteudo);

        conteudo.add(Box.createVerticalStrut(25));

        criarTituloSecao(conteudo, "Análise da biblioteca");

        criarPainelAnalise(conteudo);

        JScrollPane scroll = new JScrollPane(conteudo);

        scroll.setBorder(null);
        scroll.setBackground(FUNDO);

        scroll.getViewport().setBackground(FUNDO);

        scroll.getVerticalScrollBar().setUnitIncrement(16);

        add(scroll, BorderLayout.CENTER);

        criarRodape();
    }

    // Cabeçalho
    private void criarCabecalho() {

        JPanel cabecalho = new JPanel(new BorderLayout());

        cabecalho.setOpaque(false);

        JPanel painelTitulo = new JPanel();
        painelTitulo.setOpaque(false);

        painelTitulo.setLayout(
                new BoxLayout(painelTitulo, BoxLayout.Y_AXIS)
        );

        JLabel titulo = new JLabel("Relatórios da Biblioteca");

        titulo.setFont(FONTE_TITULO);
        titulo.setForeground(TEXTO);

        JLabel subtitulo = new JLabel(
                "Acompanhe os indicadores e o desempenho da biblioteca."
        );

        subtitulo.setFont(FONTE_NORMAL);
        subtitulo.setForeground(CINZENTO);

        subtitulo.setBorder(
                new EmptyBorder(5, 0, 0, 0)
        );

        painelTitulo.add(titulo);
        painelTitulo.add(subtitulo);

        JButton btnAtualizar = new JButton("Actualizar");

        btnAtualizar.setFont(FONTE_NEGRITO);
        btnAtualizar.setForeground(Color.WHITE);
        btnAtualizar.setBackground(AZUL);

        btnAtualizar.setIcon(
                new IconeRelatorio("actualizar", Color.WHITE)
        );

        btnAtualizar.setIconTextGap(10);
        btnAtualizar.setFocusPainted(false);
        btnAtualizar.setBorderPainted(false);
        btnAtualizar.setOpaque(true);

        btnAtualizar.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );

        btnAtualizar.setBorder(
                new EmptyBorder(12, 18, 12, 18)
        );

        btnAtualizar.addActionListener(
                e -> atualizarRelatorios()
        );

        cabecalho.add(painelTitulo, BorderLayout.WEST);
        cabecalho.add(btnAtualizar, BorderLayout.EAST);

        add(cabecalho, BorderLayout.NORTH);
    }

    // Títulos das secções
    private void criarTituloSecao(
            JPanel painel,
            String texto) {

        JLabel titulo = new JLabel(texto);

        titulo.setFont(
                new Font("Segoe UI", Font.BOLD, 17)
        );

        titulo.setForeground(TEXTO);

        titulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        titulo.setBorder(
                new EmptyBorder(0, 0, 15, 0)
        );

        painel.add(titulo);
    }

    // Criar os nove cartões
    private void criarCartoesEstatisticos(JPanel conteudo) {

        JPanel painel = new JPanel(
                new GridLayout(3, 3, 16, 16)
        );

        painel.setBackground(FUNDO);

        painel.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 390)
        );

        lblTotalLivros = criarCartao(
                painel, "Total de Livros",
                "livros", new Color(42, 105, 170)
        );

        lblLivrosDisponiveis = criarCartao(
                painel, "Livros Disponíveis",
                "disponiveis", new Color(30, 150, 105)
        );

        lblLivrosEmprestados = criarCartao(
                painel, "Livros Emprestados",
                "emprestados", new Color(220, 145, 45)
        );

        lblTotalLeitores = criarCartao(
                painel, "Total de Leitores",
                "leitores", new Color(130, 90, 190)
        );

        lblTotalEmprestimos = criarCartao(
                painel, "Total de Empréstimos",
                "emprestimos", new Color(40, 130, 180)
        );

        lblEmprestimosAtivos = criarCartao(
                painel, "Empréstimos Activos",
                "activos", new Color(30, 145, 125)
        );

        lblEmprestimosDevolvidos = criarCartao(
                painel, "Empréstimos Devolvidos",
                "devolvidos", new Color(45, 155, 95)
        );

        lblEmprestimosAtrasados = criarCartao(
                painel, "Empréstimos Atrasados",
                "atrasados", new Color(210, 75, 75)
        );

        lblDisponibilidade = criarCartao(
                painel, "Disponibilidade dos Livros",
                "disponibilidade", AZUL
        );

        painel.setAlignmentX(Component.LEFT_ALIGNMENT);

        conteudo.add(painel);
    }

    // Criar um cartão estatístico
    private JLabel criarCartao(
            JPanel painel,
            String titulo,
            String tipo,
            Color cor) {

        JPanel cartao = new JPanel(new BorderLayout(10, 10));

        cartao.setBackground(Color.WHITE);

        cartao.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(225, 230, 237)
                        ),
                        new EmptyBorder(17, 17, 17, 17)
                )
        );

        // Nome do indicador
        JLabel lblTitulo = new JLabel(titulo);

        lblTitulo.setFont(
                new Font("Segoe UI", Font.PLAIN, 13)
        );

        lblTitulo.setForeground(CINZENTO);

        // Valor
        JLabel lblValor = new JLabel("0");

        lblValor.setFont(
                new Font("Segoe UI", Font.BOLD, 29)
        );

        lblValor.setForeground(cor);

        // Ícone
        JLabel lblIcone = new JLabel(
                new IconeRelatorio(tipo, cor)
        );

        lblIcone.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        lblIcone.setPreferredSize(
                new Dimension(48, 48)
        );

        JPanel painelSuperior = new JPanel(new BorderLayout());

        painelSuperior.setOpaque(false);

        painelSuperior.add(
                lblTitulo, BorderLayout.CENTER
        );

        painelSuperior.add(
                lblIcone, BorderLayout.EAST
        );

        cartao.add(
                painelSuperior, BorderLayout.NORTH
        );

        cartao.add(
                lblValor, BorderLayout.CENTER
        );

        painel.add(cartao);

        return lblValor;
    }

    // Painel de análise
    private void criarPainelAnalise(JPanel conteudo) {

        JPanel painelAnalise = new JPanel(
                new GridLayout(1, 2, 16, 16)
        );

        painelAnalise.setBackground(FUNDO);

        painelAnalise.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 205)
        );

        // Cartão de disponibilidade
        JPanel cartaoDisponibilidade = criarCartaoAnalise(
                "Disponibilidade dos livros",
                "percentagem"
        );

        barraDisponibilidade = criarBarra(
                new Color(30, 150, 105)
        );

        lblResumoDisponibilidade = new JLabel(
                "0 de 0 livros disponíveis"
        );

        lblResumoDisponibilidade.setFont(FONTE_NORMAL);
        lblResumoDisponibilidade.setForeground(CINZENTO);

        cartaoDisponibilidade.add(
                barraDisponibilidade, BorderLayout.CENTER
        );

        cartaoDisponibilidade.add(
                lblResumoDisponibilidade, BorderLayout.SOUTH
        );

        // Cartão de situação dos empréstimos
        JPanel cartaoEmprestimos = criarCartaoAnalise(
                "Situação dos empréstimos",
                "emprestimos"
        );

        JPanel painelBarras = new JPanel(
                new GridLayout(3, 1, 5, 5)
        );

        painelBarras.setOpaque(false);

        barraAtivos = criarBarra(
                new Color(42, 105, 170)
        );

        barraDevolvidos = criarBarra(
                new Color(30, 150, 105)
        );

        barraAtrasados = criarBarra(
                new Color(210, 75, 75)
        );

        painelBarras.add(
                criarLinhaBarra("Activos", barraAtivos)
        );

        painelBarras.add(
                criarLinhaBarra("Devolvidos", barraDevolvidos)
        );

        painelBarras.add(
                criarLinhaBarra("Atrasados", barraAtrasados)
        );

        lblResumoEmprestimos = new JLabel(
                "Total de empréstimos: 0"
        );

        lblResumoEmprestimos.setFont(FONTE_NORMAL);
        lblResumoEmprestimos.setForeground(CINZENTO);

        cartaoEmprestimos.add(
                painelBarras, BorderLayout.CENTER
        );

        cartaoEmprestimos.add(
                lblResumoEmprestimos, BorderLayout.SOUTH
        );

        painelAnalise.add(cartaoDisponibilidade);
        painelAnalise.add(cartaoEmprestimos);

        painelAnalise.setAlignmentX(Component.LEFT_ALIGNMENT);

        conteudo.add(painelAnalise);
    }

    // Cartão de análise
    private JPanel criarCartaoAnalise(
            String titulo,
            String tipo) {

        JPanel cartao = new JPanel(new BorderLayout(10, 15));

        cartao.setBackground(Color.WHITE);

        cartao.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(225, 230, 237)
                        ),
                        new EmptyBorder(18, 18, 18, 18)
                )
        );

        JLabel lblTitulo = new JLabel(titulo);

        lblTitulo.setFont(FONTE_NEGRITO);
        lblTitulo.setForeground(TEXTO);

        JLabel icone = new JLabel(
                new IconeRelatorio(tipo, AZUL)
        );

        cartao.add(lblTitulo, BorderLayout.NORTH);
        cartao.add(icone, BorderLayout.EAST);

        return cartao;
    }

    // Criar barra de progresso
    private JProgressBar criarBarra(Color cor) {

        JProgressBar barra = new JProgressBar(0, 100);

        barra.setValue(0);

        barra.setForeground(cor);
        barra.setBackground(new Color(235, 238, 243));

        barra.setBorderPainted(false);

        barra.setPreferredSize(
                new Dimension(100, 12)
        );

        return barra;
    }

    // Linha com texto e barra
    private JPanel criarLinhaBarra(
            String texto,
            JProgressBar barra) {

        JPanel linha = new JPanel(new BorderLayout(10, 3));

        linha.setOpaque(false);

        JLabel lbl = new JLabel(texto);

        lbl.setFont(
                new Font("Segoe UI", Font.PLAIN, 12)
        );

        lbl.setForeground(CINZENTO);

        linha.add(lbl, BorderLayout.WEST);
        linha.add(barra, BorderLayout.CENTER);

        return linha;
    }

    // Rodapé
    private void criarRodape() {

        JLabel rodape = new JLabel(
                "Sistema de Gestão de Bibliotecas  |  Relatório Geral"
        );

        rodape.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        rodape.setFont(
                new Font("Segoe UI", Font.PLAIN, 11)
        );

        rodape.setForeground(
                new Color(145, 150, 160)
        );

        rodape.setBorder(
                new EmptyBorder(12, 0, 0, 0)
        );

        add(rodape, BorderLayout.SOUTH);
    }

    // Actualizar todos os relatórios
    public void atualizarRelatorios() {

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

                if (emprestimo.getDataDevolucaoPrevista() != null
                        && emprestimo.getDataDevolucaoPrevista()
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

        // Actualizar os cartões
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

        // Actualizar barras de análise
        barraDisponibilidade.setValue(
                (int) Math.round(percentagem)
        );

        lblResumoDisponibilidade.setText(
                livrosDisponiveis + " de "
                        + totalLivros + " livros disponíveis"
        );

        // Percentagem dos empréstimos
        int percentagemAtivos = 0;
        int percentagemDevolvidos = 0;
        int percentagemAtrasados = 0;

        if (totalEmprestimos > 0) {

            percentagemAtivos = (int) Math.round(
                    emprestimosAtivos * 100.0 / totalEmprestimos
            );

            percentagemDevolvidos = (int) Math.round(
                    emprestimosDevolvidos * 100.0 / totalEmprestimos
            );

            percentagemAtrasados = (int) Math.round(
                    emprestimosAtrasados * 100.0 / totalEmprestimos
            );
        }

        barraAtivos.setValue(percentagemAtivos);
        barraDevolvidos.setValue(percentagemDevolvidos);
        barraAtrasados.setValue(percentagemAtrasados);

        lblResumoEmprestimos.setText(
                "Total de empréstimos: " + totalEmprestimos
        );

        revalidate();
        repaint();
    }

    // Classe responsável pelos ícones gráficos
    private static class IconeRelatorio implements Icon {

        private String tipo;
        private Color cor;

        public IconeRelatorio(String tipo, Color cor) {

            this.tipo = tipo;
            this.cor = cor;
        }

        @Override
        public int getIconWidth() {
            return 32;
        }

        @Override
        public int getIconHeight() {
            return 32;
        }

        @Override
        public void paintIcon(
                Component componente,
                Graphics g,
                int x,
                int y) {

            Graphics2D g2 = (Graphics2D) g.create();

            g2.translate(x, y);

            g2.setColor(cor);

            g2.setStroke(
                    new BasicStroke(
                            2f,
                            BasicStroke.CAP_ROUND,
                            BasicStroke.JOIN_ROUND
                    )
            );

            switch (tipo) {

                case "livros":

                    g2.drawRoundRect(5, 4, 21, 25, 3, 3);
                    g2.drawLine(10, 4, 10, 29);
                    g2.drawLine(14, 11, 22, 11);
                    g2.drawLine(14, 16, 22, 16);

                    break;

                case "disponiveis":

                    g2.drawOval(4, 4, 24, 24);
                    g2.drawLine(10, 16, 14, 21);
                    g2.drawLine(14, 21, 23, 10);

                    break;

                case "emprestados":

                    g2.drawLine(5, 10, 26, 10);
                    g2.drawLine(21, 5, 26, 10);
                    g2.drawLine(21, 15, 26, 10);

                    g2.drawLine(27, 22, 6, 22);
                    g2.drawLine(11, 17, 6, 22);
                    g2.drawLine(11, 27, 6, 22);

                    break;

                case "leitores":

                    g2.drawOval(11, 3, 11, 11);
                    g2.drawArc(5, 16, 23, 18, 0, 180);

                    break;

                case "emprestimos":

                    g2.drawRoundRect(5, 4, 22, 25, 3, 3);
                    g2.drawLine(10, 11, 22, 11);
                    g2.drawLine(10, 16, 22, 16);
                    g2.drawLine(10, 21, 18, 21);

                    break;

                case "activos":

                    g2.drawOval(4, 4, 24, 24);
                    g2.drawLine(16, 9, 16, 16);
                    g2.drawLine(16, 16, 22, 20);

                    break;

                case "devolvidos":

                    g2.drawOval(4, 4, 24, 24);
                    g2.drawLine(9, 16, 14, 21);
                    g2.drawLine(14, 21, 23, 10);

                    break;

                case "atrasados":

                    g2.drawPolygon(
                            new int[]{16, 29, 3},
                            new int[]{3, 27, 27},
                            3
                    );

                    g2.drawLine(16, 11, 16, 19);
                    g2.fillOval(15, 22, 3, 3);

                    break;

                case "disponibilidade":

                    g2.drawArc(4, 4, 24, 24, 0, 360);
                    g2.drawLine(16, 16, 16, 6);
                    g2.drawLine(16, 16, 25, 20);

                    break;

                case "actualizar":

                    g2.drawArc(4, 4, 24, 24, 45, 270);
                    g2.drawLine(4, 4, 4, 11);
                    g2.drawLine(4, 4, 11, 4);

                    break;

                default:
                    g2.drawOval(5, 5, 22, 22);
                    break;
            }

            g2.dispose();
        }
    }
}
