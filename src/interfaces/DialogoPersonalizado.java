
package interfaces;

import javax.swing.*;
import java.awt.*;

public class DialogoPersonalizado {

    private static final Color AZUL = new Color(42, 105, 170);
    private static final Color VERDE = new Color(35, 155, 100);
    private static final Color VERMELHO = new Color(210, 65, 65);
    private static final Color LARANJA = new Color(225, 145, 40);
    private static final Color FUNDO = new Color(245, 247, 250);
    private static final Color TEXTO = new Color(45, 55, 70);

    private DialogoPersonalizado() {
    }

    // Janela de confirmação
    public static boolean confirmar(
            Window pai,
            String titulo,
            String mensagem,
            String textoConfirmar,
            String textoCancelar) {

        final boolean[] resultado = {false};

        JDialog dialogo = criarDialogo(pai, titulo);

        JPanel painel = criarPainelPrincipal();

        painel.add(criarCabecalho("?", AZUL, titulo));
        painel.add(criarMensagem(mensagem));

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        botoes.setBackground(FUNDO);
        botoes.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        JButton cancelar = criarBotao(textoCancelar, new Color(115, 125, 140));
        JButton confirmar = criarBotao(textoConfirmar, AZUL);

        cancelar.addActionListener(e -> dialogo.dispose());

        confirmar.addActionListener(e -> {
            resultado[0] = true;
            dialogo.dispose();
        });

        botoes.add(cancelar);
        botoes.add(confirmar);

        painel.add(botoes);

        dialogo.setContentPane(painel);
        dialogo.pack();
        dialogo.setMinimumSize(new Dimension(430, 220));
        dialogo.setLocationRelativeTo(pai);
        dialogo.setVisible(true);

        return resultado[0];
    }

    // Janela de sucesso
    public static void sucesso(Window pai, String titulo, String mensagem) {
        mostrarMensagem(pai, titulo, mensagem, "✓", VERDE);
    }

    // Janela de aviso
    public static void aviso(Window pai, String titulo, String mensagem) {
        mostrarMensagem(pai, titulo, mensagem, "!", LARANJA);
    }

    // Janela de erro
    public static void erro(Window pai, String titulo, String mensagem) {
        mostrarMensagem(pai, titulo, mensagem, "×", VERMELHO);
    }

    private static void mostrarMensagem(
            Window pai,
            String titulo,
            String mensagem,
            String simbolo,
            Color cor) {

        JDialog dialogo = criarDialogo(pai, titulo);

        JPanel painel = criarPainelPrincipal();

        painel.add(criarCabecalho(simbolo, cor, titulo));
        painel.add(criarMensagem(mensagem));

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        botoes.setBackground(FUNDO);
        botoes.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        JButton fechar = criarBotao("Entendido", cor);
        fechar.addActionListener(e -> dialogo.dispose());

        botoes.add(fechar);
        painel.add(botoes);

        dialogo.setContentPane(painel);
        dialogo.pack();
        dialogo.setMinimumSize(new Dimension(430, 210));
        dialogo.setLocationRelativeTo(pai);
        dialogo.setVisible(true);
    }

    private static JDialog criarDialogo(Window pai, String titulo) {
        JDialog dialogo = new JDialog(
                pai,
                titulo,
                Dialog.ModalityType.APPLICATION_MODAL
        );

        dialogo.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialogo.setResizable(false);

        return dialogo;
    }

    private static JPanel criarPainelPrincipal() {
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBackground(FUNDO);
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 20));
        return painel;
    }

    private static JPanel criarCabecalho(
            String simbolo,
            Color cor,
            String titulo) {

        JPanel painel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));
        painel.setBackground(FUNDO);
        painel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel icone = new JLabel(simbolo, SwingConstants.CENTER);
        icone.setFont(new Font("Segoe UI", Font.BOLD, 25));
        icone.setForeground(Color.WHITE);
        icone.setOpaque(true);
        icone.setBackground(cor);
        icone.setPreferredSize(new Dimension(48, 48));

        JLabel texto = new JLabel(titulo);
        texto.setFont(new Font("Segoe UI", Font.BOLD, 19));
        texto.setForeground(TEXTO);

        painel.add(icone);
        painel.add(texto);

        return painel;
    }

    private static JPanel criarMensagem(String mensagem) {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBackground(FUNDO);
        painel.setBorder(BorderFactory.createEmptyBorder(15, 63, 15, 10));
        painel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel texto = new JLabel(
                "<html><div style='width:290px;'>" + mensagem + "</div></html>"
        );

        texto.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        texto.setForeground(new Color(90, 100, 115));

        painel.add(texto, BorderLayout.CENTER);

        return painel;
    }

    private static JButton criarBotao(String texto, Color cor) {
        JButton botao = new JButton(texto);

        botao.setFont(new Font("Segoe UI", Font.BOLD, 13));
        botao.setForeground(Color.WHITE);
        botao.setBackground(cor);
        botao.setFocusPainted(false);
        botao.setBorderPainted(false);
        botao.setOpaque(true);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botao.setBorder(BorderFactory.createEmptyBorder(11, 20, 11, 20));

        return botao;
    }
}
