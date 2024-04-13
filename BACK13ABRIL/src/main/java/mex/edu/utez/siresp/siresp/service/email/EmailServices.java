package mex.edu.utez.siresp.siresp.service.email;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import mex.edu.utez.siresp.siresp.model.email.Email;
import mex.edu.utez.siresp.siresp.utils.Response;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import java.sql.SQLException;


@Service
public class EmailServices {


    private final  JavaMailSender javaMailSender;


    private final  TemplateEngine templateEngine;

    public EmailServices(JavaMailSender javaMailSender, TemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

    @Transactional(rollbackFor = SQLException.class)
    public Response<Email> sendEmail(Email datos) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

        try {
            helper.setTo(datos.getReceptor());
            helper.setFrom(datos.getTransmitter());
            helper.setSubject(datos.getTitle());

            // Crear un contexto Thymeleaf para pasar datos a la plantilla
            Context thymeleafContext = new Context();
            thymeleafContext.setVariable("transmitter", datos.getTransmitter());
            thymeleafContext.setVariable("receptor", datos.getReceptor());
            thymeleafContext.setVariable("text", datos.getText());
            thymeleafContext.setVariable("title", datos.getTitle());
            thymeleafContext.setVariable("body", datos.getBody());

// Procesar la plantilla Thymeleaf
            String content = templateEngine.process("email-template", thymeleafContext);

            helper.setText(content, true);

            // Enviar el correo
            javaMailSender.send(mimeMessage);

            return new Response<>(
                    null,
                    false,
                    200,
                    "Email send with template"
            );
        } catch (MessagingException e) {
            e.printStackTrace();  // Manejo adecuado de las excepciones en un entorno de producción
            return new Response<>(
                    null,
                    true,
                    500,
                    "Error sending email with template"
            );
        }
    }

}