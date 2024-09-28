package br.com.exemplo.validacao.util;

import java.util.Optional;

public abstract class DocumentoUtil {

  protected abstract void validarTamanhaPermitido() throws RuntimeException;

  protected abstract void validarMascara() throws RuntimeException;

  protected abstract void validarCalculoPrimeiraEtapa() throws RuntimeException;

  protected abstract void validarCalculoSegundaEtapa() throws RuntimeException;

  protected static int getNumeroRealVerificador(int totalCalculado) {
    int digitoVerificado = 11;
    int resultado = totalCalculado % digitoVerificado;
    return (resultado < 2) ? 0 : (digitoVerificado - resultado);
  }

  protected static String extrairCaracteresSemPontoDigitoBarra(String valor) {
    return Optional.of(valor)
        .map(v -> v.replaceAll("[.\\-/]", ""))
        .orElseThrow(() -> new RuntimeException("Erro ao extrair os caracteres sem .-/"));
  }

  protected static String repetir(String valor, int total) {
    String novoValor = valor.repeat(total);
    return novoValor;
  }
}
