package com.stripe.functional;

import static org.junit.Assert.assertNotNull;

import com.stripe.BaseStripeMockTest;
import com.stripe.exception.StripeException;
import com.stripe.model.BalanceTransaction;
import com.stripe.model.BalanceTransactionCollection;
import com.stripe.net.APIResource;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class BalanceTransactionTest extends BaseStripeMockTest {
  public static final String RESOURCE_ID = "bt_123";

  @Test
  public void testRetrieve() throws StripeException {
    BalanceTransaction resource = BalanceTransaction.retrieve(RESOURCE_ID);

    verifyRequest(
        APIResource.RequestMethod.GET,
        String.format("/v1/balance/history/%s", RESOURCE_ID)
    );
  }

  @Test
  public void testList() throws StripeException {
    Map<String, Object> listParams = new HashMap<String, Object>();
    listParams.put("limit", 1);

    BalanceTransactionCollection resources = BalanceTransaction.list(listParams);

    assertNotNull(resources);
    verifyRequest(
        APIResource.RequestMethod.GET,
        String.format("/v1/balance/history")
    );
  }
}
