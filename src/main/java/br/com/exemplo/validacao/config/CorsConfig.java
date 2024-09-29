package br.com.exemplo.validacao.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/v3/api-docs/**").allowedOrigins("*");
    registry.addMapping("/swagger-ui/**").allowedOrigins("*");
    registry.addMapping("/**")
        .allowedOrigins("*") // Permite todas as origens
        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
        .allowedHeaders("*")
        .allowCredentials(false)// Só deve ser (true) caso especifique o caminho do setAllowedOrigins
        .maxAge(3600L);
  }

}
