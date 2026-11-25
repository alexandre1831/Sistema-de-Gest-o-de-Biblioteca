
import estruturas.MinhaListaDupla;

public class Main {

    public static void main(String[] args) {

        MinhaListaDupla<Integer> lista =
                new MinhaListaDupla<>();

        lista.adicionarFim(10);
        lista.adicionarFim(20);
        lista.adicionarFim(30);

        lista.adicionar(1, 15);

        System.out.println("Percurso normal:");
        lista.mostrarFrente();

        System.out.println("Percurso inverso:");
        lista.mostrarInverso();

        System.out.println("Elemento na posição 2: "
                + lista.consultar(2));

        System.out.println("Elemento removido: "
                + lista.extrair(1));

        System.out.println("Lista depois da remoção:");
        lista.mostrarFrente();

        System.out.println("Tamanho: "
                + lista.getTamanho());
    }
}
