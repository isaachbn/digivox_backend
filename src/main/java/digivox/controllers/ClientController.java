package digivox.controllers;

import digivox.dtos.ClientDTO;
import digivox.models.Client;
import digivox.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping({"/api/clients"})
public class ClientController {
    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public List findAll() {
        return clientService.findAll();
    }

    @GetMapping(path = {"/{id}"})
    public ResponseEntity findById(@PathVariable long id){
        return clientService.findById(id);
    }

    @PostMapping
    public ResponseEntity<Client> create(@RequestBody @Valid ClientDTO clientDTO) {
        return clientService.create(clientDTO);
    }

    @PutMapping(value="/{id}")
    public ResponseEntity<Client> update(@PathVariable("id") long id, @RequestBody @Valid ClientDTO clientDTO) {
        return clientService.update(id, clientDTO);
    }

    @DeleteMapping(path ={"/{id}"})
    public ResponseEntity<?> delete(@PathVariable long id) {
        return clientService.remove(id);
    }
}
