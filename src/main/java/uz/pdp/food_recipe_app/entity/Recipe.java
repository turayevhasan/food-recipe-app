package uz.pdp.food_recipe_app.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.pdp.food_recipe_app.entity.base.BaseTimeLong;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "recipe")
public class Recipe extends BaseTimeLong {
    @Column(nullable = false)
    private String name;

    private UUID videoId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User user;

    @Column(nullable = false)
    private String country;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "recipe")
    private List<Procedure> procedures;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "recipe")
    private List<Ingredient> ingredients;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "recipe")
    private List<Review> reviews;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "recipe")
    private List<Rating> ratings;
}
