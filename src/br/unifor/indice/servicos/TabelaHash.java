package br.unifor.indice.servicos;

import br.unifor.indice.modelo.Palavra;

import java.util.*;

public class TabelaHash {
    private Map<Character, List<Palavra>> tabela;

    public TabelaHash() {
        tabela = new HashMap<>();
    }

    public void inserirPalavra(Palavra palavra, int linha) {
        char letraInicial = palavra.getPalavra().charAt(0);
        tabela.putIfAbsent(letraInicial, new LinkedList<>());

        List<Palavra> listaPalavras = tabela.get(letraInicial); // Lista auxiliar
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
    public Map<Character, List<Palavra>> getTabela() {
        return tabela;
    }

//    public void imprimirTabela() {
//        for (Map.Entry<Character, List<Palavra>> entry : tabela.entrySet()) {
//            char chave = entry.getKey();
//            System.out.println("Chave: " + chave);
//            for (Palavra palavra : entry.getValue()) {
//                System.out.println(palavra);
//            }
//        }

//    public void imprimirTabela() {
//        for (Map.Entry<Character, List<Palavra>> entry : tabela.entrySet()) {
//            char chave = entry.getKey();
//            int hashCode = chave; // Obtém o hash code da chave (caractere)
//
//            System.out.println("Chave: " + chave + " --- HashCode: " + hashCode);
//
//            for (Palavra palavra : entry.getValue()) {
//                System.out.println("   Palavra: " + palavra.getPalavra() + " --- Ocorrências: " + palavra.getOcorrencias());
//            }
//        }
//    }
}







