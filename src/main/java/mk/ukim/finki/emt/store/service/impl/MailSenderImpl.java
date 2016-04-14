package mk.ukim.finki.emt.store.service.impl;

import com.paypal.base.codec.CharEncoding;
import mk.ukim.finki.emt.store.model.User;
import mk.ukim.finki.emt.store.service.MailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

/**
 * Created by ristes on 4/13/16.
 */
@Service
public class MailSenderImpl implements MailSender {

  @Autowired
  private JavaMailSender mailSender;


  @Override
  public void sendEmail(User user, String subject, String content, boolean isHtml) {
    final String to = user.email;
    final String from = "contact@emt-store.com";

    MimeMessage mimeMessage = mailSender.createMimeMessage();
    try {
      MimeMessageHelper message =
        new MimeMessageHelper(
          mimeMessage, false, CharEncoding.UTF_8);
      message.setTo(to);
      message.setFrom(from);
      message.setSubject(subject);
      message.setText(content, isHtml);
      mailSender.send(mimeMessage);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
