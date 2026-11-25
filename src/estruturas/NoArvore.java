
package estruturas;

public class NoArvore<T> {

    private T dado;
    private NoArvore<T> esquerdo;
    private NoArvore<T> direito;

    // Construtor
    public NoArvore(T dado) {
        this.dado = dado;
        this.esquerdo = null;
        this.direito = null;
    }

    // Obter dado
    public T getDado() {
        return dado;
    }

    // Alterar dado
    public void setDado(T dado) {
        this.dado = dado;
    }

    // Obter filho esquerdo
    public NoArvore<T> getEsquerdo() {
        return esquerdo;
    }

    // Definir filho esquerdo
    public void setEsquerdo(NoArvore<T> esquerdo) {
        this.esquerdo = esquerdo;
    }

    // Obter filho direito
    public NoArvore<T> getDireito() {
        return direito;
    }

    // Definir filho direito
    public void setDireito(NoArvore<T> direito) {
        this.direito = direito;
    }
}
