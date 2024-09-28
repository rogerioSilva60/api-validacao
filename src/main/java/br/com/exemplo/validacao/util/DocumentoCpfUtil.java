package br.com.exemplo.validacao.util;

import java.util.Comparator;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;

public class DocumentoCpfUtil extends DocumentoUtil{

  private static final Logger LOGGER = LoggerFactory.getLogger(DocumentoCpfUtil.class);
  private final static int TAMANHO_TOTAL_CPF = 14;
  private final String cpf;

  public DocumentoCpfUtil(String cpf) {
    this.cpf = cpf;
  }

  @Override
  protected void validarTamanhaPermitido() throws RuntimeException {
    Optional.of(cpf)
        .filter(c -> TAMANHO_TOTAL_CPF == c.length())
        .orElseThrow(() -> new RuntimeException(
            String.format("O tamanho de caracteres permitido é %d e o enviado foi %d",
                TAMANHO_TOTAL_CPF, cpf.length())));
  }

  @Override
  protected void validarMascara() throws RuntimeException {
    Optional.of(cpf)
        .filter(c -> c.matches("\\d{3}.\\d{3}.\\d{3}-\\d{2}"))
        .orElseThrow(() -> new RuntimeException("Cpf deve estar no formato 000.000.000-00."));
  }

  @Override
  protected void validarCalculoPrimeiraEtapa() throws RuntimeException {
    var novoCpf = extrairCaracteresSemPontoDigitoBarra(cpf);
    checarCpfInvalidos(novoCpf);
    var primeirosNoveNumerosDoCpf = novoCpf.substring(0, 9);
    var penultimoDigito = Integer.parseInt(novoCpf.substring(9, 10));
    int totalCalculado = calcularNumeroBaseDoCpf(8, primeirosNoveNumerosDoCpf);
    int valorVerificado = getNumeroRealVerificador(totalCalculado);

    if(valorVerificado != penultimoDigito) {
      LOGGER.error("Cpf Inválido na verificação da primeira etapa, Corrigir o número informado e tente novamente!");
      throw new RuntimeException("Cpf Inválido, Corrigir o número informado e tente novamente!");
    }
  }

  @Override
  protected void validarCalculoSegundaEtapa() throws RuntimeException {
    var novoCpf = extrairCaracteresSemPontoDigitoBarra(cpf);
    String primeirosDezNumerosDoCpf = novoCpf.substring(0, 10);
    var ultimoDigito = Integer.parseInt(novoCpf.substring(10, 11));
    int totalCalculado = calcularNumeroBaseDoCpf(9, primeirosDezNumerosDoCpf);
    int valorVerificado = getNumeroRealVerificador(totalCalculado);

    if(valorVerificado != ultimoDigito) {
      LOGGER.error("Cpf Inválido na verificação da segunda etapa, Corrigir o número informado e tente novamente!");
      throw new RuntimeException("Cpf Inválido, Corrigir o número informado e tente novamente!");
    }
  }

  private void checarCpfInvalidos(@NonNull String cpf) throws RuntimeException {
    Optional<String> cpfExistenteOptional = Stream
        .of(repetir("0", 11), repetir("1", 11), repetir("2", 11),
            repetir("3", 11), repetir("4", 11), repetir("5", 11),
            repetir("6", 11), repetir("7", 11), repetir("8", 11),
            repetir("9", 11))
        .filter(c -> c.equals(cpf))
        .findFirst();

    if(cpfExistenteOptional.isPresent()) {
      throw new RuntimeException(
        "Cpf informado não é permitido, Corrigir o número informado e tente novamente!");
    }
  }
  private int calcularNumeroBaseDoCpf(Integer intervaloFinal, String numeros) throws RuntimeException {
    return IntStream.rangeClosed(0, intervaloFinal)
        .boxed()
        .sorted(Comparator.reverseOrder())
        .map(position -> {
          var multiplicador = (intervaloFinal - position) + 2;
          int numeroExtraidoDoCpf = Integer.parseInt(numeros.substring(position, (position + 1)));
          return multiplicador * numeroExtraidoDoCpf;
        })
        .reduce(Integer::sum)
        .orElseThrow(() -> new RuntimeException(
            "Falhar ao gerar calculo base do CPF, Solicitar ao suporte a verificação da inconsistência!"));
  }
}
