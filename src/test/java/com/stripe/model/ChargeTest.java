package com.stripe.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import com.stripe.BaseStripeMockTest;
import com.stripe.net.APIResource;

import org.junit.Test;

public class ChargeTest extends BaseStripeMockTest {
  @Test
  public void testDeserialize() throws Exception {
    String data = getFixture("/v1/charges/ch_123");
    Charge charge = APIResource.GSON.fromJson(data, Charge.class);
    assertNotNull(charge);
    assertNotNull(charge.getId());
    assertEquals("charge", charge.getObject());
    assertNull(charge.getApplicationObject());
    assertNull(charge.getApplicationFeeObject());
    assertNull(charge.getBalanceTransactionObject());
    assertNull(charge.getCustomerObject());
    assertNull(charge.getDestinationObject());
    assertNull(charge.getDisputeObject());
    assertNull(charge.getInvoiceObject());
    //assertNull(charge.getOnBehalfOfObject());
    assertNull(charge.getOrderObject());
    assertNull(charge.getReviewObject());
    assertNull(charge.getSourceTransferObject());
    assertNull(charge.getTransferObject());
  }

  @Test
  public void testDeserializeWithExpansions() throws Exception {
    String[] expansions = {
      // "application",
      // "application_fee",
      "balance_transaction",
      "customer",
      "destination",
      "dispute",
      "invoice",
      "on_behalf_of",
      "order",
      // "review",
      "source_transfer",
      "transfer"
    };
    String data = getFixture("/v1/charges/ch_123", expansions);
    Charge charge = APIResource.GSON.fromJson(data, Charge.class);
    assertNotNull(charge);
    // Application application = charge.getApplicationObject();
    // assertNotNull(application);
    // assertNotNull(application.getId());
    // ApplicationFee applicationFee = charge.getApplicationFeeObject();
    // assertNotNull(applicationFee);
    // assertNotNull(applicationFee.getId());
    BalanceTransaction balanceTransaction = charge.getBalanceTransactionObject();
    assertNotNull(balanceTransaction);
    assertNotNull(balanceTransaction.getId());
    Customer customer = charge.getCustomerObject();
    assertNotNull(customer);
    assertNotNull(customer.getId());
    Account destination = charge.getDestinationObject();
    assertNotNull(destination);
    assertNotNull(destination.getId());
    Dispute dispute = charge.getDisputeObject();
    assertNotNull(dispute);
    assertNotNull(dispute.getId());
    Invoice invoice = charge.getInvoiceObject();
    assertNotNull(invoice);
    assertNotNull(invoice.getId());
    Order order = charge.getOrderObject();
    assertNotNull(order);
    assertNotNull(order.getId());
    // Review review = charge.getReviewObject();
    // assertNotNull(review);
    // assertNotNull(review.getId());
    Transfer sourceTransfer = charge.getSourceTransferObject();
    assertNotNull(sourceTransfer);
    assertNotNull(sourceTransfer.getId());
    Transfer transfer = charge.getTransferObject();
    assertNotNull(transfer);
    assertNotNull(transfer.getId());
  }
}
