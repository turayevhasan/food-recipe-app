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
@Table(name = "rating")
public class Rating extends TimeLong {
    @Column(nullable = false)
    private Integer stars;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Recipe recipe;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User user;
}
