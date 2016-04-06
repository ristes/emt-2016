package mk.ukim.finki.emt.store;

import com.paypal.api.payments.Address;
import com.paypal.api.payments.CreditCard;
import mk.ukim.finki.emt.store.model.Product;
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
public class SpringWebShowcaseApplicationTests {

  @Autowired
  PaypalServiceImpl paypalService;

  @Test
  public void contextLoads() {


    List<Product> products = new ArrayList<Product>();
    Product p1 = new Product();
    p1.name = "product 1";
    p1.price = 10;
    Product p2 = new Product();
    products.add(p1);
    p2.name = "product 2";
    p2.price = 20;
    products.add(p2);

    Address billingAddress = new Address();
    billingAddress.setCity("Johnstown");
    billingAddress.setCountryCode("US");
    billingAddress.setLine1("52 N Main ST");
    billingAddress.setPostalCode("43210");
    billingAddress.setState("OH");

    // ###CreditCard
    // A resource representing a credit card that can be
    // used to fund a payment.
    CreditCard creditCard = new CreditCard();
    creditCard.setBillingAddress(billingAddress);
    creditCard.setCvv2(111);
    creditCard.setExpireMonth(11);
    creditCard.setExpireYear(2018);
    creditCard.setFirstName("Joe");
    creditCard.setLastName("Shopper");
    creditCard.setNumber("5500005555555559");
    creditCard.setType("mastercard");

    paypalService.executeCreditCardPayment(billingAddress, creditCard, products);
  }

}
