
package estruturas;

import java.util.NoSuchElementException;

public class MinhaFila<T> {

    private No<T> inicio;
    private No<T> fim;
    private int tamanho;

    // Construtor
    public MinhaFila() {
        inicio = null;
        fim = null;
        tamanho = 0;
    }

    // 1. Enfileirar: adicionar elemento no fim
    public void enfileirar(T dado) {

        No<T> novo = new No<>(dado);

        if (estaVazia()) {

            inicio = novo;
            fim = novo;

        } else {

            fim.setProximo(novo);
            fim = novo;
        }

        tamanho++;
    }

    // 2. Desenfileirar: retirar elemento do início
    public T desenfileirar() {

        if (estaVazia()) {
            throw new NoSuchElementException(
                    "A fila está vazia!"
            );
        }

        No<T> removido = inicio;

        inicio = inicio.getProximo();

        removido.setProximo(null);

        tamanho--;

        if (inicio == null) {
            fim = null;
        }

        return removido.getDado();
    }

    // 3. Consultar primeiro elemento
    public T consultarInicio() {

        if (estaVazia()) {
            throw new NoSuchElementException(
                    "A fila está vazia!"
            );
        }

        return inicio.getDado();
    }

    // 4. Consultar último elemento
    public T consultarFim() {

        if (estaVazia()) {
            throw new NoSuchElementException(
                    "A fila está vazia!"
            );
        }

        return fim.getDado();
    }

    // 5. Consultar tamanho
    public int getTamanho() {
        return tamanho;
    }

    // 6. Verificar se está vazia
    public boolean estaVazia() {
        return tamanho == 0;
    }

    // 7. Limpar fila
    public void limpar() {

        inicio = null;
        fim = null;
        tamanho = 0;
    }

    // 8. Mostrar elementos
    public void mostrar() {

        No<T> atual = inicio;

        System.out.print("Início → ");

        while (atual != null) {

            System.out.print(atual.getDado() + " → ");

            atual = atual.getProximo();
        }

        System.out.println("Fim → null");
    }
}
