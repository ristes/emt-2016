package mk.ukim.finki.emt.store.tasks;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by ristes on 4/20/16.
 */
@Component
public class NewsletterTask {

  @Scheduled(cron = "20 * * * * ?")
  public void sendNewsletter() {
    System.out.println("Sending newsletter");
  }

  @Scheduled(fixedRate = 20000,initialDelay = 1000)
  public void test1() {
    System.out.println("Started test1");
  }
}
