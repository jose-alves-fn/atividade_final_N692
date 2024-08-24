package br.unifor.indice.servicos;

public class GerenciadorDeArquivo {

    private final String caminhoTexto = "src/br/unifor/indice/recursos/texto.txt";
    private final String caminhoPalavrasChave = "src/br/unifor/indice/recursos/palavras-chave.txt";
    private final String caminhoResultados = "src/br/unifor/indice/recursos/indice-remissivo.txt";

    public GerenciadorDeArquivo() {
    }

    public String getCaminhoTexto() {
        return caminhoTexto;
    }

    public String getCaminhoPalavrasChave() {
        return caminhoPalavrasChave;
    }

    public String getCaminhoResultados() {
        return caminhoResultados;
    }
}
