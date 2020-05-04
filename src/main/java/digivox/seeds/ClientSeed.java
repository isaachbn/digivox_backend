package digivox.seeds;

import digivox.models.Client;
import digivox.repositories.ClientReposiitory;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ClientSeed {
    private ClientReposiitory clientReposiitory;

    @EventListener
    @Order(1)
    @Async
    public void run(ApplicationReadyEvent event) {
        Page<Client> page = clientReposiitory.findAll(PageRequest.of(0, 1, Sort.by(Sort.Direction.ASC, "name")));

        if (page.isEmpty()) {
            clientReposiitory.saveAndFlush(new Client("Isaac Henrique", "isaachbnn@gmail.com"));
            clientReposiitory.saveAndFlush(new Client("Fulano da Silva", "fulano@gmail.com"));
        }
    }
}
