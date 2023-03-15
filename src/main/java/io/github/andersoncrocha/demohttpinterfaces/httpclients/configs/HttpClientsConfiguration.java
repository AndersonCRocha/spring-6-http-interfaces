package io.github.andersoncrocha.demohttpinterfaces.httpclients.configs;

import io.github.andersoncrocha.demohttpinterfaces.httpclients.interceptors.BearerAuthMyClientInterceptor;
import io.github.andersoncrocha.demohttpinterfaces.httpclients.PersonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.time.Duration;

@Configuration
public class HttpClientsConfiguration {

    @Bean
    public PersonClient personClient(BearerAuthMyClientInterceptor requestInterceptor) {
        final var webClient = WebClient.builder()
                .baseUrl("http://localhost:3000")
                .filter(requestInterceptor)
                .build();

        final var factory = HttpServiceProxyFactory.builder()
                .clientAdapter(WebClientAdapter.forClient(webClient))
                .blockTimeout(Duration.ofSeconds(30))
                .build();

        return factory.createClient(PersonClient.class);
    }

}
