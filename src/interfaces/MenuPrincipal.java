
package interfaces;

import servicos.LivroService;
import servicos.LeitorService;
import servicos.EmprestimoService;

import javax.swing.*;
import java.awt.*;

public class MenuPrincipal extends JFrame {

    private LivroService livroService;
    private LeitorService leitorService;
    private EmprestimoService emprestimoService;

    private JPanel painelLateral;
    private JPanel painelCentral;

    private CardLayout cardLayout;

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

        setSize(1200, 750);

        setMinimumSize(new Dimension(950, 600));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        setLayout(new BorderLayout());
    }

    // Criar menu lateral
    private void criarMenuLateral() {

        painelLateral = new JPanel();

        painelLateral.setPreferredSize(new Dimension(240, 0));

        painelLateral.setBackground(new Color(25, 35, 55));

        painelLateral.setLayout(
                new BoxLayout(painelLateral, BoxLayout.Y_AXIS)
        );

        JLabel titulo = new JLabel("BIBLIOTECA");

        titulo.setForeground(Color.WHITE);

        titulo.setFont(new Font("Arial", Font.BOLD, 23));

        titulo.setBorder(
                BorderFactory.createEmptyBorder(30, 25, 30, 10)
        );

        painelLateral.add(titulo);

        adicionarBotao("Dashboard");
        adicionarBotao("Livros");
        adicionarBotao("Leitores");
        adicionarBotao("Empréstimos");
        adicionarBotao("Devoluções");
        adicionarBotao("Histórico");
        adicionarBotao("Relatórios");

        painelLateral.add(Box.createVerticalGlue());

        adicionarBotao("Sair");

        add(painelLateral, BorderLayout.WEST);
    }

    // Criar botões do menu
    private void adicionarBotao(String texto) {

        JButton botao = new JButton(texto);

        botao.setMaximumSize(new Dimension(220, 48));

        botao.setAlignmentX(Component.CENTER_ALIGNMENT);

        botao.setFont(new Font("Arial", Font.PLAIN, 15));

        botao.setForeground(Color.WHITE);

        botao.setBackground(new Color(25, 35, 55));

        botao.setFocusPainted(false);

        botao.setBorderPainted(false);

        botao.setHorizontalAlignment(SwingConstants.LEFT);

        botao.setBorder(
                BorderFactory.createEmptyBorder(10, 25, 10, 10)
        );

        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));

        botao.addActionListener(e -> executarAcao(texto));

        painelLateral.add(botao);
    }

    // Criar painel central e adicionar as interfaces
    private void criarPainelCentral() {

        cardLayout = new CardLayout();

        painelCentral = new JPanel(cardLayout);

        painelCentral.setBackground(new Color(245, 247, 250));

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
        TelaLeitores telaLeitores = new TelaLeitores(leitorService);

        painelCentral.add(telaLeitores, "Leitores");

        // 4. Empréstimos
        TelaEmprestimos telaEmprestimos =
                new TelaEmprestimos(emprestimoService);

        painelCentral.add(telaEmprestimos, "Empréstimos");

        // 5. Devoluções - interface por desenvolver
        TelaDevolucoes telaDevolucoes =
                new TelaDevolucoes(emprestimoService);

        painelCentral.add(
                telaDevolucoes,
                "Devoluções"
        );

        // 6. Histórico - interface por desenvolver
        TelaHistoricos telaHistoricos =
                new TelaHistoricos(emprestimoService);

        painelCentral.add(
                telaHistoricos,
                "Histórico"
        );

        // 7. Relatórios - interface por desenvolver
        TelaRelatorios telaRelatorios =
                new TelaRelatorios(
                        livroService,
                        leitorService,
                        emprestimoService
                );

        painelCentral.add(
                telaRelatorios,
                "Relatórios"
        );

        add(painelCentral, BorderLayout.CENTER);

        // Abrir Dashboard inicialmente
        cardLayout.show(painelCentral, "Dashboard");
    }

    // Criar painel provisório para módulos ainda não implementados
    private JPanel criarPainelEmDesenvolvimento(
            String titulo,
            String descricao) {

        JPanel painel = new JPanel(new GridBagLayout());

        painel.setBackground(new Color(245, 247, 250));

        JPanel conteudo = new JPanel();

        conteudo.setLayout(new BoxLayout(conteudo, BoxLayout.Y_AXIS));

        conteudo.setOpaque(false);

        JLabel lblTitulo = new JLabel(titulo);

        lblTitulo.setFont(new Font("Arial", Font.BOLD, 26));

        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblDescricao = new JLabel(descricao);

        lblDescricao.setFont(new Font("Arial", Font.PLAIN, 15));

        lblDescricao.setForeground(new Color(90, 100, 115));

        lblDescricao.setAlignmentX(Component.CENTER_ALIGNMENT);

        conteudo.add(lblTitulo);

        conteudo.add(Box.createVerticalStrut(15));

        conteudo.add(lblDescricao);

        painel.add(conteudo);

        return painel;
    }

    // Executar ações dos botões
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

                int resposta = JOptionPane.showConfirmDialog(
                        this,
                        "Deseja realmente sair do sistema?",
                        "Confirmar saída",
                        JOptionPane.YES_NO_OPTION
                );

                if (resposta == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }

                break;
        }
    }

    // Mostrar interface seleccionada
    private void abrirTela(String nomeTela) {

        cardLayout.show(painelCentral, nomeTela);
    }
}
