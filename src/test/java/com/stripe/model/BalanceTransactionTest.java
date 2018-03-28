package com.stripe.model;

import static org.junit.Assert.assertNotNull;

import com.stripe.BaseStripeMockTest;
import com.stripe.model.BalanceTransaction;
import com.stripe.net.APIResource;

import org.junit.Test;

public class BalanceTransactionTest extends BaseStripeMockTest {
  @Test
  public void testDeserialize() throws Exception {
    String data = getFixture("/v1/balance/history/txn_123");
    BalanceTransaction resource = APIResource.GSON.fromJson(data, BalanceTransaction.class);
    assertNotNull(resource);
    assertNotNull(resource.getId());
  }

  @Test
  public void testDeserializeExpansions() throws Exception {
    // TODO: Figure out why stripe-mock does not expand source when asked
    String data = getResourceAsString("/api_fixtures/balance_transaction_expansion.json");
    BalanceTransaction resource = APIResource.GSON.fromJson(data, BalanceTransaction.class);
    assertNotNull(resource);
    HasId source = resource.getSourceObject();
    assertNotNull(source);
    assertNotNull(source.getId());
  }
}
