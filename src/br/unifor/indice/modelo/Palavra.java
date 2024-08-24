package br.unifor.indice.modelo;

import java.util.ArrayList;
import java.util.List;

public class Palavra {

    private String palavra;
    private List<Integer> ocorrencias;

    public Palavra(String palavra) {
        this.palavra = palavra;
        this.ocorrencias = new ArrayList<>();
    }

    public String getPalavra() {
        return palavra;
    }

    public void setPalavra(String palavra) {
        this.palavra = palavra;
    }

    public List<Integer> getOcorrencias() {
        return ocorrencias;
    }

    public void adicionarOcorrencia(int ocorrencia) {
        this.ocorrencias.add(ocorrencia);
    }

    @Override
    public String toString() {
        return palavra + ocorrencias;
    }
}

