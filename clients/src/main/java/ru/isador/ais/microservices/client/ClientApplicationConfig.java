package ru.isador.ais.microservices.client;

import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType.HAL;

@Configuration
@EnableHypermediaSupport(type = HAL)
public class ClientApplicationConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowedHeaders("Content-Type");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/swagger-ui/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/swagger-ui/4.14.3/");
        registry.addResourceHandler("/openapi.yml")
                .addResourceLocations("classpath:META-INF/openapi.yml");
        registry.addResourceHandler("/css/**")
                .addResourceLocations("classpath:static/css/");
        registry.addResourceHandler("/img/**")
                .addResourceLocations("classpath:static/img/");
        registry.addResourceHandler("/js/**")
                .addResourceLocations("classpath:static/js/");
    }

}
