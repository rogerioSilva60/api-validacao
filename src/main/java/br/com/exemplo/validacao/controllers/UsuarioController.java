package br.com.exemplo.validacao.controllers;

import br.com.exemplo.validacao.controllers.dtos.UsuarioRequestDTO;
import br.com.exemplo.validacao.controllers.dtos.UsuarioResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Tag(name = "Usuario", description = "Serviços relacionados ao usuario")
@RestController
@RequestMapping(value = "/v1/usuarios")
public class UsuarioController {

  @Operation(summary = "Salva usuário", description = "Deve salvar usuário")
  @ApiResponses(
      value = {
          @ApiResponse(
              responseCode = "201", description = "Usuário cadastrado",
              content = {
                  @Content(
                      mediaType = "application/json",
                      schema = @Schema(
                          implementation = UsuarioResponseDTO.class
                      )
                  )
              }
          ),
          @ApiResponse (
              responseCode = "400", description = "Requisição inválida", content = @Content
          ),
          @ApiResponse (
              responseCode = "500", description = "Ocorreu erro interno", content = @Content
          )
      }
  )
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping
  public ResponseEntity<UsuarioResponseDTO> salvar(
      @Valid @RequestBody UsuarioRequestDTO usuarioRequestDTO) {
    UsuarioResponseDTO usuarioResponseDTO = new UsuarioResponseDTO(UUID.randomUUID(),
        usuarioRequestDTO.nome(), usuarioRequestDTO.cpfCnpj());

    URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
        .path("/{id}")
        .buildAndExpand(usuarioResponseDTO.id()).toUri();

    return ResponseEntity.created(uri).body(usuarioResponseDTO);
  }

}
