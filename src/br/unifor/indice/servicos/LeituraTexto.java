package br.unifor.indice.servicos;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LeituraTexto {

    private String caminhoArquivo;

    public LeituraTexto(String caminhoArquivo) {
        this.caminhoArquivo = caminhoArquivo;
    }

    public String lerTexto() {
        StringBuilder conteudo = new StringBuilder();

        try (BufferedReader leitor = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = leitor.readLine()) != null) {
                conteudo.append(linha).append(System.lineSeparator());
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }

        return conteudo.toString();
    }
}

//    public static void main(String[] args) {
//        LeituraTexto leitor = new LeituraTexto("src/br/unifor/indice/recursos/texto.txt");
//        String texto = leitor.lerTexto();
//        System.out.println(texto);






