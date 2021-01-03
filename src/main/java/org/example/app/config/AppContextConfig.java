package org.example.app.config;

import org.example.app.services.IdProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@ComponentScan(basePackages = "org.example.app")
public class AppContextConfig {
  @Bean
  @Scope
  public IdProvider idProvider() {
    return new IdProvider();
  }
}
