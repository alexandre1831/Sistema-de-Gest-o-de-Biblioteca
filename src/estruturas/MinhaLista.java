
package estruturas;

public class MinhaLista<T> {

    private No<T> primeiro;
    private No<T> ultimo;
    private int tamanho;

    // Construtor
    public MinhaLista() {
        primeiro = null;
        ultimo = null;
        tamanho = 0;
    }

    // 1. Adicionar no início
    public void adicionarInicio(T dado) {

        No<T> novo = new No<>(dado);

        if (estaVazia()) {
            primeiro = novo;
            ultimo = novo;
        } else {
            novo.setProximo(primeiro);
            primeiro = novo;
        }

        tamanho++;
    }

    // 2. Adicionar no fim
    public void adicionarFim(T dado) {

        No<T> novo = new No<>(dado);

        if (estaVazia()) {
            primeiro = novo;
            ultimo = novo;
        } else {
            ultimo.setProximo(novo);
            ultimo = novo;
        }

        tamanho++;
    }

    // 3. Adicionar numa posição específica
    public void adicionar(int posicao, T dado) {

        if (posicao < 0 || posicao > tamanho) {
            throw new IndexOutOfBoundsException(
                    "Posição inválida: " + posicao
            );
        }

        if (posicao == 0) {
            adicionarInicio(dado);
            return;
        }

        if (posicao == tamanho) {
            adicionarFim(dado);
            return;
        }

        No<T> novo = new No<>(dado);
        No<T> atual = primeiro;

        for (int i = 0; i < posicao - 1; i++) {
            atual = atual.getProximo();
        }

        novo.setProximo(atual.getProximo());
        atual.setProximo(novo);

        tamanho++;
    }

    // 4. Consultar elemento numa posição
    public T consultar(int posicao) {

        validarPosicao(posicao);

        No<T> atual = primeiro;

        for (int i = 0; i < posicao; i++) {
            atual = atual.getProximo();
        }

        return atual.getDado();
    }

    // 5. Pesquisar elemento
    public int pesquisar(T dado) {

        No<T> atual = primeiro;
        int posicao = 0;

        while (atual != null) {

            if (java.util.Objects.equals(
                    atual.getDado(), dado)) {
                return posicao;
            }

            atual = atual.getProximo();
            posicao++;
        }

        return -1;
    }

    // 6. Extrair elemento de uma posição
    public T extrair(int posicao) {

        validarPosicao(posicao);

        No<T> removido;

        if (posicao == 0) {

            removido = primeiro;
            primeiro = primeiro.getProximo();

            tamanho--;

            if (tamanho == 0) {
                ultimo = null;
            }

            return removido.getDado();
        }

        No<T> anterior = primeiro;

        for (int i = 0; i < posicao - 1; i++) {
            anterior = anterior.getProximo();
        }

        removido = anterior.getProximo();

        anterior.setProximo(removido.getProximo());

        if (removido == ultimo) {
            ultimo = anterior;
        }

        tamanho--;

        return removido.getDado();
    }

    // 7. Eliminar por posição
    public void eliminar(int posicao) {
        extrair(posicao);
    }

    // 8. Eliminar por valor
    public boolean eliminarElemento(T dado) {

        int posicao = pesquisar(dado);

        if (posicao == -1) {
            return false;
        }

        extrair(posicao);
        return true;
    }

    // 9. Consultar tamanho
    public int getTamanho() {
        return tamanho;
    }

    // 10. Verificar se está vazia
    public boolean estaVazia() {
        return tamanho == 0;
    }

    // 11. Limpar a lista
    public void limpar() {
        primeiro = null;
        ultimo = null;
        tamanho = 0;
    }

    // 12. Mostrar elementos
    public void mostrar() {

        No<T> atual = primeiro;

        System.out.print("Lista: ");

        while (atual != null) {

            System.out.print(atual.getDado() + " -> ");

            atual = atual.getProximo();
        }

        System.out.println("null");
    }

    // Método auxiliar de validação
    private void validarPosicao(int posicao) {

        if (posicao < 0 || posicao >= tamanho) {
            throw new IndexOutOfBoundsException(
                    "Posição inválida: " + posicao
            );
        }
    }
}
