
package interfaces;

import servicos.LivroService;
import servicos.LeitorService;
import servicos.EmprestimoService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MenuPrincipal extends JFrame {

    private LivroService livroService;
    private LeitorService leitorService;
    private EmprestimoService emprestimoService;

    private JPanel painelLateral;
    private JPanel painelCentral;

    private CardLayout cardLayout;

    // Cores do menu
    private final Color COR_FUNDO = new Color(25, 35, 55);
    private final Color COR_HOVER = new Color(42, 57, 82);
    private final Color COR_ACTIVO = new Color(42, 105, 170);
    private final Color COR_TEXTO = Color.WHITE;

    private JButton botaoAtivo;

    public MenuPrincipal(
            LivroService livroService,
            LeitorService leitorService,
            EmprestimoService emprestimoService) {

        this.livroService = livroService;
        this.leitorService = leitorService;
        this.emprestimoService = emprestimoService;

        configurarJanela();
        criarMenuLateral();
        criarPainelCentral();
    }

    // Configurar janela principal
    private void configurarJanela() {

        setTitle("Sistema de Gestão de Bibliotecas");

        setSize(1250, 780);

        setMinimumSize(new Dimension(1000, 650));

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                confirmarSaida();
            }
        });

        setLocationRelativeTo(null);

        setLayout(new BorderLayout());
    }

    // Criar menu lateral
    private void criarMenuLateral() {

        painelLateral = new JPanel();

        painelLateral.setPreferredSize(new Dimension(250, 0));

        painelLateral.setBackground(COR_FUNDO);

        painelLateral.setLayout(
                new BoxLayout(painelLateral, BoxLayout.Y_AXIS)
        );

        // Cabeçalho da barra lateral
        JPanel painelTitulo = new JPanel(new FlowLayout(
                FlowLayout.CENTER, 8, 30
        ));

        painelTitulo.setBackground(COR_FUNDO);

        JLabel iconeBiblioteca = new JLabel("▤");

        iconeBiblioteca.setFont(
                new Font("Segoe UI Symbol", Font.BOLD, 27)
        );

        iconeBiblioteca.setForeground(new Color(75, 170, 235));

        JLabel titulo = new JLabel("BIBLIOTECA");

        titulo.setForeground(COR_TEXTO);

        titulo.setFont(
                new Font("Segoe UI", Font.BOLD, 22)
        );

        painelTitulo.add(iconeBiblioteca);
        painelTitulo.add(titulo);

        painelTitulo.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 85)
        );

        painelLateral.add(painelTitulo);

        // Identificação da secção
        JLabel lblMenu = new JLabel("MENU PRINCIPAL");

        lblMenu.setForeground(new Color(155, 170, 190));

        lblMenu.setFont(
                new Font("Segoe UI", Font.BOLD, 11)
        );

        lblMenu.setBorder(
                BorderFactory.createEmptyBorder(15, 22, 12, 0)
        );

        lblMenu.setAlignmentX(Component.LEFT_ALIGNMENT);

        painelLateral.add(lblMenu);

        // Opções de navegação
        adicionarBotao("Dashboard", "▦");
        adicionarBotao("Livros", "▣");
        adicionarBotao("Leitores", "♟");
        adicionarBotao("Empréstimos", "⇄");
        adicionarBotao("Devoluções", "↩");
        adicionarBotao("Histórico", "◷");
        adicionarBotao("Relatórios", "▥");

        // Espaço flexível
        painelLateral.add(Box.createVerticalGlue());

        // Separador
        JSeparator separador = new JSeparator();

        separador.setForeground(new Color(65, 78, 98));

        separador.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 1)
        );

        painelLateral.add(separador);

        // Botão sair
        adicionarBotao("Sair", "⏻");

        painelLateral.add(Box.createVerticalStrut(12));

        // Linha de separação à direita
        painelLateral.setBorder(
                BorderFactory.createMatteBorder(
                        0, 0, 0, 1,
                        new Color(45, 58, 78)
                )
        );

        add(painelLateral, BorderLayout.WEST);
    }

    // Criar botões do menu com ícones

    private void adicionarBotao(String texto, String simbolo) {

        JButton botao = new JButton(texto);

        // Dimensões
        botao.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 50)
        );

        botao.setMinimumSize(
                new Dimension(220, 50)
        );

        botao.setPreferredSize(
                new Dimension(240, 50)
        );

        botao.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Fonte
        botao.setFont(
                new Font("Segoe UI", Font.PLAIN, 15)
        );

        // Cores
        botao.setForeground(Color.WHITE);
        botao.setBackground(COR_FUNDO);

        // Ícone desenhado pelo próprio Java
        botao.setIcon(new IconeMenu(texto));

        botao.setIconTextGap(18);

        // Alinhamento
        botao.setHorizontalAlignment(SwingConstants.LEFT);
        botao.setHorizontalTextPosition(SwingConstants.RIGHT);
        botao.setVerticalAlignment(SwingConstants.CENTER);

        // Espaçamento interno
        botao.setBorder(
                BorderFactory.createEmptyBorder(
                        10, 22, 10, 10
                )
        );

        // Remover contornos
        botao.setFocusPainted(false);
        botao.setBorderPainted(false);
        botao.setContentAreaFilled(true);
        botao.setOpaque(true);

        // Cursor
        botao.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );

        // Dashboard seleccionado inicialmente
        if (texto.equals("Dashboard")) {

            botaoAtivo = botao;

            botao.setBackground(COR_ACTIVO);

            botao.setFont(
                    new Font("Segoe UI", Font.BOLD, 15)
            );
        }

        // Efeito hover
        botao.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {

                if (botao != botaoAtivo) {
                    botao.setBackground(COR_HOVER);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {

                if (botao != botaoAtivo) {
                    botao.setBackground(COR_FUNDO);
                }
            }
        });

        // Acção do botão
        botao.addActionListener(e -> {

            if (!texto.equals("Sair")) {
                seleccionarBotao(botao);
            }

            executarAcao(texto);
        });

        painelLateral.add(botao);
    }


    // Destacar o botão seleccionado
    private void seleccionarBotao(JButton novoBotao) {

        if (botaoAtivo != null) {

            botaoAtivo.setBackground(COR_FUNDO);

            botaoAtivo.setFont(
                    new Font("Segoe UI", Font.PLAIN, 15)
            );
        }

        botaoAtivo = novoBotao;

        botaoAtivo.setBackground(COR_ACTIVO);

        botaoAtivo.setFont(
                new Font("Segoe UI", Font.BOLD, 15)
        );
    }

    // Criar painel central e adicionar as interfaces
    private void criarPainelCentral() {

        cardLayout = new CardLayout();

        painelCentral = new JPanel(cardLayout);

        painelCentral.setBackground(
                new Color(245, 247, 250)
        );

        // 1. Dashboard
        Dashboard dashboard = new Dashboard(
                livroService,
                leitorService,
                emprestimoService
        );

        painelCentral.add(dashboard, "Dashboard");

        // 2. Livros
        TelaLivros telaLivros = new TelaLivros(livroService);

        painelCentral.add(telaLivros, "Livros");

        // 3. Leitores
        TelaLeitores telaLeitores =
                new TelaLeitores(leitorService);

        painelCentral.add(telaLeitores, "Leitores");

        // 4. Empréstimos
        TelaEmprestimos telaEmprestimos =
                new TelaEmprestimos(emprestimoService);

        painelCentral.add(telaEmprestimos, "Empréstimos");

        // 5. Devoluções
        TelaDevolucoes telaDevolucoes =
                new TelaDevolucoes(emprestimoService);

        painelCentral.add(telaDevolucoes, "Devoluções");

        // 6. Histórico
        TelaHistoricos telaHistoricos =
                new TelaHistoricos(emprestimoService);

        painelCentral.add(telaHistoricos, "Histórico");

        // 7. Relatórios
        TelaRelatorios telaRelatorios =
                new TelaRelatorios(
                        livroService,
                        leitorService,
                        emprestimoService
                );

        painelCentral.add(telaRelatorios, "Relatórios");

        add(painelCentral, BorderLayout.CENTER);

        // Abrir Dashboard inicialmente
        cardLayout.show(painelCentral, "Dashboard");
    }

    // Executar acções dos botões
    private void executarAcao(String opcao) {

        switch (opcao) {

            case "Dashboard":
                abrirTela("Dashboard");
                break;

            case "Livros":
                abrirTela("Livros");
                break;

            case "Leitores":
                abrirTela("Leitores");
                break;

            case "Empréstimos":
                abrirTela("Empréstimos");
                break;

            case "Devoluções":
                abrirTela("Devoluções");
                break;

            case "Histórico":
                abrirTela("Histórico");
                break;

            case "Relatórios":
                abrirTela("Relatórios");
                break;

            case "Sair":
                confirmarSaida();
                break;
        }
    }

    // Mostrar interface seleccionada
    private void abrirTela(String nomeTela) {

        cardLayout.show(painelCentral, nomeTela);
    }

    private static class IconeMenu implements Icon {

        private final String tipo;

        public IconeMenu(String tipo) {
            this.tipo = tipo;
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
        public void paintIcon(
                Component componente,
                Graphics g,
                int x,
                int y) {

            Graphics2D g2 = (Graphics2D) g.create();

            g2.translate(x, y);

            g2.setColor(Color.WHITE);

            g2.setStroke(
                    new BasicStroke(
                            1.8f,
                            BasicStroke.CAP_ROUND,
                            BasicStroke.JOIN_ROUND
                    )
            );

            switch (tipo) {

                // Dashboard: quatro quadrados
                case "Dashboard":

                    g2.drawRoundRect(2, 2, 7, 7, 2, 2);
                    g2.drawRoundRect(13, 2, 7, 7, 2, 2);
                    g2.drawRoundRect(2, 13, 7, 7, 2, 2);
                    g2.drawRoundRect(13, 13, 7, 7, 2, 2);

                    break;

                // Livros
                case "Livros":

                    g2.drawRoundRect(3, 2, 16, 18, 2, 2);
                    g2.drawLine(7, 2, 7, 20);
                    g2.drawLine(10, 7, 16, 7);
                    g2.drawLine(10, 11, 16, 11);

                    break;

                // Leitores
                case "Leitores":

                    g2.drawOval(7, 2, 8, 8);
                    g2.drawArc(3, 11, 16, 14, 0, 180);
                    g2.drawArc(0, 7, 9, 12, 0, 180);

                    break;

                // Empréstimos
                case "Empréstimos":

                    g2.drawLine(2, 7, 18, 7);
                    g2.drawLine(14, 3, 18, 7);
                    g2.drawLine(14, 11, 18, 7);

                    g2.drawLine(20, 16, 4, 16);
                    g2.drawLine(8, 12, 4, 16);
                    g2.drawLine(8, 20, 4, 16);

                    break;

                // Devoluções
                case "Devoluções":

                    g2.drawArc(3, 3, 16, 16, 45, 270);
                    g2.drawLine(3, 3, 3, 10);
                    g2.drawLine(3, 3, 10, 3);

                    break;

                // Histórico: relógio
                case "Histórico":

                    g2.drawOval(2, 2, 18, 18);
                    g2.drawLine(11, 5, 11, 11);
                    g2.drawLine(11, 11, 16, 14);

                    break;

                // Relatórios: gráfico
                case "Relatórios":

                    g2.drawLine(2, 20, 20, 20);
                    g2.drawLine(2, 20, 2, 2);

                    g2.fillRect(5, 12, 3, 8);
                    g2.fillRect(10, 8, 3, 12);
                    g2.fillRect(15, 3, 3, 17);

                    break;

                // Sair: símbolo de energia
                case "Sair":

                    g2.drawArc(3, 5, 16, 16, 45, 270);
                    g2.drawLine(11, 1, 11, 11);

                    break;
            }

            g2.dispose();
        }
    }
    private void confirmarSaida() {

        boolean resposta = DialogoPersonalizado.confirmar(
                this,
                "Sair do sistema",
                "Tem a certeza de que deseja sair do Sistema de Gestão de Bibliotecas?",
                "Sim, sair",
                "Continuar no sistema"
        );

        if (resposta) {
            System.exit(0);
        }
    }



}
