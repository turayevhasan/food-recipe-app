package uz.pdp.food_recipe_app.payload.review.res;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import uz.pdp.food_recipe_app.util.FormatPatterns;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewRes {
    private Long id;
    private String text;
    private boolean deleted;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = FormatPatterns.DATE_TIME_FORMAT)
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = FormatPatterns.DATE_TIME_FORMAT)
    private LocalDateTime updatedAt;
}
