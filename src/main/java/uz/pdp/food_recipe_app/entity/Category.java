package uz.pdp.food_recipe_app.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.pdp.food_recipe_app.entity.base.TimeLong;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "category")
public class Category extends TimeLong {
    @Column(unique = true, nullable = false)
    private String name;
    private String photoPath;
}
