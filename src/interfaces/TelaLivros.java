
package interfaces;

import modelos.Livro;
import servicos.LivroService;
import estruturas.MinhaLista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TelaLivros extends JPanel {

    private LivroService livroService;

    // Campos do formulário
    private JTextField txtCodigo;
    private JTextField txtTitulo;
    private JTextField txtAutor;
    private JTextField txtEditora;
    private JTextField txtAno;

    // Campo de pesquisa
    private JTextField txtPesquisa;

    // Tabela
    private JTable tabela;
    private DefaultTableModel modeloTabela;

    // Construtor
    public TelaLivros(LivroService livroService) {

        this.livroService = livroService;

        configurarPainel();
        criarComponentes();
        atualizarTabela();
    }

    // Configuração principal
    private void configurarPainel() {

        setLayout(new BorderLayout(15, 15));
        setBackground(new Color(245, 247, 250));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    }

    // Criar componentes
    private void criarComponentes() {

        // Cabeçalho
        JLabel titulo = new JLabel("Gestão de Livros");
        titulo.setFont(new Font("Arial", Font.BOLD, 25));

        JLabel descricao = new JLabel(
                "Cadastrar, pesquisar e consultar livros da biblioteca."
        );

        JPanel painelCabecalho = new JPanel(new GridLayout(2, 1));
        painelCabecalho.setBackground(getBackground());

        painelCabecalho.add(titulo);
        painelCabecalho.add(descricao);

        add(painelCabecalho, BorderLayout.NORTH);

        // Centro
        JPanel painelCentro = new JPanel(new BorderLayout(10, 15));
        painelCentro.setBackground(getBackground());

        painelCentro.add(criarFormulario(), BorderLayout.NORTH);
        painelCentro.add(criarTabela(), BorderLayout.CENTER);

        add(painelCentro, BorderLayout.CENTER);
    }

    // Formulário de cadastro
    private JPanel criarFormulario() {

        JPanel painel = new JPanel(new GridLayout(2, 5, 10, 10));

        painel.setBorder(
                BorderFactory.createTitledBorder("Dados do Livro")
        );

        painel.setBackground(Color.WHITE);

        txtCodigo = new JTextField();
        txtTitulo = new JTextField();
        txtAutor = new JTextField();
        txtEditora = new JTextField();
        txtAno = new JTextField();

        painel.add(criarCampo("Código", txtCodigo));
        painel.add(criarCampo("Título", txtTitulo));
        painel.add(criarCampo("Autor", txtAutor));
        painel.add(criarCampo("Editora", txtEditora));
        painel.add(criarCampo("Ano de publicação", txtAno));

        JButton btnCadastrar = new JButton("Cadastrar Livro");
        btnCadastrar.addActionListener(e -> cadastrarLivro());

        JPanel painelBotao = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelBotao.setBackground(Color.WHITE);
        painelBotao.add(btnCadastrar);

        painel.add(painelBotao);

        return painel;
    }

    // Criar campo com identificação
    private JPanel criarCampo(String nome, JTextField campo) {

        JPanel painel = new JPanel(new BorderLayout(5, 5));
        painel.setBackground(Color.WHITE);

        JLabel label = new JLabel(nome);

        painel.add(label, BorderLayout.NORTH);
        painel.add(campo, BorderLayout.CENTER);

        return painel;
    }

    // Criar tabela e pesquisa
    private JPanel criarTabela() {

        JPanel painel = new JPanel(new BorderLayout(10, 10));
        painel.setBackground(getBackground());

        modeloTabela = new DefaultTableModel(
                new String[]{
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
        tabela.setRowHeight(25);
        tabela.getTableHeader().setFont(
                new Font("Arial", Font.BOLD, 12)
        );

        JScrollPane scroll = new JScrollPane(tabela);

        // Pesquisa
        txtPesquisa = new JTextField(20);

        JButton btnPesquisar = new JButton("Pesquisar");
        JButton btnListar = new JButton("Mostrar Todos");
        JButton btnEliminar = new JButton("Eliminar Livro");

        btnPesquisar.addActionListener(e -> pesquisarLivro());
        btnListar.addActionListener(e -> atualizarTabela());
        btnEliminar.addActionListener(e -> eliminarLivro());

        JPanel painelPesquisa = new JPanel(new FlowLayout(FlowLayout.LEFT));

        painelPesquisa.setBackground(Color.WHITE);
        painelPesquisa.add(new JLabel("Pesquisar por código ou título:"));
        painelPesquisa.add(txtPesquisa);
        painelPesquisa.add(btnPesquisar);
        painelPesquisa.add(btnListar);
        painelPesquisa.add(btnEliminar);

        painel.add(painelPesquisa, BorderLayout.NORTH);
        painel.add(scroll, BorderLayout.CENTER);

        return painel;
    }

    // Cadastrar livro
    private void cadastrarLivro() {

        try {

            String codigo = txtCodigo.getText().trim();
            String titulo = txtTitulo.getText().trim();
            String autor = txtAutor.getText().trim();
            String editora = txtEditora.getText().trim();

            int ano = Integer.parseInt(txtAno.getText().trim());

            boolean resultado = livroService.cadastrarLivro(
                    codigo, titulo, autor, editora, ano
            );

            if (resultado) {

                JOptionPane.showMessageDialog(
                        this,
                        "Livro cadastrado com sucesso!"
                );

                limparCampos();
                atualizarTabela();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Já existe um livro com esse código."
                );
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Introduza um ano de publicação válido."
            );

        } catch (IllegalArgumentException e) {

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage()
            );
        }
    }

    // Atualizar tabela
    private void atualizarTabela() {

        modeloTabela.setRowCount(0);

        MinhaLista<Livro> livros = livroService.listarLivros();

        for (int i = 0; i < livros.getTamanho(); i++) {

            Livro livro = livros.consultar(i);

            adicionarLinha(livro);
        }
    }

    // Adicionar livro à tabela
    private void adicionarLinha(Livro livro) {

        modeloTabela.addRow(new Object[]{
                livro.getCodigo(),
                livro.getTitulo(),
                livro.getAutor(),
                livro.getEditora(),
                livro.getAnoPublicacao(),
                livro.isDisponivel() ? "Disponível" : "Emprestado"
        });
    }

    // Pesquisar livro
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

        // Primeiro tenta pesquisar pelo código
        Livro livro = livroService.buscarPorCodigo(pesquisa);

        if (livro != null) {

            adicionarLinha(livro);
            return;
        }

        // Se não encontrar, pesquisa pelo título
        MinhaLista<Livro> resultados =
                livroService.buscarPorTitulo(pesquisa);

        for (int i = 0; i < resultados.getTamanho(); i++) {

            adicionarLinha(resultados.consultar(i));
        }

        if (resultados.getTamanho() == 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Nenhum livro encontrado."
            );
        }
    }

    // Eliminar livro selecionado
    private void eliminarLivro() {

        int linha = tabela.getSelectedRow();

        if (linha == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Selecione um livro na tabela."
            );

            return;
        }

        String codigo = modeloTabela.getValueAt(linha, 0).toString();

        int confirmacao = JOptionPane.showConfirmDialog(
                this,
                "Tem certeza de que deseja eliminar este livro?",
                "Confirmar eliminação",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacao == JOptionPane.YES_OPTION) {

            boolean resultado = livroService.removerLivro(codigo);

            if (resultado) {

                JOptionPane.showMessageDialog(
                        this,
                        "Livro eliminado com sucesso!"
                );

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Não foi possível eliminar o livro. "
                                + "Verifique se possui empréstimo ativo."
                );
            }

            atualizarTabela();
        }
    }

    // Limpar formulário
    private void limparCampos() {

        txtCodigo.setText("");
        txtTitulo.setText("");
        txtAutor.setText("");
        txtEditora.setText("");
        txtAno.setText("");

        txtCodigo.requestFocus();
    }
}
