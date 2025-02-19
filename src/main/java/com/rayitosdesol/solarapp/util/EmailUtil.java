package com.rayitosdesol.solarapp.util;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.util.Map;
import java.io.IOException;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;


@Component
public class EmailUtil {

    private final JavaMailSender mailSender;
    private final Configuration freemarkerConfig;

    public EmailUtil(JavaMailSender mailSender, Configuration freemarkerConfig) {
        this.mailSender = mailSender;
        this.freemarkerConfig = freemarkerConfig;
    }

    public void sendQuotationEmail(String to, String subject, Map<String, Object> model) throws MessagingException, TemplateException, IOException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(to);
        helper.setSubject(subject);

        Template template = freemarkerConfig.getTemplate("quotation-email-template.html");
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

        helper.setText(html, true);
        mailSender.send(message);
    }
}