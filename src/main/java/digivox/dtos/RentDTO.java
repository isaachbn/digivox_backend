package digivox.dtos;

import digivox.models.Client;
import digivox.models.Item;
import digivox.models.Rent;
import digivox.services.ClientService;
import digivox.services.ItemService;
import digivox.validators.Unique;
import lombok.Getter;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
public class RentDTO {
    @NotNull
    @FutureOrPresent
    private LocalDate delivery;

    @NotNull
    @Unique(service = ClientService.class, fieldName = "client", message = "Deve existir esse client")
    private Long client;

    @NotNull
    @Unique(service = ItemService.class, fieldName = "item", message = "Deve existir esse item")
    private Long item;

    public Rent convertModel() {
        return new Rent(delivery, new Client(client), new Item(item));
    }
}
