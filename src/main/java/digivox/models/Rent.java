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

    @Column(name = "withdrawal", nullable = false, columnDefinition = "DATE")
    @JsonFormat(pattern="dd-MM-yyyy")
    private LocalDate withdrawal;

    @Column(name = "delivery", columnDefinition = "DATE")
    @JsonFormat(pattern="dd-MM-yyyy")
    private LocalDate delivery;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Item.class)
    private Item item;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Client.class)
    private Client client;

    @Column(name = "delivered", nullable = false, columnDefinition = "boolean default false")
    private Boolean delivered;

    public Rent(LocalDate delivery, Client client, Item item) {
        this.delivery = delivery;
        this.client = client;
        this.item = item;
        this.withdrawal = LocalDate.now();
        this.delivered = false;
    }
}
