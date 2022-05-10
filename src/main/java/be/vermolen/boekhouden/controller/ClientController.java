package be.vermolen.boekhouden.controller;

import be.vermolen.boekhouden.model.Client;
import be.vermolen.boekhouden.model.Salutation;
import be.vermolen.boekhouden.model.dto.CreateClientDto;
import be.vermolen.boekhouden.service.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/client")
@AllArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @GetMapping
    public List<Client> getAll() {
        return clientService.getAll();
    }

    @GetMapping("/{id}")
    public Client get(@PathVariable Long id) {
        return clientService.get(id);
    }

    @PutMapping
    public Client update(@RequestBody CreateClientDto client) {
        return clientService.update(client);
    }

    @PostMapping
    public Client create(@RequestBody CreateClientDto client) {
        return clientService.create(client);
    }

    @GetMapping("/salutations")
    public Salutation[] getAllSalutation() {
        return Salutation.values();
    }
}
