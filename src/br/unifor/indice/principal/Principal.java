package br.unifor.indice.principal;

//import br.unifor.indice.servicos.LeituraTexto;
//import br.unifor.indice.servicos.TabelaHash;
//import br.unifor.indice.modelo.Palavra;
//
//public class Principal {
//
//    public static void main(String[] args) {
//        LeituraTexto leitor = new LeituraTexto("src/br/unifor/indice/recursos/texto.txt");
//        String texto = leitor.lerTexto();
//
//        TabelaHash tabelaHash = new TabelaHash();
//        String[] linhas = texto.split("\n");
//
//        for (int i = 0; i < linhas.length; i++) {
//            String[] palavras = linhas[i].split("\\s+");
//
//            for (String palavraStr : palavras) {
//                // Remove caracteres especiais, mas mantém números e letras
////                String palavraLimpa = palavraStr.replaceAll("[^a-zA-Z0-9]", "");
//                String palavraLimpa = palavraStr.replaceAll("[^a-zA-Z0-9À-ÿ]", ""); // Preserva caracteres especiais
//
//
//                if (!palavraLimpa.isEmpty()) {
//                    Palavra palavra = new Palavra(palavraLimpa);
//                    tabelaHash.inserirPalavra(palavra, i + 1);
//                }
//            }
//        }
//
//        tabelaHash.imprimirTabela();
//    }
//}


import br.unifor.indice.servicos.GravarResultados;
import br.unifor.indice.servicos.LeituraPalavrasChave;
import br.unifor.indice.servicos.LeituraTexto;
import br.unifor.indice.servicos.PesquisaPalavras;
import br.unifor.indice.servicos.TabelaHash;
import br.unifor.indice.modelo.Palavra;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class Principal {

    public static void main(String[] args) {
        // Caminhos dos arquivos
        String caminhoTexto = "src/br/unifor/indice/recursos/texto.txt";
        String caminhoPalavrasChave = "src/br/unifor/indice/recursos/palavras-chave.txt";
        String caminhoResultados = "src/br/unifor/indice/recursos/resultados.txt";

        // Leitura do texto e criação da tabela hash
        LeituraTexto leitorTexto = new LeituraTexto(caminhoTexto);
        String texto = leitorTexto.lerTexto();

        TabelaHash tabelaHash = new TabelaHash();
        String[] linhas = texto.split("\n");

        for (int i = 0; i < linhas.length; i++) {
            String[] palavras = linhas[i].split("\\s+");

            for (String palavraStr : palavras) {
                // Remove caracteres especiais, mas mantém números e letras e caracteres acentuados
                String palavraLimpa = palavraStr.replaceAll("[^a-zA-Z0-9À-ÿ]", ""); // Preserva caracteres especiais

                if (!palavraLimpa.isEmpty()) {
                    Palavra palavra = new Palavra(palavraLimpa);
                    tabelaHash.inserirPalavra(palavra, i + 1);
                }
            }
        }

        // Leitura das palavras-chave.txt
        LeituraPalavrasChave leituraPalavrasChave = new LeituraPalavrasChave(caminhoPalavrasChave);
        List<String> palavrasChave = leituraPalavrasChave.lerPalavrasChave();

        // Pesquisa das palavras-chave.txt na tabela hash
        Map<String, List<Integer>> resultados = new HashMap<>();
        PesquisaPalavras pesquisaPalavras = new PesquisaPalavras(tabelaHash.getTabela());
        resultados = pesquisaPalavras.buscarPalavras(palavrasChave);

        // Gravar os resultados em um arquivo
        GravarResultados gravarResultados = new GravarResultados(caminhoResultados);
        gravarResultados.gravar(resultados);

        System.out.println("Pesquisa concluída. Resultados gravados em " + caminhoResultados);
    }
}





