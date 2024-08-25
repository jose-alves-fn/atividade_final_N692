# ☕ Geração de Índice Remissivo (Java) 

**Geração de Índice Remissivo** é um projeto escrito na linguaguem Java para a compontente de Estrutura de dados (N692) do curso de Análise e Desenvolvimento de Sistemas da Universidade de Fortaleza (Unifor).

---

## 🔭 Características do Projeto

Esta aplicação Java é um sistema de indexação e busca de palavras-chave em um texto. Suas principais funcionalidades são:

- Leitura de um arquivo de texto. 
- Processamento do texto, criando um índice das palavras e suas ocorrências (linhas onde aparecem).
- Leitura de um arquivo contendo palavras-chave a serem buscadas.
- Busca das palavras-chave no índice criado.
- Gravação dos resultados da busca em um arquivo de saída.


O sistema utiliza uma estrutura de dados de tabela hash para armazenar e buscar palavras de forma eficiente. Cada palavra é associada à lista de números das linhas onde ela aparece no texto. O fluxo básico da aplicação é:

1. Ler o texto de entrada de um arquivo.
2. Processar o texto, criando um índice na forma de uma tabela hash.
3. Ler as palavras-chave de um arquivo separado.
4. Buscar cada palavra-chave no índice.
5. Gravar os resultados (palavras encontradas e suas ocorrências) em um arquivo de saída.

A aplicação é estruturada em várias classes, cada uma com responsabilidades específicas, como leitura de arquivos, processamento de texto, gerenciamento da tabela hash, pesquisa de palavras e gravação de resultados.
Este sistema pode ser útil para criar índices remissivos de livros, analisar a frequência de palavras em textos ou realizar buscas rápidas de termos específicos em documentos grandes.

---

## 📦 Classes

### `Principal`

A classe `Principal` é o ponto de entrada principal do programa, responsável por orquestrar o fluxo geral de processamento de texto e busca de palavras-chave. Essa estrutura permite que o programa processe o texto, busque palavras-chave e registre os resultados de maneira organizada e modular, com a responsabilidade distribuída entre diferentes classes e métodos.

```java
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
```
---
### `Palavra`

A classe `Palavra` encapsula uma palavra e suas ocorrências em um texto, permitindo armazenar e manipular as posições onde a palavra aparece. Ela oferece métodos para obter e definir a palavra, adicionar novas ocorrências e gerar uma representação em string do objeto. É uma classe "core" do projeto, sugerida pelo professor, visto que cada palavra lida será instanciada por meio dessa classe. Os atributos serão populados e será a partir desses valores que o índice remissivo poderá ser gerado.

```java
package br.unifor.indice.modelo;

import java.util.ArrayList;
import java.util.List;

public class Palavra {

    private String palavra;
    private List<Integer> ocorrencias;

    public Palavra(String palavra) {
        this.palavra = palavra;
        this.ocorrencias = new ArrayList<>();
    }

    public String getPalavra() {
        return palavra;
    }

    public void setPalavra(String palavra) {
        this.palavra = palavra;
    }

    public List<Integer> getOcorrencias() {
        return ocorrencias;
    }

    public void adicionarOcorrencia(int ocorrencia) {
        this.ocorrencias.add(ocorrencia);
    }

    @Override
    public String toString() {
        return palavra + ocorrencias;
    }
}

```
---
### `GerenciadorDeArquivo`

A classe `GerenciadorDeArquivo` centraliza os caminhos dos arquivos necessários para o processamento do índice remissivo. Na classe foram inseridos métodos para recuperar esses caminhos de maneira simples. Ela facilita a manutenção e a modificação dos caminhos dos arquivos em um único lugar no código.

```Java
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
```
---
### `LeituraTexto`

A classe `LeituraTexto` lê o conteúdo de um arquivo de texto e retorna esse conteúdo como uma única string, preservando a formatação das quebras de linha. Se houver algum problema na leitura do arquivo, uma mensagem de erro é exibida no console.

```Java
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
```
---
### `ProcessadorDeTexto`

A classe `ProcessadorDeTexto` se concentra na lógica de processamento do texto. A responsabilidade dessa classe é a de garantir que as palavras presentes no texto poderão ser processadas de forma adequada para que a aplicação alcance seu objetivo. 

```Java
package br.unifor.indice.servicos;

import br.unifor.indice.modelo.Palavra;

public class ProcessadorDeTexto {
    public void processarTexto(String texto, TabelaHash tabelaHash) {
        String[] linhas = texto.split("\n");

        for (int i = 0; i < linhas.length; i++) {
            String[] palavras = linhas[i].split("\\s+");

            for (String palavraStr : palavras) {

                String palavraLimpa = palavraStr.replaceAll("[^a-zA-Z0-9À-ÿ-]", "").toLowerCase();;

                if (!palavraLimpa.isEmpty()) {
                    Palavra palavra = new Palavra(palavraLimpa);
                    tabelaHash.inserirPalavra(palavra, i + 1);
                }
            }
        }
    }
}
```
---
### `TabelaHash`

A classe `TabelaHash` organiza palavras em uma estrutura de tabela hash, onde as palavras são mapeadas pela sua letra inicial. Ela permite inserir novas palavras e registrar as linhas em que essas palavras aparecem no texto. Se uma palavra já estiver na tabela, a ocorrência adicional é registrada; se não, a palavra é inserida com sua primeira ocorrência. A tabela resultante pode ser obtida através do método `getTabela()`. 

```Java
package br.unifor.indice.servicos;

import br.unifor.indice.modelo.Palavra;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TabelaHash {
    private Map<Character, List<Palavra>> tabela;

    public TabelaHash() {
        tabela = new HashMap<>();
    }

    public void inserirPalavra(Palavra palavra, int linha) {
        char letraInicial = palavra.getPalavra().charAt(0);
        tabela.putIfAbsent(letraInicial, new ArrayList<>());

        List<Palavra> listaPalavras = tabela.get(letraInicial);
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
}
```

---
### `LeituraPalavrasChave`

A classe `LeituraPalavrasChave` realiza a leitura de palavras-chave a partir de um arquivo .txt presente no projeto, pesquisa essas palavras em uma tabela hash e grava os resultados da pesquisa em outro arquivo. As classes principais envolvidas são `LeituraPalavrasChave` (para ler as palavras-chave), `PesquisaPalavras` (para buscar as palavras na tabela hash), e `GravarResultados` (para salvar os resultados da busca em um arquivo).

```Java
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
            String linha = leitor.readLine(); // Lê toda a linha
            if (linha != null) {
                String[] palavras = linha.split(",");
                for (String palavra : palavras) {
                    palavrasChave.add(palavra.trim());
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo de palavras-chave.txt: " + e.getMessage());
        }

        System.out.println("Palavras-chave lidas: " + palavrasChave);

        return palavrasChave;
    }
}
```

---
### `PesquisaPalavras`

A classe `PesquisaPalavras` executa a busca de palavras-chave em uma tabela hash mapeada por letras iniciais, retornando as ocorrências das palavras encontradas no texto. Ela percorre a lista de palavras associadas a cada letra inicial na tabela hash, compara com as palavras-chave fornecidas e armazena os resultados (as linhas onde cada palavra aparece) em um mapa. É uma classe que define bastante o projeto, visto que se objetiva criar um índice por meio da busca de palavras que queiramos pesquisar.

```Java
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
```

---
### `GravarResultados`

A classe `GravarResultados`, sendo o ponto final do processamento, é responsável por gravar os resultados da pesquisa em um arquivo de texto. Ela ordena alfabeticamente as palavras encontradas e salva cada palavra junto com suas respectivas ocorrências em um formato legível. Se ocorrer algum erro durante a gravação, a classe captura a exceção e exibe uma mensagem de erro.

```Java
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
```
---

## 💻 Requerimentos

- Java Development Kit (JDK) instalado, versão 8 ou superior.
- Qualquer ambiente de desenvolvimento integrado (IDE) como Eclipse, IntelliJ IDEA, ou NetBeans, ou um editor de texto e conhecimento para compilar e executar Java via linha de comando.
- Não há necessidade de importação de módulos externos. Nenhuma biblioteca é necessária além das bibliotecas padrão do Java.
- Os arquivos de código-fonte (.java) precisam estar organizados na estrutura de pacotes correta (br.unifor.indice.principal, br.unifor.indice.servicos, br.unifor.indice.modelo).
- Um diretório de recursos (src/br/unifor/indice/recursos/) contendo:
> 1. Um arquivo texto.txt que vai receber um texto arbitrário a ser processado;
> 2. Um arquivo palavras-chave.txt: O arquivo com as palavras-chave a serem buscadas;
> 3. Um local para o arquivo de saída indice-remissivo.txt ser gerado.

## 👯 Como acessar este projeto

---
### Basta clonar este repositório por meio do git clone:

```sh 
https://github.com/jose-alves-fn/atividade_final_N692
```
---

## 👋 Obrigado!☕







