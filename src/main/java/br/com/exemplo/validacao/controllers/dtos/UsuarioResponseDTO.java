package br.com.exemplo.validacao.controllers.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;

@Schema(name = "Usuario saída")
public record UsuarioResponseDTO(
    UUID id,
    @Schema(example = "Mário Quintana")
    String nome,
    @Schema(example = "000.000.000-00")
    String cpfCnpj
) {

}
