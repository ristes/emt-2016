package mk.ukim.finki.emt.store;

import com.paypal.api.payments.Address;
import com.paypal.api.payments.CreditCard;
import mk.ukim.finki.emt.store.model.Product;
import mk.ukim.finki.emt.store.model.User;
import mk.ukim.finki.emt.store.service.UserRegistrationMailSender;
import mk.ukim.finki.emt.store.service.impl.PaypalServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = StoreApplication.class)
@WebAppConfiguration
public class MailTests {

  @Autowired
  UserRegistrationMailSender userRegistrationMailSender;

  @Test
  public void synchronousMailSend() {
    User test=new User();
    test.username="test";
    test.email="test@test.com";

    userRegistrationMailSender.sendRegistrationMail(test,false);
  }

  @Test
  public void asynchronousMailSend() {
    User test=new User();
    test.username="test";
    test.email="test@test.com";

    userRegistrationMailSender.sendRegistrationMail(test,true);
  }
}
