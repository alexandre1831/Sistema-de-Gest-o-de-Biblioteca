
package estruturas;

public class No<T> {

    private T dado;
    private No<T> proximo;

    // Construtor
    public No(T dado) {
        this.dado = dado;
        this.proximo = null;
    }

    // Obter o dado
    public T getDado() {
        return dado;
    }

    // Alterar o dado
    public void setDado(T dado) {
        this.dado = dado;
    }

    // Obter o próximo nó
    public No<T> getProximo() {
        return proximo;
    }

    // Definir o próximo nó
    public void setProximo(No<T> proximo) {
        this.proximo = proximo;
    }
}
