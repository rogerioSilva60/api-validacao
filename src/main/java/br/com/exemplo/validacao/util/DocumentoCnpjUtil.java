package br.com.exemplo.validacao.util;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DocumentoCnpjUtil extends DocumentoUtil {

  private static final Logger LOGGER = LoggerFactory.getLogger(DocumentoCnpjUtil.class);
  private final static int TAMANHO_TOTAL_COM_MASCARA_CNPJ = 18;
  private final String cnpj;

  public DocumentoCnpjUtil(String cnpj) {
    this.cnpj = cnpj;
  }

  @Override
  protected void validarTamanhaPermitido() throws RuntimeException {
    Optional.of(cnpj)
        .filter(c -> TAMANHO_TOTAL_COM_MASCARA_CNPJ == c.length())
        .orElseThrow(() -> new RuntimeException(
            String.format("O tamanho de caracteres permitido é %d e o enviado foi %d",
                TAMANHO_TOTAL_COM_MASCARA_CNPJ, cnpj.length())));
  }

  @Override
  protected void validarMascara() throws RuntimeException {
    Optional.of(cnpj)
        .filter(c -> c.matches("\\d{2}.\\d{3}.\\d{3}/\\d{4}-\\d{2}"))
        .orElseThrow(() -> new RuntimeException("Cnpj deve está no formato 00.000.000/0000-00."));
  }

  @Override
  protected void validarCalculoPrimeiraEtapa() throws RuntimeException {
    var novoCnpj = extrairCaracteresSemPontoDigitoBarra(cnpj);
    checarCnpjInvalidos(novoCnpj);
    var primeiroMutiplicadorBase = Arrays.asList(5,4,3,2,9,8,7,6,5,4,3,2);
    var dozePrimeirosNumerosDoCnpj = novoCnpj.substring(0, 12);
    var penultimoDigito = Integer.parseInt(novoCnpj.substring(12, 13));
    int totalCalculado = calcularNumeroBaseDoCnpj(primeiroMutiplicadorBase, dozePrimeirosNumerosDoCnpj);
    int valorVerificado = getNumeroRealVerificador(totalCalculado);

    if(valorVerificado != penultimoDigito) {
      LOGGER.error("Cnpj Inválido na verificação da primeira etapa, Corrigir o número informado e tente novamente!");
      throw new RuntimeException("Cnpj Inválido, Corrigir o número informado e tente novamente!");
    }
  }

  @Override
  protected void validarCalculoSegundaEtapa() throws RuntimeException {
    var novoCnpj = extrairCaracteresSemPontoDigitoBarra(cnpj);
    var segundoMutiplicadorBase = Arrays.asList(6,5,4,3,2,9,8,7,6,5,4,3,2);
    var trezePrimeirosNumerosDoCnpj = novoCnpj.substring(0, 13);
    var ultimoDigito = Integer.parseInt(novoCnpj.substring(13, 14));
    int totalCalculado = calcularNumeroBaseDoCnpj(segundoMutiplicadorBase, trezePrimeirosNumerosDoCnpj);
    int valorVericado = getNumeroRealVerificador(totalCalculado);

    if(valorVericado != ultimoDigito) {
      LOGGER.error("Cnpj Inválido na verificação da segunda etapa, Corrigir o número informado e tente novamente!");
      throw new RuntimeException("Cnpj Inválido, Corrigir o número informado e tente novamente!");
    }
  }

  private void checarCnpjInvalidos(String cnpj) throws RuntimeException {
    Optional<String> cnpjExistenteOptional = Stream
        .of(repetir("0", 14), repetir("1", 14), repetir("2", 14),
            repetir("3", 14), repetir("4", 14), repetir("5", 14), repetir("6", 14),
            repetir("7", 14), repetir("8", 14), repetir("9", 14))
        .filter(c -> c.equals(cnpj))
        .findFirst();

    if(cnpjExistenteOptional.isPresent()) {
      throw new RuntimeException(
        "Cnpj informado não é permitido, Corrigir o número informado e tente novamente!");
    }
  }
  private int calcularNumeroBaseDoCnpj(List<Integer> multiplicadores, String numeros) throws RuntimeException {
    return IntStream.range(0, multiplicadores.size())
        .mapToObj(i -> Integer.parseInt(numeros.substring(i, (i + 1))) * multiplicadores.get(i))
        .reduce(Integer::sum)
        .orElseThrow(() -> new RuntimeException(
            "Falhar ao gerar calculo base do CNPJ, Solicitar ao suporte a verificação da inconsistência!"));
  }
}
