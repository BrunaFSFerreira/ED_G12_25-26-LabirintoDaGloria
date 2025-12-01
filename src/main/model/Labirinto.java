package main.model;

import main.data.impl.graph.UnweightedGraph.AdjListGraph;
import main.data.impl.list.LinkedList;

/**
 * Representa o labirinto do jogo.
 * O labirinto é composto por divisões conectadas por corredores.
 * Utiliza um grafo para representar as conexões entre as divisões.
 * @see Divisao
 * @see Corredor
 */
public class Labirinto {

    private final AdjListGraph<Divisao> divs = new AdjListGraph<>();

    /**
     * Adiciona uma nova divisão ao labirinto.
     * @param d A divisão a ser adicionada.
     * @return true se a divisão foi adicionada com sucesso, false caso contrário.
     */
    public boolean addDivisao(Divisao d) {
        if (d != null || d.getId() == null || findDivisaoById(d.getId())) {
            return false;
        }

        divs.addVertex(d);
        return true;
    }

    /**
     * Adiciona um corredor entre duas divisões no labirinto.
     * @param d1 A divisão de origem.
     * @param d2 A divisão de destino.
     * @param c O corredor que conecta as duas divisões.
     * @return true se o corredor foi adicionado com sucesso, false caso contrário.
     */
    public boolean addCorredor(Divisao d1, Divisao d2, Corredor c) {
        if (d1 == null || d2 == null || c == null) {
            return false;
        }
        if (!findDivisaoById(d1.getId()) || !findDivisaoById(d2.getId())) {
            return false;
        }

        divs.addEdge(d1, d2, 0);
        return true;
    }

    /**
     * Obtém todas as divisões que são entradas do labirinto.
     * Uma divisão é considerada uma entrada se nenhum corredor leva a ela.
     * @return Uma lista de divisões que são entradas do labirinto.
     */
    public LinkedList<Divisao> getEntradas() {
        LinkedList<Divisao> entradas = new LinkedList<>();
        for (Divisao divisao : divs) {
            boolean isEntrada = true;
            for (Divisao vizinho : divs) {
                for (Corredor corredor : vizinho.getVizinhos()) {
                    if (corredor.getDestino().equals(divisao)) {
                        isEntrada = false;
                        break;
                    }
                }
                if (!isEntrada) break;
            }
            if (isEntrada) {
                entradas.add(divisao);
            }
        }
        return entradas;
    }

    /**
     * Obtém todas as divisões que possuem tesouros no Labirinto.
     * @return Uma lista contem todas as divisões com tesouros.
     */
    public LinkedList<Divisao> getTesouros() {
       LinkedList<Divisao> tesouros = new LinkedList<>();
        for (Divisao divisao : divs) {
            if (divisao.isTemTesouro()) {
                tesouros.add(divisao);
            }
        }
        return tesouros;
    }

    public void loadJSONMap(String filePath) {
        // Implementação futura para carregar o labirinto a partir de um arquivo JSON
    }

    /**
     * Verifica se uma divisão com o ID especificado já existe no labirinto.
     * @param id O ID da divisão a ser verificada.
     * @return true se a divisão existir, false caso contrário.
     */
    private boolean findDivisaoById(String id) {
        for (Divisao divisao : divs) {
            if (divisao.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

}
