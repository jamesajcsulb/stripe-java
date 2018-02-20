package com.stripe.model;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import com.stripe.BaseStripeMockTest;
import com.stripe.net.APIResource;

import org.junit.Test;

public class TransferTest extends BaseStripeMockTest {
  @Test
  public void testDeserialize() throws Exception {
    String data = getFixture("/v1/transfers/tr_123");
    Transfer transfer = APIResource.GSON.fromJson(data, Transfer.class);
    assertNotNull(transfer);
    assertNotNull(transfer.getId());
    assertNull(transfer.getBalanceTransactionObject());
    assertNull(transfer.getDestinationObject());
    assertNull(transfer.getDestinationPaymentObject());
    assertNull(transfer.getSourceTransactionObject());
  }

  @Test
  public void testDeserializeWithExpansions() throws Exception {
    String[] expansions = {
      "balance_transaction",
      "destination",
      "destination_payment",
      "source_transaction"
    };
    String data = getFixture("/v1/transfers/tr_123", expansions);
    Transfer transfer = APIResource.GSON.fromJson(data, Transfer.class);
    assertNotNull(transfer);
    BalanceTransaction balanceTransaction = transfer.getBalanceTransactionObject();
    assertNotNull(balanceTransaction);
    assertNotNull(balanceTransaction.getId());
    Account destination = transfer.getDestinationObject();
    assertNotNull(destination);
    assertNotNull(destination.getId());
    Charge destinationPayment = transfer.getDestinationPaymentObject();
    assertNotNull(destinationPayment);
    assertNotNull(destinationPayment.getId());
    Charge sourceTransaction = transfer.getSourceTransactionObject();
    assertNotNull(sourceTransaction);
    assertNotNull(sourceTransaction.getId());
  }
}
