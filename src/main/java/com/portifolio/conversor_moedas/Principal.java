package com.portifolio.conversor_moedas;

import java.util.Scanner;

public class Principal {
    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumoApi = new ConsumoApi();

    public void iniciarConversor() {
        try {
            System.out.println("=== Conversor de Moedas ===");

            // Solicitar moeda de origem
            System.out.print("Digite a moeda de origem (ex: BRL): ");
            String moedaOrigem = leitura.nextLine().toUpperCase();

            // Solicitar moeda de destino
            System.out.print("Digite a moeda de destino (ex: USD): ");
            String moedaDestino = leitura.nextLine().toUpperCase();

            // Pedir valor a ser convertido
            System.out.print("Digite o valor a ser convertido: ");
            double valor = leitura.nextDouble();

            // Consultar a API
            double taxaDeCambio = consumoApi.buscarTaxaDeCambio(moedaOrigem, moedaDestino);

            // Verificar se houve erro
            if (taxaDeCambio == -1) {
                System.out.println("Erro: Não foi possível obter a cotação. Verifique as moedas inseridas.");
                return;
            }

            // Calcular e exibir o resultado
            double valorConvertido = valor * taxaDeCambio;
            System.out.println("Taxa de câmbio: 1 " + moedaOrigem + " = " + taxaDeCambio + " " + moedaDestino);
            System.out.println("Valor convertido: " + valor + " " + moedaOrigem + " = " + valorConvertido + " " + moedaDestino);

        } catch (Exception e) {
            System.out.println("Erro: Entrada inválida. Tente novamente.");
        } finally {
            leitura.close();
        }
    }

    public static void main(String[] args) {
        Principal programa = new Principal();
        programa.iniciarConversor();
    }
}
