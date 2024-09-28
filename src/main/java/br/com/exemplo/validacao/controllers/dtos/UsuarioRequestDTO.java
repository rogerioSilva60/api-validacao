package br.com.exemplo.validacao.controllers.dtos;

import br.com.exemplo.validacao.core.validation.CpfCnpj;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "Usuário entrada")
public record UsuarioRequestDTO (
    @Schema(description = "Nome do usuário", example = "Mário Quintana")
    @NotBlank
    String nome,
    @Schema(description = "CpfCnpj do usuário", example = "000.000.000-00")
    @NotBlank
    @CpfCnpj
    String cpfCnpj
) { }
