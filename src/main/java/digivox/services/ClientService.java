package digivox.services;

import digivox.models.Client;
import digivox.repositories.ClientReposiitory;
import digivox.validators.FieldValueExists;
import org.springframework.beans.factory.annotation.Autowired;
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

    public Optional<Client> findById(Long id) {
        return clientReposiitory.findById(id);
    }

    public Client save(Client client) {
        return clientReposiitory.save(client);
    }

    public void remove(Long id) {
        clientReposiitory.deleteById(id);
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
