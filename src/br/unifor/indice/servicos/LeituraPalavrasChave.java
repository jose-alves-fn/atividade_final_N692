package br.unifor.indice.servicos;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LeituraPalavrasChave {

    private String caminhoArquivo;

    public LeituraPalavrasChave(String caminhoArquivo) {
        this.caminhoArquivo = caminhoArquivo;
    }

    public List<String> lerPalavrasChave() {
        List<String> palavrasChave = new ArrayList<>();

        try (BufferedReader leitor = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = leitor.readLine()) != null) {
                palavrasChave.add(linha.trim());
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo de palavras-chave.txt: " + e.getMessage());
        }

        return palavrasChave;
    }
}

