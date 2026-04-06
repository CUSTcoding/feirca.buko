package ccs.com.feirca_buko_api.auth.service;

import jakarta.mail.internet.MimeMessage;
import ccs.com.feirca_buko_api.common.exception.BadRequestException;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendPasswordResetEmail(String to, String token, String userName) {
        try {
            String link = "http://localhost:8080/api/auth/password/reset/confirm?token=" + token;

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");

            helper.setTo(to);
            helper.setSubject("Redefinir senha - Feirca.Buko");

            String htmlMsg = "<h2>Feirca.Tiva</h2>" +
                    "<p>Olá <strong>" + userName + "</strong>,</p>" +
                    "<p>Recebemos uma solicitação para redefinir sua senha.</p>" +
                    "<p>Clique no botão abaixo para criar uma nova senha:</p>" +
                    "<p><a href='" + link + "' style='background-color:#4CAF50;color:white;padding:10px 20px;text-decoration:none;border-radius:5px;'>Redefinir senha</a></p>" +
                    "<p>Se você não solicitou esta alteração, ignore este email.</p>" +
                    "<br><p>Atenciosamente,<br>Equipe Feirca.Tiva</p>";

            helper.setText(htmlMsg, true);
            mailSender.send(message);
        } catch (MessagingException | MailException exception) {
            throw new BadRequestException("Não foi possível enviar o email de redefinição");
        }
    }
}
