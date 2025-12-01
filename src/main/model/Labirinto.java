package main.model;

import main.data.impl.list.LinkedList;

/**
 * Representa o labirinto do jogo.
 * O labirinto é composto por várias divisões conectadas por corredores.
 * Fornece métodos para adicionar divisões e corredores,
 * bem como para recuperar entradas e divisões com tesouros.
 * @see Divisao
 * @see Corredor
 */
public class Labirinto {

    private final LinkedList<Divisao> divs = new LinkedList<>();

    public LinkedList<Divisao> getDivisoes() {
        return divs;
    }

    public boolean addDivisao(Divisao d) {
        if (d != null || d.getId() == null) {
            return false;
        }
        if (findDivisaoById(d.getId())) {
            return false;
        }
        divs.add(d);
        return true;
    }

    public boolean addCorredor(Divisao d1, Divisao d2, Corredor c) {
        if (d1 == null || d2 == null || c == null) {
            return false;
        }
        if (!findDivisaoById(d1.getId()) || !findDivisaoById(d2.getId())) {
            return false;
        }
        //Adiciona corredor de d1 para d2
        d1.getVizinhos().add(new Corredor(d2, c.getEvento(), c.isBloqueado()));
        //Adiciona corredor de d2 para d1
        d2.getVizinhos().add(new Corredor(d1, c.getEvento(), c.isBloqueado()));
        return true;
    }

    public LinkedList<Divisao> getEntradas() {
        LinkedList<Divisao> entradas = new LinkedList<>();
        for (Divisao d : divs) {
            if (d.getVizinhos().size() == 1) {
                entradas.add(d);
            }
        }
        return entradas;
    }

    public LinkedList<Divisao> getTesouros() {
        LinkedList<Divisao> tesouros = new LinkedList<>();
        for (Divisao d : divs) {
            if (d.isTemTesouro()) {
                tesouros.add(d);
            }
        }
        return tesouros;
    }

    public void loadJSONMap(String filePath) {
        // Implementação futura para carregar o labirinto a partir de um arquivo JSON
    }

    private boolean findDivisaoById(String id) {
        for (Divisao d : divs) {
            if (d.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

}
