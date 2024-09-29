package br.com.exemplo.validacao.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

  @Value("${aplicacao.nome}")
  private String aplicacaoNome;
  @Value("${aplicacao.descricao}")
  private String aplicacaoDescricao;
  @Value("${aplicacao.versao}")
  private String aplicacaoVersao;

  @Bean
  public GroupedOpenApi publicApi() {
    return GroupedOpenApi.builder()
        .group("public")
        .pathsToMatch("/**")
        .build();
  }

  @Bean
  public OpenAPI apiDefinition() {

    OpenAPI openAPI = new OpenAPI();
    openAPI.info(new Info().title(aplicacaoNome).description(aplicacaoDescricao)
        .version(aplicacaoVersao))
        .servers(List.of(
            new Server().url("https://validacao.fly.dev"),
            new Server().url("http://localhost:8080")
        ));

    return openAPI;
  }

}
