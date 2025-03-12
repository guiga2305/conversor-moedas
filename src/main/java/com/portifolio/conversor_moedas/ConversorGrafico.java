package com.portifolio.conversor_moedas;

import javax.swing.*;

public class ConversorGrafico {
    public static void main(String[] args) {
        String moedaOrigem = JOptionPane.showInputDialog("Digite a moeda de origem (ex: BRL):").toUpperCase();
        String moedaDestino = JOptionPane.showInputDialog("Digite a moeda de destino (ex: USD):").toUpperCase();
        double valor = Double.parseDouble(JOptionPane.showInputDialog("Digite o valor a ser convertido:"));

        ConsumoApi consumoApi = new ConsumoApi();
        double taxaDeCambio = consumoApi.buscarTaxaDeCambio(moedaOrigem, moedaDestino);

        if (taxaDeCambio == -1) {
            JOptionPane.showMessageDialog(null, "Erro ao obter a taxa de câmbio.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double valorConvertido = valor * taxaDeCambio;
        JOptionPane.showMessageDialog(null, String.format("Valor convertido: %.2f %s = %.2f %s", valor, moedaOrigem, valorConvertido, moedaDestino));
    }
}
