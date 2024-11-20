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
@Table(name = "review_like")
public class Like extends BaseTimeLong {
    @Column(nullable = false)
    private Boolean likeOrDislike;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Review review;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User user;
}
