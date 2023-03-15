package io.github.andersoncrocha.demohttpinterfaces.httpclients;

import io.github.andersoncrocha.demohttpinterfaces.dtos.CreatePersonRequestDTO;
import io.github.andersoncrocha.demohttpinterfaces.dtos.PersonResponseDTO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;

import java.util.List;

public interface PersonClient {

    @GetExchange("/token")
    String generateToken();

    @PostExchange("/person")
    PersonResponseDTO save(@RequestBody CreatePersonRequestDTO request);

    @GetExchange("/person")
    List<PersonResponseDTO> list();

    @GetExchange("/person/{id}")
    PersonResponseDTO getById(@PathVariable String id);

}
