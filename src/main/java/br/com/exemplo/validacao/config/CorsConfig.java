package br.com.exemplo.validacao.config;

import java.util.Collections;
import java.util.List;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

  @Bean
  public FilterRegistrationBean<CorsFilter> corsFilterFilterRegistrationBean() {
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowCredentials(false);// Só deve ser (true) caso especifique o caminho do setAllowedOrigins
    config.setAllowedOrigins(Collections.singletonList("*"));
    config.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS","HEAD","PATCH"));
    config.setAllowedHeaders(Collections.singletonList("*"));
    config.setMaxAge(3600L);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", config);

    FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>();
    bean.setFilter(new CorsFilter(source));
    bean.setOrder(Ordered.HIGHEST_PRECEDENCE);

    return bean;
  }

}
