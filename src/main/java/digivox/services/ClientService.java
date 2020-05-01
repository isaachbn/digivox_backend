package digivox.services;

import digivox.dtos.ClientDTO;
import digivox.models.Client;
import digivox.repositories.ClientReposiitory;
import digivox.validators.FieldValueExists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService implements FieldValueExists {
    private final ClientReposiitory clientReposiitory;

    @Autowired
    public ClientService(ClientReposiitory clientReposiitory) {
        this.clientReposiitory = clientReposiitory;
    }

    public List<Client> findAll() {
        return clientReposiitory.findAll();
    }

    public ResponseEntity<Client> findById(Long id) {
        return clientReposiitory.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<Client> create(ClientDTO clientDTO) {
        Client clientModel = clientReposiitory.save(clientDTO.convertModel());
        return new ResponseEntity<>(clientModel, HttpStatus.CREATED);
    }

    public ResponseEntity<?> remove(Long id) {
        return clientReposiitory.findById(id)
                .map(record -> {
                    clientReposiitory.deleteById(id);
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
                }).orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<Client> update(Long id, ClientDTO clientDTO) {
        return clientReposiitory.findById(id)
                .map(item -> {
                    item.setName(clientDTO.getName());
                    item.setEmail(clientDTO.getEmail());
                    Client updated = clientReposiitory.save(item);
                    return ResponseEntity.ok().body(updated);
                }).orElse(ResponseEntity.notFound().build());
    }

    @Override
    public boolean fieldValueExists(Object value, String fieldName) throws UnsupportedOperationException {
        if (!fieldName.equals("client")) {
            return true;
        }

        if (value == null || value instanceof String) {
            return true;
        }

        Optional<Client> optionalType = this.clientReposiitory.findById(Long.parseLong(value.toString()));

        return !optionalType.isPresent();
    }
}
