
package interfaces;

import modelos.Leitor;
import servicos.LeitorService;

import javax.swing.*;
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

    public TelaLeitores(LeitorService leitorService) {
        this.leitorService = leitorService;

        configurarPainel();
        criarComponentes();
        atualizarTabela();
    }

    private void configurarPainel() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(245, 247, 250));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
    }

    private void criarComponentes() {

        // Título
        JLabel lblTitulo = new JLabel("Gestão de Leitores");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));

        add(lblTitulo, BorderLayout.NORTH);

        // Painel principal
        JPanel painelCentral = new JPanel(new BorderLayout(10, 10));
        painelCentral.setOpaque(false);

        // Formulário
        JPanel painelFormulario = new JPanel(new GridLayout(5, 2, 10, 10));
        painelFormulario.setBorder(
                BorderFactory.createTitledBorder("Dados do Leitor")
        );

        txtCodigo = new JTextField();
        txtNome = new JTextField();
        txtTelefone = new JTextField();
        txtEmail = new JTextField();
        txtEndereco = new JTextField();

        painelFormulario.add(new JLabel("Código:"));
        painelFormulario.add(txtCodigo);

        painelFormulario.add(new JLabel("Nome:"));
        painelFormulario.add(txtNome);

        painelFormulario.add(new JLabel("Telefone:"));
        painelFormulario.add(txtTelefone);

        painelFormulario.add(new JLabel("Email:"));
        painelFormulario.add(txtEmail);

        painelFormulario.add(new JLabel("Endereço:"));
        painelFormulario.add(txtEndereco);

        // Botões
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton btnCadastrar = new JButton("Cadastrar");
        JButton btnPesquisar = new JButton("Pesquisar");
        JButton btnAtualizar = new JButton("Atualizar");
        JButton btnRemover = new JButton("Remover");
        JButton btnLimpar = new JButton("Limpar");

        painelBotoes.add(btnCadastrar);
        painelBotoes.add(btnPesquisar);
        painelBotoes.add(btnAtualizar);
        painelBotoes.add(btnRemover);
        painelBotoes.add(btnLimpar);

        JPanel painelSuperior = new JPanel(new BorderLayout(10, 10));
        painelSuperior.add(painelFormulario, BorderLayout.CENTER);
        painelSuperior.add(painelBotoes, BorderLayout.SOUTH);

        painelCentral.add(painelSuperior, BorderLayout.NORTH);

        // Pesquisa
        JPanel painelPesquisa = new JPanel(new FlowLayout(FlowLayout.LEFT));

        txtPesquisa = new JTextField(20);
        JButton btnMostrarTodos = new JButton("Mostrar Todos");

        painelPesquisa.add(new JLabel("Código do leitor:"));
        painelPesquisa.add(txtPesquisa);
        painelPesquisa.add(btnMostrarTodos);

        // Tabela
        modeloTabela = new DefaultTableModel(
                new Object[]{
                        "Código", "Nome", "Telefone", "Email", "Endereço"
                }, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabela = new JTable(modeloTabela);
        tabela.setRowHeight(25);

        JScrollPane scrollPane = new JScrollPane(tabela);

        JPanel painelInferior = new JPanel(new BorderLayout(5, 5));
        painelInferior.add(painelPesquisa, BorderLayout.NORTH);
        painelInferior.add(scrollPane, BorderLayout.CENTER);

        painelCentral.add(painelInferior, BorderLayout.CENTER);

        add(painelCentral, BorderLayout.CENTER);

        // Eventos
        btnCadastrar.addActionListener(e -> cadastrarLeitor());
        btnPesquisar.addActionListener(e -> pesquisarLeitor());
        btnAtualizar.addActionListener(e -> atualizarLeitor());
        btnRemover.addActionListener(e -> removerLeitor());
        btnLimpar.addActionListener(e -> limparCampos());
        btnMostrarTodos.addActionListener(e -> atualizarTabela());

        tabela.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                preencherFormulario();
            }
        });
    }

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
                        "Preencha todos os campos."
                );
                return;
            }

            leitorService.cadastrarLeitor(
                    codigo, nome, telefone, email, endereco
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Leitor cadastrado com sucesso!"
            );

            limparCampos();
            atualizarTabela();

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(
                    this, ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void pesquisarLeitor() {
        String codigo = txtPesquisa.getText().trim();

        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this, "Informe o código do leitor."
            );
            return;
        }

        Leitor leitor = leitorService.buscarPorCodigo(codigo);

        if (leitor == null) {
            JOptionPane.showMessageDialog(
                    this, "Leitor não encontrado."
            );
            return;
        }

        preencherCampos(leitor);
    }

    private void atualizarLeitor() {
        try {
            String codigo = txtCodigo.getText().trim();
            String nome = txtNome.getText().trim();
            String telefone = txtTelefone.getText().trim();
            String email = txtEmail.getText().trim();
            String endereco = txtEndereco.getText().trim();

            if (codigo.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this, "Informe o código do leitor."
                );
                return;
            }

            leitorService.atualizarLeitor(
                    codigo, nome, telefone, email, endereco
            );

            JOptionPane.showMessageDialog(
                    this, "Dados actualizados com sucesso!"
            );

            limparCampos();
            atualizarTabela();

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(
                    this, ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void removerLeitor() {
        String codigo = txtCodigo.getText().trim();

        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this, "Seleccione ou informe o código do leitor."
            );
            return;
        }

        int resposta = JOptionPane.showConfirmDialog(
                this,
                "Tem a certeza que deseja remover este leitor?",
                "Confirmar remoção",
                JOptionPane.YES_NO_OPTION
        );

        if (resposta != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            leitorService.removerLeitor(codigo);

            JOptionPane.showMessageDialog(
                    this, "Leitor removido com sucesso!"
            );

            limparCampos();
            atualizarTabela();

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(
                    this, ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void preencherFormulario() {
        int linha = tabela.getSelectedRow();

        if (linha == -1) {
            return;
        }

        txtCodigo.setText(modeloTabela.getValueAt(linha, 0).toString());
        txtNome.setText(modeloTabela.getValueAt(linha, 1).toString());
        txtTelefone.setText(modeloTabela.getValueAt(linha, 2).toString());
        txtEmail.setText(modeloTabela.getValueAt(linha, 3).toString());
        txtEndereco.setText(modeloTabela.getValueAt(linha, 4).toString());
    }

    private void preencherCampos(Leitor leitor) {
        txtCodigo.setText(leitor.getCodigo());
        txtNome.setText(leitor.getNome());
        txtTelefone.setText(leitor.getTelefone());
        txtEmail.setText(leitor.getEmail());
        txtEndereco.setText(leitor.getEndereco());
    }

    private void limparCampos() {
        txtCodigo.setText("");
        txtNome.setText("");
        txtTelefone.setText("");
        txtEmail.setText("");
        txtEndereco.setText("");
        txtPesquisa.setText("");
        tabela.clearSelection();
    }

    private void atualizarTabela() {
        modeloTabela.setRowCount(0);

        for (int i = 0; i < leitorService.getQuantidadeLeitores(); i++) {
            Leitor leitor = leitorService.listarLeitores().consultar(i);

            modeloTabela.addRow(new Object[]{
                    leitor.getCodigo(),
                    leitor.getNome(),
                    leitor.getTelefone(),
                    leitor.getEmail(),
                    leitor.getEndereco()
            });
        }
    }
}
