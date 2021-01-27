package at.fhtw.bif3.controller.dto;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class CardDTO {

    @SerializedName("Id")
    String id;

    @SerializedName("Name")
    String name;

    @SerializedName("Damage")
    double damage;

    @SerializedName("Weakness")
    Double weakness;

    public CardDTO() {
    }

    public CardDTO(String id, String name, double damage, Double weakness) {
        this.id = id;
        this.name = name;
        this.damage = damage;
        this.weakness = weakness;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public Double getWeakness() {
        return weakness;
    }

    public void setWeakness(Double weakness) {
        this.weakness = weakness;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CardDTO cardDTO = (CardDTO) o;
        return Double.compare(cardDTO.damage, damage) == 0 && id.equals(cardDTO.id) && name.equals(cardDTO.name) && weakness.equals(cardDTO.weakness);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, damage, weakness);
    }

    @Override
    public String toString() {
        return "CardDTO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", damage=" + damage +
                ", weakness=" + weakness +
                '}';
    }
}
