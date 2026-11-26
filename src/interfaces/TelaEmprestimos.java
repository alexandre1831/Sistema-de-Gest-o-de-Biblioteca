
package interfaces;

import modelos.Emprestimo;
import servicos.EmprestimoService;
import estruturas.MinhaLista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TelaEmprestimos extends JPanel {

    private EmprestimoService emprestimoService;

    // Campos do formulário
    private JTextField txtCodigoLivro;
    private JTextField txtCodigoLeitor;
    private JTextField txtDiasPrazo;

    // Campo de pesquisa
    private JTextField txtPesquisa;

    // Tabela
    private JTable tabela;
    private DefaultTableModel modeloTabela;

    // Construtor
    public TelaEmprestimos(EmprestimoService emprestimoService) {

        this.emprestimoService = emprestimoService;

        configurarPainel();
        criarComponentes();
        atualizarTabela();
    }

    // 1. Configurar painel
    private void configurarPainel() {

        setLayout(new BorderLayout(15, 15));

        setBackground(new Color(245, 247, 250));

        setBorder(
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        );
    }

    // 2. Criar componentes principais
    private void criarComponentes() {

        // Cabeçalho
        JLabel titulo = new JLabel("Gestão de Empréstimos");

        titulo.setFont(
                new Font("Arial", Font.BOLD, 25)
        );

        JLabel descricao = new JLabel(
                "Registar, consultar e controlar empréstimos de livros."
        );

        JPanel painelCabecalho = new JPanel(
                new GridLayout(2, 1)
        );

        painelCabecalho.setBackground(getBackground());

        painelCabecalho.add(titulo);
        painelCabecalho.add(descricao);

        add(painelCabecalho, BorderLayout.NORTH);

        // Área central
        JPanel painelCentro = new JPanel(
                new BorderLayout(10, 15)
        );

        painelCentro.setBackground(getBackground());

        painelCentro.add(
                criarFormulario(),
                BorderLayout.NORTH
        );

        painelCentro.add(
                criarTabela(),
                BorderLayout.CENTER
        );

        add(painelCentro, BorderLayout.CENTER);
    }

    // 3. Criar formulário
    private JPanel criarFormulario() {

        JPanel painel = new JPanel(
                new GridLayout(2, 3, 10, 10)
        );

        painel.setBackground(Color.WHITE);

        painel.setBorder(
                BorderFactory.createTitledBorder(
                        "Registar Novo Empréstimo"
                )
        );

        txtCodigoLivro = new JTextField();
        txtCodigoLeitor = new JTextField();
        txtDiasPrazo = new JTextField("7");

        painel.add(
                criarCampo("Código do Livro", txtCodigoLivro)
        );

        painel.add(
                criarCampo("Código do Leitor", txtCodigoLeitor)
        );

        painel.add(
                criarCampo("Prazo em dias", txtDiasPrazo)
        );

        JButton btnRegistar = new JButton(
                "Registar Empréstimo"
        );

        btnRegistar.addActionListener(
                e -> registarEmprestimo()
        );

        JPanel painelBotao = new JPanel(
                new FlowLayout(FlowLayout.LEFT)
        );

        painelBotao.setBackground(Color.WHITE);

        painelBotao.add(btnRegistar);

        painel.add(painelBotao);

        return painel;
    }

    // 4. Criar campo com identificação
    private JPanel criarCampo(
            String nome,
            JTextField campo) {

        JPanel painel = new JPanel(
                new BorderLayout(5, 5)
        );

        painel.setBackground(Color.WHITE);

        JLabel label = new JLabel(nome);

        painel.add(label, BorderLayout.NORTH);
        painel.add(campo, BorderLayout.CENTER);

        return painel;
    }

    // 5. Criar tabela
    private JPanel criarTabela() {

        JPanel painel = new JPanel(
                new BorderLayout(10, 10)
        );

        painel.setBackground(getBackground());

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
            public boolean isCellEditable(
                    int linha,
                    int coluna) {

                return false;
            }
        };

        tabela = new JTable(modeloTabela);

        tabela.setRowHeight(25);

        tabela.getTableHeader().setFont(
                new Font("Arial", Font.BOLD, 12)
        );

        JScrollPane scroll = new JScrollPane(tabela);

        // Área de pesquisa
        txtPesquisa = new JTextField(12);

        JButton btnPesquisar = new JButton("Pesquisar");

        JButton btnListar = new JButton("Mostrar Todos");

        JButton btnDevolver = new JButton("Registar Devolução");

        btnPesquisar.addActionListener(
                e -> pesquisarEmprestimo()
        );

        btnListar.addActionListener(
                e -> atualizarTabela()
        );

        btnDevolver.addActionListener(
                e -> registarDevolucao()
        );

        JPanel painelPesquisa = new JPanel(
                new FlowLayout(FlowLayout.LEFT)
        );

        painelPesquisa.setBackground(Color.WHITE);

        painelPesquisa.add(
                new JLabel("Código do empréstimo:")
        );

        painelPesquisa.add(txtPesquisa);
        painelPesquisa.add(btnPesquisar);
        painelPesquisa.add(btnListar);
        painelPesquisa.add(btnDevolver);

        painel.add(
                painelPesquisa,
                BorderLayout.NORTH
        );

        painel.add(
                scroll,
                BorderLayout.CENTER
        );

        return painel;
    }

    // 6. Registar empréstimo
    private void registarEmprestimo() {

        try {

            String codigoLivro =
                    txtCodigoLivro.getText().trim();

            String codigoLeitor =
                    txtCodigoLeitor.getText().trim();

            int diasPrazo = Integer.parseInt(
                    txtDiasPrazo.getText().trim()
            );

            Emprestimo emprestimo =
                    emprestimoService.registarEmprestimo(
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
                            + emprestimo.getDataDevolucaoPrevista()
            );

            limparCampos();

            atualizarTabela();

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Introduza um prazo válido em dias."
            );

        } catch (IllegalArgumentException |
                 IllegalStateException e) {

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage(),
                    "Erro no empréstimo",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    // 7. Atualizar tabela
    private void atualizarTabela() {

        modeloTabela.setRowCount(0);

        MinhaLista<Emprestimo> emprestimos =
                emprestimoService.getEmprestimos();

        for (int i = 0; i < emprestimos.getTamanho(); i++) {

            Emprestimo emprestimo =
                    emprestimos.consultar(i);

            adicionarLinha(emprestimo);
        }
    }

    // 8. Adicionar empréstimo à tabela
    private void adicionarLinha(Emprestimo emprestimo) {

        modeloTabela.addRow(new Object[]{

                emprestimo.getCodigo(),

                emprestimo.getLivro().getTitulo(),

                emprestimo.getLeitor().getNome(),

                emprestimo.getDataEmprestimo(),

                emprestimo.getDataDevolucaoPrevista(),

                emprestimo.getDataDevolucaoReal() == null
                        ? "-"
                        : emprestimo.getDataDevolucaoReal(),

                emprestimo.isAtivo()
                        ? "Ativo"
                        : "Devolvido"
        });
    }

    // 9. Pesquisar empréstimo
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
                emprestimoService.buscarEmprestimoPorCodigo(
                        codigo
                );

        if (emprestimo != null) {

            adicionarLinha(emprestimo);

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Empréstimo não encontrado."
            );
        }
    }

    // 10. Registar devolução
    private void registarDevolucao() {

        int linha = tabela.getSelectedRow();

        if (linha == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Selecione um empréstimo na tabela."
            );

            return;
        }

        String codigo = modeloTabela.getValueAt(
                linha, 0
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
                        "Devolução registada com sucesso!"
                );

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Não foi possível registar a devolução. "
                                + "Verifique se o empréstimo existe "
                                + "e ainda está ativo."
                );
            }

            atualizarTabela();
        }
    }

    // 11. Limpar formulário
    private void limparCampos() {

        txtCodigoLivro.setText("");

        txtCodigoLeitor.setText("");

        txtDiasPrazo.setText("7");

        txtCodigoLivro.requestFocus();
    }
}
