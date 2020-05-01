package digivox.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "type")
public class Type {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name")
    private String name;

    public Type(String name) {
        this.name = name;
    }

    public Type(Long id) {
        this.id = id;
    }
}
