package mk.ukim.finki.emt.store.service;

import mk.ukim.finki.emt.store.model.User;

/**
 * Created by ristes on 4/13/16.
 */
public interface MailSender {
  void sendEmail(User user, String subject, String content, boolean isHtml);
}
