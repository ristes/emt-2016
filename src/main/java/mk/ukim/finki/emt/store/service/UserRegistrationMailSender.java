package mk.ukim.finki.emt.store.service;

import mk.ukim.finki.emt.store.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import java.util.Locale;

/**
 * Created by ristes on 4/13/16.
 */
@Service
public class UserRegistrationMailSender {


  @Autowired
  private SpringTemplateEngine templateEngine;

  @Autowired
  MailSender asyncMailSender;

  @Autowired
  @Qualifier("mailSenderImpl")
  MailSender syncMailSender;

  @Autowired
  JavaMailSender mailSender;

  public void sendRegistrationMail(User user, boolean isAsync) {

    Locale locale = Locale.getDefault();
    Context context = new Context(locale);
    context.setVariable("user", user);
    context.setVariable("baseUrl", "https://localhost/login");
    String content = templateEngine.process("mail/activationEmail", context);

    if (isAsync) {
      asyncMailSender.sendEmail(user, "registration completed", content, true);
    } else {
      syncMailSender.sendEmail(user, "registration completed", content, true);
    }
  }

}
