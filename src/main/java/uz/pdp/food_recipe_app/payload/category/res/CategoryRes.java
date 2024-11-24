package uz.pdp.food_recipe_app.payload.category.res;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.food_recipe_app.util.FormatPatterns;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryRes {
    private Long id;
    private String name;
    private String photoPath;
    private boolean deleted;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = FormatPatterns.DATE_TIME_FORMAT)
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = FormatPatterns.DATE_TIME_FORMAT)
    private LocalDateTime updatedAt;
}
