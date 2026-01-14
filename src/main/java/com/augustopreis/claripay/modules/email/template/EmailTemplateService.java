package com.augustopreis.claripay.modules.email.template;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailTemplateService {

  private final TemplateEngine templateEngine;

  @Value("${email.reset-password-url}")
  private String resetPasswordUrl;

  public String buildForgotPasswordEmail(String userName, String resetToken) {
    Context context = new Context();

    context.setVariable("userName", userName);
    context.setVariable("resetLink", resetPasswordUrl + "?token=" + resetToken);
    context.setVariable("expiryHours", 24);

    return templateEngine.process("email/forgot-password", context);
  }
}
