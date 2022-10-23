package ru.isador.ais.microservices.order;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType.HAL;

@Configuration
@EnableHypermediaSupport(type = HAL)
public class OrderApplicationConfig implements WebMvcConfigurer {

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
    }

    @Bean
    public Queue queue() {
        return new Queue("bonuses");
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange("bonuses-topic");
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange topicExchange) {
        return BindingBuilder.bind(queue).to(topicExchange).with("bonuses-key");
    }
}
