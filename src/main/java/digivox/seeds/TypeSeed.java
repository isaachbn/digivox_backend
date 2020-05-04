package digivox.seeds;

import digivox.models.Type;
import digivox.repositories.TypeRepository;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.Synchronize;
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
public class TypeSeed {
    private TypeRepository typeRepository;

    @EventListener
    @Order(0)
    @Async()
    public void run(ApplicationReadyEvent event) {
        Page<Type> page = typeRepository.findAll(PageRequest.of(0, 1, Sort.by(Sort.Direction.ASC, "name")));

        if (page.isEmpty()) {
            typeRepository.saveAndFlush(new Type("Cultura"));
            typeRepository.saveAndFlush(new Type("Tecnologia"));
        }
    }
}
