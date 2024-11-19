package uz.pdp.food_recipe_app.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.food_recipe_app.entity.base.BaseTimeLong;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "auth_role")
public class Role extends BaseTimeLong {
    private String name;
}
