package digivox.dtos;

import digivox.models.Type;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class TypeDTO {
    @NotBlank
    private String name;

    public Type convertModel() {
        return new Type(name);
    }
}
