import java.util.Random;

public class Hash {
    class No {
        int dado;
        No proximo;
        No(int valor) {
            this.dado = valor;
            this.proximo = null;
        }
    }

    No[] tabela = new No[100];

    // Três últimos valores que entraram em listas (colisões)
    int ultimo1 = -1; // o mais antigo dos três
    int ultimo2 = -1;
    int ultimo3 = -1; // o mais recente

    int h(int valor) {
        return valor % 100;
    }

    void imprimir_hashing(int posicao) {
        No atual = tabela[posicao];
        while (atual != null) {
            System.out.print(atual.dado + " -> ");
            atual = atual.proximo;
        }
        System.out.println("null");
    }

    void insere_hashing(int valor) {
        No novoNo = new No(valor);
        int i = h(valor);
        if (tabela[i] == null) {
            tabela[i] = novoNo;
        } else {
            No atual = tabela[i];
            while (atual.proximo != null && atual.proximo.dado < valor) {
                atual = atual.proximo;
            }
            novoNo.proximo = atual.proximo;
            atual.proximo = novoNo;

            // Guarda os três últimos valores inseridos em listas
            ultimo1 = ultimo2;
            ultimo2 = ultimo3;
            ultimo3 = valor;
        }
    }

    void contar_informacoes() {
        int vazias = 0;
        int listas = 0;
        int maiorTamanho = 0;
        int posicaoMaior = -1;

        for (int i = 0; i < 100; i++) {
            if (tabela[i] == null) {
                vazias++;
            } else if (tabela[i].proximo != null) {
                listas++;
            }

            // Conta o tamanho da lista desta posição
            int tamanho = 0;
            No atual = tabela[i];
            while (atual != null) {
                tamanho++;
                atual = atual.proximo;
            }
            if (tamanho > maiorTamanho) {
                maiorTamanho = tamanho;
                posicaoMaior = i;
            }
        }

        System.out.println("Posicoes vazias: " + vazias);
        System.out.println("Listas encadeadas geradas: " + listas);
        System.out.println("Maior lista: posicao " + posicaoMaior + ", com " + maiorTamanho + " elementos");
        imprimir_hashing(posicaoMaior);
        System.out.println("Tres ultimos valores inseridos em listas: "
                + ultimo1 + ", " + ultimo2 + ", " + ultimo3);
    }

    public static void main(String[] args) {
        Hash hash = new Hash();
        Random gerador = new Random(1090);
        for (int i = 0; i < 1000; i++) {
            int numero = gerador.nextInt(100001);
            hash.insere_hashing(numero);
        }
        hash.contar_informacoes();
    }
}