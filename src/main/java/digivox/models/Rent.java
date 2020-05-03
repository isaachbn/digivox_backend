package digivox.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "rents")
public class Rent {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "withdrawal", nullable = false)
    @JsonFormat(pattern="dd-MM-yyyy")
    private LocalDate withdrawal;

    @Column(name = "delivery")
    @JsonFormat(pattern="dd-MM-yyyy")
    private LocalDate delivery;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Item.class)
    private Item item;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Client.class)
    private Client client;

    public Rent(LocalDate delivery, Client client, Item item) {
        this.delivery = delivery;
        this.client = client;
        this.item = item;
        this.withdrawal = LocalDate.now();
    }
}
