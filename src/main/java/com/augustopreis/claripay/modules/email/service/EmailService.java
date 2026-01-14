package com.augustopreis.claripay.modules.email.service;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.augustopreis.claripay.modules.email.dto.EmailDTO;
import com.augustopreis.claripay.modules.email.template.EmailTemplateService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService {

  private final JavaMailSender mailSender;
  private final EmailTemplateService templateService;

  @Value("${email.from}")
  private String fromEmail;

  @Value("${email.from-name}")
  private String fromName;

  @Async
  public void sendEmail(EmailDTO emailDTO) {
    try {
      SimpleMailMessage message = new SimpleMailMessage();

      message.setFrom(fromEmail);
      message.setTo(emailDTO.getTo());
      message.setSubject(emailDTO.getSubject());
      message.setText(emailDTO.getBody());

      mailSender.send(message);
    } catch (MailException e) {
      log.error("Erro ao enviar email para \"{}\": {}", emailDTO.getTo(), e.getMessage());
    }
  }

  @Async
  public void sendHtmlEmail(EmailDTO emailDTO) {
    try {
      MimeMessage mimeMessage = mailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

      helper.setFrom(fromEmail, fromName);
      helper.setTo(emailDTO.getTo());
      helper.setSubject(emailDTO.getSubject());
      helper.setText(emailDTO.getBody(), true);

      mailSender.send(mimeMessage);
    } catch (UnsupportedEncodingException | MailException | MessagingException e) {
      log.error("Erro ao enviar email HTML para \"{}\": {}", emailDTO.getTo(), e.getMessage());
    }
  }

  public void sendForgotPasswordEmail(String toEmail, String userName, String resetToken) {
    String htmlContent = templateService.buildForgotPasswordEmail(userName, resetToken);

    EmailDTO emailDTO = EmailDTO.builder()
        .to(toEmail)
        .subject("Recuperação de Senha - Claripay")
        .body(htmlContent)
        .build();

    sendHtmlEmail(emailDTO);
  }
}
