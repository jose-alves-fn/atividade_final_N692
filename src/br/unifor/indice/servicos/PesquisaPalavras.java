package br.unifor.indice.servicos;

import br.unifor.indice.modelo.Palavra;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PesquisaPalavras {

    private Map<Character, List<Palavra>> tabelaHash;

    public PesquisaPalavras(Map<Character, List<Palavra>> tabelaHash) {
        this.tabelaHash = tabelaHash;
    }

    public Map<String, List<Integer>> buscarPalavras(List<String> palavrasChave) {
        Map<String, List<Integer>> resultados = new HashMap<>();

        for (String chave : palavrasChave) {
            char letraInicial = chave.toLowerCase().charAt(0);
            List<Palavra> listaPalavras = tabelaHash.getOrDefault(letraInicial, null);

            if (listaPalavras != null) {
                for (Palavra palavra : listaPalavras) {
                    if (palavra.getPalavra().equalsIgnoreCase(chave)) {
                        resultados.put(chave, palavra.getOcorrencias());
                        break;
                    }
                }
            }
        }

        return resultados;
    }
}



