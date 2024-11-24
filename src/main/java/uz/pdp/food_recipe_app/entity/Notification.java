package uz.pdp.food_recipe_app.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.pdp.food_recipe_app.entity.base.BaseTimeLong;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "notification")
public class Notification extends BaseTimeLong {
    @Column(nullable = false)
    private Boolean read;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String text;

    private Long recipeId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User user;
}
