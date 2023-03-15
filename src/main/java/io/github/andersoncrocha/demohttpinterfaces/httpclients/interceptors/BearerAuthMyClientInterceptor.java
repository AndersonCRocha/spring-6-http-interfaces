package io.github.andersoncrocha.demohttpinterfaces.httpclients.interceptors;

import io.github.andersoncrocha.demohttpinterfaces.httpclients.PersonClient;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import reactor.core.publisher.Mono;

@Component
public class BearerAuthMyClientInterceptor implements ExchangeFilterFunction {

    private final PersonClient personClient;

    /**
     * Anotado com Lazy pois está utilizando o mesmo personClient para geração do token e demais requisições, e na criação
     * do Bean para o personClient é necessário a instância do interceptor {@link io.github.andersoncrocha.demohttpinterfaces.httpclients.configs.HttpClientsConfiguration},
     * causando assim uma referência circular, Uma outra forma de resolver a referência circular é criar um outro
     * Bean apenas para busca do token e injetá-lo aqui, já que este não necessita do interceptor para adição do token
     *
     * @param personClient personClient http para a geração do token
     */
    public BearerAuthMyClientInterceptor(@Lazy PersonClient personClient) {
        this.personClient = personClient;
    }

    @Override
    public Mono<ClientResponse> filter(ClientRequest request, ExchangeFunction next) {
        if (request.url()
                .toString()
                .contains("/token")) {
            /*
             * Se a requisição atual for para geração do token, deve já retornar aqui para evitar um problema de
             * recursividade infinita
             */
            return next.exchange(request);
        }

        /*
         * Uma forma melhor para ser abordada aqui é gerar o token e guardar ele em cache enquanto estiver válido,
         * evitando assim que sempre seja necessária a geração de um novo token
         * Obs: para isso é necessário que a API retorne também a validade do token
         */
        final var newRequest = ClientRequest.from(request)
                .header(HttpHeaders.AUTHORIZATION, personClient.generateToken())
                .build();

        return next.exchange(newRequest);
    }

}
