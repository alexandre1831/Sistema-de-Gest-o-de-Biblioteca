
package interfaces;

import servicos.LivroService;
import servicos.LeitorService;
import servicos.EmprestimoService;

import javax.swing.*;
import java.awt.*;

public class Dashboard extends JPanel {

    private LivroService livroService;
    private LeitorService leitorService;
    private EmprestimoService emprestimoService;

    private JLabel lblTotalLivros;
    private JLabel lblLivrosDisponiveis;
    private JLabel lblTotalLeitores;
    private JLabel lblTotalEmprestimos;
    private JLabel lblEmprestimosAtivos;

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

    // Configurar painel
    private void configurarPainel() {

        setBackground(new Color(245, 247, 250));
        setLayout(new BorderLayout());
    }

    // Criar componentes
    private void criarComponentes() {

        // Cabeçalho
        JPanel cabecalho = new JPanel(new BorderLayout());

        cabecalho.setBackground(new Color(25, 45, 75));

        cabecalho.setBorder(
                BorderFactory.createEmptyBorder(20, 25, 20, 25)
        );

        JLabel titulo = new JLabel("GESTÃO DE BIBLIOTECA");

        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));

        JLabel subtitulo = new JLabel("Painel de controlo");

        subtitulo.setForeground(Color.LIGHT_GRAY);
        subtitulo.setFont(new Font("Arial", Font.PLAIN, 14));

        JPanel textos = new JPanel(new GridLayout(2, 1));

        textos.setOpaque(false);
        textos.add(titulo);
        textos.add(subtitulo);

        cabecalho.add(textos, BorderLayout.WEST);

        // Painel central
        JPanel painelConteudo = new JPanel(new BorderLayout(15, 15));

        painelConteudo.setBackground(new Color(245, 247, 250));

        painelConteudo.setBorder(
                BorderFactory.createEmptyBorder(25, 25, 25, 25)
        );

        JLabel tituloResumo = new JLabel("Resumo geral");

        tituloResumo.setFont(new Font("Arial", Font.BOLD, 22));
        tituloResumo.setForeground(new Color(35, 45, 65));

        JPanel painelCartoes = new JPanel(
                new GridLayout(2, 3, 15, 15)
        );

        painelCartoes.setOpaque(false);

        lblTotalLivros = criarCartao(
                painelCartoes,
                "Total de livros",
                new Color(52, 152, 219)
        );

        lblLivrosDisponiveis = criarCartao(
                painelCartoes,
                "Livros disponíveis",
                new Color(39, 174, 96)
        );

        lblTotalLeitores = criarCartao(
                painelCartoes,
                "Total de leitores",
                new Color(142, 68, 173)
        );

        lblTotalEmprestimos = criarCartao(
                painelCartoes,
                "Total de empréstimos",
                new Color(230, 126, 34)
        );

        lblEmprestimosAtivos = criarCartao(
                painelCartoes,
                "Empréstimos ativos",
                new Color(192, 57, 43)
        );

        painelConteudo.add(tituloResumo, BorderLayout.NORTH);
        painelConteudo.add(painelCartoes, BorderLayout.CENTER);

        // Rodapé
        JLabel rodape = new JLabel(
                "Sistema de Gestão de Bibliotecas | Versão 1.0",
                SwingConstants.CENTER
        );

        rodape.setFont(new Font("Arial", Font.PLAIN, 12));

        rodape.setBorder(
                BorderFactory.createEmptyBorder(12, 10, 12, 10)
        );

        add(cabecalho, BorderLayout.NORTH);
        add(painelConteudo, BorderLayout.CENTER);
        add(rodape, BorderLayout.SOUTH);
    }

    // Criar cartões estatísticos
    private JLabel criarCartao(
            JPanel painel,
            String titulo,
            Color cor) {

        JPanel cartao = new JPanel(new BorderLayout(5, 15));

        cartao.setBackground(Color.WHITE);

        cartao.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(225, 230, 235)
                        ),
                        BorderFactory.createEmptyBorder(
                                20, 15, 20, 15
                        )
                )
        );

        JLabel lblTitulo = new JLabel(titulo);

        lblTitulo.setFont(new Font("Arial", Font.BOLD, 14));
        lblTitulo.setForeground(new Color(75, 85, 100));

        JLabel lblValor = new JLabel(
                "0",
                SwingConstants.CENTER
        );

        lblValor.setFont(new Font("Arial", Font.BOLD, 32));
        lblValor.setForeground(cor);

        cartao.add(lblTitulo, BorderLayout.NORTH);
        cartao.add(lblValor, BorderLayout.CENTER);

        painel.add(cartao);

        return lblValor;
    }

    // Atualizar indicadores
    public void atualizarDashboard() {

        lblTotalLivros.setText(
                String.valueOf(
                        livroService.getQuantidadeLivros()
                )
        );

        lblLivrosDisponiveis.setText(
                String.valueOf(
                        livroService.contarLivrosDisponiveis()
                )
        );

        lblTotalLeitores.setText(
                String.valueOf(
                        leitorService.getQuantidadeLeitores()
                )
        );

        lblTotalEmprestimos.setText(
                String.valueOf(
                        emprestimoService.getQuantidadeEmprestimos()
                )
        );

        lblEmprestimosAtivos.setText(
                String.valueOf(
                        emprestimoService.contarEmprestimosAtivos()
                )
        );
    }
}


