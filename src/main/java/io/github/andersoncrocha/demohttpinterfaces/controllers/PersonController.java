package io.github.andersoncrocha.demohttpinterfaces.controllers;

import io.github.andersoncrocha.demohttpinterfaces.dtos.CreatePersonRequestDTO;
import io.github.andersoncrocha.demohttpinterfaces.dtos.PersonResponseDTO;
import io.github.andersoncrocha.demohttpinterfaces.httpclients.PersonClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("person")
public class PersonController {

    private final PersonClient personClient;

    public PersonController(PersonClient personClient) {
        this.personClient = personClient;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PersonResponseDTO save(@RequestBody CreatePersonRequestDTO requestDTO) {
        return personClient.save(requestDTO);
    }

    @GetMapping
    public List<PersonResponseDTO> list() {
        return personClient.list();
    }

    @GetMapping("{id}")
    public PersonResponseDTO getById(@PathVariable String id) {
        return personClient.getById(id);
    }

}
