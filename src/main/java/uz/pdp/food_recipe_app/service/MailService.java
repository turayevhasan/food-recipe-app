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
                            background-color: #4B9CD3;
                            color: white;
                            text-align: center;
                            padding: 20px;
                        }
                        .content {
                            padding: 30px;
                            text-align: left;
                        }
                        .footer {
                            background-color: #E1EBEE;
                            color: #777;
                            text-align: center;
                            padding: 15px;
                            font-size: 12px;
                        }
                        a {
                            color: #4CAF50;
                            text-decoration: none;
                        }
                        .form-group {
                            margin-bottom: 15px;
                        }
                        label {
                            display: block;
                            margin-bottom: 5px;
                            font-weight: bold;
                        }
                        .email-password{
                                    width: 100%%;
                                    padding: 10px;
                                    font-size: 14px;
                                    border: 1px solid #ccc;
                                    border-radius: 4px;
                                    box-sizing: border-box;
                        }
                          .form-text {
                                    font-size: 12px;
                                    color: #6c757d;
                                }
                                .btn-primary {
                                    background-color: #007bff;
                                    color: white;
                                    padding: 10px 15px;
                                    border: none;
                                    border-radius: 4px;
                                    cursor: pointer;
                                    font-size: 14px;
                                    display: block; /* Block-level element */
                                    margin: 20px auto; /* Center the button */
                                }
                                .btn-primary:hover {
                                    background-color: #0056b3;
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
                            <p><b>© 2024 Food Recipe App.</b></p>
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
