package com.drawproject.dev.service;

import com.drawproject.dev.dto.Mail;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
public class MailService {

    @Autowired
    JavaMailSender sender;

    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    private Configuration config;

    public void sendMessage(Mail mail, Map<String, Object> model) {

        MimeMessage message = sender.createMimeMessage();

        try {
            // set mediaType
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

            Template template = config.getTemplate(mail.getTemplate());
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

            helper.setTo(mail.getTo());
            helper.setText(html, true);
            helper.setSubject(mail.getSubject());
            helper.setFrom(from);
            sender.send(message);

        } catch (IOException | TemplateException | MessagingException e) {
            throw new RuntimeException(e);
        }

    }

}
