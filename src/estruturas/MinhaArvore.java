
package estruturas;

import java.util.NoSuchElementException;

public class MinhaArvore<T extends Comparable<T>> {

    private NoArvore<T> raiz;
    private int tamanho;

    // Construtor
    public MinhaArvore() {
        raiz = null;
        tamanho = 0;
    }

    // 1. Inserir elemento
    public boolean inserir(T dado) {

        if (dado == null) {
            throw new IllegalArgumentException(
                    "Não é permitido inserir null na árvore."
            );
        }

        if (pesquisar(dado)) {
            return false;
        }

        raiz = inserirRecursivo(raiz, dado);
        tamanho++;

        return true;
    }

    // Método auxiliar para inserir
    private NoArvore<T> inserirRecursivo(
            NoArvore<T> atual, T dado) {

        if (atual == null) {
            return new NoArvore<>(dado);
        }

        int comparacao = dado.compareTo(atual.getDado());

        if (comparacao < 0) {

            atual.setEsquerdo(
                    inserirRecursivo(atual.getEsquerdo(), dado)
            );

        } else if (comparacao > 0) {

            atual.setDireito(
                    inserirRecursivo(atual.getDireito(), dado)
            );
        }

        return atual;
    }

    // 2. Pesquisar elemento
    public boolean pesquisar(T dado) {

        if (dado == null) {
            return false;
        }

        return pesquisarRecursivo(raiz, dado);
    }

    // Método auxiliar para pesquisar
    private boolean pesquisarRecursivo(
            NoArvore<T> atual, T dado) {

        if (atual == null) {
            return false;
        }

        int comparacao = dado.compareTo(atual.getDado());

        if (comparacao == 0) {
            return true;
        }

        if (comparacao < 0) {
            return pesquisarRecursivo(
                    atual.getEsquerdo(), dado
            );
        }

        return pesquisarRecursivo(
                atual.getDireito(), dado
        );
    }

    // 3. Remover elemento
    public boolean remover(T dado) {

        if (dado == null || !pesquisar(dado)) {
            return false;
        }

        raiz = removerRecursivo(raiz, dado);
        tamanho--;

        return true;
    }

    // Método auxiliar para remover
    private NoArvore<T> removerRecursivo(
            NoArvore<T> atual, T dado) {

        if (atual == null) {
            return null;
        }

        int comparacao = dado.compareTo(atual.getDado());

        if (comparacao < 0) {

            atual.setEsquerdo(
                    removerRecursivo(atual.getEsquerdo(), dado)
            );

        } else if (comparacao > 0) {

            atual.setDireito(
                    removerRecursivo(atual.getDireito(), dado)
            );

        } else {

            // Caso 1: nó sem filhos
            if (atual.getEsquerdo() == null
                    && atual.getDireito() == null) {
                return null;
            }

            // Caso 2: nó sem filho esquerdo
            if (atual.getEsquerdo() == null) {
                return atual.getDireito();
            }

            // Caso 2: nó sem filho direito
            if (atual.getDireito() == null) {
                return atual.getEsquerdo();
            }

            // Caso 3: nó com dois filhos
            NoArvore<T> sucessor =
                    encontrarMenor(atual.getDireito());

            atual.setDado(sucessor.getDado());

            atual.setDireito(
                    removerRecursivo(
                            atual.getDireito(),
                            sucessor.getDado()
                    )
            );
        }

        return atual;
    }

    // 4. Encontrar o menor elemento
    private NoArvore<T> encontrarMenor(NoArvore<T> atual) {

        while (atual.getEsquerdo() != null) {
            atual = atual.getEsquerdo();
        }

        return atual;
    }

    public T obterMenor() {

        if (estaVazia()) {
            throw new NoSuchElementException(
                    "A árvore está vazia."
            );
        }

        return encontrarMenor(raiz).getDado();
    }

    // 5. Encontrar o maior elemento
    private NoArvore<T> encontrarMaior(NoArvore<T> atual) {

        while (atual.getDireito() != null) {
            atual = atual.getDireito();
        }

        return atual;
    }

    public T obterMaior() {

        if (estaVazia()) {
            throw new NoSuchElementException(
                    "A árvore está vazia."
            );
        }

        return encontrarMaior(raiz).getDado();
    }

    // 6. Percurso em ordem: esquerda, raiz, direita
    public void percursoEmOrdem() {
        percursoEmOrdemRecursivo(raiz);
        System.out.println();
    }

    private void percursoEmOrdemRecursivo(
            NoArvore<T> atual) {

        if (atual != null) {

            percursoEmOrdemRecursivo(atual.getEsquerdo());

            System.out.print(atual.getDado() + " ");

            percursoEmOrdemRecursivo(atual.getDireito());
        }
    }

    // 7. Percurso pré-ordem: raiz, esquerda, direita
    public void percursoPreOrdem() {
        percursoPreOrdemRecursivo(raiz);
        System.out.println();
    }

    private void percursoPreOrdemRecursivo(
            NoArvore<T> atual) {

        if (atual != null) {

            System.out.print(atual.getDado() + " ");

            percursoPreOrdemRecursivo(atual.getEsquerdo());

            percursoPreOrdemRecursivo(atual.getDireito());
        }
    }

    // 8. Percurso pós-ordem: esquerda, direita, raiz
    public void percursoPosOrdem() {
        percursoPosOrdemRecursivo(raiz);
        System.out.println();
    }

    private void percursoPosOrdemRecursivo(
            NoArvore<T> atual) {

        if (atual != null) {

            percursoPosOrdemRecursivo(atual.getEsquerdo());

            percursoPosOrdemRecursivo(atual.getDireito());

            System.out.print(atual.getDado() + " ");
        }
    }

    // 9. Consultar tamanho
    public int getTamanho() {
        return tamanho;
    }

    // 10. Verificar se está vazia
    public boolean estaVazia() {
        return tamanho == 0;
    }

    // 11. Calcular altura
    public int altura() {
        return calcularAltura(raiz);
    }

    private int calcularAltura(NoArvore<T> atual) {

        if (atual == null) {
            return -1;
        }

        int alturaEsquerda =
                calcularAltura(atual.getEsquerdo());

        int alturaDireita =
                calcularAltura(atual.getDireito());

        return 1 + Math.max(
                alturaEsquerda,
                alturaDireita
        );
    }

    // 12. Limpar árvore
    public void limpar() {
        raiz = null;
        tamanho = 0;
    }
}
