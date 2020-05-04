package digivox.seeds;

import digivox.models.Item;
import digivox.models.Type;
import digivox.repositories.ItemRepository;
import digivox.repositories.TypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class ItemSeed {
    private ItemRepository itemRepository;
    private TypeRepository typeRepository;

    @EventListener
    @Order(2)
    @Async
    public void run(ApplicationReadyEvent event) {
        Page<Item> page = itemRepository.findAll(
                PageRequest.of(0, 1, Sort.by(Sort.Direction.ASC, "name"))
        );

        if (page.isEmpty()) {
            Optional<Type> typeCulturaOptional = typeRepository.findById(Long.valueOf(1));
            Optional<Type> typeTecnologiaOptional = typeRepository.findById(Long.valueOf(2));

            typeCulturaOptional.map(typeCultura -> {
                itemRepository.saveAndFlush(new Item("Por que fazemos o que fazemos?", 80.40, typeCultura));
                itemRepository.saveAndFlush(new Item("O filho eterno", 53.99, typeCultura));
                itemRepository.saveAndFlush(new Item("O príncipe", 123.42, typeCultura));
                return typeCultura;
            });

            typeTecnologiaOptional.map(typeTecnologia -> {
                itemRepository.saveAndFlush(new Item("kubernetes", 120.35, typeTecnologia));
                itemRepository.saveAndFlush(new Item("O Codificador Limpo", 90.00, typeTecnologia));
                return typeTecnologia;
            });
        }
    }
}
