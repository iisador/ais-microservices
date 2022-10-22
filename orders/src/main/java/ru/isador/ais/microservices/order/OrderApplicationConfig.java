package ru.isador.ais.microservices.order;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType.HAL;

@Configuration
@EnableJpaRepositories("ru.isador.ais.microservices.order.data")
@EnableWebMvc
@EnableHypermediaSupport(type = HAL)
@ComponentScan("ru.isador.ais.microservices.order.web")
public class OrderApplicationConfig {
}
