package digivox.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "items")
public class Item {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Double price;

    @Column(name = "rented", nullable = false, columnDefinition = "boolean default false")
    private Boolean rented;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Type.class)
    private Type type;

    public Item(String name, Double price, Type type) {
        this.name = name;
        this.price = price;
        this.type = type;
        this.rented = false;
    }

    public Item(Long id) {
        this.id = id;
    }
}
