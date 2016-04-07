package mk.ukim.finki.emt.store.service.impl;


import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;
import com.paypal.base.rest.PayPalResource;
import mk.ukim.finki.emt.store.model.Product;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ristes on 6.5.15.
 */
@Service
public class PaypalServiceImpl {

  private APIContext apiContext;
  OAuthTokenCredential credential;

  @PostConstruct
  public void init() {
    InputStream is = PaypalServiceImpl.class.getResourceAsStream("/sdk_config.properties");
    try {
      credential = PayPalResource.initConfig(is);
    } catch (PayPalRESTException e) {
      e.printStackTrace();
    }
  }

  public void config() {
    try {
      String accessToken = credential.getAccessToken();
      apiContext = new APIContext(accessToken);

    } catch (PayPalRESTException e) {
      e.printStackTrace();
    }
  }

  public List<Payment> executeCreditCardPayment(Address billingAddress, CreditCard creditCard, List<Product> items) {


    List<Payment> payments = new ArrayList<Payment>();
    // The Payment creation API requires a list of
    // Transaction; add the created `Transaction`
    // to a List
    for (Product item : items) {
      config();
      // ###FundingInstrument
      // A resource representing a Payeer's funding instrument.
      // Use a Payer ID (A unique identifier of the payer generated
      // and provided by the facilitator. This is required when
      // creating or using a tokenized funding instrument)
      // and the `CreditCardDetails`
      FundingInstrument fundingInstrument = new FundingInstrument();
      fundingInstrument.setCreditCard(creditCard);


      // The Payment creation API requires a list of
      // FundingInstrument; add the created `FundingInstrument`
      // to a List
      List<FundingInstrument> fundingInstrumentList = new ArrayList<FundingInstrument>();
      fundingInstrumentList.add(fundingInstrument);

      // ###Payer
      // A resource representing a Payer that funds a payment
      // Use the List of `FundingInstrument` and the Payment Method
      // as 'credit_card'
      Payer payer = new Payer();
      payer.setFundingInstruments(fundingInstrumentList);
      payer.setPaymentMethod("credit_card");


      // ###Details
      // Let's you specify details of a payment amount.
      Details details = new Details();
      details.setSubtotal((int) item.price + "");
      details.setTax((int) (item.price * 0.18) + "");

      // ###Amount
      // Let's you specify a payment amount.
      Amount amount = new Amount();
      amount.setCurrency("USD");
      // Total must be equal to sum of shipping, tax and subtotal.
      amount.setTotal((int) (item.price * 1.18) + "");
      amount.setDetails(details);

      // ###Transaction
      // A transaction defines the contract of a
      // payment - what is the payment for and who
      // is fulfilling it. Transaction is created with
      // a `Payee` and `Amount` types
      Transaction transaction = new Transaction();
      transaction.setAmount(amount);
      transaction.setDescription("EMT store: " + item.name);

      List<Transaction> transactions = new ArrayList<Transaction>();
      transactions.add(transaction);

      // ###Payment
      // A Payment Resource; create one using
      // the above types and intent as 'sale'
      Payment payment = new Payment();
      payment.setIntent("sale");
      payment.setPayer(payer);
      payment.setTransactions(transactions);
      Payment createdPayment = null;
      try {

        // Use this variant if you want to pass in a request id
        // that is meaningful in your application, ideally
        // a order id.
      /*
       * String requestId = Long.toString(System.nanoTime(); APIContext
			 * apiContext = new APIContext(accessToken, requestId ));
			 */

        // Create a payment by posting to the APIService
        // using a valid AccessToken
        // The return object contains the status;
        createdPayment = payment.create(apiContext);
        payments.add(createdPayment);
      } catch (PayPalRESTException e) {
        e.printStackTrace();
      }
    }


    return payments;
  }
}
