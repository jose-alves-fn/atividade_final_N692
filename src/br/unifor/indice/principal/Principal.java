package br.unifor.indice.principal;

// Para ver tudo no terminal
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

import br.unifor.indice.servicos.*;
import java.util.List;
import java.util.Map;

public class Principal {
    public static void main(String[] args) {
        // Caminhos dos arquivos
        GerenciadorDeArquivo gerenciadorDeArquivo = new GerenciadorDeArquivo();
        String caminhoTexto = gerenciadorDeArquivo.getCaminhoTexto();
        String caminhoPalavrasChave = gerenciadorDeArquivo.getCaminhoPalavrasChave();
        String caminhoResultados = gerenciadorDeArquivo.getCaminhoResultados();

        // Leitura do texto e criação da tabela hash
        LeituraTexto leitorTexto = new LeituraTexto(caminhoTexto);
        String texto = leitorTexto.lerTexto();

        TabelaHash tabelaHash = new TabelaHash();
        ProcessadorDeTexto processadorDeTexto = new ProcessadorDeTexto();
        processadorDeTexto.processarTexto(texto, tabelaHash);

        // Leitura das palavras-chave
        LeituraPalavrasChave leituraPalavrasChave = new LeituraPalavrasChave(caminhoPalavrasChave);
        List<String> palavrasChave = leituraPalavrasChave.lerPalavrasChave();

        // Pesquisa das palavras-chave na tabela hash
        PesquisaPalavras pesquisaPalavras = new PesquisaPalavras(tabelaHash.getTabela());
        Map<String, List<Integer>> resultados = pesquisaPalavras.buscarPalavras(palavrasChave);

        // Gravar os resultados em um arquivo
        GravarResultados gravarResultados = new GravarResultados(caminhoResultados);
        gravarResultados.gravar(resultados);

        System.out.println("Pesquisa concluída. Resultados gravados em " + caminhoResultados);
    }
}






