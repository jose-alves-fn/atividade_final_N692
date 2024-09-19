package br.unifor.indice.principal;

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

        // Acionando a tabela hash
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






