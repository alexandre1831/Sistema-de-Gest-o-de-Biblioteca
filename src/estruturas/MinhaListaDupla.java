

package estruturas;

public class MinhaListaDupla<T> {

    private NoDuplo<T> primeiro;
    private NoDuplo<T> ultimo;
    private int tamanho;

    // Construtor
    public MinhaListaDupla() {
        primeiro = null;
        ultimo = null;
        tamanho = 0;
    }

    // 1. Adicionar no início
    public void adicionarInicio(T dado) {

        NoDuplo<T> novo = new NoDuplo<>(dado);

        if (estaVazia()) {
            primeiro = novo;
            ultimo = novo;
        } else {
            novo.setProximo(primeiro);
            primeiro.setAnterior(novo);
            primeiro = novo;
        }

        tamanho++;
    }

    // 2. Adicionar no fim
    public void adicionarFim(T dado) {

        NoDuplo<T> novo = new NoDuplo<>(dado);

        if (estaVazia()) {
            primeiro = novo;
            ultimo = novo;
        } else {
            novo.setAnterior(ultimo);
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

        NoDuplo<T> atual = obterNo(posicao);
        NoDuplo<T> anterior = atual.getAnterior();

        NoDuplo<T> novo = new NoDuplo<>(dado);

        novo.setAnterior(anterior);
        novo.setProximo(atual);

        anterior.setProximo(novo);
        atual.setAnterior(novo);

        tamanho++;
    }

    // 4. Consultar elemento numa posição
    public T consultar(int posicao) {

        return obterNo(posicao).getDado();
    }

    // 5. Pesquisar elemento
    public int pesquisar(T dado) {

        NoDuplo<T> atual = primeiro;
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

        NoDuplo<T> removido = obterNo(posicao);

        NoDuplo<T> anterior = removido.getAnterior();
        NoDuplo<T> proximo = removido.getProximo();

        if (anterior == null) {
            primeiro = proximo;
        } else {
            anterior.setProximo(proximo);
        }

        if (proximo == null) {
            ultimo = anterior;
        } else {
            proximo.setAnterior(anterior);
        }

        tamanho--;

        // Desligar o nó removido
        removido.setAnterior(null);
        removido.setProximo(null);

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

    // 9. Obter tamanho
    public int getTamanho() {
        return tamanho;
    }

    // 10. Verificar se está vazia
    public boolean estaVazia() {
        return tamanho == 0;
    }

    // 11. Obter primeiro elemento
    public T obterPrimeiro() {

        if (estaVazia()) {
            throw new java.util.NoSuchElementException(
                    "A lista está vazia."
            );
        }

        return primeiro.getDado();
    }

    // 12. Obter último elemento
    public T obterUltimo() {

        if (estaVazia()) {
            throw new java.util.NoSuchElementException(
                    "A lista está vazia."
            );
        }

        return ultimo.getDado();
    }

    // 13. Mostrar do início para o fim
    public void mostrar() {

        NoDuplo<T> atual = primeiro;

        System.out.print("Lista: ");

        while (atual != null) {

            System.out.print(atual.getDado() + " <-> ");

            atual = atual.getProximo();
        }

        System.out.println("null");
    }

    // 14. Mostrar do fim para o início
    public void mostrarInverso() {

        NoDuplo<T> atual = ultimo;

        System.out.print("Lista inversa: ");

        while (atual != null) {

            System.out.print(atual.getDado() + " <-> ");

            atual = atual.getAnterior();
        }

        System.out.println("null");
    }

    // 15. Limpar a lista
    public void limpar() {

        NoDuplo<T> atual = primeiro;

        while (atual != null) {

            NoDuplo<T> proximo = atual.getProximo();

            atual.setAnterior(null);
            atual.setProximo(null);

            atual = proximo;
        }

        primeiro = null;
        ultimo = null;
        tamanho = 0;
    }

    // Método auxiliar: obter nó numa posição
    private NoDuplo<T> obterNo(int posicao) {

        validarPosicao(posicao);

        NoDuplo<T> atual;

        // Percorrer a partir do início
        if (posicao < tamanho / 2) {

            atual = primeiro;

            for (int i = 0; i < posicao; i++) {
                atual = atual.getProximo();
            }

        } else {

            // Percorrer a partir do fim
            atual = ultimo;

            for (int i = tamanho - 1; i > posicao; i--) {
                atual = atual.getAnterior();
            }
        }

        return atual;
    }

    // Validar posição
    private void validarPosicao(int posicao) {

        if (posicao < 0 || posicao >= tamanho) {
            throw new IndexOutOfBoundsException(
                    "Posição inválida: " + posicao
            );
        }
    }
}
