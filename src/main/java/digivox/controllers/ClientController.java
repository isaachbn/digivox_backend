package digivox.controllers;

import digivox.dtos.ClientDTO;
import digivox.models.Client;
import digivox.services.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping({"/api/clients"})
@AllArgsConstructor
public class ClientController {
    private final ClientService clientService;

    @GetMapping
    public List findAll() {
        return clientService.findAll();
    }

    @GetMapping(path = {"/{id}"})
    public ResponseEntity findById(@PathVariable long id) {
        Optional<Client> client = clientService.findById(id);

        return client
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Client> create(@RequestBody @Valid ClientDTO clientDTO) {
        Client clientModel =clientService.save(clientDTO.convertModel());

        return new ResponseEntity<>(clientModel, HttpStatus.CREATED);
    }

    @PutMapping(value="/{id}")
    public ResponseEntity<Client> update(@PathVariable("id") long id, @RequestBody @Valid ClientDTO clientDTO) {
        Optional<Client> clientModel = clientService.findById(id);

        return clientModel
                .map(client -> {
                    client.setName(clientDTO.getName());
                    client.setEmail(clientDTO.getEmail());
                    Client updated = clientService.save(client);
                    return ResponseEntity.ok().body(updated);
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping(path ={"/{id}"})
    public ResponseEntity<?> delete(@PathVariable long id) {
        Optional<Client> clientModel = clientService.findById(id);

        return clientModel
                .map(record -> {
                    clientService.remove(id);
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
                }).orElse(ResponseEntity.notFound().build());
    }
}
