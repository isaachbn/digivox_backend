package digivox.dtos;

import digivox.models.Client;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
public class ClientDTO {
    @NotBlank
    private String name;

    @Email
    @NotBlank
    private String email;

    public Client convertModel() {
        return new Client(name, email);
    }
}
