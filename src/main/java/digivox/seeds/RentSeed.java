package digivox.seeds;

import digivox.models.Client;
import digivox.models.Item;
import digivox.models.Rent;
import digivox.repositories.ClientReposiitory;
import digivox.repositories.ItemRepository;
import digivox.repositories.RentRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class RentSeed {
    private ClientReposiitory clientReposiitory;
    private ItemRepository itemRepository;
    private RentRepository rentRepository;

    @EventListener
    @Order(4)
    @Async
    public void run(ApplicationReadyEvent event) {
        Page<Rent> page = rentRepository.findAll(
                PageRequest.of(0, 1, Sort.by(Sort.Direction.ASC, "id"))
        );

        if (page.isEmpty()) {
            List<Item> items = itemRepository.findAll();
            Optional<Client> clientOptional = clientReposiitory.findById(Long.valueOf(3));

            clientOptional.map(client -> {
                items.stream()
                        .filter(item -> "Cultura".equals(item.getType().getName()))
                        .map(item -> {
                            rentRepository.saveAndFlush(new Rent(LocalDate.now(), client, item));
                            return item;
                        })
                        .collect(Collectors.toList());

                return client;
            });
        }
    }
}
