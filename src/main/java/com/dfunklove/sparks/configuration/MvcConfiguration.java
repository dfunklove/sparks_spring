package com.dfunklove.sparks.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

/* This is needed to pass the exception object to the error page. */
@Configuration
class MvcConfiguration {
  @Bean(name="simpleMappingExceptionResolver")
  public SimpleMappingExceptionResolver
                  createSimpleMappingExceptionResolver() {
    SimpleMappingExceptionResolver r =
                new SimpleMappingExceptionResolver();

    r.setDefaultErrorView("error");    // No default
    //r.setWarnLogCategory("org.dfunklove.sparks");     // No default
    return r;
  }
}