package uz.pdp.food_recipe_app.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import uz.pdp.food_recipe_app.payload.mail.SendEmailDto;

@Service
@Slf4j
@RequiredArgsConstructor
@EnableAsync
public class MailSenderService {
    private final JavaMailSender javaMailSender;

    @Async
    public void send(SendEmailDto emailDto) throws MessagingException {
            log.info("Sending email to {}", emailDto.getTo());

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setFrom("food-recipe.uz");
            mimeMessageHelper.setTo(emailDto.getTo());
            mimeMessageHelper.setSubject(emailDto.getSubject());
            mimeMessageHelper.setText(emailDto.getBody(), emailDto.isHtml());

            javaMailSender.send(mimeMessage);

            log.info("Email sent to {}", emailDto.getTo());
    }
}