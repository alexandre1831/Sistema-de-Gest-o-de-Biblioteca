
package interfaces;

import servicos.LivroService;
import servicos.LeitorService;
import servicos.EmprestimoService;
import modelos.Emprestimo;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class Dashboard extends JPanel {

    private LivroService livroService;
    private LeitorService leitorService;
    private EmprestimoService emprestimoService;

    // Indicadores
    private JLabel lblTotalLivros;
    private JLabel lblLivrosDisponiveis;
    private JLabel lblTotalLeitores;
    private JLabel lblTotalEmprestimos;
    private JLabel lblEmprestimosAtivos;

    // Gráficos
    private GraficoCircular graficoLivros;
    private GraficoBarras graficoEmprestimos;

    // Cores do sistema
    private final Color AZUL_ESCURO = new Color(25, 45, 75);
    private final Color AZUL = new Color(52, 152, 219);
    private final Color VERDE = new Color(39, 174, 96);
    private final Color LARANJA = new Color(230, 126, 34);
    private final Color VERMELHO = new Color(192, 57, 43);
    private final Color ROXO = new Color(142, 68, 173);
    private final Color FUNDO = new Color(245, 247, 250);

    public Dashboard(
            LivroService livroService,
            LeitorService leitorService,
            EmprestimoService emprestimoService) {

        this.livroService = livroService;
        this.leitorService = leitorService;
        this.emprestimoService = emprestimoService;

        configurarPainel();
        criarComponentes();
        atualizarDashboard();
    }

    // =====================================================
    // CONFIGURAÇÃO PRINCIPAL
    // =====================================================

    private void configurarPainel() {

        setLayout(new BorderLayout());
        setBackground(FUNDO);
    }

    // =====================================================
    // CRIAÇÃO DOS COMPONENTES
    // =====================================================

    private void criarComponentes() {

        // CABEÇALHO
        JPanel cabecalho = new JPanel(new BorderLayout());

        cabecalho.setBackground(AZUL_ESCURO);
        cabecalho.setBorder(
                BorderFactory.createEmptyBorder(22, 30, 22, 30)
        );

        JLabel titulo = new JLabel("GESTÃO DE BIBLIOTECA");

        titulo.setFont(new Font("Segoe UI", Font.BOLD, 25));
        titulo.setForeground(Color.WHITE);

        JLabel subtitulo = new JLabel(
                "Visão geral e estatísticas do sistema"
        );

        subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitulo.setForeground(new Color(200, 210, 225));

        JPanel textos = new JPanel(new GridLayout(2, 1, 0, 5));

        textos.setOpaque(false);
        textos.add(titulo);
        textos.add(subtitulo);

        cabecalho.add(textos, BorderLayout.WEST);

        // =================================================
        // BOTÃO ACTUALIZAR - ESTILO MODERNO
        // =================================================

        JButton btnAtualizar = new JButton("Actualizar dados");

        btnAtualizar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnAtualizar.setForeground(Color.WHITE);
        btnAtualizar.setBackground(new Color(42, 105, 170));

        // Remover aparência padrão do Swing
        btnAtualizar.setFocusPainted(false);
        btnAtualizar.setBorderPainted(false);
        btnAtualizar.setContentAreaFilled(true);
        btnAtualizar.setOpaque(true);

        // Ícone de actualização
        btnAtualizar.setIcon(
                new IconeDashboard("actualizar", Color.WHITE)
        );

        btnAtualizar.setIconTextGap(10);

        // Espaçamento interno
        btnAtualizar.setBorder(
                BorderFactory.createEmptyBorder(12, 18, 12, 18)
        );

        // Cursor de mão
        btnAtualizar.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );

        // Acção do botão
        btnAtualizar.addActionListener(
                e -> atualizarDashboard()
        );

        cabecalho.add(btnAtualizar, BorderLayout.EAST);

        // =================================================
        // CONTEÚDO
        // =================================================

        JPanel conteudo = new JPanel();

        conteudo.setLayout(
                new BoxLayout(conteudo, BoxLayout.Y_AXIS)
        );

        conteudo.setBackground(FUNDO);

        conteudo.setBorder(
                BorderFactory.createEmptyBorder(25, 25, 25, 25)
        );

        // TÍTULO DOS INDICADORES
        JLabel tituloResumo = criarTitulo("Resumo geral");

        conteudo.add(tituloResumo);
        conteudo.add(Box.createVerticalStrut(15));

        // =================================================
        // CARTÕES ESTATÍSTICOS
        // =================================================

        JPanel painelCartoes = new JPanel(
                new GridLayout(1, 5, 15, 15)
        );

        painelCartoes.setOpaque(false);

        painelCartoes.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 125)
        );

        lblTotalLivros = criarCartao(
                painelCartoes,
                "Total de livros",
                AZUL
        );

        lblLivrosDisponiveis = criarCartao(
                painelCartoes,
                "Livros disponíveis",
                VERDE
        );

        lblTotalLeitores = criarCartao(
                painelCartoes,
                "Total de leitores",
                ROXO
        );

        lblTotalEmprestimos = criarCartao(
                painelCartoes,
                "Total de empréstimos",
                LARANJA
        );

        lblEmprestimosAtivos = criarCartao(
                painelCartoes,
                "Empréstimos activos",
                VERMELHO
        );

        conteudo.add(painelCartoes);
        conteudo.add(Box.createVerticalStrut(30));

        // =================================================
        // TÍTULO DOS GRÁFICOS
        // =================================================

        JLabel tituloGraficos = criarTitulo("Análise gráfica");

        conteudo.add(tituloGraficos);
        conteudo.add(Box.createVerticalStrut(15));

        // =================================================
        // PAINEL DOS GRÁFICOS
        // =================================================

        JPanel painelGraficos = new JPanel(
                new GridLayout(1, 2, 20, 0)
        );

        painelGraficos.setOpaque(false);

        painelGraficos.setPreferredSize(
                new Dimension(900, 350)
        );

        painelGraficos.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 350)
        );

        graficoLivros = new GraficoCircular();

        graficoEmprestimos = new GraficoBarras();

        painelGraficos.add(
                criarPainelGrafico(
                        "Disponibilidade dos livros",
                        graficoLivros
                )
        );

        painelGraficos.add(
                criarPainelGrafico(
                        "Situação dos empréstimos",
                        graficoEmprestimos
                )
        );

        conteudo.add(painelGraficos);
        conteudo.add(Box.createVerticalStrut(25));

        // =================================================
        // NOTA INFORMATIVA
        // =================================================

        JLabel nota = new JLabel(
                "Os gráficos são actualizados com base nos dados registados no sistema."
        );

        nota.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        nota.setForeground(new Color(110, 120, 135));

        conteudo.add(nota);

        // =================================================
        // SCROLL
        // =================================================

        JScrollPane scroll = new JScrollPane(conteudo);

        scroll.setBorder(null);
        scroll.getViewport().setBackground(FUNDO);
        scroll.getVerticalScrollBar().setUnitIncrement(18);

        // =================================================
        // RODAPÉ
        // =================================================

        JLabel rodape = new JLabel(
                "Sistema de Gestão de Bibliotecas | Versão 1.0",
                SwingConstants.CENTER
        );

        rodape.setFont(
                new Font("Segoe UI", Font.PLAIN, 11)
        );

        rodape.setForeground(new Color(120, 130, 145));

        rodape.setBorder(
                BorderFactory.createEmptyBorder(12, 10, 12, 10)
        );

        // ADICIONAR COMPONENTES AO DASHBOARD
        add(cabecalho, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(rodape, BorderLayout.SOUTH);
    }

    // =====================================================
    // ÍCONE DO BOTÃO ACTUALIZAR
    // =====================================================

    private static class IconeDashboard implements Icon {

        private String tipo;
        private Color cor;

        public IconeDashboard(String tipo, Color cor) {
            this.tipo = tipo;
            this.cor = cor;
        }

        @Override
        public int getIconWidth() {
            return 24;
        }

        @Override
        public int getIconHeight() {
            return 24;
        }

        @Override
        public void paintIcon(Component componente, Graphics g, int x, int y) {

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

            if (tipo.equals("actualizar")) {

                g2.drawArc(3, 3, 18, 18, 45, 270);
                g2.drawLine(3, 3, 3, 9);
                g2.drawLine(3, 3, 9, 3);
            }

            g2.dispose();
        }
    }

    // =====================================================
    // CRIAR TÍTULOS
    // =====================================================

    private JLabel criarTitulo(String texto) {

        JLabel titulo = new JLabel(texto);

        titulo.setFont(
                new Font("Segoe UI", Font.BOLD, 19)
        );

        titulo.setForeground(new Color(40, 50, 70));

        titulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        return titulo;
    }

    // =====================================================
    // CARTÕES ESTATÍSTICOS
    // =====================================================

    private JLabel criarCartao(
            JPanel painel,
            String titulo,
            Color cor) {

        JPanel cartao = new JPanel(
                new BorderLayout(5, 12)
        );

        cartao.setBackground(Color.WHITE);

        cartao.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(225, 230, 235)
                        ),
                        BorderFactory.createEmptyBorder(
                                18, 15, 18, 15
                        )
                )
        );

        JLabel lblTitulo = new JLabel(titulo);

        lblTitulo.setFont(
                new Font("Segoe UI", Font.BOLD, 12)
        );

        lblTitulo.setForeground(
                new Color(90, 100, 115)
        );

        JLabel lblValor = new JLabel(
                "0",
                SwingConstants.CENTER
        );

        lblValor.setFont(
                new Font("Segoe UI", Font.BOLD, 29)
        );

        lblValor.setForeground(cor);

        cartao.add(lblTitulo, BorderLayout.NORTH);
        cartao.add(lblValor, BorderLayout.CENTER);

        painel.add(cartao);

        return lblValor;
    }

    // =====================================================
    // PAINEL INDIVIDUAL DOS GRÁFICOS
    // =====================================================

    private JPanel criarPainelGrafico(
            String titulo,
            JPanel grafico) {

        JPanel painel = new JPanel(
                new BorderLayout(10, 15)
        );

        painel.setBackground(Color.WHITE);

        painel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(225, 230, 235)
                        ),
                        BorderFactory.createEmptyBorder(
                                18, 20, 18, 20
                        )
                )
        );

        JLabel lblTitulo = new JLabel(titulo);

        lblTitulo.setFont(
                new Font("Segoe UI", Font.BOLD, 15)
        );

        lblTitulo.setForeground(
                new Color(45, 55, 75)
        );

        painel.add(lblTitulo, BorderLayout.NORTH);
        painel.add(grafico, BorderLayout.CENTER);

        return painel;
    }

    // =====================================================
    // ACTUALIZAR DASHBOARD
    // =====================================================

    public void atualizarDashboard() {

        int totalLivros = livroService.getQuantidadeLivros();

        int disponiveis = livroService.contarLivrosDisponiveis();

        int indisponiveis = totalLivros - disponiveis;

        int totalLeitores = leitorService.getQuantidadeLeitores();

        int totalEmprestimos =
                emprestimoService.getQuantidadeEmprestimos();

        int ativos = emprestimoService.contarEmprestimosAtivos();

        int devolvidos = totalEmprestimos - ativos;

        int atrasados = 0;

        // Calcular empréstimos atrasados
        for (int i = 0; i < totalEmprestimos; i++) {

            Emprestimo emprestimo =
                    emprestimoService.getEmprestimos().consultar(i);

            if (emprestimo != null
                    && emprestimo.isAtivo()
                    && emprestimo.getDataDevolucaoPrevista() != null
                    && emprestimo.getDataDevolucaoPrevista()
                    .isBefore(LocalDate.now())) {

                atrasados++;
            }
        }

        // Actualizar cartões
        lblTotalLivros.setText(
                String.valueOf(totalLivros)
        );

        lblLivrosDisponiveis.setText(
                String.valueOf(disponiveis)
        );

        lblTotalLeitores.setText(
                String.valueOf(totalLeitores)
        );

        lblTotalEmprestimos.setText(
                String.valueOf(totalEmprestimos)
        );

        lblEmprestimosAtivos.setText(
                String.valueOf(ativos)
        );

        // Actualizar gráficos
        graficoLivros.setDados(
                disponiveis,
                Math.max(0, indisponiveis)
        );

        graficoEmprestimos.setDados(
                ativos,
                devolvidos,
                atrasados
        );

        revalidate();
        repaint();
    }

    // =====================================================
    // GRÁFICO CIRCULAR (DONUT)
    // =====================================================

    private class GraficoCircular extends JPanel {

        private int disponiveis;
        private int indisponiveis;

        public GraficoCircular() {

            setOpaque(false);
            setPreferredSize(new Dimension(350, 250));
        }

        public void setDados(
                int disponiveis,
                int indisponiveis) {

            this.disponiveis = disponiveis;
            this.indisponiveis = indisponiveis;

            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {

            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            int total = disponiveis + indisponiveis;

            int x = 25;
            int y = 25;
            int tamanho = 175;

            if (total == 0) {

                g2.setColor(new Color(230, 235, 240));

                g2.fillArc(
                        x, y, tamanho, tamanho, 0, 360
                );

            } else {

                int anguloDisponivel =
                        (int) Math.round(
                                360.0 * disponiveis / total
                        );

                g2.setColor(VERDE);

                g2.fillArc(
                        x, y, tamanho, tamanho,
                        90, anguloDisponivel
                );

                g2.setColor(VERMELHO);

                g2.fillArc(
                        x, y, tamanho, tamanho,
                        90 + anguloDisponivel,
                        360 - anguloDisponivel
                );
            }

            // Criar o centro branco
            g2.setColor(Color.WHITE);

            g2.fillOval(
                    x + 48,
                    y + 48,
                    tamanho - 96,
                    tamanho - 96
            );

            // Valor central
            g2.setColor(AZUL_ESCURO);

            g2.setFont(
                    new Font("Segoe UI", Font.BOLD, 25)
            );

            String valor = String.valueOf(total);

            FontMetrics fm = g2.getFontMetrics();

            g2.drawString(
                    valor,
                    x + (tamanho - fm.stringWidth(valor)) / 2,
                    y + 98
            );

            g2.setFont(
                    new Font("Segoe UI", Font.PLAIN, 10)
            );

            String legendaCentro = "LIVROS";

            fm = g2.getFontMetrics();

            g2.drawString(
                    legendaCentro,
                    x + (tamanho - fm.stringWidth(legendaCentro)) / 2,
                    y + 117
            );

            // Legenda
            int legendaX = 230;

            g2.setColor(VERDE);

            g2.fillRoundRect(
                    legendaX, 65, 12, 12, 4, 4
            );

            g2.setColor(new Color(65, 75, 90));

            g2.setFont(
                    new Font("Segoe UI", Font.PLAIN, 12)
            );

            g2.drawString(
                    "Disponíveis: " + disponiveis,
                    legendaX + 20, 76
            );

            g2.setColor(VERMELHO);

            g2.fillRoundRect(
                    legendaX, 105, 12, 12, 4, 4
            );

            g2.setColor(new Color(65, 75, 90));

            g2.drawString(
                    "Indisponíveis: " + indisponiveis,
                    legendaX + 20, 116
            );

            if (total == 0) {

                g2.setFont(
                        new Font("Segoe UI", Font.ITALIC, 11)
                );

                g2.setColor(Color.GRAY);

                g2.drawString(
                        "Sem livros registados",
                        legendaX,
                        160
                );
            }

            g2.dispose();
        }
    }

    // =====================================================
    // GRÁFICO DE BARRAS
    // =====================================================

    private class GraficoBarras extends JPanel {

        private int ativos;
        private int devolvidos;
        private int atrasados;

        public GraficoBarras() {

            setOpaque(false);
            setPreferredSize(new Dimension(350, 250));
        }

        public void setDados(
                int ativos,
                int devolvidos,
                int atrasados) {

            this.ativos = ativos;
            this.devolvidos = devolvidos;
            this.atrasados = atrasados;

            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {

            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            int maior = Math.max(
                    1,
                    Math.max(ativos, devolvidos)
            );

            int larguraBarra = 48;
            int alturaMaxima = 145;

            int[] valores = {
                    ativos,
                    devolvidos,
                    atrasados
            };

            String[] nomes = {
                    "Activos",
                    "Devolvidos",
                    "Atrasados"
            };

            Color[] cores = {
                    AZUL,
                    VERDE,
                    VERMELHO
            };

            int[] posicoesX = {
                    35, 145, 255
            };

            // Linha de base
            g2.setColor(new Color(210, 218, 228));

            g2.drawLine(
                    15, 190,
                    getWidth() - 15, 190
            );

            // Desenhar barras
            for (int i = 0; i < valores.length; i++) {

                int altura = (int) (
                        (double) valores[i] / maior
                                * alturaMaxima
                );

                int x = posicoesX[i];
                int y = 190 - altura;

                g2.setColor(cores[i]);

                g2.fillRoundRect(
                        x,
                        y,
                        larguraBarra,
                        altura,
                        12,
                        12
                );

                // Valor acima da barra
                g2.setColor(
                        new Color(50, 60, 75)
                );

                g2.setFont(
                        new Font("Segoe UI", Font.BOLD, 12)
                );

                String valor = String.valueOf(valores[i]);

                FontMetrics fm = g2.getFontMetrics();

                g2.drawString(
                        valor,
                        x + (larguraBarra - fm.stringWidth(valor)) / 2,
                        Math.max(18, y - 8)
                );

                // Nome abaixo da barra
                g2.setFont(
                        new Font("Segoe UI", Font.PLAIN, 11)
                );

                fm = g2.getFontMetrics();

                g2.drawString(
                        nomes[i],
                        x + (larguraBarra - fm.stringWidth(nomes[i])) / 2,
                        212
                );
            }

            // Informação adicional
            g2.setColor(new Color(100, 110, 125));

            g2.setFont(
                    new Font("Segoe UI", Font.PLAIN, 11)
            );

            g2.drawString(
                    "Total de empréstimos: "
                            + (ativos + devolvidos),
                    35,
                    245
            );

            g2.dispose();
        }
    }
}
