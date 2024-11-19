package uz.pdp.food_recipe_app.service;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.food_recipe_app.payload.mail.SendEmailDto;

@Service
@RequiredArgsConstructor
public class MailService {
    private final MailSenderService mailSenderService;

    public void sendMessage(String email, String body, String title, String subject) throws MessagingException {
        String htmlContent = """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <style>
                        body {
                            font-family: Arial, sans-serif;
                            background-color: #f4f4f4;
                            margin: 0;
                            padding: 0;
                        }
                        .container {
                            max-width: 600px;
                            margin: 50px auto;
                            background-color: #ffffff;
                            border-radius: 8px;
                            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                            overflow: hidden;
                        }
                        .header {
                            background-color: #4CAF50;
                            color: white;
                            text-align: center;
                            padding: 20px;
                        }
                        .content {
                            padding: 30px;
                            text-align: left;
                        }
                        .footer {
                            background-color: #f4f4f4;
                            color: #777;
                            text-align: center;
                            padding: 15px;
                            font-size: 12px;
                        }
                        a {
                            color: #4CAF50;
                            text-decoration: none;
                        }
                    </style>
                </head>
                <body>
                    <div class="container">
                        <div class="header">
                            <h1>%s</h1>
                        </div>
                        <div class="content">
                            <p>%s</p>
                        </div>
                        <div class="footer">
                            <p>© 2024 Food Recipe App.</p>
                        </div>
                    </div>
                </body>
                </html>
                """.formatted(title, body);

        SendEmailDto sendEmailDto = SendEmailDto.builder()
                .to(email)
                .subject(subject)
                .body(htmlContent)
                .html(true)
                .build();
        mailSenderService.send(sendEmailDto);
    }
}
