package br.com.exemplo.validacao.util;

public abstract class ValidacaoDocumentoUtil {

  public static void validar(DocumentoUtil documentoUtil) {
    documentoUtil.validarTamanhaPermitido();
    documentoUtil.validarMascara();
    documentoUtil.validarCalculoPrimeiraEtapa();
    documentoUtil.validarCalculoSegundaEtapa();
  }

}
