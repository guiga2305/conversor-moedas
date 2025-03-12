package com.portifolio.conversor_moedas;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class ConsumoApi {
    private static final String BASE_URL = "https://economia.awesomeapi.com.br/json/last/";

    public double buscarTaxaDeCambio(String moedaOrigem, String moedaDestino) {
        try {
            // Validar entradas
            if (moedaOrigem == null || moedaOrigem.isEmpty() || moedaDestino == null || moedaDestino.isEmpty()) {
                System.out.println("Erro: As moedas de origem e destino não podem estar vazias.");
                return -1;
            }

            // Construa o endpoint com as moedas
            String urlStr = BASE_URL + moedaOrigem.toUpperCase() + "-" + moedaDestino.toUpperCase();
            System.out.println("URL da API: " + urlStr); // Imprimir para depuração

            // Configurar a conexão
            URL url = new URL(urlStr);
            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
            conexao.setRequestMethod("GET");
            conexao.setRequestProperty("Accept", "application/json");

            // Verificar o código da resposta
            int codigoResposta = conexao.getResponseCode();
            if (codigoResposta != 200) {
                System.out.println("Erro ao acessar a API. Código de resposta: " + codigoResposta);
                return -1; // Indica falha
            }

            // Ler a resposta da API
            BufferedReader leitor = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
            StringBuilder resposta = new StringBuilder();
            String linha;
            while ((linha = leitor.readLine()) != null) {
                resposta.append(linha);
            }
            leitor.close();

            // Processar e interpretar os dados JSON
            JSONObject json = new JSONObject(resposta.toString());

            // O nome do campo será no formato MOEDA_ORIGEMMOEDA_DESTINO
            String campo = moedaOrigem.toUpperCase() + moedaDestino.toUpperCase();
            if (!json.has(campo)) {
                System.out.println("Erro: A resposta não contém o campo esperado: " + campo);
                return -1;
            }

            // Retornar o valor atual da taxa de conversão (campo "bid")
            return json.getJSONObject(campo).getDouble("bid");

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
            e.printStackTrace();
            return -1;
        }
    }
}