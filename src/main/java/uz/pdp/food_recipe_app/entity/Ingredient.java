package uz.pdp.food_recipe_app.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.pdp.food_recipe_app.entity.base.BaseTimeLong;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "ingredient")
public class Ingredient extends BaseTimeLong {
    @Column(nullable = false)
    private String name;

    private UUID photo;

    @Column(nullable = false)
    private Double weight;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Recipe recipe;

}
