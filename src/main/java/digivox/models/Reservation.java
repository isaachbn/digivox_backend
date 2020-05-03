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
@Entity(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "pick_up")
    @JsonFormat(pattern="dd-MM-yyyy")
    private LocalDate pickUp;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Item.class)
    private Item item;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Client.class)
    private Client client;

    public Reservation(LocalDate pickUp, Client client, Item item) {
        this.pickUp = pickUp;
        this.client = client;
        this.item = item;
    }
}
