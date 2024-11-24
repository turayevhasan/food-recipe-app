package uz.pdp.food_recipe_app.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.pdp.food_recipe_app.entity.base.BaseTimeUUID;
import uz.pdp.food_recipe_app.enums.UserStatus;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "auth_user")
public class User extends BaseTimeUUID {
    @Column(nullable = false)
    private String fullName;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String country;

    private String bio;

    @ManyToOne(optional = false)
    private Role role;

    private UUID photoId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private UserStatus status = UserStatus.INACTIVE;

    public boolean isActive() {
        return this.status == UserStatus.ACTIVE;
    }

    ////////////////////////////////////////////////////

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Recipe> recipes;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Review> reviews;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Saved> savings;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Notification> notifications;
}
