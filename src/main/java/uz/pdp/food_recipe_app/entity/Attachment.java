package uz.pdp.food_recipe_app.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import uz.pdp.food_recipe_app.entity.base.BaseTimeUUID;
import uz.pdp.food_recipe_app.util.BaseConstants;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "attachment")
public class Attachment extends BaseTimeUUID {
    @Column(nullable = false)
    private String originalName;

    @Column(nullable = false)
    private String extension;

    @Column(nullable = false)
    private Long fileSize;

    @Column(nullable = false)
    private String contentType;

    public String getFilePath() {
        return BaseConstants.FILE_UPLOAD_PATH + super.getId() + "." + this.extension;
    }
}
