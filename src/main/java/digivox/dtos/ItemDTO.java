package digivox.dtos;

import digivox.models.Item;
import digivox.models.Type;
import digivox.services.TypeService;
import digivox.validators.Unique;
import lombok.Getter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class ItemDTO {
    @NotBlank
    private String name;

    @NotNull
    @DecimalMin("1.00")
    private Double price;

    @NotNull
    @Unique(service = TypeService.class, fieldName = "type", message = "Deve existir esse type")
    private Long type;

    public Item convertModel() {
        return new Item(name, price, new Type(type));
    }
}
