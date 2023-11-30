package pro.sky.telegrambot.entity;

import javax.persistence.*;
import java.util.Objects;


/**
 * class of pets
 */
@Entity
@Table(name = "pets")
public class Pet implements Comparable<Pet> {

    /**
     * id of pet
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * kind of animal (for example: cat, dog)
     */
    @Column(name = "kind_of_pet")
    private String kindOfPet;

    /**
     * name of the pet
     */
    @Column(name = "name")
    private String name;

    public Long getId() {
        return id;
    }

    public String getKindOfPet() {
        return kindOfPet;
    }

    public void setKindOfPet(String kindOfPet) {
        this.kindOfPet = kindOfPet;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pet pet = (Pet) o;
        return Objects.equals(id, pet.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public int compareTo(Pet o) {
        return o.getId().compareTo(id);
    }
}
