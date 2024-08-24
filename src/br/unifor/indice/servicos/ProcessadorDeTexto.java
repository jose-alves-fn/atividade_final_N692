package br.unifor.indice.servicos;

import br.unifor.indice.modelo.Palavra;

public class ProcessadorDeTexto {
    public void processarTexto(String texto, TabelaHash tabelaHash) {
        String[] linhas = texto.split("\n");

        for (int i = 0; i < linhas.length; i++) {
            String[] palavras = linhas[i].split("\\s+");

            for (String palavraStr : palavras) {
                String palavraLimpa = palavraStr.replaceAll("[^a-zA-Z0-9À-ÿ]", "");

                if (!palavraLimpa.isEmpty()) {
                    Palavra palavra = new Palavra(palavraLimpa);
                    tabelaHash.inserirPalavra(palavra, i + 1);
                }
            }
        }
    }
}
