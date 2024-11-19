package uz.pdp.food_recipe_app.payload.mail;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SendEmailDto {
    private String to;
    private String subject;
    private String body;
    private boolean html;
}