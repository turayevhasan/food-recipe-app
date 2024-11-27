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
@Table(name = "code")
public class Code extends TimeLong {
    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private String email;
}
