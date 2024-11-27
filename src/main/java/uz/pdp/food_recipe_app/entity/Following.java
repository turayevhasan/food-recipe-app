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
@Table(name = "following", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "following_id"}))
public class Following extends TimeLong {
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User following;
}
