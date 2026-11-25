
package estruturas;

import java.util.NoSuchElementException;

public class MinhaPilha<T> {

    private No<T> topo;
    private int tamanho;

    // Construtor
    public MinhaPilha() {
        topo = null;
        tamanho = 0;
    }

    // 1. Empilhar: adicionar elemento no topo
    public void empilhar(T dado) {

        No<T> novo = new No<>(dado);

        novo.setProximo(topo);

        topo = novo;

        tamanho++;
    }

    // 2. Desempilhar: retirar elemento do topo
    public T desempilhar() {

        if (estaVazia()) {
            throw new NoSuchElementException(
                    "A pilha está vazia!"
            );
        }

        No<T> removido = topo;

        topo = topo.getProximo();

        removido.setProximo(null);

        tamanho--;

        return removido.getDado();
    }

    // 3. Consultar o elemento do topo
    public T consultarTopo() {

        if (estaVazia()) {
            throw new NoSuchElementException(
                    "A pilha está vazia!"
            );
        }

        return topo.getDado();
    }

    // 4. Consultar tamanho
    public int getTamanho() {
        return tamanho;
    }

    // 5. Verificar se está vazia
    public boolean estaVazia() {
        return tamanho == 0;
    }

    // 6. Limpar a pilha
    public void limpar() {
        topo = null;
        tamanho = 0;
    }

    // 7. Mostrar elementos
    public void mostrar() {

        No<T> atual = topo;

        System.out.println("Pilha (topo para base):");

        while (atual != null) {

            System.out.println("| " + atual.getDado() + " |");

            atual = atual.getProximo();
        }

        System.out.println("-----");
    }
}
