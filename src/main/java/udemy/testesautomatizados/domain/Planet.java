package udemy.testesautomatizados.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.apache.commons.lang3.builder.EqualsBuilder;


@Entity
@Data
@Table(name = "tb_planets")
public class Planet {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty()
    @Column(nullable = false, unique = true)
    private String name;

    @NotEmpty()
    @Column(nullable = false)
    private String climate;

    @NotEmpty()
    @Column(nullable = false)
    private String terrain;

    public Planet(String name, String climate, String terrain) {
        this.name = name;
        this.climate = climate;
        this.terrain = terrain;
    }

    public Planet(Long id, String name, String climate, String terrain) {
        this.id = id;
        this.name = name;
        this.climate = climate;
        this.terrain = terrain;
    }

    public Planet() {
    }

    public Planet(String climate, String terrain) {
    }

    @Override
    public boolean equals(Object obj){
        return EqualsBuilder.reflectionEquals(obj, this);
    }

    @Override
    public String toString() {
        return "Planet Id: " + id + "\nName: " + name + "\nClimate: " + climate + "\nTerrain: " + terrain;
    }
}
