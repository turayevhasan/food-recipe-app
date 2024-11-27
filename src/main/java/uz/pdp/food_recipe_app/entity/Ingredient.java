package uz.pdp.food_recipe_app.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.pdp.food_recipe_app.entity.base.TimeLong;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "ingredient")
public class Ingredient extends TimeLong {
    @Column(nullable = false)
    private String name;

    private String photoPath;

    @Column(nullable = false)
    private Double weight;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Recipe recipe;
}
