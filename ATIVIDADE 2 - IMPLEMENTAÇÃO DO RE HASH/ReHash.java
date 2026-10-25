import java.util.Random;

public class ReHash {
    static int[] tabela = new int[100];
    static int ocupadas = 0;
    static int bloqueados = 0;
    static int totalColisoes = 0;
    // Pódio dos 3 elementos que mais colidiram (v = valor, c = colisões)
    static int v1 = -1, c1 = -1;   // 1º lugar
    static int v2 = -1, c2 = -1;   // 2º lugar
    static int v3 = -1, c3 = -1;   // 3º lugar
    static int h(int valor) {
        return valor % 100;
    }
    static int r(int i) {
        return (i + 1) % 100;
    }
    static void inserir(int valor) {
        // Limite de 90%: com 90 elementos, não insere mais
        if (ocupadas >= 90) {
            bloqueados++;
            return;
        }
        int i = h(valor);
        int colisoes = 0;
        while (tabela[i] != -1) {   // posição ocupada: colidiu, tenta a próxima
            colisoes++;
            i = r(i);
        }
        tabela[i] = valor;
        ocupadas++;
        totalColisoes += colisoes;
        // Atualiza o pódio (em caso de empate, fica quem chegou primeiro)
        if (colisoes > c1) {
            v3 = v2; c3 = c2;
            v2 = v1; c2 = c1;
            v1 = valor; c1 = colisoes;
        } else if (colisoes > c2) {
            v3 = v2; c3 = c2;
            v2 = valor; c2 = colisoes;
        } else if (colisoes > c3) {
            v3 = valor; c3 = colisoes;
        }
    }
    static int buscar(int valor) {
        int i = h(valor);
        while (tabela[i] != -1 && tabela[i] != valor) {
            i = r(i);
        }
        if (tabela[i] == valor) {
            return i;
        }
        return -1;
    }
    public static void main(String[] args) {
        // Marca todas as posições como livres
        for (int i = 0; i < 100; i++) {
            tabela[i] = -1;
        }
        Random gerador = new Random(1090);
        for (int i = 0; i < 100; i++) {
            inserir(gerador.nextInt(100001));
        }
        System.out.println("Elementos inseridos: " + ocupadas);
        System.out.println("Insercoes bloqueadas (tabela em 90%): " + bloqueados);
        System.out.println("Total de colisoes: " + totalColisoes);
        System.out.println("\nOs 3 elementos que mais colidiram:");
        System.out.println("1o: " + v1 + " (" + c1 + " colisoes)");
        System.out.println("2o: " + v2 + " (" + c2 + " colisoes)");
        System.out.println("3o: " + v3 + " (" + c3 + " colisoes)");
        System.out.print("\nPosicoes livres: ");
        for (int i = 0; i < 100; i++) {
            if (tabela[i] == -1) {
                System.out.print(i + " ");
            }
        }
        System.out.println();
        System.out.println("\nTeste de busca:");
        System.out.println(v1 + " encontrado na posicao " + buscar(v1));
        System.out.println(v2 + " encontrado na posicao " + buscar(v2));
        System.out.println(v3 + " encontrado na posicao " + buscar(v3));
    }
}