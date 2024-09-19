package br.unifor.indice.servicos;

import br.unifor.indice.modelo.Palavra;

import java.util.*;

public class TabelaHash {
    private Map<Character, List<Palavra>> tabela;

    public TabelaHash() {
        tabela = new HashMap<>();
    }

    public void inserirPalavra(Palavra palavra, int linha) {
        char letraInicial = palavra.getPalavra().charAt(0);
        tabela.putIfAbsent(letraInicial, new LinkedList<>());

        List<Palavra> listaPalavras = tabela.get(letraInicial); // Lista auxiliar
        boolean encontrada = false;

        for (Palavra p : listaPalavras) {
            if (p.getPalavra().equalsIgnoreCase(palavra.getPalavra())) {
                p.adicionarOcorrencia(linha);
                encontrada = true;
                break;
            }
        }

        if (!encontrada) {
            palavra.adicionarOcorrencia(linha);
            listaPalavras.add(palavra);
        }
    }
    public Map<Character, List<Palavra>> getTabela() {
        return tabela;
    }

}









