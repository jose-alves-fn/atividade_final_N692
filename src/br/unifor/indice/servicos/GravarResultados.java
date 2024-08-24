package br.unifor.indice.servicos;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class GravarResultados {

    private String caminhoArquivo;

    public GravarResultados(String caminhoArquivo) {
        this.caminhoArquivo = caminhoArquivo;
    }

    public void gravar(Map<String, List<Integer>> resultados) {
        // Usar TreeMap para garantir que as chaves sejam ordenadas
        Map<String, List<Integer>> resultadosOrdenados = new TreeMap<>(resultados);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivo))) {
            for (Map.Entry<String, List<Integer>> entry : resultadosOrdenados.entrySet()) {
                String palavra = entry.getKey();
                List<Integer> ocorrencias = entry.getValue();
                writer.write(palavra + ": " + ocorrencias);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao gravar os resultados: " + e.getMessage());
        }
    }
}



