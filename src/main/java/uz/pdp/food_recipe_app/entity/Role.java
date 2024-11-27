package uz.pdp.food_recipe_app.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.food_recipe_app.entity.base.TimeLong;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "auth_role")
public class Role extends TimeLong {
    @Column(nullable = false, unique = true)
    private String name;
}
