package br.unifor.indice.servicos;

import br.unifor.indice.modelo.Palavra;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TabelaHash {
    private Map<Character, List<Palavra>> tabela;

    public TabelaHash() {
        tabela = new HashMap<>();
    }

    public void inserirPalavra(Palavra palavra, int linha) {
        char letraInicial = palavra.getPalavra().charAt(0);
        tabela.putIfAbsent(letraInicial, new ArrayList<>());

        List<Palavra> listaPalavras = tabela.get(letraInicial);
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

    public void imprimirTabela() {
        for (Map.Entry<Character, List<Palavra>> entry : tabela.entrySet()) {
            char chave = entry.getKey();
            System.out.println("Chave: " + chave);
            for (Palavra palavra : entry.getValue()) {
                System.out.println(palavra);
            }
        }
    }

    public Map<Character, List<Palavra>> getTabela() {
        return tabela;
    }
}
