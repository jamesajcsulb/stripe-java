package com.stripe.model;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import com.stripe.BaseStripeMockTest;
import com.stripe.net.APIResource;

import org.junit.Test;

public class TopupTest extends BaseStripeMockTest {
  @Test
  public void testDeserialize() throws Exception {
    String data = getFixture("/v1/topups/tu_123");
    Topup topup = APIResource.GSON.fromJson(data, Topup.class);
    assertNotNull(topup);
    assertNotNull(topup.getId());
    assertNull(topup.getBalanceTransactionObject());
  }

  @Test
  public void testDeserializeWithExpansions() throws Exception {
    String[] expansions = {"balance_transaction"};
    String data = getFixture("/v1/transfers/tr_123", expansions);
    Topup topup = APIResource.GSON.fromJson(data, Topup.class);
    assertNotNull(topup);
    BalanceTransaction balanceTransaction = topup.getBalanceTransactionObject();
    assertNotNull(balanceTransaction);
    assertNotNull(balanceTransaction.getId());
  }
}
